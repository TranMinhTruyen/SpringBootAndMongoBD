package com.example.controller;


import com.example.common.request.ProductRequest;
import com.example.common.request.UserRequest;
import com.example.common.response.CartResponse;
import com.example.common.response.CommonResponse;
import com.example.common.response.ProductResponse;
import com.example.services.ProductServices;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tran Minh Truyen
 */

@Tag(name = "ProductController")
@RestController
@CrossOrigin("*")
@RequestMapping("api/product")
public class ProductController {

	@Autowired
	private ProductServices productServices;

	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@PostMapping(value = "createProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("EMP"))
				)
		){
			if (productServices.isExists(productRequest.getName()))
				return new ResponseEntity<>("Product is exists", HttpStatus.BAD_REQUEST);
			if (productServices.createProduct(productRequest))
				return new ResponseEntity<>(productRequest, HttpStatus.OK);
			else
				return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		else return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
	}


	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
	@GetMapping(value="getProductByKeyword")
	public ResponseEntity<?>getProductByKeyword(@RequestParam int page,
												@RequestParam int size,
												@RequestParam(required = false) String keyword){
		CommonResponse commonResponse = productServices.getProductByKeyWord(page, size, keyword);
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("EMP"))
				)
		){
			if (productServices.updateProduct(id, productRequest)){
				return new ResponseEntity<>(productRequest, HttpStatus.OK);
			}
			else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		else return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
	}


	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@DeleteMapping(value = "deleteProduct")
	public ResponseEntity<?>deleteProduct(@RequestParam int id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))))
		{
			if (productServices.deleteProduct(id)){
				return new ResponseEntity<>("product is deleted", HttpStatus.OK);
			}
			else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		else return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
	}
}
