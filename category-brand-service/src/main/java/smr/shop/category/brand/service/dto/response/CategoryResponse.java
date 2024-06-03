package smr.shop.category.brand.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/14/2024
 * Time: 4:27 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse implements Serializable {

    private Long id;

    private String name;

    private String slug;

    private String description;

    private List<CategoryResponse> children;
}
