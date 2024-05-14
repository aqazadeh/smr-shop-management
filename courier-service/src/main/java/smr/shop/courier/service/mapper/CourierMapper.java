package smr.shop.courier.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.courier.service.dto.request.CourierCreateRequest;
import smr.shop.courier.service.dto.request.CourierUpdateRequest;
import smr.shop.courier.service.dto.response.CourierResponse;
import smr.shop.courier.service.model.CourierEntity;

import java.util.UUID;

@Component
public class CourierMapper {
    public CourierEntity courierCreateRequestToCourier(CourierCreateRequest request) {
        return CourierEntity.builder()
                .userId(request.getUserId())
                .imageId(UUID.randomUUID())
                .name(request.getName())
                .surname(request.getSurname())
                .build();
    }

    public CourierEntity courierUpdateRequestToUpdateCourier(CourierUpdateRequest request, CourierEntity courierEntity) {
        courierEntity.setName(request.getName());
        courierEntity.setSurname(request.getSurname());
        courierEntity.setRating(request.getRating());
        courierEntity.setActiveType(request.getActiveType());
        courierEntity.setIsAccepted(request.getIsAccepted());
        return courierEntity;
    }

    public CourierResponse courierEntitytoCourierResponse(CourierEntity courierEntity) {
        return CourierResponse.builder()
                .id(courierEntity.getId())
                .userId(courierEntity.getUserId())
                .imageId(courierEntity.getImageId())
                .name(courierEntity.getName())
                .surname(courierEntity.getSurname())
                .rating(courierEntity.getRating())
                .activeType(courierEntity.getActiveType())
                .isAccepted(courierEntity.getIsAccepted())
                .build();
    }
}
