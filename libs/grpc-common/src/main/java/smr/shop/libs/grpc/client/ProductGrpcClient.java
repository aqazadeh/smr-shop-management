package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.shop.libs.grpc.object.ProductGrpcId;
import smr.shop.libs.grpc.object.ShopGrpcId;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.ProductServiceGrpc;

@Component
public class ProductGrpcClient {

    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    public ProductGrpcResponse getProductByProductId(Long productId) {
        ProductGrpcId request = ProductGrpcId.newBuilder().setId(productId).build();
//        return productServiceBlockingStub.getProductById(request);
        return ProductGrpcResponse.newBuilder()
                .setId(1)
                .setSlug("product-1")
                .setShopId(1)
                .setPrice(110)
                .setShippingPrice(3)
                .setName("Product 1")
                .setThumbnail("test-image")
                .setDiscount(DiscountGrpcResponse.newBuilder()
                        .setId("1d017d73-f356-4454-89d7-5123a6c26804")
                        .setAmount(5)
                        .build())
                .build();
    }


    public ProductGrpcResponse getProductByShopId(ShopGrpcId productGrpcRequest) {
//        return productServiceBlockingStub.getProductByShopId(productGrpcRequest);
        return ProductGrpcResponse.newBuilder()
                .setId(1)
                .setSlug("product-1")
                .setShopId(1)
                .setPrice(110)
                .setShippingPrice(3)
                .setName("Product 1")
                .setThumbnail("test-image")
                .setDiscount(DiscountGrpcResponse.newBuilder()
                        .setId("1d017d73-f356-4454-89d7-5123a6c26804")
                        .setAmount(5)
                        .build())
                .build();
    }
}
