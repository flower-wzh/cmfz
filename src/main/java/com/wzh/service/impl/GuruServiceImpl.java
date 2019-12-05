package com.wzh.service.impl;

import com.wzh.dao.GuruDao;
import com.wzh.entity.Guru;
import com.wzh.service.GuruService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {

    @Autowired
    private GuruDao guruDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Guru> findAll() {
        return guruDao.selectAll();
    }

    @Override
    public Map<String, Object> addGuru(Guru guru) {
        Map<String,Object> map = new HashMap<>();
        guru.setId(UUID.randomUUID().toString().replace("-",""));
        guruDao.insert(guru);
        map.put("guruId",guru.getId());
        map.put("status",200);
        return map;
    }

    @Override
    public void removeGuru(String id) {
        guruDao.deleteByPrimaryKey(id);
    }

    @Override
    public void removeGurus(String[] id) {
        guruDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public Map<String, Object> modifyGuru(Guru guru) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtil.isEmpty(guru.getPhoto())){
            guru.setPhoto(null);
        }
        guruDao.updateByPrimaryKeySelective(guru);
        map.put("guruId",guru.getId());
        map.put("status",200);
        return map;
    }

    @Override
    public Guru findOneGuru(String id) {
        return guruDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Guru> showAll(Integer rows, Integer page) {
        Integer offset = (page-1)*rows;
        return guruDao.selectByRowBounds(new Guru(),new RowBounds(offset,rows));
    }

    @Override
    public Integer count() {
        return guruDao.selectCount(new Guru());
    }
}
