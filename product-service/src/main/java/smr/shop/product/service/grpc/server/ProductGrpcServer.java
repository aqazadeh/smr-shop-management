package smr.shop.product.service.grpc.server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import smr.shop.libs.grpc.object.ProductGrpcId;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.ProductServiceGrpc;
import smr.shop.libs.grpc.product.ShopProductGrpcId;
import smr.shop.product.service.service.ProductService;

@GrpcService
public class ProductGrpcServer extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    public ProductGrpcServer(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void getProductById(ProductGrpcId request, StreamObserver<ProductGrpcResponse> responseObserver) {
        ProductGrpcResponse response = productService.getProductInformation(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductByShopId(ShopProductGrpcId request, StreamObserver<ProductGrpcResponse> responseObserver) {
        ProductGrpcResponse response = productService.getProductInformation(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
