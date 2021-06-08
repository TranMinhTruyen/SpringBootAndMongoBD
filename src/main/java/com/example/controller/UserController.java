package com.example.controller;

import com.example.common.request.UserRequest;
import com.example.common.response.CommonResponse;
import com.example.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/user")
public class UserController {

  @Autowired
  UserServices userServices;

  @PostMapping(value = "createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createEmployee(@RequestBody UserRequest userRequest){
    if (userServices.createUser(userRequest))
      return new ResponseEntity<>(userRequest, HttpStatus.OK);
    else
      return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value="getAllUser")
  public  ResponseEntity<?>getAllUser(@RequestParam int page, @RequestParam int size){
    CommonResponse response = userServices.getAllUser(page, size);
    if (response != null){
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    else return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
  }

}
