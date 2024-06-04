package smr.shop.upload.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.upload.service.model.UploadEntity;

import java.util.Optional;
import java.util.UUID;

public interface UploadRepository extends JpaRepository<UploadEntity, UUID> {
    Optional<UploadEntity> findByUrl(String imageUrl);
}
