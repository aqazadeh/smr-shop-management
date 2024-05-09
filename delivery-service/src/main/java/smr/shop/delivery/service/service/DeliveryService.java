package smr.shop.delivery.service.service;

import smr.shop.delivery.service.dto.request.DeliveryCreateRequest;
import smr.shop.delivery.service.dto.request.DeliveryUpdateRequest;
import smr.shop.delivery.service.dto.request.UpdateDeliveryStatusRequest;
import smr.shop.delivery.service.dto.response.DeliveryResponse;
import smr.shop.delivery.service.model.Delivery;
import smr.shop.delivery.service.model.valueobject.DeliveryStatus;

import java.util.List;

public interface DeliveryService {
    DeliveryResponse createDelivery(DeliveryCreateRequest request);
    DeliveryResponse updateDelivery(Long id, DeliveryUpdateRequest request);
    void deleteDelivery(Long id);
    List<DeliveryResponse> getAllDelivery(Integer page);
    DeliveryResponse getDeliveryById(Long id);
    DeliveryResponse updateDeliveryStatus(Long id, UpdateDeliveryStatusRequest request);

    Delivery findById(Long id);
}
