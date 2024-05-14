package smr.shop.ticket.service.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.ticket.service.model.TicketMessage;


import java.awt.print.Pageable;
import java.util.UUID;

public interface TicketMessageRepository extends JpaRepository<TicketMessage, UUID> {
    Page<TicketMessage> findAllByTicketId(UUID ticketId, Pageable pageable);
}