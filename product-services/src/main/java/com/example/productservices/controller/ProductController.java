package com.example.productservices.controller;

import com.core.request.ProductRequest;
import com.core.response.CommonResponse;
import com.core.response.ProductResponse;
import com.example.productservices.services.ProductServices;
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

@Tag(name = "ProductController")
@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping(value = "createProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
        if (productServices.isExists(productRequest.getName()))
            return new ResponseEntity<>("Product is exists", HttpStatus.BAD_REQUEST);
        ProductResponse productResponse = productServices.createProduct(productRequest);
        if (productResponse != null){
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }


    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @GetMapping(value="getProductByKeyword")
    public ResponseEntity<?>getProductByKeyword(@RequestParam int page,
                                                @RequestParam int size,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String brand,
                                                @RequestParam(required = false) String category,
                                                @RequestParam(required = false, defaultValue = "0") float price){
        CommonResponse commonResponse = productServices.getProductByKeyWord(page, size, name, brand, category, price);
        if (commonResponse != null){
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }


    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @GetMapping(value="getAllProduct")
    public ResponseEntity<?>getAllProduct(@RequestParam int page,
                                          @RequestParam int size){
        CommonResponse commonResponse = productServices.getAllProduct(page, size);
        if (commonResponse != null){
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }


    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            security = {@SecurityRequirement(name = "Authorization")})
    @PutMapping(value = "updateProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>updateProduct(@RequestParam int id, @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productServices.updateProduct(id, productRequest);
        if (productResponse != null){
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }


    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            security = {@SecurityRequirement(name = "Authorization")})
    @DeleteMapping(value = "deleteProduct")
    public ResponseEntity<?>deleteProduct(@RequestParam int id){
        if (productServices.deleteProduct(id)){
            return new ResponseEntity<>("product is deleted", HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }
}