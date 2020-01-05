package com.htzg.meatorder.util;

import java.time.ZoneId;

public interface CommonConstant {

    public static final ZoneId shanghai = ZoneId.of("Asia/Shanghai");

    public static final String DEFAULT_RESTAURANT = "默认餐馆";

    /**
     * 每天晚餐开始时间，在这之后的都认为是晚餐，之前的是午餐
     */
    public static final int DAILY_SUPPER_TIME = 14;
}
