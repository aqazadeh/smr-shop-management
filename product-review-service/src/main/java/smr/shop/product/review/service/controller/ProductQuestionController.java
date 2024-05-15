package smr.shop.product.review.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ProductQuestionResponse> createProductQuestion(@RequestBody CreateProductQuestionRequest request) {
        ProductQuestionResponse productQuestionResponse = procuctQuestionService.createProductQuestion(request);
        return ResponseEntity.ok(productQuestionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductQuestion(@PathVariable UUID id) {
        procuctQuestionService.deleteProductQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
