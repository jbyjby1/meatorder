package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.RsAllOrders;

import javax.servlet.http.HttpServletResponse;

public interface ExportService {

    /**
     * 通过excel的方式导出订单（给饭店使用）
     * @param rsAllOrders 查询出的所有订单
     * @param httpServletResponse http response
     * @return
     */
    public boolean exportCurrentDayliOrders(RsAllOrders rsAllOrders, HttpServletResponse httpServletResponse) ;

}
