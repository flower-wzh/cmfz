package com.wzh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        Long xw = redisTemplate.opsForSet().add("xw", "a4efaa239ced4143a7489bc877c35326","ea5417a3825a4cd897e8ea587f415b29");
        System.out.println(xw);
    }
}
