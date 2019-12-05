package com.wzh;

import com.wzh.entity.Admin;
import com.wzh.dao.AdminDao;
import com.wzh.service.ElasticSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzApplication.class)
public class CmfzApplicationTests {
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    public void contextLoads() {
        List<Admin> admins = adminDao.selectAll();
        admins.forEach(admin -> System.out.println(admin));
    }

    @Test
    public void insert() {
        Admin admin = new Admin(null, "xiaowang", "123456");
        adminDao.insert(admin);
        System.out.println(admin);
    }


    @Test
    public void create() {
        elasticSearchService.createIndex();
    }
}
