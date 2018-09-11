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
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    //    日志
    final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public ResultModel selectByKeyAndPassword(HttpServletRequest request, UserParams userParams) {
//        初始化序列化结果集
        ResultModel result = ResultUtil.info(WizardAuditEnum.StatusEnum.STATUS_FAIL.getValue(),WizardAuditEnum.StatusEnum.STATUS_FAIL.getDesc());
//        查询数据库，返回值为0的化就是没有登录失败，为1的化就是成功
        //添加用户的状态
        userParams.setUser_status(WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getValue());
        logger.info("登录验证—>start");
        int influence = userMapper.selectByKeyAndPassword(userParams);
        if(influence == 1){
            result = ResultUtil.success(WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getValue(),WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getDesc());
            logger.info("登录成功->end");
        }else {
            logger.info("登录失败—>end");
        }
        return result;
    }

    @Override
    public ResultModel updateByUserID(HttpServletRequest request,UserParams UpUserParams,UserParams InUserParams) {
        //初始化返回对象
        ResultModel result = ResultUtil.info(WizardAuditEnum.StatusEnum.STATUS_FAIL.getValue(),WizardAuditEnum.StatusEnum.STATUS_FAIL.getDesc());
        logger.info("更新操作—>start");
        int UpInfluence = 0;
        int InInfluence = 0;
        if(UpUserParams != null){
            UpInfluence = userMapper.updateByUserID(UpUserParams);
        } else if (InUserParams != null){
            //查看新增用户元看来是否已经注册过
            UserParams userParams = new UserParams();
            userParams.setUser_studentId(InUserParams.getUser_studentId());
            userParams.setUser_status(WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getValue());
            int influence = userMapper.selectByKeyAndPassword(userParams);
            if(influence != 1){
                InInfluence = userMapper.insertByUserId(InUserParams);
            }
        }
        logger.info("更新操作—>end");
        //更新成功后返回int类型，1
        if(UpInfluence == 1 && InInfluence ==1){
            result = ResultUtil.success(WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getValue(),WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getDesc());
        }
        return result;
    }

    @Override
    public ResultModel selectUserByPrimaryKey(HttpServletRequest request, UserParams userParams) {
        //初始化返回对象
        ResultModel result = ResultUtil.info(WizardAuditEnum.StatusEnum.STATUS_FAIL.getValue(),WizardAuditEnum.StatusEnum.STATUS_FAIL.getDesc());
        //设置用户状态
        userParams.setUser_status(WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getValue());
        logger.info("查询用户信息->start");
        List<Map<String,Object>> userParamsList = userMapper.selectUserByPrimaryKey(userParams);
        logger.info("查询用户信息->end");
        if(userParamsList.size()>0){
            result = ResultUtil.success(WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getValue(),WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getDesc());
            //把数据存入返回对象
            result.setData(userParamsList);
        }
        return result;
    }

    @Override
    public ResultModel deleteByUserID(HttpServletRequest request, UserParams userParams) {
        //初始化返回对象
        ResultModel result = ResultUtil.info(WizardAuditEnum.StatusEnum.STATUS_FAIL.getValue(),WizardAuditEnum.StatusEnum.STATUS_FAIL.getDesc());
        userParams.setUser_status(WizardAuditEnum.StatusEnum.STATUS_FAIL.getValue());
        logger.info("删除用户->start");
        int influence = userMapper.deleteByUserID(userParams);
        logger.info("删除用户->end");
        if(influence ==1){
            result = ResultUtil.success(WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getValue(),WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getDesc());
        }
        return result;
    }

}
