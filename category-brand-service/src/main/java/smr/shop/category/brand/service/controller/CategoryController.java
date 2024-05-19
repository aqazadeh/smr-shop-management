package smr.shop.category.brand.service.controller;

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

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/15/2024
 * Time: 10:03 AM
 */
@RestController
@RequestMapping(value = "/api/1.0/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    -------------------------------------- POST --------------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    public ResponseEntity<EmptyResponse> updateCategory(
            @PathVariable(name = "categoryId") Long categoryIdId,
            @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {

            categoryService.updateCategory(categoryIdId, categoryUpdateRequest);
        EmptyResponse response = EmptyResponse.builder()
                .message("Category updated successfully")
                .build();

        return ResponseEntity.ok(response);
    }

//    -------------------------------------- DELETE --------------------------------------

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
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
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

}
