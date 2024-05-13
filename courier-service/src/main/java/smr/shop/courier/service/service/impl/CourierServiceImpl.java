package smr.shop.courier.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.courier.service.dto.request.CourierCreateRequest;
import smr.shop.courier.service.dto.request.CourierUpdateRequest;
import smr.shop.courier.service.dto.request.UpdateCourierActiveTypeRequest;
import smr.shop.courier.service.dto.response.CourierResponse;
import smr.shop.courier.service.exception.CourierException;
import smr.shop.courier.service.mapper.CourierMapper;
import smr.shop.courier.service.model.Courier;
import smr.shop.courier.service.repository.CourierRepository;
import smr.shop.courier.service.service.CourierService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.util.List;

@Service
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final CourierMapper courierMapper;

    public CourierServiceImpl(CourierRepository courierRepository, CourierMapper courierMapper) {
        this.courierRepository = courierRepository;
        this.courierMapper = courierMapper;
    }

    @Override
    public CourierResponse createCourier(CourierCreateRequest request) {
        Courier courier = courierMapper.toCourier(request);
        courier = courierRepository.save(courier);
        return courierMapper.toCourierResponse(courier);
    }

    @Override
    public CourierResponse updateCourier(Long id, CourierUpdateRequest request) {
        Courier courier = findById(id);
        Courier updateCourier = courierMapper.toUpdateCourier(request, courier);
        courier = courierRepository.save(updateCourier);
        return courierMapper.toCourierResponse(courier);
    }

    @Override
    public void deleteCourier(Long id) {
        Courier courier = findById(id);
        courierRepository.delete(courier);
    }

    @Override
    public List<CourierResponse> getAllCourier(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return courierRepository.findAll(pageable).map(courierMapper::toCourierResponse).toList();
    }

    @Override
    public CourierResponse getCourierById(Long id) {
        Courier courier = findById(id);
        return courierMapper.toCourierResponse(courier);
    }

    @Override
    public CourierResponse updateCourierActiveType(Long id, UpdateCourierActiveTypeRequest request) {
        Courier courier = findById(id);
        courier.setActiveType(request.getStatus());
        courier = courierRepository.save(courier);
        return courierMapper.toCourierResponse(courier);
    }

    @Override
    public Courier findById(Long id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new CourierException("Courier Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
