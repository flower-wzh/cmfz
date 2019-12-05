package com.wzh.service.impl;

import com.wzh.dao.CounterDao;
import com.wzh.entity.Counter;
import com.wzh.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterDao counterDao;
    @Override
    public Map<String, Object> addCounter(Counter counter) {
        Map<String,Object> map = new HashMap<>();
        counter.setId(UUID.randomUUID().toString().replace("-",""));
        counter.setCreateDate(new Date());
        counterDao.insertSelective(counter);
        map.put("counter",counter);
        map.put("status",200);
        return map;
    }

    @Override
    public void removeCounter(String id) {
        counterDao.deleteByPrimaryKey(id);
    }

    @Override
    public void removeCounters(String[] id) {

    }

    @Override
    public Map<String, Object> modifyCounter(Counter counter) {
        Map<String,Object> map = new HashMap<>();
        counterDao.updateByPrimaryKeySelective(counter);
        map.put("status",200);
        map.put("counter",counter);
        return map;
    }

    @Override
    public Counter findOneCounter(String id) {
        return counterDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Counter> findAll(String cId) {
        Example example = new Example(Counter.class);
        example.createCriteria().andEqualTo("courseId",cId);
        return counterDao.selectByExample(example);
    }

    @Override
    public Integer count() {
        return counterDao.selectCount(new Counter());
    }
}
