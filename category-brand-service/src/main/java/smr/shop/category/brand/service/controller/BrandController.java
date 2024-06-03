package smr.shop.category.brand.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import smr.shop.libs.common.dto.response.ErrorResponse;

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
    @Operation(summary = "Create new Category", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> createBrand(@RequestBody @Valid BrandCreateRequest brandCreateRequest) {
        brandService.createBrand(brandCreateRequest);
        EmptyResponse response = EmptyResponse.builder()
                .message("Brand created successfully")
                .build();

        return ResponseEntity.ok(response);
    }

//    -------------------------------------- PUT --------------------------------------

    @PutMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update brand with id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateBrand(@PathVariable(name = "brandId") Long brandId, @RequestBody @Valid BrandUpdateRequest brandUpdateRequest) {
        brandService.updateBrand(brandId, brandUpdateRequest);
        EmptyResponse response = EmptyResponse.builder()
                .message("Brand updated successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{brandId}/image/{imageId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update brand image with brandId and imageId", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateBrandImage(@PathVariable Long brandId, @PathVariable UUID imageId) {
        brandService.updateBrandImage(brandId, imageId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Image updated successfully with brandId: " + brandId + " and image id: " + imageId)
                .build();

        return ResponseEntity.ok(response);
    }

//    -------------------------------------- GET --------------------------------------

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all brands data", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BrandResponse.class))))
    })
    public ResponseEntity<List<BrandResponse>> getAllBrand(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(brandService.getAllBrands(page));
    }


    @GetMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get brand with id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable Long brandId) {
        return ResponseEntity.ok(brandService.getBrand(brandId));
    }

//    -------------------------------------- DELETE --------------------------------------

    @DeleteMapping("/{brandId}/image")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete brand image with id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> deleteBrandImage(@PathVariable Long brandId) {
        brandService.deleteBrandImage(brandId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Image delete successfully with brandId: " + brandId)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete brand with id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> deleteBrand(@PathVariable(name = "brandId") Long brandId) {
        brandService.deleteBrand(brandId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Brand deleted successfully with id " + brandId)
                .build();
        return ResponseEntity.ok(response);
    }
}

