package com.wzh.aspect;

import com.alibaba.fastjson.JSON;
import com.wzh.service.UserCityVoService;
import com.wzh.service.UserService;
import io.goeasy.GoEasy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Aspect
@Configuration
public class AspectForGoeasy {

    @Autowired
    private UserCityVoService userCityVoService;
    @Autowired
    private UserService userService;

    @Around(value = "@annotation(com.wzh.annotation.Urband)")
    public Object logInfo(ProceedingJoinPoint proceedingJoinPoint) {
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-4e21fe5847ed4e04b61cfbe1999b2eba");
        Map<String, Object> map = userCityVoService.urbanDistribution();
        String urband = JSON.toJSONString(map);
        goEasy.publish("urband", urband);
        return proceed;
    }

    @After(value = "@annotation(com.wzh.annotation.Regist)")
    public void logInfo(JoinPoint joinPoint) {
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-4e21fe5847ed4e04b61cfbe1999b2eba");
        Map<String, Object> map = userService.registDistribution();
        String regist = JSON.toJSONString(map);
        goEasy.publish("regist", regist);
    }
}
