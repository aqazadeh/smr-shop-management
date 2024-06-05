package smr.shop.user.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.user.service.model.UserAddress;

import java.util.List;
import java.util.UUID;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {

    List<UserAddress> findAllByUserId(UUID userId);
}