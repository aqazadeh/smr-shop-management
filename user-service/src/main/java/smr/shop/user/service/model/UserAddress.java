package smr.shop.user.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserAddress {
    @Id
    private UUID id;
    private UUID userId;
    private String name;
    private String street;
    private String city;
    private String state;
}