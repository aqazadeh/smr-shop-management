package smr.shop.payment.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import smr.shop.payment.service.model.valueobject.TransactionStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class WithdrawTransactions {
    @Id
    private UUID id;


    private Long shopId;

    private Long courier;

    //TODO add transaction detail

    private TransactionStatus status;

    @CreationTimestamp
    private ZonedDateTime createdAt;
}
