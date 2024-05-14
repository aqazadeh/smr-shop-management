package smr.shop.courier.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.courier.service.dto.request.CourierCreateRequest;
import smr.shop.courier.service.dto.request.CourierUpdateRequest;
import smr.shop.courier.service.dto.response.CourierResponse;
import smr.shop.courier.service.model.CourierEntity;

import java.util.UUID;

@Component
public class CourierServiceMapper {
    public CourierEntity courierCreateRequestToCourier(CourierCreateRequest request) {
        CourierEntity.CourierEntityBuilder builder = CourierEntity.builder();
        builder.userId(request.getUserId());
        builder.imageId(UUID.randomUUID());
        builder.name(request.getName());
        builder.surname(request.getSurname());
        return builder.build();
    }

    public CourierEntity toUpdateCourier(CourierUpdateRequest request, CourierEntity courier) {
        if (request.getName() != null) courier.setName(request.getName());
        if (request.getSurname() != null) courier.setSurname(request.getSurname());
        if (request.getRating() != null) courier.setRating(request.getRating());
        if (request.getIsAccepted() != null) courier.setIsAccepted(request.getIsAccepted());
        return courier;
    }

    public CourierResponse toCourierResponse(CourierEntity courier) {
        return CourierResponse.builder()
                .id(courier.getId())
                .userId(courier.getUserId())
                .imageId(courier.getImageId())
                .name(courier.getName())
                .surname(courier.getSurname())
                .rating(courier.getRating())
                .activeType(courier.getActiveType())
                .isAccepted(courier.getIsAccepted())
                .build();
    }
}
