package com.htzg.meatorder.service.tools;

import java.time.LocalDateTime;
import java.util.List;

public interface DateService {

    /**
     * 判断今天是不是节假日，如果接口异常则返回不是节假日
     * @return 今天是不是节假日
     */
    public boolean isTodayHoliday();

    /**
     * 判断当天是不是节假日，如果接口异常则返回不是节假日
     * @return 当天是不是节假日
     */
    public boolean isHoliday(String date);

}
