package smr.shop.flash.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 7:44 PM
 */
@Value
public class FlashDealProductUpdateRequest {

    @NotBlank
    Long flashDealEntityId;

    @NotBlank
    Long productId;
}
