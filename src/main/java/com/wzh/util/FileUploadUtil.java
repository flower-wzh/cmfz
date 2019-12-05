package com.wzh.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUploadUtil {


    public static String upload(MultipartFile file, String realPath) {
        String fileName = "";
        //判断服务其是否存在该路径
        File newFile = new File(realPath);
        if(!newFile.exists()){
            newFile.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        //新文件名前缀
        String newFileNamePrefix = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())
                + UUID.randomUUID().toString().replace("-","");
        //新文件名后缀
        String newFileNameSuffix = "."+ FilenameUtils.getExtension(originalFilename);
        fileName = newFileNamePrefix + newFileNameSuffix;
        try {
            //文件上传操作
            file.transferTo(new File(realPath,fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回上传的文件名
        return fileName;
    }
}
