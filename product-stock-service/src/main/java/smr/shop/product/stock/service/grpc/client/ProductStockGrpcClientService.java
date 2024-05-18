package smr.shop.product.stock.service.grpc.client;

import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;

public interface ProductStockGrpcClientService {
    ProductGrpcResponse getByProductId(Long productId);

    ShopGrpcResponse getShopByShopId(Long shopId);
}
