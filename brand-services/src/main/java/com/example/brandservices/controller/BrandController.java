package com.example.brandservices.controller;

import com.core.request.BrandRequest;
import com.core.request.ProductRequest;
import com.core.response.BrandResponse;
import com.core.response.CommonResponse;
import com.example.brandservices.services.BrandServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BrandController")
@RestController
@CrossOrigin("*")
public class BrandController {

    @Autowired
    private BrandServices brandServices;

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping(value = "createBrand", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBrand(@RequestBody BrandRequest brandRequest) {
        if (brandServices.isExists(brandRequest.getName()))
            return new ResponseEntity<>("Brand is exists", HttpStatus.BAD_REQUEST);
        if (brandServices.createBrand(brandRequest))
            return new ResponseEntity<>(brandRequest, HttpStatus.OK);
        else
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @GetMapping(value = "getBrandByKeyword")
    public ResponseEntity<?> getBrandByKeyword(@RequestParam int page,
                                               @RequestParam int size,
                                               @RequestParam(required = false) String keyword) {
        CommonResponse commonResponse = brandServices.getBrandbyKeyword(page, size, keyword);
        if (commonResponse != null) {
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @GetMapping(value = "getAllBrand")
    public ResponseEntity<?> getAllBrand(@RequestParam int page,
                                         @RequestParam int size) {
        CommonResponse commonResponse = brandServices.getAllBrand(page, size);
        if (commonResponse != null) {
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            security = {@SecurityRequirement(name = "Authorization")})
    @PutMapping(value = "updateBrand", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBrand(@RequestParam int id, @RequestBody BrandRequest brandRequest) {
        BrandResponse brandResponse = brandServices.updateBrand(id, brandRequest);
        if (brandResponse != null) {
            return new ResponseEntity<>(brandResponse, HttpStatus.OK);
        } else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            security = {@SecurityRequirement(name = "Authorization")})
    @DeleteMapping(value = "deleteBrand")
    public ResponseEntity<?> deleteBrand(@RequestParam int id) {
        if (brandServices.deleteBrand(id)) {
            return new ResponseEntity<>("category is deleted", HttpStatus.OK);
        } else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }
}

