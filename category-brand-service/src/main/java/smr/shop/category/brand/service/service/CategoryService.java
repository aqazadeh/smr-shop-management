package smr.shop.category.brand.service.service;

import smr.shop.category.brand.service.dto.request.CategoryCreateRequest;
import smr.shop.category.brand.service.dto.request.CategoryUpdateRequest;
import smr.shop.category.brand.service.dto.response.CategoryResponse;
import smr.shop.category.brand.service.model.CategoryEntity;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.object.CategoryGrpcId;

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

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long categoryId);

    CategoryResponse getCategoryBySlug(String slug);

    CategoryEntity findById(Long categoryId);

    CategoryGrpcResponse getCategoryInformation(CategoryGrpcId request);
}
