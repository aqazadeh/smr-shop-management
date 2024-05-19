package smr.shop.product.stock.service.grpc.client.impl;

import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.product.stock.service.grpc.client.ProductStockGrpcClientService;

@Component
public class ProductStockGrpcClientServiceImpl implements ProductStockGrpcClientService {
    @Override
    public ProductGrpcResponse getByProductId(Long productId) {
        return null;
    }

    @Override
    public ShopGrpcResponse getShopByShopId(Long shopId) {
        return null;
    }
}
