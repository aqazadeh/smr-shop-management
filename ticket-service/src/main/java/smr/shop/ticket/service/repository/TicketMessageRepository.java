package smr.shop.ticket.service.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.ticket.service.model.TicketMessage;

import java.util.UUID;

public interface TicketMessageRepository extends JpaRepository<TicketMessage, UUID> {
    Page<TicketMessage> findAllByTicketId(UUID ticketId, Pageable pageable);
}