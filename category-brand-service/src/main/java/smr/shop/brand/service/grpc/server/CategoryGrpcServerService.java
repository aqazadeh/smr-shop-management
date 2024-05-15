package smr.shop.brand.service.grpc.server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import smr.shop.brand.service.service.CategoryService;
import smr.shop.libs.grpc.category.CategoryGrpcRequest;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.category.CategoryServiceGrpc;


@GrpcService
public class CategoryGrpcServerService extends CategoryServiceGrpc.CategoryServiceImplBase {

    private final CategoryService categoryService;

    public CategoryGrpcServerService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void getCategory(CategoryGrpcRequest request, StreamObserver<CategoryGrpcResponse> responseObserver) {
        CategoryGrpcResponse categoryGrpcResponse = categoryService.getCategoryInformation(request);
        responseObserver.onNext(categoryGrpcResponse);
        responseObserver.onCompleted();
    }
}