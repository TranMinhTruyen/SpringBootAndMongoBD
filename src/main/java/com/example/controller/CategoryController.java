package com.example.controller;


import com.example.common.request.BrandRequest;
import com.example.common.request.CategoryRequest;
import com.example.services.CategoryServices;
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

@Tag(name = "CategoryController")
@RestController
@CrossOrigin("*")
@RequestMapping("api/category")
public class CategoryController {
	@Autowired
	private CategoryServices categoryServices;

	@Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
			security = {@SecurityRequirement(name = "Authorization")})
	@PostMapping(value = "createCategory", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
				(
						authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))
				)
		){
			if (categoryServices.isExists(categoryRequest.getName())){
				return new ResponseEntity<>("Category is exists", HttpStatus.UNAUTHORIZED);
			}
			if (categoryServices.createCategory(categoryRequest))
				return new ResponseEntity<>(categoryRequest, HttpStatus.OK);
			else
				return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}
		else return new ResponseEntity<>("You don't have permission", HttpStatus.UNAUTHORIZED);
	}
}
