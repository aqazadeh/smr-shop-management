package smr.shop.product.service.model;

import jakarta.persistence.*;
import lombok.*;
import smr.shop.product.service.model.valueobject.ProductStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long shopId;

    private Long categoryId;

    private UUID discountId;

    private Long brandId;

    private String name;

    private String slug;

    private String description;

    private String thumbnail;

    @ElementCollection
    private List<String> imageIds;

    @ElementCollection
    private List<String> tags;

    private Double price;

    private Double minPrice;

    private Double maxPrice;

    private Double shippingPrice;

    private Float rating;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ProductStatus status = ProductStatus.PENDING;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}