package smr.shop.shop.service.model.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ShopAddress {

    private String name;

    private String street;

    private String city;

    private String state;
}
