package com.wzh.service.impl;

import com.wzh.annotation.LogService;
import com.wzh.dao.AlbumDao;
import com.wzh.entity.Album;
import com.wzh.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Override
    @LogService(value = "添加专辑")
    public Map<String, Object> addAlbum(Album album) {
        Map<String,Object> map = new HashMap<>();
        album.setId(UUID.randomUUID().toString().replace("-",""));
        album.setCreateDate(new Date());
        albumDao.insert(album);
        map.put("albumId",album.getId());
        map.put("status",200);
        return map;
    }

    @Override
    @LogService(value = "删除专辑")
    public void removeAlbum(String id) {
        albumDao.deleteByPrimaryKey(id);
    }

    @Override
    @LogService(value = "删除专辑")
    public void removeAlbums(String[] id) {
        albumDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    @LogService(value = "修改专辑")
    public Map<String, Object> modifyAlbum(Album album) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtil.isEmpty(album.getCover())){
            album.setCover(null);
        }
        albumDao.updateByPrimaryKeySelective(album);
        map.put("albumId",album.getId());
        map.put("status",200);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Album findOneAlbum(String id) {
        return albumDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Album> findAll(Integer rows, Integer page) {
        Integer offset = (page-1)*rows;
        return albumDao.selectByRowBounds(new Album(),new RowBounds(offset,rows));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        return albumDao.selectCount(new Album());
    }

    @Override
    public List<Album> findAlbums() {
        return albumDao.selectAll();
    }
}
