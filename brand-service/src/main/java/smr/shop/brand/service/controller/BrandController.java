package smr.shop.brand.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.brand.service.dto.request.BrandCreateRequest;
import smr.shop.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.brand.service.dto.response.BrandResponse;
import smr.shop.brand.service.service.BrandService;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BrandResponse> createBrand(@RequestBody BrandCreateRequest brandCreateRequest) {
        log.info("Create brand request: {}", brandCreateRequest);
        BrandResponse brandResponse = brandService.create(brandCreateRequest);
        return ResponseEntity.ok(brandResponse);
    }

    @PutMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BrandResponse> updateBrand(
            @PathVariable(name = "brandId") Long brandId,
            @RequestBody BrandUpdateRequest brandUpdateRequest) {
        BrandResponse brandResponse = brandService.updateBrand(brandId, brandUpdateRequest);
        return ResponseEntity.ok(brandResponse);
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BrandResponse>> getAllBrand(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(brandService.getAllBrands(page));
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


    @DeleteMapping("/{brandId}/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> deleteBrandImage(@PathVariable Long brandId) {
        brandService.deleteBrandImage(brandId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Image delete successfully with brandId: " + brandId)
                .build();

        return ResponseEntity.ok(response);
    }
}

