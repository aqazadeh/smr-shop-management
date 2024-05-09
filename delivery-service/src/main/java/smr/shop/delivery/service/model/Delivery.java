package smr.shop.delivery.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import smr.shop.delivery.service.model.valueobject.DeliveryStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long courierId;

    private UUID orderId;

    private DeliveryStatus status;

    @CreatedDate
    private ZonedDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;

}

/*
kuryer bir sifarisi aldiqda bu table de tutulur,
kuryer sifarisi qebul etdikde status stared olur, kuryer yolda olarken qeza ve
diger hadiseler oldugu halda failed, tamamlandiqda ise complated statusuna kecir
 */