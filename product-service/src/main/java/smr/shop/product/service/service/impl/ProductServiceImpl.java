package smr.shop.product.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.product.service.dto.messaging.StockCreateMessageModel;
import smr.shop.product.service.dto.request.ProductCreateRequest;
import smr.shop.product.service.dto.request.ProductUpdateRequest;
import smr.shop.product.service.dto.response.ProductResponse;
import smr.shop.product.service.exception.ProductException;
import smr.shop.product.service.grpc.client.ProductGrpcClientService;
import smr.shop.product.service.mapper.ProductServiceMapper;
import smr.shop.product.service.messaging.ProductStockCreateMessagePublisher;
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


//    TODO Check UserShop
    private final ProductRepository productRepository;

    private final ProductServiceMapper productServiceMapper;

    private final ProductGrpcClientService productGrpcClientService;

    private final ProductStockCreateMessagePublisher productStockCreateMessagePublisher;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductServiceMapper productServiceMapper,
                              ProductGrpcClientService productGrpcClientService,
                              ProductStockCreateMessagePublisher productStockCreateMessagePublisher) {
        this.productRepository = productRepository;
        this.productServiceMapper = productServiceMapper;
        this.productGrpcClientService = productGrpcClientService;
        this.productStockCreateMessagePublisher = productStockCreateMessagePublisher;
    }

// --------------------------------------- Create or Add ---------------------------------------

@Override
public void createProduct(ProductCreateRequest request) {
    CategoryGrpcResponse categoryGrpcResponse = productGrpcClientService.getCategory(request.getCategoryId());
    BrandGrpcResponse brandGrpcResponse = productGrpcClientService.getBrand(request.getBrandId());
    UploadGrpcResponse uploadGrpcResponse = productGrpcClientService.getImage(request.getThumbnail());
    ShopGrpcResponse shopGrpcResponse = productGrpcClientService.getShopByUserId(UserHelper.getUserId());

    ProductEntity product = productServiceMapper.productCreateRequestToProductEntity(request);
    product.setShopId(shopGrpcResponse.getShopId());
    // TODO Check image ids

    StockCreateMessageModel model = productServiceMapper.productStockRequestToStockCreateMessageModel(request.getStock());
    productRepository.save(product);
    productStockCreateMessagePublisher.publish(model);
}

    @Override
    public void addProductThumbNail(Long productId, String imageId) {

        ProductEntity product = findById(productId);

        validateUserShop(product);

        UploadGrpcResponse image = productGrpcClientService.getImage(imageId);

        product.setThumbnail(imageId);

        productRepository.save(product);

    }


    @Override
    public void addProductImage(Long productId, String imageId) {

        ProductEntity product = findById(productId);

        validateUserShop(product);

        UploadGrpcResponse image = productGrpcClientService.getImage(imageId);

        product.getImageIds().add(imageId);

        productRepository.save(product);

    }

// --------------------------------------- Delete ---------------------------------------

    @Override
    public void removeProductThumbNail(Long productId) {

        ProductEntity product = findById(productId);

        validateUserShop(product);

        product.setThumbnail(null);

        productRepository.save(product);

    }

    @Override
    public void removeProductImage(Long productId, String imageId) {

        ProductEntity product = findById(productId);

        validateUserShop(product);

        UploadGrpcResponse image = productGrpcClientService.getImage(imageId);

        product.getImageIds().remove(imageId);

        productRepository.save(product);

    }

    @Override
    public void deleteProduct(Long productId) {

        ProductEntity product = findById(productId);

        validateUserShop(product);

        product.setStatus(ProductStatus.DELETED);

        productRepository.save(product);

    }

    @Override
    public void deleteProductsByBrand(Long brandId) {

        productRepository.updateStatusByBrandId(ProductStatus.DELETED, brandId);

    }

    @Override
    public void deleteProductsByShop(Long shopId) {

        productRepository.updateStatusByShopId(ProductStatus.DELETED, shopId);

    }

    @Override
    public void deleteProductsByCategory(Long categoryId) {

        productRepository.updateStatusByCategoryId(ProductStatus.DELETED, categoryId);

    }

// --------------------------------------- Get ---------------------------------------

    @Override
    public ProductResponse getProductById(Long productId) {
        ProductEntity product = findById(productId);

        CategoryGrpcResponse categoryGrpcResponse =  productGrpcClientService.getCategory(product.getCategoryId());
        BrandGrpcResponse brandGrpcResponse = productGrpcClientService.getBrand(product.getBrandId());
        ShopGrpcResponse shopGrpcResponse = productGrpcClientService.getShopByShopId(product.getShopId());
        DiscountGrpcResponse discountGrpcResponse = productGrpcClientService.getDiscount(product.getId());
        List<ProductStockGrpcResponse> productStockGrpcResponse = productGrpcClientService.getStock(product.getId());

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

    public List<ProductResponse> getAllProductsByBrandId(Long brandId) {

        List<ProductEntity> productEntities = productRepository.findByBrandId(brandId);

        return productEntities.stream().map(productServiceMapper::productEntityToProductResponse).toList();

    }

// --------------------------------------- Update ---------------------------------------


    @Override
    public void updateProduct(Long productId, ProductUpdateRequest request) {

        ProductEntity product = findById(productId);

        validateUserShop(product);

        product = productServiceMapper.productUpdateRequestToProductEntity(product, request);

        productRepository.save(product);

    }

    @Override
    public void updateProductCategory(Long productId, Long categoryId) {

        ProductEntity product = findById(productId);

        validateUserShop(product);

        CategoryGrpcResponse categoryGrpcResponse = productGrpcClientService.getCategory(categoryId);

        product.setCategoryId(categoryId);

        productRepository.save(product);

    }

    @Override
    public void updateProductBrand(Long productId, Long brandId) {

        ProductEntity product = findById(productId);

        validateUserShop(product);

        BrandGrpcResponse brandGrpcResponse = productGrpcClientService.getBrand(brandId);

        product.setBrandId(brandId);

        productRepository.save(product);

    }

    @Override
    public void updateProductStatus(Long productId, ProductStatus productStatus) {

        ProductEntity product = findById(productId);

        validateUserShop(product);

        product.setStatus(productStatus);

        productRepository.save(product);

    }

    @Override
    public void updateProductRating(Long productId,Float rating) {

        ProductEntity product = findById(productId);

        validateUserShop(product);

        product.setRating(rating);

        productRepository.save(product);

    }

// --------------------------------------- Extra ---------------------------------------

    public ProductEntity findById(Long productId){

       return productRepository.findById(productId)
               .orElseThrow(()-> new ProductException("Product not found with id: " + productId, HttpStatus.NOT_FOUND));

    }

    private void validateUserShop(ProductEntity product){

        ShopGrpcResponse shopGrpcResponse = productGrpcClientService.getShopByUserId(UserHelper.getUserId());

        if (!product.getShopId().equals(shopGrpcResponse.getShopId())){
            throw  new ProductException("Your don't have access to modify this product", HttpStatus.NOT_FOUND);
        }

    }



}
