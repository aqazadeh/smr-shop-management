package smr.shop.product.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.product.service.dto.messaging.StockCreateMessageModel;
import smr.shop.product.service.dto.request.ProductCreateRequest;
import smr.shop.product.service.dto.request.ProductStockRequest;
import smr.shop.product.service.dto.request.ProductUpdateRequest;
import smr.shop.product.service.dto.response.ProductResponse;
import smr.shop.product.service.model.ProductEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

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
                .categoryId(request.getCategoryId())
                .brandId(request.getBrandId())
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .imageIds(request.getImageIds())
                .tags(Arrays.asList(request.getTags().split(",")))
                .price(request.getPrice())
                .minPrice(request.getMinPrice())
                .maxPrice(request.getMaxPrice())
                .shippingPrice(request.getShippingPrice())
                .createdAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)))
                .updatedAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)))
                .build();

    }

    public StockCreateMessageModel productStockRequestToStockCreateMessageModel(ProductStockRequest stock) {
        return StockCreateMessageModel.builder()
                .attributeName(stock.getAttributeName())
                .quantity(stock.getQuantity())
                .build();
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

    public ProductResponse productEntityToProductResponse(ProductEntity product) {
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
                .build();
    }
}
