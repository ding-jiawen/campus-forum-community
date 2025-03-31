package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.dto.Account;
import com.example.entity.dto.AccountDetails;
import com.example.entity.vo.request.DetailsSaveVO;
import com.example.entity.vo.response.AccountDetailsVO;
import com.example.entity.vo.response.AccountVO;
import com.example.service.AccountDetailsService;
import com.example.service.AccountService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class AccountController {

    @Resource
    AccountService accountService;

    @Resource
    AccountDetailsService accountDetailsService;

    /**
     * 查询用户基本信息
     * @param id 用户id
     * @return 返回用户实体
     */
    @GetMapping("/info")
    public RestBean<AccountVO> info(@RequestAttribute(Const.ATTR_USER_ID) int id) {
        Account account = accountService.findAccountById(id);
        return RestBean.success(account.asViewObject(AccountVO.class));
    }

    /**
     * 查询用户详细信息
     * @param id 用户id
     * @return 返回用户详细信息实体
     */
    @GetMapping("/details")
    public RestBean<AccountDetailsVO> details(@RequestAttribute(Const.ATTR_USER_ID) int id) {
        //  Optional: Java 8 引入的一个容器对象，用于表示可能为 null 的值
        AccountDetails details = Optional
                .ofNullable(accountDetailsService.findAccountDetailsById(id)) // 将null的对象封装成Optional对象，防止空指针异常的风险
                .orElseGet(AccountDetails::new); // 如果为空，创建一个AccountDetails对象
        return RestBean.success(details.asViewObject(AccountDetailsVO.class));
    }

    /**
     * 保存用户详细信息
     * @param id 用户id
     * @param vo 用户详细信息请求实体
     * @return 返回是否保存信息成功
     */
    @PostMapping("/save-details")
    public RestBean<Void> saveDetails(@RequestAttribute(Const.ATTR_USER_ID) int id,
                                      @RequestBody @Valid DetailsSaveVO vo){
        boolean success = accountDetailsService.saveAccountDetails(id, vo);
        return success ? RestBean.success() : RestBean.failure(400, "此用户名已经被其他用户使用，请重新更换");
    }
}
