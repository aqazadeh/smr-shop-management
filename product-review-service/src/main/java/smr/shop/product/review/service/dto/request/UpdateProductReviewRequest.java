package smr.shop.product.review.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class UpdateProductReviewRequest {
    @JsonProperty("user_id") UUID userId;
    @JsonProperty("product_id") Long productId;
    Byte rating;
    String comment;
    List<String> images;
}
