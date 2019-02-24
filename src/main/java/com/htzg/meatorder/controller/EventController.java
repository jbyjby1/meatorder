package com.htzg.meatorder.controller;

import com.htzg.meatorder.domain.Event;
import com.htzg.meatorder.domain.EventType;
import com.htzg.meatorder.domain.RsEvents;
import com.htzg.meatorder.service.EventService;
import com.htzg.meatorder.util.DataResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static com.htzg.meatorder.util.CommonConstant.shanghai;

/**
 * 事件controller，用于进行事件处理
 */
@RestController
@RequestMapping("/events")
public class EventController {

    public static Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    EventService eventService;

    @GetMapping("/{type}")
    public DataResponse queryEvent(@PathVariable String type, String date){
        try{
            EventType eventType = EventType.valueFrom(type);
            LocalDateTime day = LocalDateTime.now();
            if(StringUtils.isNotBlank(date)){
                Instant dateInstant = Instant.parse(date);
                day = LocalDateTime.ofInstant(dateInstant, shanghai);
            }
            List<Event> events = eventService.queryEvents(EventType.valueFrom(type), day);
            RsEvents rsEvents = new RsEvents();
            rsEvents.setEvents(events);
            return DataResponse.success(rsEvents);
        } catch (Exception e){
            logger.error("qeury event {} error.", type, e);
            return DataResponse.failure("查询事件失败");
        }
    }

    @PostMapping("/{type}")
    public DataResponse addEvent(@PathVariable String type){
        try{
            EventType eventType = EventType.valueFrom(type);
            LocalDateTime now = LocalDateTime.now();
            Event event = new Event();
            event.setEventName(type + now);
            event.setCreateTime(now);
            event.setTriggerTime(now);
            event.setEventType(eventType);
            boolean result = eventService.addEvent(event);
            if(result){
                return DataResponse.success(eventType.getDesc() + "成功.");
            }else{
                return DataResponse.failure(eventType.getDesc() + "失败.");
            }
        } catch (Exception e){
            logger.error("add event {} error.", type, e);
            return DataResponse.failure("增加事件失败。");
        }
    }

    @DeleteMapping("/{type}")
    public DataResponse deleteEvent(@PathVariable String type, String date){
        try{
            EventType eventType = EventType.valueFrom(type);
            LocalDateTime day = LocalDateTime.now();
            if(StringUtils.isNotBlank(date)){
                Instant dateInstant = Instant.parse(date);
                day = LocalDateTime.ofInstant(dateInstant, shanghai);
            }
            boolean result = eventService.deleteEventTypeByDate(eventType, day);
            if(result){
                return DataResponse.success("解除" + eventType.getDesc() + "成功。");
            }else{
                return DataResponse.failure("解除" + eventType.getDesc() + "失败。");
            }
        } catch (Exception e){
            logger.error("delete type {} error.", type, e);
            return DataResponse.failure("清除事件失败。");
        }
    }
}
