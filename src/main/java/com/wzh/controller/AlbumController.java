package com.wzh.controller;

import com.wzh.entity.Album;
import com.wzh.entity.Chapter;
import com.wzh.service.AlbumService;
import com.wzh.service.ChapterService;
import com.wzh.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/album")
@Slf4j
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("/findAll")
    public Map<String,Object> findAll(Integer rows,Integer page) {
        Map<String,Object> map = new HashMap<>();
        List<Album> albums = albumService.findAll(rows,page);
        Integer count = albumService.count();
        Integer pages = count%rows==0?count/rows:count/rows+1;
        map.put("page",page);
        map.put("records",count);
        map.put("total",pages);
        map.put("rows",albums);
        return map;
    }

    @RequestMapping("/edit")
    public Map<String, Object> editAlbum(Album album,String oper,String[] id) {
        log.info("controller"+album+"");
        log.info(oper);
        Map<String, Object> map = new HashMap<>();
        if("add".equals(oper)){
            map = albumService.addAlbum(album);
        }
        if ("edit".equals(oper)){
            map = albumService.modifyAlbum(album);
        }
        if ("del".equals(oper)){
            albumService.removeAlbums(id);
        }
        return map;
    }

    @RequestMapping("/upload")
    public void upload(MultipartFile cover, String id, HttpServletRequest request) throws UnknownHostException {
        if(StringUtil.isNotEmpty(cover.getOriginalFilename())){
            log.info(id);
            String realPath = request.getSession().getServletContext().getRealPath("/file/cover/");
            //调用文件上传方法
            String fileName = FileUploadUtil.upload(cover, realPath);
            //获取http
            String http = request.getScheme();
            //获取ip
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            //获取端口
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            log.info("本机地址-host"+hostAddress);
            String uri = http+"://"+hostAddress+":"+serverPort+contextPath+"/file/cover/"+fileName;
            Album album = new Album();
            album.setId(id);
            album.setCover(uri);
            log.info(uri);
            albumService.modifyAlbum(album);
        }
    }

    @GetMapping("/detail/{uId}/{id}")
    public Album detail(@PathVariable("uId") String uId, @PathVariable("id") String id) {
        return albumService.findOneAlbum(id);
    }

    @Autowired
    private ChapterService chapterService;

    @GetMapping("/chapters/{uId}/{id}")
    public Map<String, Object> chapters(@PathVariable("uId") String uId, @PathVariable("id") String id) {
        Map<String,Object> map = new HashMap<>();
        List<Chapter> chapterList = chapterService.findAllByAlbumId(id);
        map.put("status",200);
        map.put("chapters",chapterList);
        return map;
    }
}
