package smr.shop.courier.service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CourierReview {
    @Id
    private UUID id;

    private UUID orderId;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    private Byte reviewScore;

    @CreatedDate
    private ZonedDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;
}

/*
Istifadeci (sifaris veren) 1-5 arasinda kuryere reyting verir.
bu taksi programlarindaki sistem kimi isleyir

 */