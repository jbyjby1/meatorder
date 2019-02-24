package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.Event;
import com.htzg.meatorder.domain.EventType;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    /**
     * 当天订餐有没有被锁定
     * @param localDateTime
     * @return
     */
    public boolean isDailyOrderLocked(LocalDateTime localDateTime);

    /**
     * 查询某一天的某种事件
     * @param localDateTime
     * @return
     */
    public List<Event> queryEvents(EventType eventType, LocalDateTime localDateTime);

    public boolean addEvent(Event event);

    public boolean deleteEvent(String eventName, EventType eventType);

    public boolean deleteEventTypeByDate(EventType eventType, LocalDateTime localDateTime);

    public Event updateEvent(Event event);

}
