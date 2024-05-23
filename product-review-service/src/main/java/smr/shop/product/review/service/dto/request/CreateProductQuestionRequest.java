package smr.shop.product.review.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateProductQuestionRequest {

    @NotBlank
    String question;

    @JsonProperty("question_id")
    @NotNull
    UUID questionId;
}
