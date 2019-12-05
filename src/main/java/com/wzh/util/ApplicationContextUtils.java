package com.wzh.util;

import com.wzh.dao.BannerDao;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static BannerDao getBannerDao(String id) {
        BannerDao bean = (BannerDao) context.getBean(id);
        return bean;
    }

    public static BannerDao getBannerDao(Class clazz) {
        BannerDao bean = (BannerDao) context.getBean(clazz);
        return bean;
    }

    public static BannerDao getBannerDao(String id,Class clazz) {
        BannerDao bean = (BannerDao) context.getBean(id,clazz);
        return bean;
    }
}
