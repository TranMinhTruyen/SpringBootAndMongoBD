package com.example.controller;

import com.example.common.jwt.CustomUserDetail;
import com.example.common.jwt.JWTAuthenticationFilter;
import com.example.common.model.User;
import com.example.common.request.CartRequest;
import com.example.common.response.CartResponse;
import com.example.services.CartServices;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tran Minh Truyen
 */

@Tag(name = "CartController")
@RestController
@CrossOrigin("*")
@RequestMapping("api/cart")
public class CartController {

	@Autowired
	private CartServices cartServices;

	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@PostMapping(value = "createCartAndAddProductToCart")
	public ResponseEntity<?> createCartAndAddProductToCart(@RequestParam int productId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
				authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
				)
		){
			CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
			if (!cartServices.isCartExists(customUserDetail.getUser().getId())) {
				cartServices.addProductToCart(customUserDetail.getUser().getId(), productId);
				return new ResponseEntity<>("Cart is create", HttpStatus.OK);
			}
			else {
				cartServices.addProductToCart(customUserDetail.getUser().getId(), productId);
				return new ResponseEntity<>("Product is added", HttpStatus.OK);
			}
		}
		else return new ResponseEntity<>("You don't have permission", HttpStatus.UNAUTHORIZED);
	}

	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@GetMapping(value="getCartById")
	public ResponseEntity<?>getCartById(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
		){
			CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
			CartResponse cartResponse = cartServices.getCartById(customUserDetail.getUser().getId());
			if (cartResponse != null){
				return new ResponseEntity<>(cartResponse, HttpStatus.OK);
			}
			else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		else return new ResponseEntity<>("You don't have permission", HttpStatus.UNAUTHORIZED);
	}

	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@PutMapping(value = "updateProductAmount")
	public ResponseEntity<?>updateProductAmount(@RequestParam int productId,
												@RequestParam long amount) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
				)
		){
			CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
			if (cartServices.updateProductAmount(customUserDetail.getUser().getId(), productId, amount))
				return new ResponseEntity<>("Product amount is updated", HttpStatus.OK);
			else return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
		}
		else{
			return new ResponseEntity<>("You don't have permission", HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@DeleteMapping(value = "removeProductFromCart")
	public ResponseEntity<?>updateProductList(@RequestParam int productId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
				)
		){
			CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
		 	if(cartServices.removeProductFromCart(customUserDetail.getUser().getId(), productId))
				return new ResponseEntity<>("Product is removed", HttpStatus.OK);
		 	else return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
		}
		else{
			return new ResponseEntity<>("You don't have permission", HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@DeleteMapping(value = "deleteCart")
	public ResponseEntity<?>deleteCart(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
				)
		){
			CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
			cartServices.deleteCart(customUserDetail.getUser().getId());
			return new ResponseEntity<>("Cart is deleted", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("You don't have permission", HttpStatus.UNAUTHORIZED);
	}
}
