package com.wzh.service.impl;

import com.alibaba.excel.EasyExcel;
import com.wzh.dao.BannerDao;
import com.wzh.entity.Banner;
import com.wzh.listener.BannerListener;
import com.wzh.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Service
@Transactional
@Slf4j
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public Map<String,Object> addBanner(Banner banner) {
        Map<String,Object> map = new HashMap<>();
		banner.setId(UUID.randomUUID().toString().replace("-",""));
        banner.setCreateDate(new Date());
        bannerDao.insert(banner);
        map.put("bannerId",banner.getId());
        map.put("status",200);
        return map;
    }

    @Override
    public void removeBanner(String id) {
        bannerDao.deleteByPrimaryKey(id);
    }

    @Override
    public void removeBanners(String[] id) {
        bannerDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public  Map<String,Object> modifyBanner(Banner banner) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtil.isEmpty(banner.getUrl())){
            banner.setUrl(null);
        }
        bannerDao.updateByPrimaryKeySelective(banner);
        map.put("bannerId",banner.getId());
        map.put("status",200);
        return map;
    }

    @Override
    public Banner findOneBanner(String id) {
        return bannerDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> findAll(Integer rows,Integer page) {
        Integer offset = (page-1)*rows;
        return bannerDao.selectByRowBounds(new Banner(),new RowBounds(offset,rows));
    }

    @Override
    public Integer count() {
        return bannerDao.selectCount(new Banner());
    }

    @Override
    public void outBanner(OutputStream outputStream) {
        EasyExcel.write(outputStream,Banner.class).sheet("轮播图信息").doWrite(bannerDao.selectAll());

    }

    @Override
    public void outBannerModel(OutputStream outputStream) {
        EasyExcel.write(outputStream,Banner.class).sheet("轮播图信息").doWrite(Arrays.asList(new Banner()));
    }

    @Override
    public void inputBanner(InputStream inputStream) {
        EasyExcel.read(inputStream, Banner.class, new BannerListener()).sheet().doRead();
    }

    @Override
    public List<Banner> findActiveBanner() {
        Example example = new Example(Banner.class);
        example.createCriteria().andEqualTo("status","1");
        return bannerDao.selectByExample(example);
    }

    /*@Override
    public Workbook outBanner() {
        List<Banner> banners = bannerDao.selectAll();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("轮播图信息");
        Row row = sheet.createRow(0);
        sheet.setColumnWidth(0, 15*256);
        sheet.setColumnWidth(1, 15*256);
        sheet.setColumnWidth(2, 15*256);
        sheet.setColumnWidth(3, 15*256);
        sheet.setColumnWidth(5, 15*256);
        String[] title = {"ID","标题","图片","链接","创建日期","描述","状态"};
        Cell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }

        for (int i = 0; i < banners.size(); i++) {
            Row row1 = sheet.createRow(i+1);
            row1.createCell(0).setCellValue(banners.get(i).getId());
            row1.createCell(1).setCellValue(banners.get(i).getTitle());
            row1.createCell(2).setCellValue(banners.get(i).getUrl());
            row1.createCell(3).setCellValue(banners.get(i).getHref());
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
            row1.createCell(4).setCellStyle(cellStyle);
            row1.createCell(4).setCellValue(banners.get(i).getCreateDate());
            row1.createCell(5).setCellValue(banners.get(i).getDescription());
            row1.createCell(6).setCellValue(banners.get(i).getStatus());
        }
        return workbook;
    }

    @Override
    public Workbook outBannerModel() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("轮播图信息");
        Row row = sheet.createRow(0);
        sheet.setColumnWidth(0, 15*256);
        sheet.setColumnWidth(1, 15*256);
        sheet.setColumnWidth(2, 15*256);
        sheet.setColumnWidth(3, 15*256);
        sheet.setColumnWidth(5, 15*256);
        String[] title = {"ID","标题","图片","链接","创建日期","描述","状态"};
        Cell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        return workbook;
    }

    @Override
    public void inputBanner(InputStream inputStream) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheetAt = workbook.getSheetAt(0);
            for (int i = 1; i <= sheetAt.getLastRowNum(); i++) {
                log.info("开始读取数据");
                Banner banner = new Banner();
                HSSFRow row = sheetAt.getRow(i);
                banner.setId(row.getCell(0).getStringCellValue());
                banner.setTitle(row.getCell(1).getStringCellValue());
                banner.setUrl(row.getCell(2).getStringCellValue());
                banner.setHref(row.getCell(3).getStringCellValue());
                banner.setCreateDate(row.getCell(4).getDateCellValue());
                banner.setDescription(row.getCell(5).getStringCellValue());
                banner.setStatus(row.getCell(6).getStringCellValue());
                log.info(""+banner);
                bannerDao.insert(banner);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}
