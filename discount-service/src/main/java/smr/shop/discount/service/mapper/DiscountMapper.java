package smr.shop.discount.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.request.DiscountUpdateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.model.DiscountEntity;

@Component
public class DiscountMapper {
    public DiscountEntity toDiscountResponse(DiscountCreateRequest request) {
        DiscountEntity.DiscountEntityBuilder builder = DiscountEntity.builder();
        builder.productId(request.getProductId());
        return builder.build();
    }

    public DiscountEntity toUpdateDiscount(DiscountUpdateRequest request, DiscountEntity discountEntity) {
        discountEntity.setProductId(request.getProductId());
        discountEntity.setType(request.getType());
        discountEntity.setAmount(request.getAmount());
        discountEntity.setPercent(request.getPercent());
        return discountEntity;
    }

    public DiscountResponse toDiscountResponse(DiscountEntity discountEntity) {
        return DiscountResponse.builder()
                .id(discountEntity.getId())
                .productId(discountEntity.getProductId())
                .type(discountEntity.getType())
                .amount(discountEntity.getAmount())
                .percent(discountEntity.getPercent())
                .build();
    }

}
