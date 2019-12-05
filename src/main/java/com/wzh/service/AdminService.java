package com.wzh.service;

import com.wzh.entity.Admin;

public interface AdminService {

    /**
     * 管理员登录
     * @param admin
     * @return
     */
    Admin login(Admin admin);
}
