package com.core.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ResetPassword {
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;
}
