package smr.shop.product.review.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.UpdateProductQuestionRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;
import smr.shop.product.review.service.service.ProductQuestionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/question")
public class ProductQuestionController {

    private final ProductQuestionService productQuestionService;

    public ProductQuestionController(ProductQuestionService productQuestionService) {
        this.productQuestionService = productQuestionService;
    }


//    ----------------------------------- Get -----------------------------------

    @GetMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductQuestionResponse>> getProductQuestions(@PathVariable Long productId,
                                                                             @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ProductQuestionResponse> productQuestionResponseList = productQuestionService.getProductQuestions(productId, page);

        return ResponseEntity.ok(productQuestionResponseList);
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> createProductQuestion(@PathVariable Long productId,
                                                               @RequestBody CreateProductQuestionRequest request) {
        productQuestionService.createProductQuestion(productId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product Question created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateProductQuestion(@PathVariable UUID id, @RequestBody UpdateProductQuestionRequest request) {
        productQuestionService.updateProductQuestion(id, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product Question updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<EmptyResponse> deleteProductQuestion(@PathVariable UUID id) {
        productQuestionService.deleteProductQuestion(id);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product Question deleted successfully with id: " + id)
                .build();
        return ResponseEntity.ok(response);
    }

}
