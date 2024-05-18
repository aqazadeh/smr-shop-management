package smr.shop.delivery.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.delivery.service.model.DeliveryConversation;

import java.util.UUID;

public interface DeliveryConversationRepository extends JpaRepository<DeliveryConversation, UUID> {
}
