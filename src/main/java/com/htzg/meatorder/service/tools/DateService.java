package com.htzg.meatorder.service.tools;

public interface DateService {

    /**
     * 判断今天是不是节假日，如果接口异常则返回不是节假日
     * @return 今天是不是节假日
     */
    public boolean isTodayHoliday();
}
