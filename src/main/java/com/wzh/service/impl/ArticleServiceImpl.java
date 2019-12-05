package com.wzh.service.impl;

import com.wzh.dao.ArticleDao;
import com.wzh.dao.PoetryElasticsearchDao;
import com.wzh.entity.Article;
import com.wzh.entity.Guru;
import com.wzh.service.ArticleService;
import com.wzh.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

@Service
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private PoetryElasticsearchDao poetryElasticsearchDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    @Override
    public Map<String, Object> addArticle(Article article) {
        Map<String,Object> map = new HashMap<>();
        article.setId(UUID.randomUUID().toString().replace("-",""));
        article.setCreateDate(new Date());
        article.setPublishDate(new Date());
        articleDao.insertSelective(article);
        poetryElasticsearchDao.save(article);
        map.put("articleId",article.getId());
        map.put("status",200);
        return map;
    }

    @Override
    public void removeArticle(String id) {
        Article article = new Article();
        article.setId(id);
        articleDao.deleteByPrimaryKey(id);
        poetryElasticsearchDao.delete(article);
    }

    @Override
    public void removeArticles(String[] id) {
        articleDao.deleteByIdList(Arrays.asList(id));
        for (String s : id) {
            poetryElasticsearchDao.deleteById(s);
        }
    }

    @Override
    public Map<String, Object> modifyArticle(Article article) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtil.isEmpty(article.getImg())){
            article.setImg(null);
        }
        articleDao.updateByPrimaryKeySelective(article);
        poetryElasticsearchDao.save(article);
        map.put("articleId",article.getId());
        map.put("status",200);
        return map;
    }

    @Override
    public Article findOneArticle(String id) {
        return articleDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Article> findAll(Integer rows, Integer page) {
        Integer offset = (page-1)*rows;
        return articleDao.selectByRowBounds(new Article(),new RowBounds(offset,rows));
    }

    @Override
    public List<Article> findAlls() {
        return articleDao.selectAll();
    }

    @Override
    public Integer count() {
        return articleDao.selectCount(new Article());
    }

    @Override
    public List<Article> requiredArticle() {
        /*Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("guruId","0");*/
        return articleDao.requireArticle();
    }

    @Override
    public List<Article> focusArticle(String uId) {
        List<String> guruIds = userService.focusGuru(uId);
        System.out.println(guruIds);
        return articleDao.focusArticle(guruIds);
    }
}
