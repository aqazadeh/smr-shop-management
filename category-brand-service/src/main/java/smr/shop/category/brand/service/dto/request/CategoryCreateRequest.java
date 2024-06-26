package smr.shop.category.brand.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/14/2024
 * Time: 4:30 PM
 */
@Value
public class CategoryCreateRequest {

    @NotBlank
    @Size(min = 4)
    String name;

//   TODO create slug

    @NotBlank
    @Size(min = 50)
    String description;

    Long parentId;

}
