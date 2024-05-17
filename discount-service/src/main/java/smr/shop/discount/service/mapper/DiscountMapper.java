package smr.shop.discount.service.mapper;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;
import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.request.DiscountUpdateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.model.DiscountEntity;
import smr.shop.discount.service.model.valueobject.DiscountType;

@Component
public class DiscountMapper {
    public DiscountEntity discountCreateRequestToDiscountEntity(DiscountCreateRequest request) {
        DiscountEntity.DiscountEntityBuilder builder = DiscountEntity.builder();
        builder.productId(request.getProductId());
        builder.type(request.getType());
        builder.percent(request.getPercent());
        builder.amount(request.getAmount());
        return builder.build();

    }

    public DiscountEntity discountUpdateRequestToDiscountEntity(DiscountUpdateRequest request, DiscountEntity discountEntity) {
        discountEntity.setProductId(request.getProductId());
        discountEntity.setType(request.getType());
        discountEntity.setAmount(request.getAmount());
        discountEntity.setPercent(request.getPercent());
        return discountEntity;
    }

    public DiscountResponse discountEntityToDiscountResponse(DiscountEntity discountEntity) {
        return DiscountResponse.builder()
                .id(discountEntity.getId())
                .productId(discountEntity.getProductId())
                .type(discountEntity.getType())
                .amount(discountEntity.getAmount())
                .percent(discountEntity.getPercent())
                .build();
    }

}
