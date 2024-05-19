package smr.shop.category.brand.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/14/2024
 * Time: 4:20 PM
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String slug;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<CategoryEntity> children;

    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}
