package smr.shop.category.brand.service.grpc.server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import smr.shop.category.brand.service.service.CategoryService;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.category.CategoryServiceGrpc;
import smr.shop.libs.grpc.object.CategoryGrpcId;


@GrpcService
public class CategoryGrpcServer extends CategoryServiceGrpc.CategoryServiceImplBase {

    private final CategoryService categoryService;

    public CategoryGrpcServer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void getCategoryById(CategoryGrpcId request, StreamObserver<CategoryGrpcResponse> responseObserver) {
        CategoryGrpcResponse categoryGrpcResponse = categoryService.getCategoryInformation(request);
        responseObserver.onNext(categoryGrpcResponse);
        responseObserver.onCompleted();
    }
}