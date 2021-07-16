package com.example.common.response;

import com.example.common.model.Address;
import com.example.common.request.UserRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author Tran Minh Truyen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserResponse extends UserRequest {
}
