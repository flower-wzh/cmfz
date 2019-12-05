package com.wzh.service.impl;

import com.wzh.dao.AdminDao;
import com.wzh.entity.Admin;
import com.wzh.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private HttpServletRequest request;


    @Override
    public Admin login(Admin admin) {
        Admin admin1 = adminDao.selectOne(admin);
        if (admin == null) throw new RuntimeException("用户名或密码错误");
        //存入登录的标记
        request.getSession().setAttribute("admin",admin1);
        log.info(""+admin1);
        return admin1;
    }
}
