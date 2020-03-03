package com.lagou.edu.dao;

import com.lagou.edu.pojo.Account;


public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
