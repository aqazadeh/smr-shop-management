package smr.shop.product.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.product.service.dto.request.ProductCreateRequest;
import smr.shop.product.service.dto.request.ProductUpdateRequest;
import smr.shop.product.service.dto.response.ProductResponse;
import smr.shop.product.service.model.valueobject.ProductStatus;
import smr.shop.product.service.service.ProductService;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 6:42 AM
 */
@RestController
@RequestMapping("/api/1.0/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    ---------------------------------- POST ----------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> createProduct(@RequestBody ProductCreateRequest request){
        productService.createProduct(request);
        EmptyResponse response = EmptyResponse.builder().message("successfully created product").build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}/thumbnail/{imageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> addProductThumbNail(@PathVariable Long productId,
                                                             @PathVariable String imageId){
        productService.addProductThumbNail(productId, imageId);
        EmptyResponse response = EmptyResponse.builder().message("successfully added thumbnail to product productId:" + productId).build();
        return ResponseEntity.ok(response);

    }

    @PostMapping("/{productId}/image/{imageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> addProductImage(@PathVariable Long productId,
                                                         @PathVariable String imageId){

        productService.addProductImage(productId,imageId);
        EmptyResponse response = EmptyResponse.builder().message("successfully added image to product productId:" + productId).build();
        return ResponseEntity.ok(response);
    }

//    ---------------------------------- PATCH ----------------------------------

    @PatchMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateProduct(@PathVariable Long productId,
                                                       @RequestBody ProductUpdateRequest request){
        productService.updateProduct(productId, request);
        EmptyResponse response = EmptyResponse.builder().message("successfully updated product productId:" + productId).build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse>  updateProductCategory(@PathVariable Long productId,
                                                                @PathVariable Long categoryId){
        productService.updateProductCategory(productId, categoryId);
        EmptyResponse response = EmptyResponse.builder().message("successfully updated product with category productId:" + productId).build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}/brand/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateProductBrand(@PathVariable Long productId,
                                                            @PathVariable Long brandId){
        productService.updateProductBrand(productId, brandId);
        EmptyResponse response = EmptyResponse.builder().message("successfully updated product with brand productId:" + productId).build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateProductStatus(@PathVariable Long productId,
                                                             @RequestParam ProductStatus productStatus){
        productService.updateProductStatus(productId, productStatus);
        EmptyResponse response = EmptyResponse.builder().message("successfully updated product status productId:" + productId).build();
        return ResponseEntity.ok(response);

    }

//    ---------------------------------- DELETE ----------------------------------

    @DeleteMapping("/{productId}/thumbnail")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> removeProductThumbNail(@PathVariable Long productId){
        productService.removeProductThumbNail(productId);
        EmptyResponse response = EmptyResponse.builder().message("successfully removed thumbnail from product productId:" + productId).build();
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{productId}/image/{imageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> removeProductImage(@PathVariable Long productId,
                                                            @PathVariable String imageId){
        productService.removeProductImage(productId,imageId);
        EmptyResponse response = EmptyResponse.builder().message("successfully removed thumbnail from product productId:" + productId).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        EmptyResponse response = EmptyResponse.builder().message("successfully deleted product productId:" + productId).build();
        return ResponseEntity.ok(response);
    }

//    ---------------------------------- GET ----------------------------------

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> getBrandProducts(@PathVariable Long productId){
        ProductResponse productById = productService.getProductById(productId);
        return ResponseEntity.ok(productById);

    }

    @GetMapping("/slug/{slug}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> getProductBySlug(@PathVariable String slug){
        ProductResponse productById = productService.getProductBySlug(slug);
        return ResponseEntity.ok(productById);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getAllProducts(@RequestParam(value = "page", defaultValue = "0") Integer page){
        List<ProductResponse> productProducts = productService.getAllProducts(page);
        return ResponseEntity.ok(productProducts);
    }

    @GetMapping("/shop/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getShopProducts(@PathVariable Long shopId,
                                                                @RequestParam(name = "page", defaultValue = "0") Integer page){
        List<ProductResponse> responseList = productService.getAllProductsByShopId(shopId, page);
        return ResponseEntity.ok(responseList);

    }

    @GetMapping("/brand/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getBrandProducts(@PathVariable Long brandId,
                                                                  @RequestParam(name = "page", defaultValue = "0") Integer page){
        List<ProductResponse> responseList = productService.getAllProductsByBrandId(brandId, page);
        return ResponseEntity.ok(responseList);

    }

    @GetMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getCategoryProducts(@PathVariable Long categoryId,
                                                                     @RequestParam(name = "page", defaultValue = "0") Integer page){
        List<ProductResponse> responseList = productService.getAllProductsByCategoryId(categoryId, page);
        return ResponseEntity.ok(responseList);

    }

}
