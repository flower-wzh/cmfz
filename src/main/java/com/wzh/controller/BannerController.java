package com.wzh.controller;

import com.wzh.entity.Banner;
import com.wzh.service.BannerService;
import com.wzh.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/banner")
@Slf4j
public class BannerController{

    @Autowired
    private BannerService bannerService;


/*    @RequestMapping("/add")
    public void addBanner(Banner banner) {
        bannerService.addBanner(banner);
    }*/

    @RequestMapping("/edit")
    public Map<String, Object> editBanner(Banner banner,String oper,String[] id) {
        log.info("controller"+banner+"");
        log.info(oper);
        Map<String, Object> map = new HashMap<>();
        if("add".equals(oper)){
            map = bannerService.addBanner(banner);
        }
        if ("edit".equals(oper)){
            map = bannerService.modifyBanner(banner);
        }
        if ("del".equals(oper)){
            bannerService.removeBanners(id);
        }
        return map;
    }

    @RequestMapping("/upload")
    public void upload(MultipartFile url, String bannerId, HttpServletRequest request) throws UnknownHostException {
        if(StringUtil.isNotEmpty(url.getOriginalFilename())){
            log.info(bannerId);
            String realPath = request.getSession().getServletContext().getRealPath("/file/img/");
            //调用文件上传方法
            String fileName = FileUploadUtil.upload(url, realPath);
            String http = request.getScheme();
            log.info(http);
            String localHost = InetAddress.getLocalHost().toString();
            log.info(localHost);
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/file/img/"+fileName;
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl(uri);
            log.info(uri);
            bannerService.modifyBanner(banner);
        }
    }
/*    @RequestMapping("/remove")
    public void removeBanner(String id) {
        bannerService.removeBanner(id);
    }

    @RequestMapping("/modify")
    public void modifyBanner(Banner banner) {
        bannerService.modifyBanner(banner);
    }*/

    @RequestMapping("/findOne")
    public Banner findOneBanner(String id) {
        return bannerService.findOneBanner(id);
    }

    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer rows,Integer page) {
        Map<String,Object> map = new HashMap<>();
        List<Banner> banners = bannerService.findAll(rows,page);
        Integer count = bannerService.count();
        Integer pages = count%rows==0?count/rows:count/rows+1;
        map.put("page",page);
        map.put("total",pages);
        map.put("records",count);
        map.put("rows",banners);
        return map;
    }

    @RequestMapping("/out")
    public void out(HttpServletResponse response) throws IOException {
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("banners.xlsx","UTF-8"));
        //workbook.write(new FileOutputStream(new File("F://banners.xls")));
        bannerService.outBanner(response.getOutputStream());
    }

    @RequestMapping("/outModel")
    public void outModel(HttpServletResponse response) throws IOException {
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("bannersModel.xlsx","UTF-8"));
        bannerService.outBannerModel(response.getOutputStream());
    }

    @RequestMapping("/input")
    public void input(MultipartFile file) throws IOException {
        log.info(file.getOriginalFilename());
        if (StringUtil.isNotEmpty(file.getOriginalFilename())){
            InputStream inputStream = file.getInputStream();
            bannerService.inputBanner(inputStream);
        }
    }
}
