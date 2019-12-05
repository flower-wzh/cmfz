package com.wzh.controller;

import com.wzh.entity.Chapter;
import com.wzh.service.ChapterService;
import com.wzh.util.FileSizeUtil;
import com.wzh.util.FileUploadUtil;
import com.wzh.util.MusicTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chapter")
@Slf4j
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/findAll")
    public Map<String,Object> findAll(Chapter chapter,Integer rows,Integer page) {
        Map<String,Object> map = new HashMap<>();
        /**
         * 调用分页查询方法
         */
        List<Chapter> chapters = chapterService.findAllByAlbumId(chapter,rows,page);
        Integer count = chapterService.count(chapter);
        /**
         * 计算数据的页数等信息返回
         */
        Integer pages = count%rows==0?count/rows:count/rows+1;
        //封装数据
        map.put("page",page);
        map.put("records",count);
        map.put("total",pages);
        map.put("rows",chapters);
        return map;
    }


    @RequestMapping("/edit")
    public Map<String, Object> editChapter(Chapter chapter,String oper,String[] id,HttpSession session) {
        log.info("controller"+chapter+"");
        log.info(oper);
        String realPath = session.getServletContext().getRealPath("/file/music/");
        Map<String, Object> map = new HashMap<>();
        //执行添加操作返回操作信息状态
        if("add".equals(oper)){
            map = chapterService.addChapter(chapter);
        }
        //编辑操作（更新）
        if ("edit".equals(oper)){
            map = chapterService.modifyChapter(chapter);
        }
        //删除操作
        if ("del".equals(oper)){
            //查询所有需要删除的文件的信息
            List<Chapter> inIds = chapterService.findInIds(id);
            //批量删除这些文件
            chapterService.removeChapters(id);
            //一次删除服务器上的响应文件
            inIds.forEach(inId->{
                //删除服务器的该文件
                java.io.File delete = new java.io.File(realPath,inId.getFileName());
                delete.delete();
            });
        }
        return map;
    }

    @RequestMapping("/upload")
    public void upload(MultipartFile oldName, String id, HttpServletRequest request) throws IOException {
        if(StringUtil.isNotEmpty(oldName.getOriginalFilename())){
            log.info(id);
            String realPath = request.getSession().getServletContext().getRealPath("/file/music/");
            //调用文件上传方法
            String fileName = FileUploadUtil.upload(oldName, realPath);
            log.info(fileName);
            /**
             * 生成网络资源ip
             */
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/file/music/"+fileName;
            String audioInfo = MusicTimeUtil.getAudioInfo(realPath+fileName);
            /**
             * 封装更新的chapter信息
             */
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setSize(FileSizeUtil.size((int) oldName.getSize()));
            chapter.setUrl(uri);
            chapter.setTime(audioInfo);
            chapter.setFileName(fileName);
            chapter.setOldName(oldName.getOriginalFilename());
            log.info(uri);
            //更新chapter信息
            chapterService.modifyChapter(chapter);
        }
    }


    @RequestMapping("/download")
    public void downloadFile(String fileName, HttpSession session, HttpServletResponse response) throws IOException {
        String realPath = session.getServletContext().getRealPath("/file/music");
        //从服务器下载该文件
        File download = new File(realPath,fileName);
        try(
                //拿到输入输出流
                FileInputStream in = new FileInputStream(download);
                ServletOutputStream out = response.getOutputStream()
        ) {
            //根据文件名查找该文件信息
            Chapter byFileName = chapterService.findByFileName(fileName);
            log.info("downFile" + byFileName);
            /**
             * 设置下载的响应头
             */
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(byFileName.getOldName(), "UTF-8"));
            //响应文件内容
            IOUtils.copy(in, out);
        }
    }


    @RequestMapping("/delete")
    public String deleteFile(String id,HttpSession session) {
        Chapter oneChapter = chapterService.findOneChapter(id);
        chapterService.removeChapter(id);
        //删除服务器的该文件
        String realPath = session.getServletContext().getRealPath("/file/music/");
        java.io.File delete = new java.io.File(realPath,oneChapter.getFileName());
        delete.delete();
        return "ok";
    }

}
