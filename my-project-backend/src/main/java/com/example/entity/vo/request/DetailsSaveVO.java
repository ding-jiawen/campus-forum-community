package com.example.entity.vo.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户请求的用户详细信息vo
 */
@Data
public class DetailsSaveVO {
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$")
    String username;
    @Min(0)
    @Max(1)
    int gender;
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    @Length(min = 11, max = 11)
    String phone;
    @Pattern(regexp = "^[1-9][0-9]{4,10}$")
    @Length(max = 13)
    String qq;
    @Length(max = 20)
    String wx;
    @Length(max = 200)
    String desc;
}
