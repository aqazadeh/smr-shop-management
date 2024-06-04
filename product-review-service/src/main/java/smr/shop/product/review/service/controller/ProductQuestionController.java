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
    @Operation(summary = "Get Product 1uestion", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductQuestionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ProductQuestionResponse>> getProductQuestions(@PathVariable Long productId,
                                                                             @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ProductQuestionResponse> productQuestionResponseList = productQuestionService.getProductQuestions(productId, page);

        return ResponseEntity.ok(productQuestionResponseList);
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Created Product Question", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> createProductQuestion(@PathVariable Long productId,
                                                               @RequestBody CreateProductQuestionRequest request) {
        productQuestionService.createProductQuestion(productId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product question created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update Product question", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateProductQuestion(@PathVariable UUID id, @RequestBody UpdateProductQuestionRequest request) {
        productQuestionService.updateProductQuestion(id, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product question updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete product question", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> deleteProductQuestion(@PathVariable UUID id) {
        productQuestionService.deleteProductQuestion(id);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product question deleted successfully with id: " + id)
                .build();
        return ResponseEntity.ok(response);
    }

}
