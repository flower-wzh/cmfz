package com.wzh.service;

import com.wzh.entity.Banner;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface BannerService {

    /**
     * 添加轮播图
     * @param banner
     * @return
     */
    Map<String,Object> addBanner(Banner banner);

    /**
     * 删除轮播图
     * @param id
     */
    void removeBanner(String id);

    /**
     * 批量删除
     * @param id
     */
    void removeBanners(String[] id);

    /**
     * 更新轮播图
     * @param banner
     */
    Map<String,Object> modifyBanner(Banner banner);

    /**
     * 根据id查找一个Banner详情
     * @param id
     */
    Banner findOneBanner(String id);

    /**
     * 查找所有轮播图
     * @return
     */
    List<Banner> findAll(Integer rows,Integer page);

    /**
     * Banner数量
     * @return
     */
    Integer count();

    /**
     * 导出轮播图信息
     * @return
     */
    void outBanner(OutputStream outputStream);

    /**
     * 到处轮播图模板
     * @return
     */
    void outBannerModel(OutputStream outputStream);

    /**
     * 导入信息
     */
    void inputBanner(InputStream inputStream);

    /**
     * 前端展示轮播图信息
     * @return
     */
    List<Banner> findActiveBanner();

}
