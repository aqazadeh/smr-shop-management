package smr.shop.upload.service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import smr.shop.upload.service.model.valueobject.UploadStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Upload {
    @Id
    private UUID id;

    private String fileOriginalName;

    private Integer fileSize;

    private String extension;

    private UploadStatus status;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}