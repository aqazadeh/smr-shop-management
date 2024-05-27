package smr.shop.libs.outbox.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.libs.common.messaging.BaseMessageModel;
import smr.shop.libs.outbox.helper.OutboxHelper;
import smr.shop.libs.outbox.model.OutboxEntity;
import smr.shop.libs.outbox.repository.OutboxRepository;

import java.util.List;

@Service
public class OutboxService {
    private final OutboxRepository outboxRepository;

    public OutboxService(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public <T extends BaseMessageModel> void save(T object, String event) {
        String payload = OutboxHelper.convertToString(object);
        OutboxEntity outboxEntity = OutboxEntity.of(event, payload);
        outboxRepository.save(outboxEntity);
    }

    @Transactional
    public void remove(OutboxEntity outboxEntity) {
        outboxRepository.delete(outboxEntity);
    }

    public List<OutboxEntity> findAll() {
        return outboxRepository.findAll();
    }
}
