package com.example.controller;

import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import com.example.services.UserServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "UserController")
@RestController
@CrossOrigin("*")
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserServices userServices;

    @PostMapping(value = "createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest){
        if (userServices.createUser(userRequest))
          return new ResponseEntity<>(userRequest, HttpStatus.OK);
        else
          return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="getAllUser")
    public ResponseEntity<?>getAllUser(@RequestParam int page, @RequestParam int size){
        CommonResponse response = userServices.getAllUser(page, size);
        if (response != null){
          return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

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

    @PutMapping(value = "updateUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>updateUser(@RequestParam int id, @RequestBody UserRequest userRequest){
        if (userServices.updateUser(id, userRequest)){
            return new ResponseEntity<>("user is update", HttpStatus.OK);
        }
        else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "deleteUser")
    public ResponseEntity<?>deleteUser(@RequestParam int id){
        if (userServices.deleteUser(id)){
            return new ResponseEntity<>("user is delete", HttpStatus.OK);
        }
            else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }
}
