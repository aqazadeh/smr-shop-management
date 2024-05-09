package smr.shop.shop.service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import smr.shop.shop.service.model.valueobject.ShopAddress;
import smr.shop.shop.service.model.valueobject.ShopStatus;

import java.time.ZonedDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;

    private String slug;

    private String logo;

    private String phone;

    @Embedded
    private ShopAddress address;

    private Double rating;

    private Integer reviewsCount;

    private Integer salesCount;

    private Integer viewCount;

    @Enumerated(EnumType.STRING)
    private ShopStatus status;

    @CreatedDate
    private ZonedDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;

}