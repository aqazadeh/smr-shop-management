package smr.shop.product.service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import smr.shop.product.service.model.valueobject.ProductStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long shopId;

    private Long categoryId;

    private Long brandId;

    private String name;

    private String slug;

    private String description;

    private String thumbnail;

    @ElementCollection
    private List<String> imageIds;

    private String tags;

    private Double price;

    private Double minPrice;

    private Double maxPrice;

    private Double shippingPrice;

    private Float rating;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}