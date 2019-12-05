package com.wzh.dao;

import com.wzh.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface BannerDao extends Mapper<Banner>,
        DeleteByIdListMapper<Banner,String>,
        IdListMapper<Banner,String>,
        InsertListMapper<Banner> {

}
