package com.wzh.service.impl;

import com.wzh.dao.LogInfoDao;
import com.wzh.entity.LogInfo;
import com.wzh.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogInfoServiceImpl implements LogInfoService {

    @Autowired
    private LogInfoDao logInfoDao;

    @Override
    public void addLogInfo(LogInfo logInfo) {
        logInfoDao.insert(logInfo);
    }
}
