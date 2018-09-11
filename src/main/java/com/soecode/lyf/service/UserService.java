package com.soecode.lyf.service;

import com.soecode.lyf.entity.params.UserParams;
import com.soecode.lyf.entity.result.ResultModel;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    /**
     *用户登录
     * @Parms request
    */
    ResultModel selectByKeyAndPassword(HttpServletRequest request, UserParams userParams);

    /**
     *更改单个用户信息
     * @Parms request
     * @parms UpUserParams 修改用户信息
     * @parms InUserParams 新增用户信息
     */
    ResultModel updateByUserID(HttpServletRequest request, UserParams UpUserParams,UserParams InUserParams);

    /**
     *查询用户信息
     * @Parms request
     * @parms userParams
     */
    ResultModel selectUserByPrimaryKey(HttpServletRequest request, UserParams userParams);

    /**
     *根据主键查询用户
     * @Parms request
     * @parms userParams
     */
    ResultModel deleteByUserID(HttpServletRequest request, UserParams userParams);









}
