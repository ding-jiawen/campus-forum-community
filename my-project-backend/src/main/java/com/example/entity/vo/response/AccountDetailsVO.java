package com.example.entity.vo.response;

import com.example.entity.BaseData;
import lombok.Data;

/**
 * 保存用户信息成功的响应信息
 */
@Data
public class AccountDetailsVO{
    String username;
    int gender;
    String phone;
    String qq;
    String wx;
    String desc;
}
