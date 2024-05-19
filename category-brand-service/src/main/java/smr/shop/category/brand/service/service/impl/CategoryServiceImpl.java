package smr.shop.category.brand.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.category.brand.service.dto.request.CategoryCreateRequest;
import smr.shop.category.brand.service.dto.request.CategoryUpdateRequest;
import smr.shop.category.brand.service.dto.response.CategoryResponse;
import smr.shop.category.brand.service.exception.CategoryBrandServiceException;
import smr.shop.category.brand.service.mapper.CategoryBrandServiceMapper;
import smr.shop.category.brand.service.messaging.publisher.CategoryDeleteMessagePublisher;
import smr.shop.category.brand.service.model.CategoryEntity;
import smr.shop.category.brand.service.repository.CategoryRepository;
import smr.shop.category.brand.service.service.CategoryService;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.CategoryMessageModel;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.object.CategoryGrpcId;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/14/2024
 * Time: 4:35 PM
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryBrandServiceMapper categoryBrandServiceMapper;

    private final CategoryDeleteMessagePublisher categoryDeleteMessagePublisher;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryBrandServiceMapper categoryBrandServiceMapper,
                               CategoryDeleteMessagePublisher categoryDeleteMessagePublisher) {
        this.categoryRepository = categoryRepository;
        this.categoryBrandServiceMapper = categoryBrandServiceMapper;
        this.categoryDeleteMessagePublisher = categoryDeleteMessagePublisher;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void createCategory(CategoryCreateRequest request) {
        CategoryEntity categoryEntity = categoryBrandServiceMapper.categoryCreateRequestToCategoryEntity(request);
        if (request.getParentId() != null) {
            CategoryEntity parentCategoryEntity = findById(request.getParentId());
            validateCategory(parentCategoryEntity);
            categoryEntity.setParent(parentCategoryEntity);
        }
        categoryEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        categoryEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        categoryRepository.save(categoryEntity);
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    public void updateCategory(Long categoryId, CategoryUpdateRequest request) {
        CategoryEntity categoryEntity = findById(categoryId);
        validateCategory(categoryEntity);
        CategoryEntity updatedCategory = categoryBrandServiceMapper.categoryUpdateRequestToCategoryEntity(categoryEntity, request);
        if (request.getParentId() != null) {
            CategoryEntity parentCategoryEntity = findById(request.getParentId());
            validateCategory(parentCategoryEntity);
            updatedCategory.setParent(parentCategoryEntity);
        }
        categoryEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        categoryRepository.save(updatedCategory);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        CategoryEntity categoryEntity = findById(categoryId);
        validateCategory(categoryEntity);
        return categoryBrandServiceMapper.categoryEntityToCategoryResponse(categoryEntity);
    }

    @Override
    public CategoryResponse getCategoryBySlug(String slug) {
        CategoryEntity category = categoryRepository.findBySlug(slug);
        return categoryBrandServiceMapper.categoryEntityToCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findByParentIdIsNullAndIsDeletedFalse();
        return categories.stream().map(categoryBrandServiceMapper::categoryEntityToCategoryResponse).toList();
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        CategoryEntity categoryEntity = findById(categoryId);
        validateCategory(categoryEntity);
        categoryEntity.setIsDeleted(Boolean.TRUE);
        categoryEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        categoryRepository.save(categoryEntity);
        CategoryMessageModel categoryMessageModel = categoryBrandServiceMapper.categoryEntityToCategoryMessageModel(categoryEntity);
        categoryDeleteMessagePublisher.publish(categoryMessageModel);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public CategoryEntity findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryBrandServiceException("Category Not found With id : " + categoryId, HttpStatus.NOT_FOUND));
    }

    @Override
    public CategoryGrpcResponse getCategoryInformation(CategoryGrpcId request) {
        CategoryEntity categoryEntity = findById(request.getId());
        return categoryBrandServiceMapper.categoryEntityToCategoryGrpcResponse(categoryEntity);
    }

    private void validateCategory(CategoryEntity categoryEntity) {
        if (categoryEntity.getIsDeleted().equals(Boolean.TRUE)) {
            throw new CategoryBrandServiceException("Category is deleted With id : " + categoryEntity.getId(), HttpStatus.BAD_REQUEST);
        }
    }


}
