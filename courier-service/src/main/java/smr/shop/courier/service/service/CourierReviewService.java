package smr.shop.courier.service.service;

import smr.shop.courier.service.dto.request.CreateCourierReviewRequest;

public interface CourierReviewService {
    void createCourierReview(CreateCourierReviewRequest request);
}
