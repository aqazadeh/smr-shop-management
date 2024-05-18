package smr.shop.product.stock.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.dto.message.OrderMessageModel;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
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

import java.util.List;
import java.util.UUID;


@Service
public class ProductStockServiceImpl implements ProductStockService {
    private final ProductStockRepository productStockRepository;
    private final ProductStockServiceMapper productStockServiceMapper;
    private final ProductStockServiceHelper productStockServiceHelper;
    private final ProductStockGrpcClientService productStockGrpcClientService;

    public ProductStockServiceImpl(ProductStockRepository productStockRepository,
                                   ProductStockServiceMapper productStockServiceMapper,
                                   ProductStockServiceHelper productStockServiceHelper,
                                   ProductStockGrpcClientService productStockGrpcClientService) {
        this.productStockRepository = productStockRepository;
        this.productStockServiceMapper = productStockServiceMapper;
        this.productStockServiceHelper = productStockServiceHelper;
        this.productStockGrpcClientService = productStockGrpcClientService;
    }

    @Override
    public void create(CreateProductStockRequest request) {
        ProductStock productStock = productStockServiceMapper.createProductStockRequestToProductStockEntity(request);
        productStockRepository.save(productStock);
    }

    @Override
    public void createAll(List<CreateProductStockRequest> productStockRequests) {
        List<ProductStock> productStocks = productStockRequests.stream()
                .map(productStockServiceMapper::createProductStockRequestToProductStockEntity).toList();
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
    public void delete(UUID stockId) {
        ProductStock productStock = productStockServiceHelper.findById(stockId);
        validateProductStock(productStock);
        productStockRepository.deleteById(stockId);
    }

    @Override
    public void deleteByProductId(Long id) {
        List<ProductStock> productStockList = productStockServiceHelper.getByProductId(id);
        productStockRepository.deleteAll(productStockList);
    }

    @Override
    public void update(UUID id, UpdateProductStockRequest request) {
        ProductStock productStock = productStockServiceHelper.findById(id);
        validateProductStock(productStock);
        productStockServiceMapper.updateProductStockRequestToProductStockEntity(productStock, request);
        productStockRepository.save(productStock);
    }

    @Override
    public void updateQuantity(OrderMessageModel orderMessageModel) {
        //use only kafka
        ProductStock productStock = productStockServiceHelper.findById(stockId);
        productStock.setQuantity(quantity);
        productStockRepository.save(productStock);
    }

    private void validateProductStock(ProductStock productStock) {
        Long productId = productStock.getProductId();
        ProductGrpcResponse productGrpcResponse = productStockGrpcClientService.getByProductId(productId);
        UUID userId = UserHelper.getUserId();
        Long shopId = productGrpcResponse.getShopId();
        ShopGrpcResponse shopGrpcResponse = productStockGrpcClientService.getShopByShopId(shopId);
        if (!shopGrpcResponse.getUserId().equals(userId.toString())) {
            throw new ProductStockException("you dont have a permission delete this stock with id: " + productStock.getId(), HttpStatus.FORBIDDEN);
        }
    }
}