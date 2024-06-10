package smr.shop.product.service.mapper;

import org.springframework.stereotype.Component;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.ProductStockMessageModel;
import smr.shop.libs.common.dto.message.StockCreateMessageModel;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.product.service.dto.request.ProductCreateRequest;
import smr.shop.product.service.dto.request.ProductStockRequest;
import smr.shop.product.service.dto.request.ProductUpdateRequest;
import smr.shop.product.service.dto.response.*;
import smr.shop.product.service.model.ProductEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 1:12 AM
 */
@Component
public class ProductServiceMapper {


    public ProductEntity productCreateRequestToProductEntity(ProductCreateRequest request) {

        return ProductEntity.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .tags(Arrays.asList(request.getTags().split(",")))
                .price(request.getPrice())
                .minPrice(request.getMinPrice())
                .maxPrice(request.getMaxPrice())
                .shippingPrice(request.getShippingPrice())
                .createdAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)))
                .updatedAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)))
                .build();

    }

    public StockCreateMessageModel productStockRequestToStockCreateMessageModel(List<ProductStockRequest> stocks, Long id) {
        List<ProductStockMessageModel> productStockMessageModelList = stocks.stream().map(stock -> ProductStockMessageModel.builder()
                .productId(id)
                .attributeName(stock.getAttributeName())
                .quantity(stock.getQuantity())
                .build()).toList();
        return StockCreateMessageModel.builder().stocks(productStockMessageModelList).build();
    }

    public ProductEntity productUpdateRequestToProductEntity(ProductEntity product, ProductUpdateRequest request) {
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setTags(Arrays.asList(request.getTags().split(",")));
        product.setPrice(request.getPrice());
        product.setMaxPrice(request.getMaxPrice());
        product.setMinPrice(request.getMinPrice());
        product.setShippingPrice(request.getShippingPrice());
        product.setUpdatedAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)));
        return product;
    }

    public ProductResponse productEntityToProductResponse(ProductEntity product,
                                                          CategoryGrpcResponse categoryGrpcResponse,
                                                          BrandGrpcResponse brandGrpcResponse,
                                                          ShopGrpcResponse shopGrpcResponse,
                                                          DiscountGrpcResponse discountGrpcResponse,
                                                          List<ProductStockGrpcResponse> productStockGrpcResponse) {

        ProductCategoryResponse categoryResponse = ProductCategoryResponse.builder()
                .categoryName(categoryGrpcResponse.getName())
                .categorySlug(categoryGrpcResponse.getSlug())
                .build();
        ProductBrandResponse productBrandResponse = ProductBrandResponse.builder()
                .brandName(brandGrpcResponse.getName()).brandSlug(brandGrpcResponse.getSlug()).brandThumbNail(brandGrpcResponse.getImageUrl()).build();
        ProductDiscountResponse productDiscountResponse = null;
        if (product.getDiscountId() != null) {
            productDiscountResponse = ProductDiscountResponse.builder()
                    .discountId(UUID.fromString(discountGrpcResponse.getId()))
                    .discountPrice(discountGrpcResponse.getAmount())
                    .build();
        }

        ProductShopResponse productShopResponse = ProductShopResponse.builder()
                .shopName(shopGrpcResponse.getName())
                .shopSlug(shopGrpcResponse.getSlug())
                .shopThumbNail(shopGrpcResponse.getLogo())
                .build();
        List<ProductStockResponse> productStockResponseList = productStockGrpcResponse.stream()
                .map(stock -> ProductStockResponse.builder()
                        .id(UUID.fromString(stock.getId()))
                        .name(stock.getName())
                        .quantity(stock.getQuantity())
                        .build())
                .toList();

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getName())
                .productSlug(product.getSlug())
                .productDescription(product.getDescription())
                .productThumbnail(product.getThumbnail())
                .images(product.getImageIds())
                .tags(product.getTags())
                .price(product.getPrice())
                .shippingPrice(product.getShippingPrice())
                .rating(product.getRating())
                .shopDetail(productShopResponse)
                .category(categoryResponse)
                .brand(productBrandResponse)
                .discount(productDiscountResponse)
                .stocks(productStockResponseList)
                .build();
    }

    public ProductGrpcResponse productEntityToProductGrpcResponse(ProductEntity productEntity, DiscountGrpcResponse discountGrpcResponse) {
        return ProductGrpcResponse.newBuilder()
                .setId(productEntity.getId())
                .setSlug(productEntity.getSlug())
                .setShopId(productEntity.getShopId())
                .setPrice(productEntity.getPrice())
                .setShippingPrice(productEntity.getShippingPrice())
                .setName(productEntity.getName())
                .setThumbnail(productEntity.getThumbnail())
                .setDiscount(discountGrpcResponse)
                .build();
    }
}
