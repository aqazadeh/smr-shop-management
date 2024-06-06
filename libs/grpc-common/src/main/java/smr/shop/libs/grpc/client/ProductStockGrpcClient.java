package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.helper.GrpcHelper;
import smr.shop.libs.grpc.object.ProductGrpcId;
import smr.shop.libs.grpc.object.ProductStockGrpcId;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockServiceGrpc;

import java.util.Iterator;
import java.util.List;

@Component
public class ProductStockGrpcClient {

    @GrpcClient("product-stock-service")
    private ProductStockServiceGrpc.ProductStockServiceBlockingStub productStockServiceBlockingStub;

    public List<ProductStockGrpcResponse> getProductsStockByProductId(Long productId) {
        ProductGrpcId request = ProductGrpcId.newBuilder().setId(productId).build();
        Iterator<ProductStockGrpcResponse> productStockGrpcResponseIterator =
                productStockServiceBlockingStub.getProductStockByProductId(request);
        return GrpcHelper.iteratorToList(productStockGrpcResponseIterator);
    }

    public ProductStockGrpcResponse getProductByStockId(String stockId) {
        ProductStockGrpcId request = ProductStockGrpcId.newBuilder().build();
        return productStockServiceBlockingStub.getProductStockId(request);
    }
}
