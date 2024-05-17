package smr.shop.shop.service.grpc.client.impl;

import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.shop.service.grpc.client.UploadGrpcServiceClient;

@Component
public class UploadGrpcServiceClientImpl implements UploadGrpcServiceClient {
    @Override
    public UploadGrpcResponse getImageById(String string) {

        return UploadGrpcResponse.newBuilder().setUrl("http://image.com").setId("e3209d3a-b016-4ca2-a346-c21aa3243cc9").build();
//        return null;
    }
}
