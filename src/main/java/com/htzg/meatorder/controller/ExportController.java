package com.htzg.meatorder.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.htzg.meatorder.domain.DailyChicken;
import com.htzg.meatorder.domain.PersonOrder;
import com.htzg.meatorder.domain.RsAllOrders;
import com.htzg.meatorder.domain.SupportOrderStatus;
import com.htzg.meatorder.service.ExportService;
import com.htzg.meatorder.service.OrderService;
import com.htzg.meatorder.util.DataResponse;
import com.htzg.meatorder.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.htzg.meatorder.util.CommonConstant.shanghai;

/**
 * 将订单导出到excel
 */
@RestController
public class ExportController {

    public static Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    private OrderController orderController;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private ExportService exportService;

    @GetMapping("/export")
    public void exportAllOrders(String startDate, String endDate, String shopName, String statusListStr, @RequestParam(defaultValue="false") boolean onlySupper){
        DataResponse dataResponse = orderController.queryAllOrders(startDate, endDate, shopName, statusListStr, onlySupper);
        if(dataResponse.getCode() != 0){
            //查询订单失败
            logger.error("query orders error.");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            //return dataResponse;
        }
        //获取查询结果
        RsAllOrders rsAllOrders = (RsAllOrders)dataResponse.getData();
        try{
            boolean result = exportService.exportCurrentDayliOrders(rsAllOrders, response);
            if(!result) {
                logger.error("export orders error. Returned false");
                //return DataResponse.failure("导出订单数据失败");
            }
            //return DataResponse.success("导出订单数据成功");
        } catch (Exception e){
            logger.error("export orders error.", e);
            //return DataResponse.failure("导出订单数据失败");
        }
    }

    /**
     * 导出本日订单
     */
    @GetMapping("/export/today")
    public void exportToday(){
        this.exportAllOrders(Instant.now().toString(), Instant.now().toString(),
                "醉唐轩（盈创动力店）", null, false);
    }

}
