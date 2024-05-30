package smr.shop.libs.outbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.libs.outbox.model.OutboxEntity;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<OutboxEntity, UUID> {

    List<OutboxEntity> findAllByEventIn(List<String> event);
}
