package smr.shop.category.brand.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String imageUrl;

    private String slug;

    private String description;

    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}