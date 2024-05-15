package smr.shop.product.review.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class ProductReviewResponse {
    UUID id;
    @JsonProperty("user_id") UUID userId;
    @JsonProperty("product_id") Long productId;
    Byte rating;
    String comment;
    List<String> images;
}
