package com.wzh.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.wzh.dao.BannerDao;
import com.wzh.entity.Banner;
import com.wzh.util.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class BannerListener extends AnalysisEventListener<Banner> {

    private static final int BATCH_COUNT = 20;
    List<Banner> bannerList = new ArrayList<>();

    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        banner.setId(UUID.randomUUID().toString());
        bannerList.add(banner);
        log.info("解析到一条数据:{}", JSON.toJSONString(banner));
        BannerDao bannerDao = ApplicationContextUtils.getBannerDao("bannerDao");
        if (bannerList.size() >= BATCH_COUNT) {
            bannerDao.insertList(bannerList);
            bannerList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        BannerDao bannerDao = ApplicationContextUtils.getBannerDao("bannerDao");
        if(!ObjectUtils.isEmpty(bannerList)) {
            bannerDao.insertList(bannerList);
        }
        log.info("导入数据完成");
    }
}
