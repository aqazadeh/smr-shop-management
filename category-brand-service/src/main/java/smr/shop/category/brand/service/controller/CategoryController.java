package smr.shop.category.brand.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.category.brand.service.dto.request.CategoryCreateRequest;
import smr.shop.category.brand.service.dto.request.CategoryUpdateRequest;
import smr.shop.category.brand.service.dto.response.CategoryResponse;
import smr.shop.category.brand.service.service.CategoryService;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.libs.common.dto.response.ErrorResponse;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/15/2024
 * Time: 10:03 AM
 */
@RestController
@RequestMapping(value = "/api/1.0/categories")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    -------------------------------------- POST --------------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new Category", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        log.info("Create category request: {}", categoryCreateRequest);
        categoryService.createCategory(categoryCreateRequest);
        EmptyResponse response = EmptyResponse.builder()
                .message("Category created successfully")
                .build();

        return ResponseEntity.ok(response);
    }

//    -------------------------------------- PUT --------------------------------------

    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update category with id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateCategory(@PathVariable Long categoryId,
            @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {

        categoryService.updateCategory(categoryId, categoryUpdateRequest);
        EmptyResponse response = EmptyResponse.builder()
                .message("Category updated successfully")
                .build();

        return ResponseEntity.ok(response);
    }

//    -------------------------------------- DELETE --------------------------------------

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete category with id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Category deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }

//    -------------------------------------- GET --------------------------------------

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get category all data", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))))
    })
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get category with id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

}
