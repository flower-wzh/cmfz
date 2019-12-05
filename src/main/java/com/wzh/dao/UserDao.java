package com.wzh.dao;

import com.wzh.entity.User;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface UserDao extends Mapper<User>,
        IdListMapper<User,String>,
        InsertListMapper<User> {
}
