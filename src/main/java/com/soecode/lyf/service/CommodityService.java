package com.soecode.lyf.service;

import com.soecode.lyf.entity.result.ResultModel;

import javax.servlet.http.HttpServletRequest;

public interface CommodityService {
    /**
     * 查询全部商品
     *
     */
    ResultModel selectCommodityAll(HttpServletRequest request);
}
