package smr.shop.product.review.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ProductQuestionResponse {
    UUID id;

    @JsonProperty("user_id")
    UUID userId;

    @JsonProperty("product_id")
    Long productId;

    String question;

    @JsonProperty("question_id")
    UUID questionId;
}
