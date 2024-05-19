package smr.shop.category.brand.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/14/2024
 * Time: 4:27 PM
 */
@Value
@Builder
public class CategoryResponse {

    Long id;

    String name;

    String slug;

    String description;

    List<CategoryResponse> children;
}
