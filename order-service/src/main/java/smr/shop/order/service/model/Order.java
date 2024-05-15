package smr.shop.order.service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import smr.shop.order.service.model.valueobject.OrderAddress;
import smr.shop.order.service.model.valueobject.PaymentStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "orders")
public class Order {
    @Id
    private UUID id;

    private UUID userId;

    @Embedded
    private OrderAddress address;

    private Double shippingCost;

    @ElementCollection
    private List<UUID> couponId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @CreatedDate
    private ZonedDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;

}