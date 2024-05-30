package smr.shop.flash.service.model;

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
public class FlashEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String imageId;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private String slug;

    @Builder.Default
    private Boolean isActive = Boolean.TRUE;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}