package smr.shop.delivery.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.delivery.service.dto.request.DeliveryCreateRequest;
import smr.shop.delivery.service.dto.request.DeliveryUpdateRequest;
import smr.shop.delivery.service.dto.request.UpdateDeliveryStatusRequest;
import smr.shop.delivery.service.dto.response.DeliveryResponse;
import smr.shop.delivery.service.exception.DeliveryException;
import smr.shop.delivery.service.mapper.DeliveryMapper;
import smr.shop.delivery.service.model.Delivery;
import smr.shop.delivery.service.repository.DeliveryRepository;
import smr.shop.delivery.service.service.DeliveryService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryMapper deliveryMapper;
    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryMapper deliveryMapper, DeliveryRepository deliveryRepository) {
        this.deliveryMapper = deliveryMapper;
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public DeliveryResponse createDelivery(DeliveryCreateRequest request) {
        Delivery deliveryResponse = deliveryMapper.toDeliveryResponse(request);
        deliveryResponse = deliveryRepository.save(deliveryResponse);
        return deliveryMapper.toDeliveryResponse(deliveryResponse);
    }

    @Override
    public DeliveryResponse updateDelivery(Long id, DeliveryUpdateRequest request) {
        Delivery delivery = findById(id);
        Delivery updateDelivery = deliveryMapper.toUpdateDelivery(request, delivery);
        delivery = deliveryRepository.save(updateDelivery);
        return deliveryMapper.toDeliveryResponse(delivery);
    }

    @Override
    public void deleteDelivery(Long id) {
        Delivery delivery = findById(id);
        deliveryRepository.delete(delivery);
    }

    @Override
    public List<DeliveryResponse> getAllDelivery(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return deliveryRepository.findAll(pageable).map(deliveryMapper::toDeliveryResponse).toList();
    }

    @Override
    public DeliveryResponse getDeliveryById(Long id) {
        Delivery delivery = findById(id);
        return deliveryMapper.toDeliveryResponse(delivery);
    }

    @Override
    public DeliveryResponse updateDeliveryStatus(Long id, UpdateDeliveryStatusRequest request) {
        Delivery delivery = findById(id);
        delivery.setStatus(request.getStatus());
        delivery = deliveryRepository.save(delivery);
        return deliveryMapper.toDeliveryResponse(delivery);
    }

    @Override
    public Delivery findById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryException("Delivery Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
