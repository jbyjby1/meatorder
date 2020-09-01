package com.htzg.meatorder.service.tools;

import com.fasterxml.jackson.core.type.TypeReference;
import com.htzg.meatorder.util.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Administrator
 * @date 2020/9/1 0001 14:41
 */
@Service
public class InternalHolidayServiceImpl implements InternalHolidayService{

    private static Logger logger = LoggerFactory.getLogger(InternalHolidayServiceImpl.class);

    private static int HOLIDAY = 2;
    private static int WEEKEND = 1;
    private static int WORKING_DAY = 0;

    private volatile Map<String, Map<String, Integer>> holidayData;

    @Override
    public boolean isTodayHoliday() {
        LocalDate today = LocalDate.now();
        return isHoliday(today);
    }

    @Override
    public boolean isHoliday(LocalDate localDate) {
        int holiday = isDateHoliday(localDate);
        if(holiday != -1){
            return holiday != 0;
        }
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if(DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek)){
            return true;
        }
        return false;
    }

    /**
     *
     * @param localDate
     * @return
     */
    private int isDateHoliday(LocalDate localDate){
        Map<String, Map<String, Integer>> holidays = this.getHolidayData();
        String yearStr = String.valueOf(localDate.getYear());
        if(!holidays.containsKey(yearStr)){
            logger.error("Error to judge date {}. holiday data not contain this year.");
            return 0;
        }
        Map<String, Integer> dayToValue = holidays.get(yearStr);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMdd");
        String monthAndDay = localDate.format(dateTimeFormatter);
        if(dayToValue.containsKey(monthAndDay)){
            return dayToValue.get(monthAndDay);
        }else{
            return -1;
        }
    }


    private Map<String, Map<String, Integer>> getHolidayData(){
        if(this.holidayData == null){
            synchronized (this){
                if(this.holidayData == null){
                    String fileName = "holidayData/holidayData.json";
                    try(InputStream holidayDataFileInputStream = new ClassPathResource(fileName).getInputStream()){
                        String holidayDataContent = IOUtils.toString(holidayDataFileInputStream, "UTF-8");
                        this.holidayData = JsonUtils.fromJson(holidayDataContent, new TypeReference<Map<String, Map<String, Integer>>>() {
                        });
                    } catch (IOException e){
                        logger.error("query holiday data error.", e);
                    }
                }
            }
        }
        return this.holidayData;
    }

}
