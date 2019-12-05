package com.wzh.service;

import com.wzh.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {


    /**
     * 添加专辑
     * @param album
     * @return
     */
    Map<String,Object> addAlbum(Album album);

    /**
     * 删除专辑
     * @param id
     */
    void removeAlbum(String id);

    /**
     * 批量删除
     * @param id
     */
    void removeAlbums(String[] id);

    /**
     * 更新专辑
     * @param album
     */
    Map<String,Object> modifyAlbum(Album album);

    /**
     * 根据id查找一个Album详情
     * @param id
     */
    Album findOneAlbum(String id);

    /**
     * 查找所有专辑
     * @return
     */
    List<Album> findAll(Integer rows, Integer page);

    /**
     * Album数量
     * @return
     */
    Integer count();

    /**
     * 前端专辑展示
     * @return
     */
    List<Album> findAlbums();
}
