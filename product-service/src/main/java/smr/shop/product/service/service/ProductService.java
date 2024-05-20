package smr.shop.product.service.service;

import smr.shop.libs.common.dto.message.CategoryMessageModel;
import smr.shop.libs.common.dto.message.DiscountMessageModel;
import smr.shop.libs.common.dto.message.RatingMessageModel;
import smr.shop.libs.common.dto.message.ShopMessageModel;
import smr.shop.libs.grpc.object.ProductGrpcId;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.ShopProductGrpcId;
import smr.shop.product.service.dto.request.ProductCreateRequest;
import smr.shop.product.service.dto.request.ProductUpdateRequest;
import smr.shop.product.service.dto.response.ProductResponse;
import smr.shop.product.service.model.valueobject.ProductStatus;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 12:49 AM
 */

public interface ProductService {

    void createProduct(ProductCreateRequest request);

    void addProductThumbNail(Long productId, String imageId);

    void addProductImage(Long productId, String imageId);

    void updateProduct(Long productId, ProductUpdateRequest request);

    void updateProductCategory(Long productId, Long categoryId);

    void updateProductBrand(Long productId, Long brandId);

    void updateProductStatus(Long productId, ProductStatus productStatus);

    void updateProductRating(RatingMessageModel message);

    void removeProductDiscount(DiscountMessageModel message);

    void removeProductThumbNail(Long productId);

    void removeProductImage(Long productId, String imageId);

    void deleteProduct(Long productId);

    void deleteProductsByBrand(Long brandId);

    void deleteProductsByShop(ShopMessageModel shopMessageModel);

    void deleteProductsByCategory(CategoryMessageModel categoryMessageModel);

    ProductResponse getProductById(Long productId);

    ProductResponse getProductBySlug(String slug);

    List<ProductResponse> getAllProducts(Integer page);

    List<ProductResponse> getAllProductsByBrandId(Long brandId, Integer page);

    List<ProductResponse> getAllProductsByCategoryId(Long categoryId, Integer page);

    List<ProductResponse> getAllProductsByShopId(Long shopId, Integer page);

    ProductGrpcResponse getProductInformation(ProductGrpcId request);

    ProductGrpcResponse getProductInformation(ShopProductGrpcId request);
}
