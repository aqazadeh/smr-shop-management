package smr.shop.courier.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.courier.service.dto.request.CourierCreateRequest;
import smr.shop.courier.service.dto.request.CourierUpdateRequest;
import smr.shop.courier.service.dto.response.CourierResponse;
import smr.shop.courier.service.model.Courier;

import java.util.UUID;

@Component
public class CourierMapper {
    public Courier toCourier(CourierCreateRequest request) {
        // check image id is exists
        Courier.CourierBuilder builder = Courier.builder();
        builder.userId(request.getUserId());
        builder.imageId(UUID.randomUUID());
        builder.name(request.getName());
        builder.surname(request.getSurname());
        return builder.build();
    }

    public Courier toUpdateCourier(CourierUpdateRequest request, Courier courier) {
        courier.setName(request.getName());
        courier.setSurname(request.getSurname());
        courier.setRating(request.getRating());
        courier.setActiveType(request.getActiveType());
        courier.setIsAccepted(request.getIsAccepted());
        return courier;
    }

    public CourierResponse toCourierResponse(Courier courier) {
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
