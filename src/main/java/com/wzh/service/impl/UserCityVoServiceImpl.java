package com.wzh.service.impl;

import com.wzh.dao.UserCityVoDao;
import com.wzh.entity.UserCityVo;
import com.wzh.service.UserCityVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserCityVoServiceImpl implements UserCityVoService {

    @Autowired
    private UserCityVoDao userCityVoDao;

    @Override
    public Map<String,Object> urbanDistribution() {
        Map<String,Object> map = new HashMap<>();
        List<UserCityVo> man = userCityVoDao.urbanDistributionSearch("男");
        List<UserCityVo> women = userCityVoDao.urbanDistributionSearch("女");
        map.put("man",man);
        map.put("women",women);
        return map;
    }
}
