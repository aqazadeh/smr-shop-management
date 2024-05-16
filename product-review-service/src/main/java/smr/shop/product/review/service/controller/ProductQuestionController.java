package smr.shop.product.review.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;
import smr.shop.product.review.service.service.ProductQuestionService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product-question")
public class ProductQuestionController {

    private final ProductQuestionService procuctQuestionService;

    public ProductQuestionController(ProductQuestionService procuctQuestionService) {
        this.procuctQuestionService = procuctQuestionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> createProductQuestion(@RequestBody CreateProductQuestionRequest request) {
        procuctQuestionService.createProductQuestion(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product Question created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<EmptyResponse> deleteProductQuestion(@PathVariable UUID id) {
        procuctQuestionService.deleteProductQuestion(id);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product Question deleted successfully with id: " + id)
                .build();
        return ResponseEntity.ok(response);
    }
}
