package com.htzg.meatorder.service.tools;

import java.time.LocalDate;

/**
 * @Author Administrator
 * @date 2020/9/1 0001 14:40
 */
public interface InternalHolidayService {

    public boolean isTodayHoliday();

    public boolean isHoliday(LocalDate localDate);

}
