package com.wzh.dao;

import com.wzh.entity.UserCityVo;

import java.util.List;

public interface UserCityVoDao{

    List<UserCityVo> urbanDistributionSearch(String sex);
}
