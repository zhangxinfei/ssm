package com.soecode.lyf.mapper;


import com.soecode.lyf.entity.User;
import com.soecode.lyf.entity.params.UserParams;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

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
     * 通过用户id查询用户表
     */
    int selectByUserId(UserParams userParams);

}