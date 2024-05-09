package smr.shop.ticket.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import smr.shop.ticket.service.model.valueobject.WithdrawStatus;
import smr.shop.ticket.service.model.valueobject.WithdrawType;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class WithdrawRequest {
    @Id
    private UUID id;

    private WithdrawType type;

    private Long shopId;

    private Long courierId;

    private Double amount;

    private WithdrawStatus status;

    private String failureMessage;

    @CreatedDate
    private ZonedDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;

}