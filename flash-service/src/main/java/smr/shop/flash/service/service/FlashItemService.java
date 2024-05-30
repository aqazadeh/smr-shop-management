package smr.shop.flash.service.service;

import smr.shop.flash.service.dto.response.FlashItemResponse;
import smr.shop.libs.common.dto.message.ProductMessageModel;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 7:59 PM
 */

public interface FlashItemService {


    void addItem(Long flashId, Long productId);


    void deleteItem(Long flashId, UUID flashItemId);

    void deleteItems(Long flashId);


    List<FlashItemResponse> getItems(Long flashId, Integer page);

    //
    void deleteFlash(ProductMessageModel message);

}
