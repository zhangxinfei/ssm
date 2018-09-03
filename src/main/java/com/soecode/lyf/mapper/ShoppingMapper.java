package com.soecode.lyf.mapper;

import com.soecode.lyf.entity.Shopping;

public interface ShoppingMapper {
    int deleteByPrimaryKey(Integer buyId);

    int insert(Shopping record);

    int insertSelective(Shopping record);

    Shopping selectByPrimaryKey(Integer buyId);

    int updateByPrimaryKeySelective(Shopping record);

    int updateByPrimaryKey(Shopping record);
}