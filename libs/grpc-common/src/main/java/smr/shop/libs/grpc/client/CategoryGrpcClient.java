package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.brand.BrandServiceGrpc;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.category.CategoryServiceGrpc;
import smr.shop.libs.grpc.object.BrandGrpcId;
import smr.shop.libs.grpc.object.CategoryGrpcId;

@Component
public class CategoryGrpcClient {

    @GrpcClient("category-service")
    public CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceBlockingStub;

    public CategoryGrpcResponse getCategoryById(Long categoryId){
        CategoryGrpcResponse request = CategoryGrpcResponse.newBuilder().setId(categoryId).build();
//        return categoryServiceBlockingStub.getCategoryById(request);

        return CategoryGrpcResponse.newBuilder()
                .setId(1)
                .setName("brand 1")
                .setSlug("brand-1")
                .build();
    }
}
