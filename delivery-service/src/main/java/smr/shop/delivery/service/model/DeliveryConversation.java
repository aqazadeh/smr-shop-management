package smr.shop.delivery.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import smr.shop.delivery.service.model.valueobject.DeliveryConversationStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DeliveryConversation {
    @Id
    private UUID id;

    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private String message;

    @Builder.Default
    private DeliveryConversationStatus status = DeliveryConversationStatus.CREATED;

    @CreatedDate
    private ZonedDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;
}


