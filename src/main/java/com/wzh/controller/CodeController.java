package com.wzh.controller;

import com.wzh.entity.User;
import com.wzh.util.MD5Util;
import com.wzh.util.SendSms;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/code")
@RestController
public class CodeController {

    @RequestMapping("/send")
    public Map<String,Object> sendCode(String phone, HttpSession session) {
        Map<String,Object> map  = new HashMap<>();
        String code = MD5Util.getCode(4);
        try {
            String message = SendSms.send(phone, code);
            session.setAttribute("code",code);
            map.put("status",200);
            map.put("message",message);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("Status",-200);
            map.put("message","发送验证码失败，请稍候再试！");
        }
        return map;
    }

    @RequestMapping("/get")
    public Map<String,Object> get(HttpSession session) {
        Map<String,Object> map = new HashMap<>();
        String code = (String) session.getAttribute("code");
        User user = (User) session.getAttribute("user");
        map.put("code",code);
        map.put("user",user);
        return map;
    }
}
