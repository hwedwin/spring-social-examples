package com.tanghuan.dev.oauth.entity.dto;

import com.tanghuan.dev.oauth.entity.domain.Super;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by Arthur on 2017/4/25.
 */
public class UserBindDto extends Super {

    @NotBlank(message = "phone_not_null")
    @Size(min = 11, max = 11, message = "phone_error")
    private String phone;

    // 验证码
    @NotBlank(message = "valid_code_not_null")
    @Size(min = 6, max = 6, message = "valid_code_error")
    private String valid_code;

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
