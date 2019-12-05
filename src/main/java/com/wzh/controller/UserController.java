package com.wzh.controller;

import com.wzh.entity.User;
import com.wzh.service.UserCityVoService;
import com.wzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCityVoService userCityVoService;

    @RequestMapping("/findAll")
    public Map<String, Object> findAll(Integer rows, Integer page) {
        Map<String,Object> map = new HashMap<>();
        List<User> users = userService.findAllUser(rows,page);
        Integer count = userService.count();
        Integer pages = count%rows==0?count/rows:count/rows+1;
        map.put("page",page);
        map.put("records",count);
        map.put("total",pages);
        map.put("rows",users);
        return map;
    }

    @RequestMapping("/changeStatus")
    public void changeStatus(User user) {
        userService.changeStatus(user);
    }

    @RequestMapping("/registDistribution")
    public Map<String,Object> registDistribution() {
        return userService.registDistribution();
    }

    @RequestMapping("/urbanDistribution")
    public Map<String,Object> urbanDistribution() {
        return userCityVoService.urbanDistribution();
    }

    @PostMapping("/login")
    public Map<String,Object> login(User user,String code, HttpSession session) {
        Map<String,Object> map = new HashMap<>();
        try {
            if(StringUtil.isEmpty(user.getPassword())){
                String realCode = (String) session.getAttribute("code");
                if(!realCode.equalsIgnoreCase(code)){
                    map.put("status",-200);
                    map.put("message","验证码错误！");
                    return map;
                }else {
                    User loginByPhone = userService.loginByPhone(user.getPhone());
                    map.put("status",200);
                    map.put("message","验证成功");
                    map.put("user",loginByPhone);
                    return map;
                }
            }
            map = userService.login(user);
            session.setAttribute("user",map.get("user"));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","登录时异常，请稍候再试");
        }
        return map;
    }

    @PostMapping("/regist")
    public Map<String,Object> regist(User user,String code,HttpSession session) {
        Map<String,Object> map = new HashMap<>();
        try {
            if(StringUtil.isEmpty(user.getPassword())){
                String realCode = (String) session.getAttribute("code");
                if(!realCode.equalsIgnoreCase(code)){
                    map.put("status",-200);
                    map.put("message","验证码错误！");
                    return map;
                }else {
                    map = userService.regist(user);
                    return map;
                }
            }
            map = userService.regist(user);
            session.setAttribute("user",map.get("user"));
        } catch (Exception e) {
            map.put("status",-200);
            map.put("message","注册失败，请稍候再试");
        }
        return map;
    }

    @PostMapping("/forget")
    public Map<String,Object> forget(String phone,String code,HttpSession session) {
        Map<String,Object> map = new HashMap<>();
        String realCode = (String) session.getAttribute("code");
        if(realCode.equalsIgnoreCase(code)){
            User user = userService.loginByPhone(phone);
            session.setAttribute("userId",user.getId());
            map.put("status",200);
            map.put("message","验证成功");
            map.put("user",user);
            return map;
        }
        map.put("status",-200);
        map.put("message","验证码输入错误！");
        return map;
    }

    @PostMapping("changePassword")
    public Map<String,Object> changePassword(User user,HttpSession session) {
        Map<String,Object> map = new HashMap<>();
        String userId = (String) session.getAttribute("userId");
        user.setId(userId);
        try {
            map = userService.modifyUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","修改失败！");
        }
        return map;
    }

    @GetMapping("/detail")
    public User detail(String id) {
        return userService.findOneById(id);
    }

    @PostMapping("/modify")
    public Map<String,Object> modifyUser(User user) {
        Map<String,Object> map = new HashMap<>();
        try {
            map = userService.modifyUser(user);
        } catch (Exception e) {
            map.put("status",-200);
            map.put("message","修改失败！");
        }
        return map;
    }

    @GetMapping("/logout")
    public Map<String,Object> logout(String id,HttpSession session) {
        Map<String,Object> map = new HashMap<>();
        try {
            session.removeAttribute("user");
            map.put("status",200);
            map.put("message","退出成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","退出失败！");
        }
        return map;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/notice")
    public Map<String, Object> notice(String uId,String guruId) {
        Map<String,Object> map = new HashMap<>();
        try {
            redisTemplate.opsForSet().add("notice"+uId,guruId);
            map.put("status",200);
            map.put("message","关注成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","关注失败");
        }
        return map;
    }

    @RequestMapping("/myNotice")
    public Map<String, Object> myNotice(String uId) {
        Map<String,Object> map = new HashMap<>();
        try {
            List<String> strings = userService.focusGuru(uId);
            map.put("status",200);
            map.put("message","关注成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","关注失败");
        }
        return map;
    }

}
