package smr.shop.product.service.service;

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

    void updateProduct(Long productId,ProductUpdateRequest request);

    void updateProductCategory(Long productId,Long categoryId);

    void updateProductBrand(Long productId,Long brandId);

    void updateProductStatus(Long productId, ProductStatus productStatus);

    void updateProductRating(Long productId,Float rating);

    void addProductThumbNail(Long productId, String imageId);

    void removeProductThumbNail(Long productId);

    void addProductImage(Long productId, String imageId);

    void removeProductImage(Long productId, String imageId);

//   TODO change status do not delete product:
void deleteProduct(Long productId);

    void deleteProductsByBrand(Long brandId);

    void deleteProductsByCategory(Long categoryId);

    ProductResponse getProductById(Long productId);

    ProductResponse getProductBySlug(String slug);

    List<ProductResponse> getAllProducts(Integer page);

}
