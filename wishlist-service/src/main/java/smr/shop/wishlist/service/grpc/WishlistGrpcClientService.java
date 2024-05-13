package smr.shop.wishlist.service.grpc;

import smr.shop.libs.grpc.product.ProductGrpcResponse;

public interface WishlistGrpcClientService {
    ProductGrpcResponse getProduct(Long productId);
}
