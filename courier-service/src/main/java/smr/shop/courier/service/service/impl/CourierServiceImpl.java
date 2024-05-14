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
import smr.shop.courier.service.model.CourierEntity;
import smr.shop.courier.service.repository.CourierRepository;
import smr.shop.courier.service.service.CourierService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        CourierEntity courierEntity = courierMapper.courierCreateRequestToCourier(request);
        courierEntity.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        courierEntity.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        courierEntity = courierRepository.save(courierEntity);
        return courierMapper.courierEntitytoCourierResponse(courierEntity);
    }

    @Override
    public CourierResponse updateCourier(Long id, CourierUpdateRequest request) {
        CourierEntity courierEntity = findById(id);
        CourierEntity updateCourierEntity = courierMapper.courierUpdateRequestToUpdateCourier(request, courierEntity);
        courierEntity.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        courierEntity = courierRepository.save(updateCourierEntity);
        return courierMapper.courierEntitytoCourierResponse(courierEntity);
    }

    @Override
    public void deleteCourier(Long id) {
        CourierEntity courierEntity = findById(id);
        courierRepository.delete(courierEntity);
    }

    @Override
    public List<CourierResponse> getAllCourier(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return courierRepository.findAll(pageable).map(courierMapper::courierEntitytoCourierResponse).toList();
    }

    @Override
    public CourierResponse getCourierById(Long id) {
        CourierEntity courierEntity = findById(id);
        return courierMapper.courierEntitytoCourierResponse(courierEntity);
    }

    @Override
    public CourierResponse updateCourierActiveType(Long id, UpdateCourierActiveTypeRequest request) {
        CourierEntity courierEntity = findById(id);
        courierEntity.setActiveType(request.getStatus());
        courierEntity.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        courierEntity = courierRepository.save(courierEntity);
        return courierMapper.courierEntitytoCourierResponse(courierEntity);
    }

    @Override
    public CourierEntity findById(Long id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new CourierException("CourierEntity Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
