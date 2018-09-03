package com.soecode.lyf.service.impl;

import com.soecode.lyf.enums.WizardAuditEnum;
import com.soecode.lyf.mapper.UserMapper;
import com.soecode.lyf.entity.params.UserParams;
import com.soecode.lyf.entity.result.ResultModel;
import com.soecode.lyf.service.UserService;
import com.soecode.lyf.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    //    日志
    final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public ResultModel selectByKeyAndPassword(HttpServletRequest request, UserParams user) {
//        初始化序列化结果集
        ResultModel result = ResultUtil.info(WizardAuditEnum.StatusEnum.STATUS_FAIL.getValue(),WizardAuditEnum.StatusEnum.STATUS_FAIL.getDesc());
//        查询数据库，返回值为0的化就是没有登录失败，为1的化就是成功
        logger.info("登录验证—>start");
        int influence = userMapper.selectByKeyAndPassword(user);
        if(influence == 1){
            result = ResultUtil.success(WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getValue(),WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getDesc());
            logger.info("登录成功->end");
        }
            logger.info("登录失败—>end");
        return result;
    }
}
