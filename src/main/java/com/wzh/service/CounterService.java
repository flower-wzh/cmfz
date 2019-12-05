package com.wzh.service;

import com.wzh.entity.Counter;

import java.util.List;
import java.util.Map;

public interface CounterService {
    /**
     * 添加计数器
     * @param counter
     * @return
     */
    Map<String,Object> addCounter(Counter counter);

    /**
     * 删除计数器
     * @param id
     */
    void removeCounter(String id);

    /**
     * 批量删除
     * @param id
     */
    void removeCounters(String[] id);

    /**
     * 更新计数器
     * @param counter
     */
    Map<String,Object> modifyCounter(Counter counter);

    /**
     * 根据id查找一个Counter详情
     * @param id
     */
    Counter findOneCounter(String id);

    /**
     * 查找课程所有计数器
     * @return
     */
    List<Counter> findAll(String cId);

    /**
     * Counter数量
     * @return
     */
    Integer count();
}
