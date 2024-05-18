package smr.shop.auth.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.auth.service.model.UserAddress;

import java.util.UUID;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {

}