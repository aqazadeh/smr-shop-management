package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.discount.libs.grpc.product.discount.DiscountServiceGrpc;


@Component
public class DiscountGrpcClient {

    @GrpcClient("discount-service")
    public DiscountServiceGrpc.DiscountServiceBlockingStub discountServiceBlockingStub;

    public DiscountGrpcResponse getDiscountById(String discountId){
        DiscountGrpcResponse request = DiscountGrpcResponse.newBuilder().setId(discountId).build();
//        return categoryServiceBlockingStub.getCategoryById(request);

        return DiscountGrpcResponse.newBuilder()
                .setId("2a795490-e39d-45f5-8a74-a6fb5b70004b")
                .setAmount(12)
                .build();
    }
}
