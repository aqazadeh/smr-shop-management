package smr.shop.product.stock.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.OrderMessageModel;
import smr.shop.libs.common.dto.message.ProductStockMessageModel;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.grpc.client.ProductGrpcClient;
import smr.shop.libs.grpc.client.ShopGrpcClient;
import smr.shop.libs.grpc.object.ProductGrpcId;
import smr.shop.libs.grpc.object.ProductStockGrpcId;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.product.stock.service.dto.request.CreateProductStockRequest;
import smr.shop.product.stock.service.dto.request.UpdateProductStockRequest;
import smr.shop.product.stock.service.dto.response.ProductStockResponse;
import smr.shop.product.stock.service.exception.ProductStockServiceException;
import smr.shop.product.stock.service.mapper.ProductStockServiceMapper;
import smr.shop.product.stock.service.model.ProductStockEntity;
import smr.shop.product.stock.service.repository.ProductStockRepository;
import smr.shop.product.stock.service.service.ProductStockService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ProductStockServiceImpl implements ProductStockService {

    // repository
    private final ProductStockRepository productStockRepository;

    // mappers
    private final ProductStockServiceMapper productStockServiceMapper;

    // rpc
    private final ShopGrpcClient shopGrpcClient;
    private final ProductGrpcClient productGrpcClient;

    public ProductStockServiceImpl(ProductStockRepository productStockRepository,
                                   ProductStockServiceMapper productStockServiceMapper,
                                   ShopGrpcClient shopGrpcClient,
                                   ProductGrpcClient productGrpcClient) {
        this.productStockRepository = productStockRepository;
        this.productStockServiceMapper = productStockServiceMapper;
        this.shopGrpcClient = shopGrpcClient;
        this.productGrpcClient = productGrpcClient;
    }


    @Override
    @Transactional
    public void create(CreateProductStockRequest request) {
        ProductStockEntity productStockEntity = productStockServiceMapper.createProductStockRequestToProductStockEntity(request);
        productStockEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        productStockEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        productStockRepository.save(productStockEntity);
    }

    @Override
    @Transactional
    public void createAll(List<ProductStockMessageModel> productStockMessageModel) {
        List<ProductStockEntity> productStockEntities = productStockMessageModel.stream()
                .map(request -> {
                    ProductStockEntity productStockEntity = productStockServiceMapper.productStockMessageModelToProductStockEntity(request);
                    productStockEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
                    return productStockEntity;
                }).toList();
        productStockRepository.saveAll(productStockEntities);
    }

    @Override
    public ProductStockResponse getById(UUID id) {
        ProductStockEntity productStockEntity = findById(id);
        return productStockServiceMapper.productStockEntityToProductStockResponse(productStockEntity);
    }

    @Override
    public List<ProductStockResponse> getByProductId(Long productId) {
        List<ProductStockEntity> productStockEntityList = findByProductId(productId);
        return productStockEntityList.stream().map(productStockServiceMapper::productStockEntityToProductStockResponse).toList();
    }

    @Override
    public List<ProductStockGrpcResponse> getStocks(ProductGrpcId request) {
        List<ProductStockEntity> productStockEntityList = findByProductId(request.getId());
        return productStockEntityList.stream().map(productStockServiceMapper::productStockEntityToProductStockGrpcResponse).toList();
    }

    @Override
    public ProductStockGrpcResponse getStock(ProductStockGrpcId request) {
        ProductStockEntity productStockEntity = findById(UUID.fromString(request.getId()));
        return productStockServiceMapper.productStockEntityToProductStockGrpcResponse(productStockEntity);
    }

    @Override
    @Transactional
    public void delete(UUID stockId) {
        ProductStockEntity productStockEntity = findById(stockId);
        validateProductStock(productStockEntity);
        productStockRepository.deleteById(stockId);
    }

    @Override
    @Transactional
    public void deleteByProductId(Long id) {
        List<ProductStockEntity> productStockEntityList = findByProductId(id);
        productStockRepository.deleteAll(productStockEntityList);
    }

    @Override
    @Transactional
    public void update(UUID id, UpdateProductStockRequest request) {
        ProductStockEntity productStockEntity = findById(id);
        validateProductStock(productStockEntity);
        productStockServiceMapper.updateProductStockRequestToProductStockEntity(productStockEntity, request);
        productStockRepository.save(productStockEntity);
    }

    @Override
    @Transactional
    public void updateQuantityDecrease(OrderMessageModel orderMessageModel) {
        List<ProductStockEntity> productStockEntityList = new ArrayList<>();
        orderMessageModel.getItems().forEach(item -> {
            ProductStockEntity productStockEntity = findById(item.getStockId());
            productStockEntity.setQuantity(productStockEntity.getQuantity() - item.getQuantity());
            productStockEntityList.add(productStockEntity);
        });
        productStockRepository.saveAll(productStockEntityList);
    }

    @Override
    @Transactional
    public void updateQuantityIncrease(OrderMessageModel orderMessageModel) {
        List<ProductStockEntity> productStockEntityList = new ArrayList<>();
        orderMessageModel.getItems().forEach(item -> {
            ProductStockEntity productStockEntity = findById(item.getStockId());
            productStockEntity.setQuantity(productStockEntity.getQuantity() + item.getQuantity());
            productStockEntityList.add(productStockEntity);
        });
        productStockRepository.saveAll(productStockEntityList);

    }

    @Override
    public ProductStockEntity findById(UUID id) {
        return productStockRepository.findById(id).orElseThrow(() ->
                new ProductStockServiceException("Product Stock not found!", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<ProductStockEntity> findByProductId(Long productId) {
        return productStockRepository.findAllByProductId(productId);
    }

    private void validateProductStock(ProductStockEntity productStockEntity) {
        long productId = productStockEntity.getProductId();
        ProductGrpcResponse productGrpcResponse = productGrpcClient.getProductByProductId(productId);
        long shopId = productGrpcResponse.getShopId();
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByShopId(shopId);

        UUID userId = UserHelper.getUserId();
        if (!shopGrpcResponse.getUserId().equals(userId.toString())) {
            throw new ProductStockServiceException("you dont have a permission delete this stock with id: " + productStockEntity.getId(), HttpStatus.FORBIDDEN);
        }

    }
}