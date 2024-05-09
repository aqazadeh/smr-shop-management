package smr.shop.shop.service.model.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public class ShopAddress {

    private String name;

    private String street;

    private String city;

    private String state;

    private Float longitude;

    private Float latitude;
}
