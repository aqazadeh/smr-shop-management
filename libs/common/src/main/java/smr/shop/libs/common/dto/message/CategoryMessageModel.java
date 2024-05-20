package smr.shop.libs.common.dto.message;

import lombok.*;
import smr.shop.libs.common.messaging.BaseMessageModel;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class CategoryMessageModel implements BaseMessageModel {

    private Long id;


    private String name;

    private String description;

    private String slug;

    private Long parentId;

}
