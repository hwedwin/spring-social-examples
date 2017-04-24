package com.tanghuan.dev.oauth.entity.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Arthur on 2017/4/14.
 */
public class UserDto implements Serializable{

    private String username;

    @Size(min = 11, max = 11, message = "PHONE_NUM_ERROR")
    private String phone;

    private String valid_code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getValid_code() {
        return valid_code;
    }

    public void setValid_code(String valid_code) {
        this.valid_code = valid_code;
    }

}
