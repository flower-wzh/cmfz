package com.wzh.service;

import com.wzh.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 所有用户信息
     * @param rows
     * @param page
     * @return
     */
    List<User> findAllUser(Integer rows, Integer page);

    /**
     * 查看用户的数量
     * @return
     */
    Integer count();

    /**
     * 更改用户状态
     * @param user
     */
    void changeStatus(User user);


    /**
     * 注册信息
     * @return
     */
    Map<String,Object> registDistribution();

    /**
     * 关注的上师
     * @param uId
     * @return
     */
    List<String> focusGuru(String uId);

    /**
     * 用户登录
     * @param user
     * @return
     */
    Map<String, Object> login(User user);

    /**
     * 使用手机登录
     * @param phone
     * @return
     */
    User loginByPhone(String phone);

    /**
     * 用户注册
     * @param user
     * @return
     */
    Map<String, Object> regist(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Map<String, Object> modifyUser(User user);

    /**
     * 用户详情
     * @param id
     * @return
     */
    User findOneById(String id);
}
