package com.lsy.dto;

import lombok.Data;

@Data

public class ResetPasswordDTO {
    private String email;
    private String code;
    private String password;
    private String confirmPassword;
}
