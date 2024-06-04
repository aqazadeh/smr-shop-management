package smr.shop.category.brand.service.service.impl;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import smr.shop.libs.common.constant.CacheConstants;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.CategoryMessageModel;
import smr.shop.libs.common.helper.Slugify;
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

    private final CacheManager cacheManager;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryBrandServiceMapper categoryBrandServiceMapper,
                               CategoryDeleteMessagePublisher categoryDeleteMessagePublisher,
                               CacheManager cacheManager) {
        this.categoryRepository = categoryRepository;
        this.categoryBrandServiceMapper = categoryBrandServiceMapper;
        this.categoryDeleteMessagePublisher = categoryDeleteMessagePublisher;
        this.cacheManager = cacheManager;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.CATEGORY_LIST, allEntries = true)
    public void createCategory(CategoryCreateRequest request) {
        CategoryEntity categoryEntity = categoryBrandServiceMapper.categoryCreateRequestToCategoryEntity(request);
        if (request.getParentId() != null) {
            CategoryEntity parentCategoryEntity = findById(request.getParentId());
            validateCategory(parentCategoryEntity);
            categoryEntity.setParent(parentCategoryEntity);
            cacheManager.getCache(CacheConstants.CATEGORY).evict(categoryEntity.getParent());
        }
        categoryEntity.setSlug(Slugify.make(categoryEntity.getName()));
        categoryEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        categoryEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        categoryRepository.save(categoryEntity);
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.CATEGORY, key = "#categoryId"),
            @CacheEvict(value = CacheConstants.CATEGORY_GRPC, key = "#categoryId"),
            @CacheEvict(value = CacheConstants.CATEGORY_LIST, allEntries = true)
    })
    public void updateCategory(Long categoryId, CategoryUpdateRequest request) {
        CategoryEntity categoryEntity = findById(categoryId);
        validateCategory(categoryEntity);
        CategoryEntity updatedCategory = categoryBrandServiceMapper.categoryUpdateRequestToCategoryEntity(categoryEntity, request);
        if (request.getParentId() != null) {
            CategoryEntity parentCategoryEntity = findById(request.getParentId());
            validateCategory(parentCategoryEntity);
            updatedCategory.setParent(parentCategoryEntity);
        }
        categoryEntity.setSlug(Slugify.make(categoryEntity.getName()));
        categoryEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        categoryRepository.save(updatedCategory);
        clearParentCache(categoryEntity);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    @Cacheable(value = CacheConstants.CATEGORY, key = "#categoryId", sync = true)
    public CategoryResponse getCategoryById(Long categoryId) {
        CategoryEntity categoryEntity = findById(categoryId);
        validateCategory(categoryEntity);
        return categoryBrandServiceMapper.categoryEntityToCategoryResponse(categoryEntity);
    }

    @Override
    @Cacheable(value = CacheConstants.CATEGORY, key = "#slug", sync = true)
    public CategoryResponse getCategoryBySlug(String slug) {
        CategoryEntity category = categoryRepository.findBySlug(slug);
        return categoryBrandServiceMapper.categoryEntityToCategoryResponse(category);
    }

    @Override
    @Cacheable(value = CacheConstants.CATEGORY_LIST, sync = true)
    public List<CategoryResponse> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findByParentIdIsNullAndIsDeletedFalse();
        return categories.stream().map(categoryBrandServiceMapper::categoryEntityToCategoryResponse).toList();
    }

    @Override
    @Cacheable(value = CacheConstants.CATEGORY, key = "#request.id", sync = true)
    public CategoryGrpcResponse getCategoryInformation(CategoryGrpcId request) {
        CategoryEntity categoryEntity = findById(request.getId());
        return categoryBrandServiceMapper.categoryEntityToCategoryGrpcResponse(categoryEntity);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.CATEGORY, key = "#categoryId"),
            @CacheEvict(value = CacheConstants.CATEGORY_GRPC, key = "#categoryId"),
            @CacheEvict(value = CacheConstants.CATEGORY_LIST, allEntries = true)
    })
    public void deleteCategory(Long categoryId) {
        CategoryEntity categoryEntity = findById(categoryId);
        validateCategory(categoryEntity);
        categoryEntity.setIsDeleted(Boolean.TRUE);
        categoryEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        categoryRepository.save(categoryEntity);
        CategoryMessageModel categoryMessageModel = categoryBrandServiceMapper.categoryEntityToCategoryMessageModel(categoryEntity);
        categoryDeleteMessagePublisher.publish(categoryMessageModel);
        clearParentCache(categoryEntity);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public CategoryEntity findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryBrandServiceException("Category Not found With id : " + categoryId, HttpStatus.NOT_FOUND));
    }

    private void validateCategory(CategoryEntity categoryEntity) {
        if (categoryEntity.getIsDeleted().equals(Boolean.TRUE)) {
            throw new CategoryBrandServiceException("Category is deleted With id : " + categoryEntity.getId(), HttpStatus.BAD_REQUEST);
        }
    }

    private void clearParentCache(CategoryEntity categoryEntity) {
        CategoryEntity parent = categoryEntity.getParent();
        if (parent != null) {
            cacheManager.getCache(CacheConstants.CATEGORY).evict(parent.getId());
            cacheManager.getCache(CacheConstants.CATEGORY_GRPC).evict(parent.getId());
            if (parent.getParent() != null) {
                clearParentCache(parent.getParent());
            }
        }
    }


}
