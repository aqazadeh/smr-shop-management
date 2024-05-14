package smr.shop.courier.service.service;

import smr.shop.courier.service.dto.request.CourierCreateRequest;
import smr.shop.courier.service.dto.request.CourierUpdateRequest;
import smr.shop.courier.service.dto.request.UpdateCourierActiveTypeRequest;
import smr.shop.courier.service.dto.response.CourierResponse;
import smr.shop.courier.service.model.CourierEntity;

import java.util.List;

public interface CourierService {
    CourierResponse createCourier(CourierCreateRequest request);

    CourierResponse updateCourier(Long id, CourierUpdateRequest request);

    void deleteCourier(Long id);

    List<CourierResponse> getAllCourier(Integer page);

    CourierResponse getCourierById(Long id);

    CourierResponse updateCourierActiveType(Long id, UpdateCourierActiveTypeRequest request);

    CourierEntity findById(Long id);
}
