package com.wzh.service.impl;

import com.wzh.dao.ChapterDao;
import com.wzh.entity.Chapter;
import com.wzh.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

@Service
@Transactional
@Slf4j
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    @Override
    public Map<String, Object> addChapter(Chapter chapter) {
        Map<String,Object> map = new HashMap<>();
        chapter.setId(UUID.randomUUID().toString().replace("-",""));
        chapter.setCreateDate(new Date());
        chapterDao.insert(chapter);
        map.put("chapterId",chapter.getId());
        map.put("status",200);
        return map;
    }

    @Override
    public void removeChapter(String id) {
        chapterDao.deleteByPrimaryKey(id);
    }

    @Override
    public void removeChapters(String[] id) {
        chapterDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void removeByAlbumId(String id) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(id);
        chapterDao.delete(chapter);
    }

    @Override
    public Map<String, Object> modifyChapter(Chapter chapter) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtil.isEmpty(chapter.getUrl())){
            chapter.setUrl(null);
        }
        chapterDao.updateByPrimaryKeySelective(chapter);
        map.put("chapterId",chapter.getId());
        map.put("status",200);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Chapter findOneChapter(String id) {
        return chapterDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Chapter> findAllByAlbumId(Chapter chapter, Integer rows, Integer page) {
        Integer offset = (page-1)*rows;
        return chapterDao.selectByRowBounds(chapter,new RowBounds(offset,rows));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Chapter> findInIds(String[] ids) {
        Example example = new Example(Chapter.class);
        example.createCriteria().andIn("id",Arrays.asList(ids));
        return chapterDao.selectByExample(example);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Chapter findByFileName(String fileName) {
        Example example = new Example(Chapter.class);
        example.createCriteria().andEqualTo("fileName",fileName);
        return chapterDao.selectOneByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count(Chapter chapter) {
        return chapterDao.selectCount(chapter);
    }

    @Override
    public List<Chapter> findAllByAlbumId(String id) {
        Example example = new Example(Chapter.class);
        example.createCriteria().andEqualTo("albumId",id);
        return chapterDao.selectByExample(example);
    }
}
