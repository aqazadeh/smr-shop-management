package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.object.ProductGrpcId;
import smr.shop.libs.grpc.object.ProductStockGrpcId;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockServiceGrpc;

import java.util.List;

@Component
public class ProductStockGrpcClient {

    @GrpcClient("product-stock-service")
    private ProductStockServiceGrpc.ProductStockServiceBlockingStub productStockServiceBlockingStub;

    public List<ProductStockGrpcResponse> getProductsStockByProductId(Long productId) {
        ProductGrpcId request = ProductGrpcId.newBuilder().setId(productId).build();
//        Iterator<ProductStockGrpcResponse> productStockGrpcResponseIterator = productStockServiceBlockingStub.getProductStockByProductId(request);
//        return GrpcHelper.iteratorToList(productStockGrpcResponseIterator);
        ProductStockGrpcResponse xl = ProductStockGrpcResponse.newBuilder()
                .setId("df0e1429-71fc-4932-825c-77849b4ccd19")
                .setProductId(1)
                .setName("xl")
                .setQuantity(100)
                .build();

        ProductStockGrpcResponse xs = ProductStockGrpcResponse.newBuilder()
                .setId("9c0e1429-sd44-4932-825c-77849b4ccd19")
                .setProductId(1)
                .setName("xs")
                .setQuantity(100)
                .build();
        return List.of(xl, xs);
    }

    public ProductStockGrpcResponse getProductByStockId(String stockId) {
        ProductStockGrpcId request = ProductStockGrpcId.newBuilder().build();
//        return productStockServiceBlockingStub.getProductStockId(request);
        return ProductStockGrpcResponse.newBuilder()
                .setId("df0e1429-71fc-4932-825c-77849b4ccd19")
                .setProductId(1)
                .setName("xl")
                .setQuantity(100)
                .build();
    }
}
