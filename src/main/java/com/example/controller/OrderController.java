package com.example.controller;

import com.example.common.request.OrderRequest;
import com.example.common.request.ProductRequest;
import com.example.common.response.CommonResponse;
import com.example.services.OrderServices;
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

@Tag(name = "OrderController")
@RestController
@CrossOrigin("*")
@RequestMapping("api/order")
public class OrderController {

	@Autowired
	private OrderServices orderServices;

	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@PostMapping(value = "createOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
				)
		){
			if (orderServices.createOrder(orderRequest))
				return new ResponseEntity<>(orderRequest, HttpStatus.OK);
			else
				return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		else{
			if (authentication == null){
				return new ResponseEntity<>("Please login", HttpStatus.UNAUTHORIZED);
			}
			else
				return new ResponseEntity<>("You don't have permission", HttpStatus.UNAUTHORIZED);
		}
	}


	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@GetMapping(value="getOrderByCustomerId")
	public ResponseEntity<?>getOrderByCustomerId(@RequestParam int page,
												@RequestParam int size,
												@RequestParam int id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("EMP")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
				)
		){
			CommonResponse commonResponse = orderServices.getOrderByCustomerId(page, size, id);
			if (commonResponse != null){
				return new ResponseEntity<>(commonResponse, HttpStatus.OK);
			}
			else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		else{
			if (authentication == null){
				return new ResponseEntity<>("Please login", HttpStatus.UNAUTHORIZED);
			}
			else
				return new ResponseEntity<>("You don't have permission", HttpStatus.UNAUTHORIZED);
		}
	}


	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@PutMapping(value = "updateOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?>updateOrder(@RequestParam int id, @RequestBody OrderRequest orderRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("EMP")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
				)
		){
			if (orderServices.updateOrder(id, orderRequest)){
				return new ResponseEntity<>(orderRequest, HttpStatus.OK);
			}
			else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		else{
			if (authentication == null){
				return new ResponseEntity<>("Please login", HttpStatus.UNAUTHORIZED);
			}
			else
				return new ResponseEntity<>("You don't have permission", HttpStatus.UNAUTHORIZED);
		}
	}



	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@DeleteMapping(value = "deleteOrder")
	public ResponseEntity<?>deleteOrder(@RequestParam int id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) ||
								authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
				)
		){
			if (orderServices.deleteOrder(id)){
				return new ResponseEntity<>("order is deleted", HttpStatus.OK);
			}
			else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		else{
			if (authentication == null){
				return new ResponseEntity<>("Please login", HttpStatus.UNAUTHORIZED);
			}
			else
				return new ResponseEntity<>("You don't have permission", HttpStatus.UNAUTHORIZED);
		}
	}
}
