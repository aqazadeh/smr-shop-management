package smr.shop.flash.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.flash.service.dto.response.FlashItemResponse;
import smr.shop.flash.service.exception.FlashServiceException;
import smr.shop.flash.service.mapper.FlashServiceMapper;
import smr.shop.flash.service.model.FlashItemEntity;
import smr.shop.flash.service.repository.FlashItemRepository;
import smr.shop.flash.service.service.FlashItemService;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.ProductMessageModel;
import smr.shop.libs.grpc.client.ProductGrpcClient;
import smr.shop.libs.grpc.product.ProductGrpcResponse;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 8:00 PM
 */

@Service
public class FlashItemServiceImpl implements FlashItemService {
    private final FlashItemRepository flashItemRepository;
    private final FlashServiceMapper flashServiceMapper;

    private final ProductGrpcClient productGrpcClient;

    public FlashItemServiceImpl(FlashItemRepository flashItemRepository,
                                FlashServiceMapper flashServiceMapper,
                                ProductGrpcClient productGrpcClient) {
        this.flashItemRepository = flashItemRepository;
        this.flashServiceMapper = flashServiceMapper;
        this.productGrpcClient = productGrpcClient;
    }


//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void addItem(Long flashId, Long productId) {
        ProductGrpcResponse productGrpcResponse = productGrpcClient.getProductByProductId(productId);

        Optional<FlashItemEntity> isExists = flashItemRepository.findByFlashIdAndProductId(flashId, productId);
        if (isExists.isPresent()) {
            throw new FlashServiceException("product: %s already have in flash with id: %s".formatted(productId, flashId), HttpStatus.BAD_REQUEST);
        }

        FlashItemEntity flashItemEntity = new FlashItemEntity().builder()
                .id(UUID.randomUUID())
                .flashId(flashId)
                .productId(productGrpcResponse.getId())
                .createdAt(ZonedDateTime.now(ServiceConstants.ZONE_ID))
                .updatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID))
                .build();

        flashItemRepository.save(flashItemEntity);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    public void deleteItem(Long flashId, UUID flashItemId) {
        FlashItemEntity flashItemEntity = findById(flashItemId);
        if (!flashItemEntity.getFlashId().equals(flashId)) {
            throw new FlashServiceException("item: %s not found in flashId: %s".formatted(flashItemId, flashId), HttpStatus.BAD_REQUEST);
        }

        flashItemRepository.delete(flashItemEntity);
    }

    @Override
    @Transactional
    public void deleteItems(Long flashId) {
        flashItemRepository.deleteByFlashId(flashId);
    }

    @Override
    @Transactional
    public void deleteFlash(ProductMessageModel message) {
        flashItemRepository.deleteByProductId(message.getId());
    }


    //    ----------------------------------- Get -----------------------------------
    @Override
    public List<FlashItemResponse> getItems(Long flashId, Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<FlashItemEntity> flashItemEntityList = flashItemRepository.findByFlashId(flashId, pageable).stream().toList();
        return flashServiceMapper.flashItemEntityToFlashItemResponse(flashItemEntityList);
    }


//    ----------------------------------- Extra -----------------------------------

    private FlashItemEntity findById(UUID flashItemId) {
        return flashItemRepository.findById(flashItemId)
                .orElseThrow(() -> new FlashServiceException("item not found with id: " + flashItemId, HttpStatus.BAD_REQUEST));
    }
}
