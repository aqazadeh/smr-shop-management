package smr.shop.delivery.service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.delivery.service.dto.request.DeliveryCreateRequest;
import smr.shop.delivery.service.dto.request.DeliveryUpdateRequest;
import smr.shop.delivery.service.dto.request.UpdateDeliveryStatusRequest;
import smr.shop.delivery.service.dto.response.DeliveryResponse;
import smr.shop.delivery.service.exception.DeliveryServiceException;
import smr.shop.delivery.service.mapper.DeliveryServiceMapper;
import smr.shop.delivery.service.model.Delivery;
import smr.shop.delivery.service.model.valueobject.DeliveryStatus;
import smr.shop.delivery.service.repository.DeliveryRepository;
import smr.shop.delivery.service.service.DeliveryService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryServiceMapper deliveryServiceMapper;
    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryServiceMapper deliveryServiceMapper, DeliveryRepository deliveryRepository) {
        this.deliveryServiceMapper = deliveryServiceMapper;
        this.deliveryRepository = deliveryRepository;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void createDelivery(DeliveryCreateRequest request) {
        Delivery deliveryResponse = deliveryServiceMapper.toDelivery(request);
        deliveryResponse.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        deliveryResponse.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        deliveryResponse = deliveryRepository.save(deliveryResponse);
        deliveryServiceMapper.toDeliveryResponse(deliveryResponse);
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    public void updateDelivery(Long id, DeliveryUpdateRequest request) {
        Delivery delivery = findById(id);
        Delivery updateDelivery = deliveryServiceMapper.toUpdateDelivery(request, delivery);
        delivery.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        deliveryRepository.save(updateDelivery);
    }

    @Override
    @Transactional
    public DeliveryResponse updateDeliveryStatus(Long id, UpdateDeliveryStatusRequest request) {
        Delivery delivery = findById(id);
        delivery.setStatus(request.getStatus());
        delivery.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        delivery = deliveryRepository.save(delivery);
        return deliveryServiceMapper.toDeliveryResponse(delivery);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    public void deleteDelivery(Long id) {
        Delivery delivery = findById(id);
        delivery.setStatus(DeliveryStatus.FAILED);
        delivery.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        deliveryRepository.save(delivery);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public List<DeliveryResponse> getAllDelivery(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return deliveryRepository.findAll(pageable).map(deliveryServiceMapper::toDeliveryResponse).toList();
    }

    @Override
    public DeliveryResponse getDeliveryById(Long id) {
        Delivery delivery = findById(id);
        return deliveryServiceMapper.toDeliveryResponse(delivery);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public Delivery findById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryServiceException("Delivery Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
