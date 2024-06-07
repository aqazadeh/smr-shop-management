package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.category.CategoryServiceGrpc;
import smr.shop.libs.grpc.object.CategoryGrpcId;

@Component
public class CategoryGrpcClient {

    @GrpcClient("category-brand-service")
    public CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceBlockingStub;

    public CategoryGrpcResponse getCategoryById(Long categoryId){
        CategoryGrpcId request = CategoryGrpcId.newBuilder().setId(categoryId).build();
        return categoryServiceBlockingStub.getCategoryById(request);
    }
}
