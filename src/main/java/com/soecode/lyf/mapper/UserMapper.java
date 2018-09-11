package com.soecode.lyf.mapper;


import com.soecode.lyf.entity.User;
import com.soecode.lyf.entity.params.UserParams;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

//    通过id和状态查询单个用户信息
    List<Map<String,Object>> selectUserByPrimaryKey(UserParams userParams);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /*
     * 通过学号和密码判断用户登录
     */
    int selectByKeyAndPassword(UserParams userParams);
    /*
     * 通过id修改用户信息
     */
    int updateByUserID(UserParams userParams);
    /*
     * 查询全部用户信息，通过管理员id
     */
    List<Map<String,Object>> selectByUserAll(UserParams userParams);
    /*
     * 通过id修改用户信息
     */
    int insertByUserId(UserParams userParams);
    /*
     * 删除用户信息
     */
    int deleteByUserID(UserParams userParams);

}