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
     * 通过id和密码判断用户登录
     */
    int selectByKeyAndPassword(UserParams user);
}