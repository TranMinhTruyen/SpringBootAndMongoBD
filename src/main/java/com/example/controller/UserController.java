package com.example.controller;

import com.example.common.jwt.JWTTokenProvider;
import com.example.common.model.CustomUserDetail;
import com.example.common.model.User;
import com.example.common.request.LoginRequest;
import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import com.example.common.response.JwtResponse;
import com.example.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tran Minh Truyen
 */

@Tag(name = "UserController")
@RestController
@CrossOrigin("*")
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        if (userServices.createUser(userRequest))
          return new ResponseEntity<>("User is added", HttpStatus.OK);
        else
          return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
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

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @PutMapping(value = "updateUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>updateUser(@RequestParam int id, @RequestBody UserRequest userRequest) {
        if (userServices.updateUser(id, userRequest)){
            return new ResponseEntity<>("user is update", HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))))
    @DeleteMapping(value = "deleteUser")
    public ResponseEntity<?>deleteUser(@RequestParam int id){
        if (userServices.deleteUser(id)){
            return new ResponseEntity<>("user is delete", HttpStatus.OK);
        }
            else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }
}
