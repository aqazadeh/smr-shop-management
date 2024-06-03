package smr.shop.product.review.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.request.UpdateProductReviewRequest;
import smr.shop.product.review.service.dto.response.ProductReviewResponse;
import smr.shop.product.review.service.service.ProductReviewService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/review")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

//    ----------------------------------- Post -----------------------------------

    @GetMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Product Review", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductReviewResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ProductReviewResponse>> getProductReviews(@PathVariable Long productId,
                                                                         @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ProductReviewResponse> productReviewResponseList = productReviewService.getProductReviews(productId, page);

        return ResponseEntity.ok(productReviewResponseList);
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Created Product Review", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> createProductReview(@PathVariable Long productId,
                                                             @RequestBody CreateProductReviewRequest request) {
        productReviewService.createProductReview(productId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product review created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PutMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update product review", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateProductReview(@PathVariable UUID reviewId, @RequestBody UpdateProductReviewRequest updateRequest) {
        productReviewService.updateProductReviewRequest(reviewId, updateRequest);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product review updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
