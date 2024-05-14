package smr.shop.product.service.grpc;

import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 1:15 AM
 */

public interface ProductGrpcClientService {
    CategoryGrpcResponse getCategory(Long categoryId);

    BrandGrpcResponse getBrand(Long categoryId);

    UploadGrpcResponse getImage(String thumbnail);

    ShopGrpcResponse getShopByShopId(Long shopId);

    ShopGrpcResponse getShopByUserId(Long userId);

    DiscountGrpcResponse getDiscount(Long id);

    List<ProductStockGrpcResponse> getStock(Long id);

}
