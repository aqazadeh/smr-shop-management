package smr.shop.ticket.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.ticket.service.model.Ticket;

import java.awt.print.Pageable;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Page<Ticket> findAllByUserId(Long userId, Pageable pageable);
}