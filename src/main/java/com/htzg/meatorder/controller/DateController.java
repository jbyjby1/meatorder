package com.htzg.meatorder.controller;

import com.htzg.meatorder.service.tools.DateService;
import com.htzg.meatorder.util.DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/holiday")
public class DateController {

    public static Logger logger = LoggerFactory.getLogger(DateController.class);

    @Autowired
    private DateService dateService;

    @GetMapping("/{date}")
    public DataResponse isDateHoliday(@PathVariable String date){
        boolean isHoliday = dateService.isHoliday(date);
        return DataResponse.success(isHoliday);
    }

}
