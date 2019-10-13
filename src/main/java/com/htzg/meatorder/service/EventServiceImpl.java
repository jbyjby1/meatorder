package com.htzg.meatorder.service;

import com.htzg.meatorder.dao.EventMapper;
import com.htzg.meatorder.domain.Event;
import com.htzg.meatorder.domain.EventExample;
import com.htzg.meatorder.domain.EventType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.htzg.meatorder.util.CommonConstant.shanghai;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventMapper eventMapper;


    @Override
    public boolean isDailyOrderLocked(LocalDateTime localDateTime) {
        LocalDateTime day = LocalDateTime.now();
        if(localDateTime != null){
            day = localDateTime;
        }
        List<Event> lockers = this.queryEvents(EventType.DAILY_LOCK_ORDERS, day);
        return !CollectionUtils.isEmpty(lockers);
    }

    @Override
    public List<Event> queryEvents(EventType eventType, LocalDateTime localDateTime) {
        LocalDateTime start = localDateTime.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = start.plus(Duration.ofDays(1)).truncatedTo(ChronoUnit.DAYS);
        EventExample eventExample = new EventExample();
        EventExample.Criteria criteria = eventExample.createCriteria();
        criteria.andEventTypeEqualTo(eventType);
        criteria.andCreateTimeBetween(start, end);
        List<Event> result = eventMapper.selectByExample(eventExample);
        return result;
    }

    @Override
    public boolean addEvent(EventType eventType) {
        LocalDateTime now = LocalDateTime.now();
        Event event = new Event();
        event.setEventName(eventType.getValue() + now);
        event.setCreateTime(now);
        event.setTriggerTime(now);
        event.setEventType(eventType);
        return addEvent(event);
    }

    @Override
    public boolean addEvent(Event event) {
        //如果今天已经锁定了，直接返回，否则增加锁定事件
        List<Event> events = this.queryEvents(EventType.DAILY_LOCK_ORDERS, LocalDateTime.now());
        if(!CollectionUtils.isEmpty(events)){
            return true;
        }
        int result = eventMapper.insert(event);
        return result == 1;
    }

    @Override
    public boolean deleteEvent(String eventName, EventType eventType) {
        EventExample eventExample = new EventExample();
        EventExample.Criteria criteria = eventExample.createCriteria();
        criteria.andEventNameEqualTo(eventName);
        criteria.andEventTypeEqualTo(eventType);
        int result = eventMapper.deleteByExample(eventExample);
        return true;
    }

    @Override
    public boolean deleteEventTypeByDate(EventType eventType, LocalDateTime localDateTime) {
        LocalDateTime start = localDateTime.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = start.plus(Duration.ofDays(1)).truncatedTo(ChronoUnit.DAYS);
        EventExample eventExample = new EventExample();
        EventExample.Criteria criteria = eventExample.createCriteria();
        criteria.andEventTypeEqualTo(eventType);
        criteria.andCreateTimeBetween(start, end);
        int result = eventMapper.deleteByExample(eventExample);
        return true;
    }

    @Override
    public Event updateEvent(Event event) {
        eventMapper.updateByPrimaryKey(event);
        return event;
    }
}
