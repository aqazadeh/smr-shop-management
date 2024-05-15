package smr.shop.product.review.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.util.List;

@Value
public class CreateProductReviewRequest {
    @NotBlank @JsonProperty("user_id") UUID userId;
    @NotBlank @JsonProperty("product_id") Long productId;
    @NotBlank Byte rating;
    @NotBlank String comment;
    List<String> images;
}
