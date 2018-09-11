package com.soecode.lyf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.soecode.lyf.entity.params.CommodityParams;
import com.soecode.lyf.entity.result.ResultModel;
import com.soecode.lyf.service.CommodityService;
import com.soecode.lyf.util.redis.cache.GetCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/Commodity")
public class CommodityController {
    @Autowired
    CommodityService commodityService;

    //日志
    final static Logger logger = LoggerFactory.getLogger(CommodityController.class);

    /**
     * 商品查询
     * @request
     */
    @GetCache(name="Commodity",value="request")
    @RequestMapping(value = "/selectCommodityAll",method = RequestMethod.GET)
    @ResponseBody
    public String selectByKeyAndPassword(HttpServletRequest request){
        logger.info("商品查询->start");
        ResultModel result = commodityService.selectCommodityAll(request);
        logger.info("商品查询—>end");
        return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
    }

}
