package com.soecode.lyf.mapper;


import com.soecode.lyf.entity.Administrators;

public interface AdministratorsMapper {
    int deleteByPrimaryKey(Integer administratorsId);

    int insert(Administrators record);

    int insertSelective(Administrators record);

    Administrators selectByPrimaryKey(Integer administratorsId);

    int updateByPrimaryKeySelective(Administrators record);

    int updateByPrimaryKey(Administrators record);
}