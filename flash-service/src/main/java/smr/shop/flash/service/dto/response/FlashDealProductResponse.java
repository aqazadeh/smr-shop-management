package smr.shop.flash.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 7:40 PM
 */
@Value
@Builder
public class FlashDealProductResponse {

    UUID id;

    Long flashDealEntityId;

    Long productId;
}
