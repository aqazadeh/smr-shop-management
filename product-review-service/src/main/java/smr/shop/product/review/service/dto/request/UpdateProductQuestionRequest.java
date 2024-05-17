package smr.shop.product.review.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.UUID;

@Value
public class UpdateProductQuestionRequest {
    @JsonProperty("user_id") UUID userId;
    @JsonProperty("product_id") Long productId;
    String question;
    @JsonProperty("question_id") UUID questionId;
}
