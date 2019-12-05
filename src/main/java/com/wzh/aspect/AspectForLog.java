package com.wzh.aspect;

import com.wzh.annotation.LogService;
import com.wzh.entity.Admin;
import com.wzh.entity.LogInfo;
import com.wzh.service.LogInfoService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Aspect
@Configuration
public class AspectForLog {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LogInfoService logInfoService;

    @Around(value = "@annotation(com.wzh.annotation.LogService)")
    public Object logInfo(ProceedingJoinPoint proceedingJoinPoint) {
        Object result =null;
        LogInfo logInfo = new LogInfo();
        //拿到操作的管理员名字
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        String username = admin.getUsername();
        logInfo.setAdmin(username);
        //拿到动作
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        LogService annotation = signature.getMethod().getAnnotation(LogService.class);
        String value = annotation.value();
        logInfo.setAction(value);
        //状态
        logInfo.setStatus("success");
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logInfo.setStatus("error");
        }
        //设置时间
        logInfo.setDate(new Date());
        logInfo.setId(UUID.randomUUID().toString());
        logInfoService.addLogInfo(logInfo);
        return result;
    }
}
