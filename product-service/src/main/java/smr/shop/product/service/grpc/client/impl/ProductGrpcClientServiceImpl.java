package smr.shop.product.service.grpc.client.impl;

import org.springframework.stereotype.Component;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.discount.libs.grpc.product.discount.DiscountType;
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
        return CategoryGrpcResponse.newBuilder()
                .setId(1)
                .setName("Ali")
                .setSlug("product-slug")
                .build();
    }

    @Override
    public BrandGrpcResponse getBrand(Long categoryId) {
            return BrandGrpcResponse.newBuilder()
                    .setId(1)
                    .setName("Ali")
                    .setSlug("product-slug")
                    .setDescription("Ala bu qeseng descriptiondur")
                    .setImageUrl("qwertyuiop")
                    .build();
    }

    @Override
    public UploadGrpcResponse getImage(String thumbnail) {
        return UploadGrpcResponse.newBuilder()
                .setId("12345678")
                .setUrl("qwertyuiop")
                .build();
    }

    @Override
    public ShopGrpcResponse getShopByShopId(Long shopId) {
        return ShopGrpcResponse.newBuilder()
                .setShopId(1)
                .setSlug("123456")
                .setName("qwerty")
                .setThumbnail("2345678")
                .build();
    }

    @Override
    public ShopGrpcResponse getShopByUserId(UUID userId) {
        return ShopGrpcResponse.newBuilder()
                .setShopId(2)
                .setSlug("123456")
                .setName("qwerty")
                .setThumbnail("2345678")
                .build();
    }

    @Override
    public DiscountGrpcResponse getDiscount(Long id) {
        return DiscountGrpcResponse.newBuilder()
                .setDiscountId(34)
                .setType(DiscountType.Amount)
                .setAmount(345678.789)
                .setPercent(345)
                .build();
    }

    @Override
    public List<ProductStockGrpcResponse> getStock(Long id) {

        ProductStockGrpcResponse productStockGrpcResponse = ProductStockGrpcResponse.newBuilder()
                .setId(1)
                .setQuantity(1)
                .setAttributeName("productStockGrpcResponse")
                .build();

        ProductStockGrpcResponse productStockGrpcResponse1 = ProductStockGrpcResponse.newBuilder()
                .setId(2)
                .setQuantity(3)
                .setAttributeName("productStockGrpcResponse1")
                .build();

        return List.of(productStockGrpcResponse1, productStockGrpcResponse);
    }
}

