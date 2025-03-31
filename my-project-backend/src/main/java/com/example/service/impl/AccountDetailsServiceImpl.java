package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Account;
import com.example.entity.dto.AccountDetails;
import com.example.entity.vo.request.DetailsSaveVO;
import com.example.mapper.AccountDetailsMapper;
import com.example.service.AccountDetailsService;
import com.example.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsServiceImpl extends ServiceImpl<AccountDetailsMapper, AccountDetails> implements AccountDetailsService {

    @Resource
    AccountService accountService;

    /**
     * 根据id查询用户详细信息
     * @param id 用户详细信息id
     * @return 用户详细信息
     */
    @Override
    public AccountDetails findAccountDetailsById(int id) {
        return this.getById(id);
    }

    /**
     * 保存用户详细信息
     * @param id 用户详细信息id
     * @param vo 前端传来的表单实体
     * @return 返回是否保存用户信息成功
     */
    @Override
    public boolean saveAccountDetails(int id, DetailsSaveVO vo) {
        Account account = accountService.findAccountByNameOrEmail(vo.getUsername());
        // 如果要保存的用户不存在且该账户就是用户他自己，则更新Account
        if(account == null || account.getId() == id) {
            accountService.update()
                    .eq("id", id)
                    .set("username", vo.getUsername())
                    .update();
            // account_details表中有该account则更新，没有则插入
            this.saveOrUpdate(new AccountDetails(
                    id, vo.getGender(), vo.getPhone(),
                    vo.getQq(), vo.getWx(), vo.getDesc()));
            return true;
        }
        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
