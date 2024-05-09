package smr.shop.order.service.model.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderAddress {

    private String name;

    private String street;

    private String city;

    private String state;

    private Float longitude;

    private Float latitude;
}
