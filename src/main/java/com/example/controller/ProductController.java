package com.example.controller;


import com.example.common.request.ProductRequest;
import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import com.example.repository.mysql.ProductRepository;
import com.example.services.ProductServices;
import com.example.services.UserServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductController")
@RestController
@CrossOrigin("*")
@RequestMapping("api/product")
public class ProductController {
	@Autowired
	ProductServices productServices;

	@PostMapping(value = "createProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
		if (productServices.createProduct(productRequest))
			return new ResponseEntity<>(productRequest, HttpStatus.OK);
		else
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
	}


	@GetMapping(value="getAllProduct")
	public ResponseEntity<?>getAllProduct(){
		var response = productServices.getAllProduct();
		if (response != null){
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
	}
}
