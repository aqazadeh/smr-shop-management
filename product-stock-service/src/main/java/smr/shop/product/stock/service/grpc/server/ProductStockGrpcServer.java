package smr.shop.product.stock.service.grpc.server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import smr.shop.libs.grpc.object.ProductGrpcId;
import smr.shop.libs.grpc.object.ProductStockGrpcId;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockServiceGrpc;
import smr.shop.product.stock.service.service.ProductStockService;

import java.util.List;

@GrpcService
public class ProductStockGrpcServer extends ProductStockServiceGrpc.ProductStockServiceImplBase {
    private final ProductStockService productStockService;

    public ProductStockGrpcServer(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @Override
    public void getProductStockByProductId(ProductGrpcId request, StreamObserver<ProductStockGrpcResponse> responseObserver) {
        List<ProductStockGrpcResponse> responses = productStockService.getStocks(request);
        responses.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductStockId(ProductStockGrpcId request, StreamObserver<ProductStockGrpcResponse> responseObserver) {
        ProductStockGrpcResponse response = productStockService.getStock(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
