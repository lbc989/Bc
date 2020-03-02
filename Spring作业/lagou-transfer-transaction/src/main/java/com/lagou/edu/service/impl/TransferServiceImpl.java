package com.lagou.edu.service.impl;

import com.lagou.edu.MyAnnotation.MyService;
import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.MyAnnotation.MyTransactional;
import com.lagou.edu.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;



@MyService("transferService")
public class TransferServiceImpl implements TransferService {


    @Autowired
    private AccountDao accountDao;




    @Override
    @MyTransactional
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {

            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            accountDao.updateAccountByCardNo(to);
            int c = 1/0;
            accountDao.updateAccountByCardNo(from);


    }
}
