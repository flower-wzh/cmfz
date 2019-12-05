package com.wzh.service;

import com.wzh.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    /**
     * 添加文章
     * @param article
     * @return
     */
    Map<String,Object> addArticle(Article article);

    /**
     * 删除文章
     * @param id
     */
    void removeArticle(String id);

    /**
     * 批量删除
     * @param id
     */
    void removeArticles(String[] id);

    /**
     * 更新文章
     * @param article
     */
    Map<String,Object> modifyArticle(Article article);

    /**
     * 根据id查找一个Article详情
     * @param id
     */
    Article findOneArticle(String id);

    /**
     * 查找所有文章
     * @return
     */
    List<Article> findAll(Integer rows, Integer page);

    /**
     * 查所有
     * @return
     */
    List<Article> findAlls();
    /**
     * Article数量
     * @return
     */
    Integer count();

    /**
     * 必修文章
     */
    List<Article> requiredArticle();

    /**
     * 用户关注的文章
     * @return
     */
    List<Article> focusArticle(String uId);
}
