package smr.shop.product.service.grpc.client.impl;

import org.springframework.stereotype.Component;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.product.service.grpc.client.ProductGrpcClientService;

import java.util.List;
import java.util.UUID;

@Component
public class ProductGrpcClientServiceImpl implements ProductGrpcClientService {
    @Override
    public CategoryGrpcResponse getCategory(Long categoryId) {
        return null;
    }

    @Override
    public BrandGrpcResponse getBrand(Long categoryId) {
        return null;
    }

    @Override
    public UploadGrpcResponse getImage(String thumbnail) {
        return null;
    }

    @Override
    public ShopGrpcResponse getShopByShopId(Long shopId) {
        return null;
    }

    @Override
    public ShopGrpcResponse getShopByUserId(UUID userId) {
        return null;
    }

    @Override
    public DiscountGrpcResponse getDiscount(Long id) {
        return null;
    }

    @Override
    public List<ProductStockGrpcResponse> getStock(Long id) {
        return List.of();
    }
}
