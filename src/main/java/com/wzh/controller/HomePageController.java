package com.wzh.controller;

import com.wzh.entity.Album;
import com.wzh.entity.Article;
import com.wzh.entity.Banner;
import com.wzh.service.AlbumService;
import com.wzh.service.ArticleService;
import com.wzh.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
@Slf4j
public class HomePageController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @GetMapping("/homepage/{id}/{type}/{sub_type}")
    public Map<String,Object> all(@PathVariable("id") String id,
                                  @PathVariable("type") String type,
                                  @PathVariable("sub_type") String sub_type) {
        log.info(id);
        log.info(type);
        Map<String,Object> map = new HashMap<>();
        List<Banner> bannerList = new ArrayList<>();
        List<Album> albumList = new ArrayList<>();
        List<Article> articleList = new ArrayList<>();
        if ("all".equals(type)) {
             bannerList = bannerService.findActiveBanner();
            albumList = albumService.findAll(6, 1);
            articleList = articleService.findAll(6, 1);
        } else if ("wen".equals(type)) {
            albumList = albumService.findAlbums();
        } else if ("si".equals(type)) {
            if("ssyj".equals(sub_type)){
                log.info(sub_type);
                articleList = articleService.focusArticle(id);
            }else if ("xmfy".equals(sub_type)){
                log.info(sub_type);
                articleList = articleService.requiredArticle();
            }
        }
        map.put("header",bannerList);
        map.put("album",albumList);
        map.put("article",articleList);
        return map;
    }
}
