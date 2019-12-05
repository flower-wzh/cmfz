package com.wzh.dao;

import com.wzh.entity.Article;
import io.lettuce.core.dynamic.annotation.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article,String> {

    List<Article> focusArticle(@Param("list") List<String> ids);

    List<Article> requireArticle();
}
