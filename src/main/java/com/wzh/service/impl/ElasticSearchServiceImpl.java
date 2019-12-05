package com.wzh.service.impl;

import com.wzh.dao.PoetryElasticsearchDao;
import com.wzh.entity.Article;
import com.wzh.service.ArticleService;
import com.wzh.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private PoetryElasticsearchDao poetryElasticsearchDao;

    @Autowired
    private ArticleService articleService;

    @Override
    public Map<String, Object> findByMsg(String msg, Integer page) {
        log.info(msg);
        List<Article> articleList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        HighlightBuilder.Field field = new HighlightBuilder.Field("*")
                .requireFieldMatch(false)
                .preTags("<span style='color:red'>")
                .postTags("</span>");
        map.put("page",page);
        if(page == null){
            page = 0;
        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(msg).field("title").field("content"))
                .withHighlightFields(field)
                .withPageable(PageRequest.of(page,10))
                .build();
        AggregatedPage<Article> queryForPage = elasticsearchTemplate.queryForPage(searchQuery, Article.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Article> ArticleList = new ArrayList<>();
                long totalHits = searchResponse.getHits().getTotalHits();
                map.put("counts",totalHits);
                //计算页数
                Integer pages = Integer.valueOf(String.valueOf(totalHits))%10==0?Integer.valueOf(String.valueOf(totalHits))/10:(Integer.valueOf(String.valueOf(totalHits))/10)+1;
                map.put("pages",pages);
                SearchHit[] hits = searchResponse.getHits().getHits();
                for (SearchHit hit : hits) {
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    Article article = new Article();
                    article.setId(sourceAsMap.get("id").toString());
                    if(sourceAsMap.get("title")!=null&&!"".equals(sourceAsMap.get("title"))) {
                        article.setTitle(sourceAsMap.get("title").toString());
                    }
                    if(sourceAsMap.get("content")!=null&&!"".equals(sourceAsMap.get("content"))) {
                        article.setImg(sourceAsMap.get("content").toString());
                    }
                    if(sourceAsMap.get("createDate")!=null&&!"".equals(sourceAsMap.get("createDate"))) {
                        article.setCreateDate(new Date((Long) sourceAsMap.get("createDate")));
                    }
                    if (highlightFields.containsKey("content")) {
                        article.setContent(highlightFields.get("content").fragments()[0].toString());
                    }
                    if (highlightFields.containsKey("title")) {
                        article.setTitle(highlightFields.get("title").fragments()[0].toString());
                    }
                    ArticleList.add(article);
                }
                return new AggregatedPageImpl<T>((List<T>) ArticleList);
            }
        });
        queryForPage.forEach(p->articleList.add(p));
        map.put("list",articleList);
        return map;
    }

    @Override
    public void createIndex() {
        List<Article> alls = articleService.findAlls();
        poetryElasticsearchDao.saveAll(alls);
    }
}
