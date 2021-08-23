package com.example.userservices.controller;

import com.core.model.CustomUserDetail;
import com.core.model.User;
import com.core.request.LoginRequest;
import com.core.request.UserRequest;
import com.core.response.JwtResponse;
import com.example.userservices.jwt.JWTTokenProvider;
import com.example.userservices.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserController")
@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        if (!userServices.accountIsExists(userRequest.getAccount())){
            if (userServices.createUser(userRequest))
                return new ResponseEntity<>("User is added", HttpStatus.OK);
            else
                return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
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
}
