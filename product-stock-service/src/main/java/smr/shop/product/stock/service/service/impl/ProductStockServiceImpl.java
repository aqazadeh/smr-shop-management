package smr.shop.product.stock.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.OrderMessageModel;
import smr.shop.libs.common.dto.message.ProductStockMessageModel;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.grpc.client.ShopGrpcClient;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopByShopIdGrpcRequest;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.product.stock.service.dto.request.CreateProductStockRequest;
import smr.shop.product.stock.service.dto.request.UpdateProductStockRequest;
import smr.shop.product.stock.service.dto.response.ProductStockResponse;
import smr.shop.product.stock.service.exception.ProductStockException;
import smr.shop.product.stock.service.grpc.client.ProductStockGrpcClientService;
import smr.shop.product.stock.service.helper.ProductStockServiceHelper;
import smr.shop.product.stock.service.mapper.ProductStockServiceMapper;
import smr.shop.product.stock.service.model.ProductStock;
import smr.shop.product.stock.service.repository.ProductStockRepository;
import smr.shop.product.stock.service.service.ProductStockService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ProductStockServiceImpl implements ProductStockService {
    private final ProductStockRepository productStockRepository;
    private final ProductStockServiceMapper productStockServiceMapper;
    private final ProductStockServiceHelper productStockServiceHelper;
    private final ShopGrpcClient shopGrpcClient;

    public ProductStockServiceImpl(ProductStockRepository productStockRepository,
                                   ProductStockServiceMapper productStockServiceMapper,
                                   ProductStockServiceHelper productStockServiceHelper,
                                   ProductStockGrpcClientService productStockGrpcClientService, ShopGrpcClient shopGrpcClient) {
        this.productStockRepository = productStockRepository;
        this.productStockServiceMapper = productStockServiceMapper;
        this.productStockServiceHelper = productStockServiceHelper;
        this.shopGrpcClient = shopGrpcClient;
    }

    @Override
    @Transactional
    public void create(CreateProductStockRequest request) {
        ProductStock productStock = productStockServiceMapper.createProductStockRequestToProductStockEntity(request);
        productStock.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        productStock.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        productStockRepository.save(productStock);
    }

    @Override
    @Transactional
    public void createAll(List<ProductStockMessageModel> productStockMessageModel) {
        List<ProductStock> productStocks = productStockMessageModel.stream()
                .map(request -> {
                    ProductStock productStock = productStockServiceMapper.productStockMessageModelToProductStockEntity(request);
                    productStock.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
                    return productStock;
                }).toList();
        productStockRepository.saveAll(productStocks);
    }

    @Override
    public ProductStockResponse getById(UUID id) {
        ProductStock productStock = productStockServiceHelper.findById(id);
        return productStockServiceMapper.productStockEntityToProductStockResponse(productStock);
    }

    @Override
    public List<ProductStockResponse> getByProductId(Long productId) {
        List<ProductStock> productStockList = productStockServiceHelper.getByProductId(productId);
        List<ProductStockResponse> productStockResponseList = productStockList.stream().map(productStockServiceMapper::productStockEntityToProductStockResponse).toList();
        return productStockResponseList;
    }

    @Override
    @Transactional
    public void delete(UUID stockId) {
        ProductStock productStock = productStockServiceHelper.findById(stockId);
        validateProductStock(productStock);
        productStockRepository.deleteById(stockId);
    }

    @Override
    @Transactional
    public void deleteByProductId(Long id) {
        List<ProductStock> productStockList = productStockServiceHelper.getByProductId(id);
        productStockRepository.deleteAll(productStockList);
    }

    @Override
    @Transactional
    public void update(UUID id, UpdateProductStockRequest request) {
        ProductStock productStock = productStockServiceHelper.findById(id);
        validateProductStock(productStock);
        productStockServiceMapper.updateProductStockRequestToProductStockEntity(productStock, request);
        productStockRepository.save(productStock);
    }

    @Override
    @Transactional
    public void updateQuantityDecrease(OrderMessageModel orderMessageModel) {
        List<ProductStock> productStockList = new ArrayList<>();
        orderMessageModel.getItems().forEach(item -> {
            ProductStock productStock = productStockServiceHelper.findById(item.getStockId());
            productStock.setQuantity(productStock.getQuantity() - item.getQuantity());
            productStockList.add(productStock);
        });
        productStockRepository.saveAll(productStockList);
    }

    @Override
    @Transactional
    public void updateQuantityIncrease(OrderMessageModel orderMessageModel) {
        List<ProductStock> productStockList = new ArrayList<>();
        orderMessageModel.getItems().forEach(item -> {
            ProductStock productStock = productStockServiceHelper.findById(item.getStockId());
            productStock.setQuantity(productStock.getQuantity() + item.getQuantity());
            productStockList.add(productStock);
        });
        productStockRepository.saveAll(productStockList);

    }

    private void validateProductStock(ProductStock productStock) {
        long productId = productStock.getProductId();
        ProductGrpcResponse productGrpcResponse = shopGrpcClient.getByProductId(productId);
        UUID userId = UserHelper.getUserId();
        long shopId = productGrpcResponse.getShopId();

        ShopByShopIdGrpcRequest shopByShopIdGrpcRequest = ShopByShopIdGrpcRequest.newBuilder()
                .setShopId(shopId)
                .build();
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByShopId(shopByShopIdGrpcRequest);

        if (!shopGrpcResponse.getUserId().equals(userId.toString())) {
            throw new ProductStockException("you dont have a permission delete this stock with id: " + productStock.getId(), HttpStatus.FORBIDDEN);
        }

    }
}