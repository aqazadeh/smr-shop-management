package smr.shop.product.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.CategoryMessageModel;
import smr.shop.libs.common.dto.message.DiscountMessageModel;
import smr.shop.libs.common.dto.message.RatingMessageModel;
import smr.shop.libs.common.dto.message.ShopMessageModel;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.client.*;
import smr.shop.libs.grpc.object.ProductGrpcId;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.ShopProductGrpcId;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.libs.common.dto.message.StockCreateMessageModel;
import smr.shop.product.service.dto.request.ProductCreateRequest;
import smr.shop.product.service.dto.request.ProductUpdateRequest;
import smr.shop.product.service.dto.response.ProductResponse;
import smr.shop.product.service.exception.ProductServiceException;
import smr.shop.product.service.mapper.ProductServiceMapper;
import smr.shop.product.service.messaging.publisher.ProductStockCreateMessagePublisher;
import smr.shop.product.service.model.ProductEntity;
import smr.shop.product.service.model.valueobject.ProductStatus;
import smr.shop.product.service.repository.ProductRepository;
import smr.shop.product.service.service.ProductService;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 12:49 AM
 */
@Service
public class ProductServiceImpl implements ProductService {

    // TODO Fix discount id problem

    // repository
    private final ProductRepository productRepository;

    // service mapper
    private final ProductServiceMapper productServiceMapper;

    // rpc
    private final UploadGrpcClient uploadGrpcClient;
    private final BrandGrpcClient brandGrpcClient;
    private final CategoryGrpcClient categoryGrpcClient;
    private final ShopGrpcClient shopGrpcClient;
    private final DiscountGrpcClient discountGrpcClient;

    // message broker
    private final ProductStockCreateMessagePublisher productStockCreateMessagePublisher;
    private final ProductStockGrpcClient productStockGrpcClient;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductServiceMapper productServiceMapper,
                              UploadGrpcClient uploadGrpcClient,
                              BrandGrpcClient brandGrpcClient,
                              CategoryGrpcClient categoryGrpcClient,
                              ShopGrpcClient shopGrpcClient,
                              DiscountGrpcClient discountGrpcClient,
                              ProductStockCreateMessagePublisher productStockCreateMessagePublisher,
                              ProductStockGrpcClient productStockGrpcClient) {
        this.productRepository = productRepository;
        this.productServiceMapper = productServiceMapper;
        this.uploadGrpcClient = uploadGrpcClient;
        this.brandGrpcClient = brandGrpcClient;
        this.categoryGrpcClient = categoryGrpcClient;
        this.shopGrpcClient = shopGrpcClient;
        this.discountGrpcClient = discountGrpcClient;
        this.productStockCreateMessagePublisher = productStockCreateMessagePublisher;
        this.productStockGrpcClient = productStockGrpcClient;
    }


// --------------------------------------- Create or Add ---------------------------------------

    @Override
    @Transactional
    public void createProduct(ProductCreateRequest request) {
        CategoryGrpcResponse categoryGrpcResponse = categoryGrpcClient.getCategoryById(request.getCategoryId());
        BrandGrpcResponse brandGrpcResponse = brandGrpcClient.getBrandByBrandId(request.getBrandId());
        UploadGrpcResponse uploadGrpcResponse = uploadGrpcClient.getUploadById(request.getThumbnail());
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByUserId(UserHelper.getUserId().toString());

        ProductEntity product = productServiceMapper.productCreateRequestToProductEntity(request);
        product.setShopId(shopGrpcResponse.getId());
        product.setCategoryId(categoryGrpcResponse.getId());
        product.setBrandId(brandGrpcResponse.getId());
        product.setThumbnail(uploadGrpcResponse.getId());

        productRepository.save(product);

        StockCreateMessageModel model = productServiceMapper.productStockRequestToStockCreateMessageModel(request.getStock());
        productStockCreateMessagePublisher.publish(model);
    }

    @Override
    @Transactional
    public void addProductThumbNail(Long productId, String imageId) {
        ProductEntity product = findById(productId);
        validateUserShop(product);
        UploadGrpcResponse image = uploadGrpcClient.getUploadById(imageId);
        product.setThumbnail(image.getUrl());
        productRepository.save(product);
    }


    @Override
    @Transactional
    public void addProductImage(Long productId, String imageId) {
        ProductEntity product = findById(productId);
        validateUserShop(product);
        UploadGrpcResponse image = uploadGrpcClient.getUploadById(imageId);
        product.getImageIds().add(image.getUrl());
        productRepository.save(product);

    }

// --------------------------------------- Delete ---------------------------------------

    @Override
    @Transactional
    public void removeProductThumbNail(Long productId) {
        ProductEntity product = findById(productId);
        validateUserShop(product);
        product.setThumbnail(null);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void removeProductImage(Long productId, String imageId) {
        ProductEntity product = findById(productId);
        validateUserShop(product);
        UploadGrpcResponse image = uploadGrpcClient.getUploadById(imageId);
        product.getImageIds().remove(image.getUrl());
        productRepository.save(product);

    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        ProductEntity product = findById(productId);
        validateUserShop(product);
        product.setStatus(ProductStatus.DELETED);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProductsByBrand(Long brandId) {
        productRepository.updateStatusByBrandId(ProductStatus.DELETED, brandId);
    }

    @Override
    @Transactional
    public void deleteProductsByShop(ShopMessageModel shopMessageModel) {
        productRepository.updateStatusByShopId(ProductStatus.DELETED, shopMessageModel.getId());
    }

    @Override
    @Transactional
    public void deleteProductsByCategory(CategoryMessageModel categoryMessageModel) {
        productRepository.updateStatusByCategoryId(ProductStatus.DELETED, categoryMessageModel.getId());
    }

// --------------------------------------- Get ---------------------------------------

    @Override
    public ProductResponse getProductById(Long productId) {
        ProductEntity product = findById(productId);
        CategoryGrpcResponse categoryGrpcResponse = categoryGrpcClient.getCategoryById(product.getCategoryId());
        BrandGrpcResponse brandGrpcResponse = brandGrpcClient.getBrandByBrandId(product.getBrandId());
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByShopId(product.getShopId());
        DiscountGrpcResponse discountGrpcResponse = null;
        if (product.getDiscountId() != null) {
            discountGrpcResponse = discountGrpcClient.getDiscountById(product.getDiscountId().toString());
        }
        List<ProductStockGrpcResponse> productStockGrpcResponse = productStockGrpcClient.getProductsStockByProductId(product.getId());

        //TODO set other information's   productResponse
        return productServiceMapper.productEntityToProductResponse(product);
    }

    @Override
    public ProductResponse getProductBySlug(String slug) {
        ProductEntity product = productRepository.findBySlug(slug);
        return productServiceMapper.productEntityToProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<ProductEntity> productEntities = productRepository.findAll(pageable).stream().toList();
        return productEntities.stream().map(productServiceMapper::productEntityToProductResponse).toList();
    }

    @Override
    public ProductGrpcResponse getProductInformation(ProductGrpcId request) {
        ProductEntity productEntity = findById(request.getId());
        DiscountGrpcResponse discountGrpcResponse = null;
        if (productEntity.getDiscountId() != null) {
            discountGrpcResponse = discountGrpcClient.getDiscountById(productEntity.getDiscountId().toString());
        }
        return productServiceMapper.productEntityToProductGrpcResponse(productEntity, discountGrpcResponse);
    }

    @Override
    public ProductGrpcResponse getProductInformation(ShopProductGrpcId request) {
        ProductEntity productEntity = productRepository.findByIdAndShopId(request.getProduct().getId(), request.getShop().getId())
                .orElseThrow(() ->
                        new ProductServiceException("product not found with productId: %s and shopId: %s".formatted(request.getProduct().getId(), request.getShop().getId()), HttpStatus.NOT_FOUND));
        DiscountGrpcResponse discountGrpcResponse = null;
        if (productEntity.getDiscountId() != null) {
            discountGrpcResponse = discountGrpcClient.getDiscountById(productEntity.getDiscountId().toString());
        }
        return productServiceMapper.productEntityToProductGrpcResponse(productEntity, discountGrpcResponse);
    }

    @Override
    @Transactional
    public void removeProductDiscount(DiscountMessageModel message) {
        ProductEntity productEntity = findById(message.getProductId());
        productEntity.setDiscountId(null);
        productRepository.save(productEntity);
    }

    @Override
    @Transactional
    public void updateProductRating(RatingMessageModel message) {
        ProductEntity productEntity = findById(message.getProductId());
        productEntity.setRating(message.getRating());
        productRepository.save(productEntity);
    }

    @Override
    public List<ProductResponse> getAllProductsByBrandId(Long brandId, Integer page) {
        BrandGrpcResponse brandGrpcResponse = brandGrpcClient.getBrandByBrandId(brandId);
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<ProductEntity> productEntities = productRepository.findAllByBrandId(brandGrpcResponse.getId(), pageable).stream().toList();
        return productEntities.stream().map(productServiceMapper::productEntityToProductResponse).toList();

    }

    @Override
    public List<ProductResponse> getAllProductsByCategoryId(Long categoryId, Integer page) {
        CategoryGrpcResponse categoryGrpcResponse = categoryGrpcClient.getCategoryById(categoryId);
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<ProductEntity> productEntities = productRepository.findAllByCategoryId(categoryGrpcResponse.getId(), pageable).stream().toList();
        return productEntities.stream().map(productServiceMapper::productEntityToProductResponse).toList();
    }

    @Override
    public List<ProductResponse> getAllProductsByShopId(Long shopId, Integer page) {
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByShopId(shopId);
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<ProductEntity> productEntities = productRepository.findAllByShopId(shopGrpcResponse.getId(), pageable).stream().toList();
        return productEntities.stream().map(productServiceMapper::productEntityToProductResponse).toList();
    }

// --------------------------------------- Update ---------------------------------------


    @Override
    @Transactional
    public void updateProduct(Long productId, ProductUpdateRequest request) {
        ProductEntity product = findById(productId);
        validateUserShop(product);
        product = productServiceMapper.productUpdateRequestToProductEntity(product, request);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProductCategory(Long productId, Long categoryId) {
        ProductEntity product = findById(productId);
        validateUserShop(product);
        CategoryGrpcResponse categoryGrpcResponse = categoryGrpcClient.getCategoryById(categoryId);
        product.setCategoryId(categoryGrpcResponse.getId());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProductBrand(Long productId, Long brandId) {
        ProductEntity product = findById(productId);
        validateUserShop(product);
        BrandGrpcResponse brandGrpcResponse = brandGrpcClient.getBrandByBrandId(brandId);
        product.setBrandId(brandGrpcResponse.getId());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProductStatus(Long productId, ProductStatus productStatus) {
        ProductEntity product = findById(productId);
        validateUserShop(product);
        product.setStatus(productStatus);
        productRepository.save(product);
    }


// --------------------------------------- Extra ---------------------------------------

    public ProductEntity findById(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceException("Product not found with id: " + productId, HttpStatus.NOT_FOUND));

    }

    private void validateUserShop(ProductEntity product) {
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByUserId(UserHelper.getUserId().toString());
        if (!product.getShopId().equals(shopGrpcResponse.getId())) {
            throw new ProductServiceException("Your don't have access to modify this product", HttpStatus.NOT_FOUND);
        }
    }


}
