package com.hide.backend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 接收登录提交来的表单
 **/
@Data
@Accessors(chain = true)
public class LoginForm {
    private String account;
    private String password;
    private String role;
}
