package smr.shop.wishlist.service.grpc.impl;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.product.ProductGrpcRequest;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.ProductServiceGrpc;
import smr.shop.wishlist.service.grpc.WishlistGrpcClientService;

@Component
@Slf4j
public class WishlistGrpcClientServiceImpl implements WishlistGrpcClientService {

    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    @Override
    public ProductGrpcResponse getProduct(Long productId) {
        ProductGrpcRequest request = ProductGrpcRequest.newBuilder()
                .setProductId(productId)
                .build();
        return productServiceStub.getProductInfo(request);
    }
}
