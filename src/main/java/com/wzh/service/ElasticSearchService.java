package com.wzh.service;

import java.util.Map;

public interface ElasticSearchService {

    Map<String,Object> findByMsg(String msg, Integer page);

    void createIndex();
}
