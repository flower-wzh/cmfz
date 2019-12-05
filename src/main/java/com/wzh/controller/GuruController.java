package com.wzh.controller;

import com.wzh.entity.Guru;
import com.wzh.service.GuruService;
import com.wzh.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/guru")
@Slf4j
public class GuruController {

    @Autowired
    private GuruService guruService;

    @RequestMapping("/all")
    public List<Guru> findAll() {
        return guruService.findAll();
    }

    @RequestMapping("/show")
    public Map<String,Object> showAll(Integer rows, Integer page) {
        Map<String,Object> map = new HashMap<>();
        List<Guru> gurus = guruService.showAll(rows, page);
        Integer count = guruService.count();
        Integer pages = count%rows==0?count/rows:count/rows+1;
        map.put("page",page);
        map.put("records",count);
        map.put("total",pages);
        map.put("rows",gurus);
        return map;
    }

    @RequestMapping("/edit")
    public Map<String, Object> editGuru(Guru guru, String oper, String[] id) {
        log.info("controller"+guru+"");
        log.info(oper);
        Map<String, Object> map = new HashMap<>();
        if("add".equals(oper)){
            map = guruService.addGuru(guru);
        }
        if ("edit".equals(oper)){
            map = guruService.modifyGuru(guru);
        }
        if ("del".equals(oper)){
            guruService.removeGurus(id);
        }
        return map;
    }


    @RequestMapping("/upload")
    public void upload(MultipartFile photo, String guruId, HttpServletRequest request) throws UnknownHostException {
        if(StringUtil.isNotEmpty(photo.getOriginalFilename())){
            log.info(guruId);
            String realPath = request.getSession().getServletContext().getRealPath("/file/photo/");
            //调用文件上传方法
            String fileName = FileUploadUtil.upload(photo, realPath);
            //获取http
            String http = request.getScheme();
            //获取ip
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            //获取端口
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            log.info("本机地址-host"+hostAddress);
            String uri = http+"://"+hostAddress+":"+serverPort+contextPath+"/file/photo/"+fileName;
           Guru guru = new Guru();
            guru.setId(guruId);
            guru.setPhoto(uri);
            log.info(uri);
            guruService.modifyGuru(guru);
        }
    }
}
