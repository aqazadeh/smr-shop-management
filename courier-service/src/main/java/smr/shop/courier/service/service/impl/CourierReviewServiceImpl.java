package smr.shop.courier.service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import smr.shop.courier.service.dto.request.CreateCourierReviewRequest;
import smr.shop.courier.service.mapper.CourierServiceMapper;
import smr.shop.courier.service.model.CourierReview;
import smr.shop.courier.service.repository.CourierReviewRepository;
import smr.shop.courier.service.service.CourierReviewService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class CourierReviewServiceImpl implements CourierReviewService {

    private final CourierServiceMapper courierServiceMapper;
    private final CourierReviewRepository courierReviewRepository;

    public CourierReviewServiceImpl(CourierServiceMapper courierServiceMapper, CourierReviewRepository courierReviewRepository) {
        this.courierServiceMapper = courierServiceMapper;
        this.courierReviewRepository = courierReviewRepository;
    }

//    ----------------------------------- Create -----------------------------------

    @Override
    @Transactional
    public void createCourierReview(@RequestBody CreateCourierReviewRequest request) {
        CourierReview courierReview = courierServiceMapper.toCourierReview(request);
        courierReview.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        courierReview.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        courierReview = courierReviewRepository.save(courierReview);
        courierServiceMapper.toCourierReviewResponse(courierReview);
    }
}
