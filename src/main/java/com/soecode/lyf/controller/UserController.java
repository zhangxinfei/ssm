package com.soecode.lyf.controller;

import com.alibaba.fastjson.JSONObject;
import com.soecode.lyf.entity.User;
import com.soecode.lyf.entity.params.UserParams;
import com.soecode.lyf.entity.result.ResultModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.soecode.lyf.enums.CommonInfoEnum;
import com.soecode.lyf.enums.WizardAuditEnum;
import com.soecode.lyf.service.UserService;
import com.soecode.lyf.util.JwtUtils;
import com.soecode.lyf.util.ResultUtil;
import com.soecode.lyf.util.RsaJsUtils;
import com.soecode.lyf.util.entity.token.UserTokenData;
import com.soecode.lyf.util.redis.SerializeUtils;
import com.soecode.lyf.service.RedisUtilService;
import com.soecode.lyf.util.redis.cache.GetCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;

    //日志
    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 用户登录判断
     * @return
     */
//    @GetCache(name="User",value="userParams")
    @RequestMapping(value = "/selectByKeyAndPassword",method = RequestMethod.POST)
    @ResponseBody
    public String selectByKeyAndPassword(HttpServletRequest request,@RequestBody String user){
        ResultModel result = ResultUtil.info(WizardAuditEnum.StatusEnum.STATUS_FAIL.getValue(),WizardAuditEnum.StatusEnum.STATUS_FAIL.getDesc());
        try {
            logger.info("解密");
//            String user1 = JSON.parseObject(user,String.class);
            String jsonStr = RsaJsUtils.decryptByPrivateKeyData(user);
            UserParams userParams = JSON.parseObject(jsonStr, UserParams.class);
            userParams.setUser_studentId(userParams.getUser_studentId());
            logger.info("======================验证本地库是否存在数据=========================");
//            user.setUser_username(user.getUser_studentId());
//            logger.info("用户登录->start");
//            result = userService.selectByKeyAndPassword(request,userParams);
//            logger.info("用户登录—>end");
            result = userService.selectUserByPrimaryKey(request,userParams);
            if(result.getData() == null){
                logger.info("======================未查询到用户数据=========================");
                return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
            }
            logger.info("======================用户登录成功封装用户数据返回token=========================");
            //***********************组织token中存储的用户信息start*******************************
            long ttlMillis = 1000*60*60*2;
            //***********************组织token中存储的用户信息end*******************************
            UserTokenData userToken = new UserTokenData();
            userToken.setUser_id(String.valueOf(userParams.getUser_id()));//用户id
            userToken.setUser_iphone(String.valueOf(userParams.getUser_iphone()));//用户手机号
            userToken.setUser_name(userParams.getUser_name());//用户姓名
            userToken.setUser_studentId(userParams.getUser_studentId());//用户学号
            String subject = JSONObject.toJSONString(userToken, SerializerFeature.WriteMapNullValue);
//             System.out.print(StrZipUtil.uncompress(subject));
            // String audience = "编号";
            Map<String,Object> resultMap = new HashMap<String,Object>();

            //用户code                   用户ip地址
            String token = JwtUtils.createJWT(String.valueOf(userParams.getUser_id()),request.getRemoteAddr(),subject,ttlMillis,userParams.getUser_studentId());
            resultMap.put("memberName",userParams.getUser_studentId());
            resultMap.put("token",token);
            result = ResultUtil.success(WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getValue(),WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getDesc());
            result.setData(resultMap);
        }catch (Exception e){
            result = ResultUtil.info(WizardAuditEnum.StatusEnum.STATUS_FAIL.getValue(),WizardAuditEnum.StatusEnum.STATUS_FAIL.getDesc());
            e.printStackTrace();
        }
        return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
    }

    /**
     * 更改用户信息
     * @return
     */
    @RequestMapping(value = "/updateByUserID",method = RequestMethod.POST)
    @ResponseBody
    public String updateByUserID(HttpServletRequest request,@RequestBody UserParams UpUserParams,@RequestBody UserParams InUserParams){
        logger.info("修改用户信息->start");
        ResultModel result = userService.updateByUserID(request,UpUserParams,InUserParams);
        logger.info("修改用户信息—>end");
        return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
    }

    /**
     * 查询用户信息
     * @return
     */
    @RequestMapping(value = "/selectUserByPrimaryKey",method = RequestMethod.POST)
    @ResponseBody
    public String selectUserByPrimaryKey(HttpServletRequest request,@RequestBody UserParams userParams){
        logger.info("查询用户信息->start");
        ResultModel result = userService.selectUserByPrimaryKey(request,userParams);
        logger.info("查询用户信息—>end");
        return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
    }

    /**
     * 删除用户信息
     * @return
     */
    @RequestMapping(value = "/deleteByUserID",method = RequestMethod.POST)
    @ResponseBody
    public String deleteByUserID(HttpServletRequest request,@RequestBody UserParams userParams){
        logger.info("查询用户信息->start");
        ResultModel result = userService.deleteByUserID(request,userParams);
        logger.info("查询用户信息—>end");
        return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
    }

}
