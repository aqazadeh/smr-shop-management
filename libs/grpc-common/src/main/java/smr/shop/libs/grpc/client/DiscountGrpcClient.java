package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.discount.libs.grpc.product.discount.DiscountServiceGrpc;
import smr.shop.libs.grpc.object.DiscountGrpcId;


@Component
public class DiscountGrpcClient {

    @GrpcClient("discount-service")
    public DiscountServiceGrpc.DiscountServiceBlockingStub discountServiceBlockingStub;

    public DiscountGrpcResponse getDiscountById(String discountId){
        DiscountGrpcId request = DiscountGrpcId.newBuilder().setId(discountId).build();
        return discountServiceBlockingStub.getDiscountById(request);
    }
}
