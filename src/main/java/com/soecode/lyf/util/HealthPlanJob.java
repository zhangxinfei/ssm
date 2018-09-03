package com.soecode.lyf.util;

import com.soecode.lyf.mapper.UserMapper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

//import com.wy.care60.dao.MElementMapper;
//import com.wy.care60.dao.MInterEnumMapper;
//import com.wy.care60.dao.MProjectMapper;
//import com.wy.care60.model.MInterEnum;
//import com.wy.care60.model.MProject;

/**
 * Created by Administrator on 2017/12/20.
 */
@Component
public class HealthPlanJob extends QuartzJobBean {

    @Autowired
    UserMapper userMapper;

    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(new Date());

    }


}

