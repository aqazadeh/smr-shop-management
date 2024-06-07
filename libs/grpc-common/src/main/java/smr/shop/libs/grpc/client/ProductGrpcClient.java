package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.object.ProductGrpcId;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.ProductServiceGrpc;

@Component
public class ProductGrpcClient {

    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    public ProductGrpcResponse getProductByProductId(Long productId) {
        ProductGrpcId request = ProductGrpcId.newBuilder().setId(productId).build();
        return productServiceBlockingStub.getProductById(request);
    }
}
