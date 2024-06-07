package smr.shop.product.review.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class CreateProductReviewRequest {
    @NotBlank Byte rating;
    @NotBlank String comment;
    List<UUID> images;
}
