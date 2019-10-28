package com.htzg.meatorder.service.tools;

import com.htzg.meatorder.domain.holiday.HolidayResponse;
import com.htzg.meatorder.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class DateServiceImpl implements DateService {

    public static Logger logger = LoggerFactory.getLogger(DateServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean isTodayHoliday() {
        LocalDate localDate = LocalDate.now();
        String today = localDate.toString();
        try{
            ResponseEntity<HolidayResponse> holidayResponseEntity = restTemplate.getForEntity("http://api.goseek.cn/Tools/holiday?date=" + today, HolidayResponse.class);
            if(holidayResponseEntity.getStatusCode() == HttpStatus.OK){
                return holidayResponseEntity.getBody().getData() > 0;
            }else{
                logger.error("Get holiday response error. response code: {}, body: {}",
                        holidayResponseEntity.getStatusCode(), JsonUtils.toJson(holidayResponseEntity.getBody()));
                return false;
            }
        } catch (Exception e){
            logger.error("Get holiday error.", e);
            return false;
        }
    }


}
