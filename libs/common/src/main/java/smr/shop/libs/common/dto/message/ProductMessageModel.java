package smr.shop.libs.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smr.shop.libs.common.messaging.BaseMessageModel;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductMessageModel implements BaseMessageModel {

    private Long id;

    private List<ProductStockMessageModel> stocks;

}
