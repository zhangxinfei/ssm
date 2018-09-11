package com.soecode.lyf.service.impl;

import com.soecode.lyf.controller.UserController;
import com.soecode.lyf.entity.result.ResultModel;
import com.soecode.lyf.enums.WizardAuditEnum;
import com.soecode.lyf.mapper.CommodityMapper;
import com.soecode.lyf.service.CommodityService;
import com.soecode.lyf.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    CommodityMapper commodityMapper;

    //日志
    final static Logger logger = LoggerFactory.getLogger(CommodityServiceImpl.class);


    @Override
    public ResultModel selectCommodityAll(HttpServletRequest request) {
        //初始化返回对象
        ResultModel result = ResultUtil.info(WizardAuditEnum.StatusEnum.STATUS_FAIL.getValue(),WizardAuditEnum.StatusEnum.STATUS_FAIL.getDesc());
        logger.info("查询商品信息->start");
        List<Map<String,Object>> commodityList = commodityMapper.selectCommodityAll();
        logger.info("查询商品信息->end");
        if(commodityList.size() > 0){
            result = ResultUtil.success(WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getValue(),WizardAuditEnum.StatusEnum.STATUS_SUCCESS.getDesc());
            result.setData(commodityList);
        }
        return result;
    }
}
