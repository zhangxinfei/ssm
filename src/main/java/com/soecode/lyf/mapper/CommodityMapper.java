package com.soecode.lyf.mapper;

import com.soecode.lyf.entity.Commodity;

import java.util.List;
import java.util.Map;

public interface CommodityMapper {
    int deleteByPrimaryKey(Integer commodityId);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(Integer commodityId);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);

    //查询全部商品信息
    List<Map<String,Object>> selectCommodityAll();
}