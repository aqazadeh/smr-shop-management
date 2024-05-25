package smr.shop.discount.service.mapper;

import org.springframework.stereotype.Component;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.model.DiscountEntity;

@Component
public class DiscountServiceMapper {
    public DiscountEntity discountCreateRequestToDiscountEntity(DiscountCreateRequest request) {
        return DiscountEntity.builder()
                .amount(request.getAmount())
                .expireDate(request.getExpireDate())
                .build();

    }

    public DiscountResponse discountEntityToDiscountResponse(DiscountEntity discountEntity) {
        return DiscountResponse.builder()
                .id(discountEntity.getId())
                .amount(discountEntity.getAmount())
                .shopId(discountEntity.getShopId())
                .expireDate(discountEntity.getExpireDate())
                .build();
    }

    public DiscountGrpcResponse discountEntityToDiscountGrpcResponse(DiscountEntity discountEntity) {
        return DiscountGrpcResponse.newBuilder()
                .setId(discountEntity.getId().toString())
                .setAmount(discountEntity.getAmount())
                .setIsDeleted(discountEntity.getIsDeleted())
                .build();
    }
}
