package smr.shop.discount.service.service;

import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.model.DiscountEntity;
import smr.shop.libs.grpc.object.DiscountGrpcId;

import java.util.List;
import java.util.UUID;

public interface DiscountService {

    void createDiscount(Long productId, DiscountCreateRequest request);

    void deleteDiscount(UUID discountId);

    DiscountResponse getDiscountById(UUID discountId);

    DiscountEntity findById(UUID discountId);

    DiscountGrpcResponse getDiscountById(DiscountGrpcId request);

    List<DiscountResponse> getShopDiscounts(Long shopId, Integer page);
}
