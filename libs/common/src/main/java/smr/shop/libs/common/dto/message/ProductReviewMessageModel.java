package smr.shop.libs.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smr.shop.libs.common.messaging.BaseMessageModel;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductReviewMessageModel implements BaseMessageModel {
    private UUID id;

    private UUID userId;

    private Long productId;

    private float rating;

    private String comment;

//    private List<String> images;
}
