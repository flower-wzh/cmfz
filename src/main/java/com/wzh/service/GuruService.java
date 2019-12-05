package com.wzh.service;

import com.wzh.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {

    /**
     * 查所有上师数据
     * @return
     */
    List<Guru> findAll();

    /**
     * 添加上师
     * @param guru
     * @return
     */
    Map<String,Object> addGuru(Guru guru);

    /**
     * 删除上师
     * @param id
     */
    void removeGuru(String id);

    /**
     * 批量删除
     * @param id
     */
    void removeGurus(String[] id);

    /**
     * 更新上师
     * @param guru
     */
    Map<String,Object> modifyGuru(Guru guru);

    /**
     * 根据id查找一个Guru详情
     * @param id
     */
    Guru findOneGuru(String id);

    /**
     * 查找所有上师
     * @return
     */
    List<Guru> showAll(Integer rows, Integer page);

    /**
     * guru数量
     * @return
     */
    Integer count();
}
