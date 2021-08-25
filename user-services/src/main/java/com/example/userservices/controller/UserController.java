package com.example.userservices.controller;

import com.core.model.CustomUserDetail;
import com.core.model.User;
import com.core.request.LoginRequest;
import com.core.request.ResetPassword;
import com.core.request.UserRequest;
import com.core.response.CommonResponse;
import com.core.response.JwtResponse;
import com.example.userservices.jwt.JWTTokenProvider;
import com.example.userservices.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "UserController")
@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    public ResponseEntity<?> confirmCreateUser(@RequestBody UserRequest userRequest) {
        if (!userServices.accountIsExists(userRequest.getAccount(), userRequest.getEmail())){
            if (userServices.createUser(userRequest) != null) {
                return new ResponseEntity<>("User is added", HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
        }
        else return new ResponseEntity<>("Account is exists", HttpStatus.FORBIDDEN);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest,
                                        @RequestParam (required = false) String confirmKey) {
        if (!userServices.accountIsExists(userRequest.getAccount(), userRequest.getEmail())){
            if (confirmKey == null){
                String key = RandomStringUtils.randomAlphanumeric(10);
                userServices.sendEmailConfirmKey(userRequest.getEmail(), key);
                return new ResponseEntity<>("Please check email to get key", HttpStatus.OK);
            }
            if (userServices.checkConfirmKey(userRequest.getEmail(), confirmKey))
                return confirmCreateUser(userRequest);
            else return new ResponseEntity<>("Error! invalid key", HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>("Account is exists", HttpStatus.FORBIDDEN);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userServices.Login(loginRequest);
        if (user != null && user.isActive()) {
            CustomUserDetail customUserDetail = new CustomUserDetail(user);
            String jwt = jwtTokenProvider.generateToken(customUserDetail);
            JwtResponse jwtResponse = new JwtResponse(jwt);
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Not found user account", HttpStatus.NOT_FOUND);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassword resetPassword) {
        User user = userServices.resetPassword(resetPassword.getEmail());
        if (user != null) {
            return new ResponseEntity<>("Email has been sent", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Not found user email", HttpStatus.NOT_FOUND);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @GetMapping(value="getAllUser")
    public ResponseEntity<?>getAllUser(@RequestParam int page, @RequestParam int size){
        CommonResponse response = userServices.getAllUser(page, size);
        if (response != null){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @GetMapping(value="getUserByKeyword")
    public ResponseEntity<?>getUserByKeyword(@RequestParam int page,
                                             @RequestParam int size,
                                             @RequestParam(required = false) String keyword){
        CommonResponse response = userServices.getUserByKeyWord(page, size, keyword);
        if (response != null){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            security = {@SecurityRequirement(name = "Authorization")})
    @PutMapping(value = "updateUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>updateUser(@RequestParam int id, @RequestBody UserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        if (userServices.updateUser(customUserDetail.getUser().getId(), userRequest)  != null){
            return new ResponseEntity<>("user is update", HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            security = {@SecurityRequirement(name = "Authorization")})
    @DeleteMapping(value = "deleteUser")
    public ResponseEntity<?>deleteUser(@RequestParam int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")) ||
                authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))))
        {
            if (userServices.deleteUser(id)){
                return new ResponseEntity<>("user is delete", HttpStatus.OK);
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
