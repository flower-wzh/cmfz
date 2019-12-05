package com.wzh.controller;

import com.wzh.entity.Admin;
import com.wzh.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    public String login(Admin admin) {
        try {
            adminService.login(admin);
        } catch (Exception e) {
            return e.getMessage();
        }
        log.info(""+admin);
        return "success";
    }

    @RequestMapping("/logout")
    public void logout(HttpSession session) {
        session.removeAttribute("admin");
    }
}
