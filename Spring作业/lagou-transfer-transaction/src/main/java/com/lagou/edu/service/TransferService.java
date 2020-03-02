package com.lagou.edu.service;

import org.springframework.transaction.annotation.Transactional;


public interface TransferService {

    void transfer(String fromCardNo,String toCardNo,int money) throws Exception;
}
