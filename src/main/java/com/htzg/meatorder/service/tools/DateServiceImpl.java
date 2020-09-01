package com.htzg.meatorder.service.tools;

import com.fasterxml.jackson.databind.JsonNode;
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

    @Autowired
    private InternalHolidayService internalHolidayService;

    @Override
    public boolean isTodayHoliday() {
        LocalDate localDate = LocalDate.now();
        String today = localDate.toString();
        return isHoliday(today);
    }

    @Override
    public boolean isHoliday(String date) {
        return isHolidayInternal(date);
    }

    public boolean isHoliday1(String dateStr) {
        try{
            ResponseEntity<HolidayResponse> holidayResponseEntity = restTemplate.getForEntity("http://api.goseek.cn/Tools/holiday?date=" + dateStr, HolidayResponse.class);
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

    public boolean isHoliday2(String dateStr) {
        try{
            ResponseEntity<String> holidayResponseEntity = restTemplate.getForEntity("http://www.easybots.cn/api/holiday.php?d=" + dateStr, String.class);
            if(holidayResponseEntity.getStatusCode() == HttpStatus.OK){
                JsonNode jsonNode = JsonUtils.mapper().readTree(holidayResponseEntity.getBody());
                String result = jsonNode.fields().next().getValue().asText();
                return !"0".equals(result);
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

    public boolean isHolidayInternal(String dateStr) {
        try{
            LocalDate localDate = LocalDate.parse(dateStr);
            return internalHolidayService.isHoliday(localDate);
        } catch (Exception e){
            logger.error("Get holiday error.", e);
            return false;
        }
    }


}
