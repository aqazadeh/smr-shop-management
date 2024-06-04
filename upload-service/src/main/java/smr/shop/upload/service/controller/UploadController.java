package smr.shop.upload.service.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import smr.shop.upload.service.dto.response.UploadResponse;
import smr.shop.upload.service.service.UploadService;

@RestController
@RequestMapping("/api/1.0/uploads")
public class UploadController {
    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping
    public ResponseEntity<UploadResponse> uploadImage(@RequestPart MultipartFile file) {

        UploadResponse response = uploadService.upload(file);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/images/{url}")
    public ResponseEntity<Resource> getBucketsImages(@PathVariable String url) {
        var data = uploadService.getImage(url);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(new ByteArrayResource(data));
    }
}
