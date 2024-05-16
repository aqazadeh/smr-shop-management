package smr.shop.delivery.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.delivery.service.dto.request.DeliveryCreateRequest;
import smr.shop.delivery.service.dto.request.DeliveryUpdateRequest;
import smr.shop.delivery.service.dto.response.DeliveryResponse;
import smr.shop.delivery.service.model.Delivery;

import java.util.UUID;

@Component
public class DeliveryMapper {

    public Delivery toDelivery(DeliveryCreateRequest request) {
        Delivery.DeliveryBuilder builder = Delivery.builder();
        builder.courierId(request.getCourierId());
        builder.orderId(UUID.randomUUID());
        return builder.build();
    }

    public Delivery toUpdateDelivery(DeliveryUpdateRequest request, Delivery delivery) {
        if (request.getCourierId() != null) delivery.setCourierId(request.getCourierId());
        if (request.getOrderId() != null) delivery.setOrderId(request.getOrderId());
        if (request.getStatus() != null) delivery.setStatus(request.getStatus());
        return delivery;
    }

    public DeliveryResponse toDeliveryResponse(Delivery delivery) {
        return DeliveryResponse.builder()
                .id(delivery.getId())
                .courierId(delivery.getCourierId())
                .orderId(delivery.getOrderId())
                .status(delivery.getStatus())
                .build();
    }
}
