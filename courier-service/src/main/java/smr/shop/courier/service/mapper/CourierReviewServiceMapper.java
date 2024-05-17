package smr.shop.courier.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.courier.service.dto.request.CreateCourierReviewRequest;
import smr.shop.courier.service.dto.response.CourierReviewResponse;
import smr.shop.courier.service.model.CourierReview;

@Component
public class CourierReviewServiceMapper {
    public CourierReview toCourierReview(CreateCourierReviewRequest request) {
        CourierReview.CourierReviewBuilder builder = CourierReview.builder();
        builder.orderId(request.getOrderId());
        builder.userId(request.getUserId());
        //courierId error
        builder.reviewScore(request.getReviewScore());
        return builder.build();
    }

    public CourierReviewResponse toCourierReviewResponse(CourierReview review) {
        return CourierReviewResponse.builder()
                .id(review.getId())
                .orderId(review.getOrderId())
                .userId(review.getUserId())
                //courierId error
                .reviewScore(review.getReviewScore())
                .build();
    }
}
