package smr.shop.product.review.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateProductQuestionRequest {
    @JsonProperty("user_id") UUID userId;
    @JsonProperty("product_id") Long productId;
    @NotBlank String question;
    @JsonProperty("question_id") UUID questionId;
}
