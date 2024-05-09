package smr.shop.flash.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.flash.service.model.FlashDealProductEntity;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 7:58 PM
 */

public interface FlashDealProductRepository extends JpaRepository<FlashDealProductEntity, UUID> {
}
