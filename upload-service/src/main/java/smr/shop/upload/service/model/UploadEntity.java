package smr.shop.upload.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import smr.shop.upload.service.model.valueobject.UploadStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UploadEntity {
    @Id
    private UUID id;

    private String url;

    @Enumerated(EnumType.STRING)
    private UploadStatus status;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}