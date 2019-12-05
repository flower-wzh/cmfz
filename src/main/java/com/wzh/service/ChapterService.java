package com.wzh.service;

import com.wzh.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    Map<String,Object> addChapter(Chapter chapter);

    /**
     * 删除章节
     * @param id
     */
    void removeChapter(String id);

    /**
     * 批量删除
     * @param id
     */
    void removeChapters(String[] id);

    /**
     * 删除专辑
     * @param id
     */
    void removeByAlbumId(String id);

    /**
     * 更新章节
     * @param chapter
     */
    Map<String,Object> modifyChapter(Chapter chapter);

    /**
     * 根据id查找一个Chapter详情
     * @param id
     */
    Chapter findOneChapter(String id);

    /**
     * 查找一张专辑下的章节
     * @return
     */
    List<Chapter> findAllByAlbumId(Chapter chapter,Integer rows, Integer page);

    /**
     * 查找要删除除的数据
     * @param ids
     * @return
     */
    List<Chapter> findInIds(String[] ids);

    /**
     * 根据名字查找一个章节
     * @param fileName
     * @return
     */
    Chapter findByFileName(String fileName);

    /**
     * 专辑下Chapter数量
     * @return
     */
    Integer count(Chapter chapter);

    /**
     * 专辑下的所有id
     * @param id
     * @return
     */
    List<Chapter> findAllByAlbumId(String id);
}
