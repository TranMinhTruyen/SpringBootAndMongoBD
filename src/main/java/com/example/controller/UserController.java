package com.example.controller;

import com.example.common.jwt.JWTTokenProvider;
import com.example.common.jwt.CustomUserDetail;
import com.example.common.model.ResetPassword;
import com.example.common.model.User;
import com.example.common.request.LoginRequest;
import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import com.example.common.response.JwtResponse;
import com.example.common.response.UserResponse;
import com.example.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            summary = "This is API to create user, a confirm key will be sent to email to activate user")
    @PostMapping(value = "createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest,
                                        @RequestParam (required = false) String confirmKey) {
        if (!userServices.accountIsExists(userRequest.getAccount(), userRequest.getEmail())){
            if (confirmKey == null){
                String key = RandomStringUtils.randomAlphanumeric(10);
                String mess = userServices.sendEmailConfirmKey(userRequest.getEmail(), key);
                return new ResponseEntity<>(mess, HttpStatus.OK);
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

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            summary = "This is API to reset password, a new password will be sent to email")
    @PostMapping(value = "resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassword resetPassword) {
        User user = userServices.resetPassword(resetPassword.getEmail());
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
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
            security = {@SecurityRequirement(name = "Authorization")},
            summary = "This is API to update user infomation, user must login first to use this API")
    @PutMapping(value = "updateUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>updateUser(@RequestBody UserRequest userRequest) {
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
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")))
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

    @Operation(responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
            security = {@SecurityRequirement(name = "Authorization")},
            summary = "This is API to get user profile, user must login first to use this API")
    @PostMapping(value = "getProfileUser")
    public ResponseEntity<?>getProfileUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        UserResponse userResponse = userServices.getProfileUser(customUserDetail.getUser().getId());
        if (userResponse != null){
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }
}
