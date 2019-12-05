package com.wzh.dao;

import com.wzh.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PoetryElasticsearchDao extends ElasticsearchRepository<Article,String> {
}
