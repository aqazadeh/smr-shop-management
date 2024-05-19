package smr.shop.category.brand.service.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.category.brand.service.dto.request.BrandCreateRequest;
import smr.shop.category.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.category.brand.service.dto.response.BrandResponse;
import smr.shop.category.brand.service.service.BrandService;
import smr.shop.libs.common.dto.response.EmptyResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/1.0/brands")
@Slf4j
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

//    -------------------------------------- POST --------------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> createBrand(@RequestBody @Valid BrandCreateRequest brandCreateRequest) {
        log.info("Create brand request: {}", brandCreateRequest);
        brandService.createBrand(brandCreateRequest);
        EmptyResponse response = EmptyResponse.builder()
                .message("Brand created successfully")
                .build();

        return ResponseEntity.ok(response);
    }

//    -------------------------------------- PUT --------------------------------------

    @PutMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateBrand(
            @PathVariable(name = "brandId") Long brandId,
            @RequestBody @Valid BrandUpdateRequest brandUpdateRequest) {
        brandService.updateBrand(brandId, brandUpdateRequest);
        EmptyResponse response = EmptyResponse.builder()
                .message("Brand updated successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{brandId}/image/{imageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateBrandImage(@PathVariable Long brandId,
                                                          @PathVariable UUID imageId) {
        brandService.updateBrandImage(brandId, imageId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Image updated successfully with brandId: " + brandId + " and image id: " + imageId)
                .build();

        return ResponseEntity.ok(response);
    }

//    -------------------------------------- GET --------------------------------------

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BrandResponse>> getAllBrand(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(brandService.getAllBrands(page));
    }

//    -------------------------------------- DELETE --------------------------------------

    @DeleteMapping("/{brandId}/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> deleteBrandImage(@PathVariable Long brandId) {
        brandService.deleteBrandImage(brandId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Image delete successfully with brandId: " + brandId)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{brandId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<EmptyResponse> deleteBrand(
            @PathVariable(name = "brandId") Long brandId) {
        brandService.deleteBrand(brandId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Brand deleted successfully with id " + brandId)
                .build();
        return ResponseEntity.ok(response);
    }
}

