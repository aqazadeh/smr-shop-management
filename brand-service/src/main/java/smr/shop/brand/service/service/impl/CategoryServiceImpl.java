package smr.shop.brand.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.brand.service.dto.request.CategoryCreateRequest;
import smr.shop.brand.service.dto.request.CategoryUpdateRequest;
import smr.shop.brand.service.dto.response.CategoryResponse;
import smr.shop.brand.service.exception.CategoryException;
import smr.shop.brand.service.mapper.BrandServiceMapper;
import smr.shop.brand.service.model.CategoryEntity;
import smr.shop.brand.service.repository.CategoryRepository;
import smr.shop.brand.service.service.CategoryService;

import java.util.List;
import java.util.Random;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/14/2024
 * Time: 4:35 PM
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final BrandServiceMapper brandServiceMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, BrandServiceMapper brandServiceMapper) {
        this.categoryRepository = categoryRepository;
        this.brandServiceMapper = brandServiceMapper;
    }

    @Override
    public void createCategory(CategoryCreateRequest request) {
        CategoryEntity category = brandServiceMapper.categoryCreateRequestToCategoryEntity(request);
        if (category.getSlug() == null) {
            String slug = category.getName() + new Random().nextInt(10);
            category.setSlug(slug);
        }
        if (request.getParentId() != null) {
            category.setParentId(request.getParentId());
        }
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long categoryId, CategoryUpdateRequest request) {
        CategoryEntity category = findById(categoryId);
        CategoryEntity updatedCategory = brandServiceMapper.categoryUpdateRequestToCategoryEntity(category, request);
        if (request.getParentId() != null) {
            updatedCategory.setParentId(request.getParentId());
        }
        categoryRepository.save(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.delete(findById(categoryId));
    }

    @Override
    @Transactional
    public CategoryResponse getCategoryById(Long categoryId) {
        CategoryEntity category = findById(categoryId);

        return brandServiceMapper.categoryEntityToCategoryResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponse getCategoryBySlug(String slug) {
        CategoryEntity category = categoryRepository.findBySlug(slug);

        return brandServiceMapper.categoryEntityToCategoryResponse(category);
    }

    @Override
    @Transactional
    public List<CategoryResponse> getAllCategories() {

//    TODO Get all categories by pageable

        List<CategoryEntity> categories = categoryRepository.findByParentIdIsNull();
        return categories.stream().map(brandServiceMapper::categoryEntityToCategoryResponse).toList();
    }



    @Override
    public CategoryEntity findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryException("Category Not found With id : " + categoryId, HttpStatus.NOT_FOUND));
    }


}
