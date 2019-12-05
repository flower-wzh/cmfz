package com.wzh;

import com.wzh.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCount {

    @Autowired
    private UserService userService;

    @Test
    public void test01() {
        Map<String, Object> map = userService.registDistribution();
        map.forEach((k,v)-> System.out.println(k+":"+v));
    }
}
