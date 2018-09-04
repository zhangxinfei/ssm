package com.soecode.lyf.service;

import com.soecode.lyf.entity.params.UserParams;
import com.soecode.lyf.entity.result.ResultModel;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    /**
     *根据主键查询用户
     * @Parms request
    */
    ResultModel selectByKeyAndPassword(HttpServletRequest request, UserParams userParams);

    /**
     *根据主键查询用户
     * @Parms request
     */
    ResultModel updateByUserID(HttpServletRequest request, UserParams userParams);










}
