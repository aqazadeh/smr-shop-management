package smr.shop.payment.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import smr.shop.payment.service.model.valueobject.PaymentMethod;
import smr.shop.payment.service.model.valueobject.PaymentStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Payment {
    @Id
    private UUID id;

    private UUID orderId;

    private PaymentStatus paymentStatus;

    private Double amount;

    private PaymentMethod method;

    @CreatedDate
    private ZonedDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;

}