package com.wzh.controller;

import com.wzh.entity.Article;
import com.wzh.service.ArticleService;
import com.wzh.service.ElasticSearchService;
import com.wzh.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ElasticSearchService elasticSearchService;

    @RequestMapping("/findAll")
    public Map<String, Object> findAll(Integer rows,Integer page) {
        Map<String,Object> map = new HashMap<>();
        List<Article> articles = articleService.findAll(rows,page);
        Integer count = articleService.count();
        Integer pages = count%rows==0?count/rows:count/rows+1;
        map.put("page",page);
        map.put("records",count);
        map.put("total",pages);
        map.put("rows",articles);
        return map;
    }

    @RequestMapping("/upload")
    public Map<String,Object> upload(MultipartFile uploadFile, HttpServletRequest request) throws UnknownHostException {
        Map<String,Object> map = new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/file/article/");
        //调用文件上传方法
        String fileName = FileUploadUtil.upload(uploadFile, realPath);
        //获取http
        String http = request.getScheme();
        //获取ip
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        log.info(fileName);
        //获取端口
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        log.info("本机地址-host"+hostAddress);
        String uri = http+"://"+hostAddress+":"+serverPort+contextPath+"/file/article/"+fileName;
        map.put("error",0);
        map.put("url",uri);
        return map;
    }

    @RequestMapping("/add")
    public Map<String, Object> addArticle(MultipartFile file,Article article,HttpServletRequest request) throws UnknownHostException {
        Article article1 = upFile(file, article, request);
        return articleService.addArticle(article1);
    }

    private Article upFile(MultipartFile file, Article article, HttpServletRequest request) throws UnknownHostException {
        String realPath = request.getSession().getServletContext().getRealPath("/file/articleCover");
        String fileName = FileUploadUtil.upload(file, realPath);
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/file/articleCover/"+fileName;
        article.setImg(uri);
        log.info("article:"+article);
        return article;
    }

    @RequestMapping("/modify")
    public Map<String, Object> modifyArticle(MultipartFile file,Article article,HttpServletRequest request) throws UnknownHostException {
        Article article1 = upFile(file, article, request);
        return articleService.modifyArticle(article1);
    }

    @RequestMapping("/edit")
    public void editArticle(String[] id,String oper) {
        if("del".equals(oper)) {
            articleService.removeArticles(id);
        }
    }

    @RequestMapping("/search")
    public Map<String,Object> search(String message, Integer page) {
        log.info(message);
        return elasticSearchService.findByMsg(message,page);
    }


    @RequestMapping(value = "/files")
    public Map<String,Object> fileManager(HttpServletRequest request, HttpServletResponse response) {

        HashMap<String ,Object> map = new HashMap<>();
        List<Object> fileList = new ArrayList<>();
        String realPath = request.getSession().getServletContext().getRealPath("/file/article");
        File file = new File(realPath);
        File[] files = file.listFiles();
        for (File file1 : files) {
            Map<String ,Object> fileMap = new HashMap<>();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            fileMap.put("filetype", FilenameUtils.getExtension(file1.getName()));
            fileMap.put("filename",file1.getName());
            log.info(file1.getName());
            log.info(file1.getName().substring(0,17));
            fileMap.put("datetime",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Long.valueOf(file1.getName().substring(0,17))));
            fileList.add(fileMap);
        }
        map.put("current_url",request.getContextPath()+"/file/article/");
        map.put("total_count",files.length);
        map.put("file_list",fileList);

        return map;
    }

    @RequestMapping("/detail/{uId}/{id}")
    public Article detail(@PathVariable("uId") String uId, @PathVariable("id") String id) {
        return articleService.findOneArticle(id);
    }
}