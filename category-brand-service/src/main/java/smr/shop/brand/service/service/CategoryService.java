package smr.shop.brand.service.service;

import smr.shop.brand.service.dto.request.CategoryCreateRequest;
import smr.shop.brand.service.dto.request.CategoryUpdateRequest;
import smr.shop.brand.service.dto.response.CategoryResponse;
import smr.shop.brand.service.model.CategoryEntity;
import smr.shop.libs.grpc.category.CategoryGrpcRequest;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/14/2024
 * Time: 4:35 PM
 */

public interface CategoryService {

    void createCategory(CategoryCreateRequest request);

    void updateCategory(Long categoryIdId, CategoryUpdateRequest request);

    void deleteCategory(Long categoryId);

//    TODO Get all categories by pageable
    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long categoryId);

    CategoryResponse getCategoryBySlug(String slug);

    CategoryEntity findById(Long categoryId);

    CategoryGrpcResponse getCategoryInformation(CategoryGrpcRequest request);
}
