package com.htzg.meatorder.service.tasks;

import com.htzg.meatorder.domain.DailyChicken;
import com.htzg.meatorder.domain.EventType;
import com.htzg.meatorder.domain.RsAllOrders;
import com.htzg.meatorder.domain.RsDailyOrder;
import com.htzg.meatorder.domain.WXWork.RobotSendMessage;
import com.htzg.meatorder.domain.WXWork.RobotSendResponse;
import com.htzg.meatorder.domain.WXWork.RobotText;
import com.htzg.meatorder.service.ChickenService;
import com.htzg.meatorder.service.EventService;
import com.htzg.meatorder.service.OrderService;
import com.htzg.meatorder.service.tools.DateService;
import com.htzg.meatorder.util.JsonUtils;
import com.htzg.meatorder.util.PinyinUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class LockTasks {

    public static Logger logger = LoggerFactory.getLogger(LockTasks.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private ChickenService chickenService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DateService dateService;

    @Value("${wxwork.message.robot.id}")
    private String robotId;

    @Value("${notification.on}")
    private boolean notificationOn;

    /**
     * 每天定时发送通知“还有5分钟锁定，请大家及时点餐”
     * cron表达式：Seconds  Minutes  Hours  DayofMonth  Month  DayofWeek  Year
     */
    @Scheduled(cron = "0 40 17 * * ?")
    public void lockTask(){
        if(!notificationOn){
            //通知服务未开启，直接返回
            return;
        }
        if(dateService.isTodayHoliday()){
            //节假日不通知，跳过
            logger.info("today is holiday. Skip send message.");
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_JSON);
        RobotSendMessage robotSendMessage = new RobotSendMessage();
        RobotText robotText = new RobotText();
        robotText.setContent("【点餐系统】系统将在17:45锁定，请及时点餐。");
        robotSendMessage.setMsgtype("text");
        robotSendMessage.setText(robotText);
        //将请求头部和参数合成一个请求
        HttpEntity<RobotSendMessage> requestEntity = new HttpEntity<>(robotSendMessage, headers);
        try{
            //执行HTTP请求，将返回的结构使用RobotSendResponse类格式化
            ResponseEntity<RobotSendResponse> response = restTemplate.exchange("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + robotId,
                    method, requestEntity, RobotSendResponse.class);
            if(response.getBody().getErrorCode() == 0){
                logger.info("send lock event message success.");
            }else{
                logger.error("Send lock message error. response code: {}, body: {}",
                        response.getStatusCode(), JsonUtils.toJson(response.getBody()));
            }
        } catch (Exception e){
            logger.error("Send message error.", e);
        }
    }

    /**
     * 每天定时锁定，并发送通知“点餐系统已锁定，吃鸡者是@某人”
     * cron表达式：Seconds  Minutes  Hours  DayofMonth  Month  DayofWeek  Year
     */
    @Scheduled(cron = "10 45 17 * * ?")
    //@Scheduled(cron = "0/5 * * * * ?")
    public void chickenTask(){
        if(!notificationOn){
            //通知服务未开启，直接返回
            return;
        }
        if(dateService.isTodayHoliday()){
            //节假日不通知，跳过
            logger.info("today is holiday. Skip send message.");
            return;
        }
        logger.info("start to send chicken event message...");
        List<DailyChicken> dailyChickens = null;
        try{
            RsAllOrders orders = orderService.queryAllOrders(LocalDateTime.now(), LocalDateTime.now(), null, null);
            //如果今日没人点餐，跳过
            if(CollectionUtils.isEmpty(orders.getPersonOrders())){
                logger.info("today no one have order. Skip send message.");
                return;
            }
            //如果没锁定，锁定一下
            eventService.addEvent(EventType.DAILY_LOCK_ORDERS);
            //获取今天吃鸡选手
            dailyChickens = chickenService.queryDailyChicken();
            //如果没人吃鸡，跳过
            if(CollectionUtils.isEmpty(dailyChickens)){
                logger.info("today no one bingo. Skip send message.");
                return;
            }
        } catch (Exception e){
            logger.error("Query daily order for send message error.", e);
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_JSON);
        RobotSendMessage robotSendMessage = new RobotSendMessage();
        RobotText robotText = new RobotText();
        String chickenStr = dailyChickens.stream().map(DailyChicken::getChickenName)
                .reduce((a, b) -> a + "、" + b).orElseGet(() -> "无人");
        robotText.setContent("【点餐系统】系统已锁定，今日吃鸡：" + chickenStr + "，请及时处理。");
        List<String> mentionedList = dailyChickens.stream().map(DailyChicken::getChickenName)
                .map(PinyinUtils::getPinYin)
                .collect(Collectors.toList());
        robotText.setMentionedList(mentionedList);
        robotSendMessage.setMsgtype("text");
        robotSendMessage.setText(robotText);

        //将请求头部和参数合成一个请求
        HttpEntity<RobotSendMessage> requestEntity = new HttpEntity<>(robotSendMessage, headers);
        try{
            //执行HTTP请求，将返回的结构使用RobotSendResponse类格式化
            ResponseEntity<RobotSendResponse> response = restTemplate.exchange("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + robotId,
                    method, requestEntity, RobotSendResponse.class);
            if(response.getBody().getErrorCode() == 0){
                logger.info("send lock event message success.");
            }else{
                logger.error("Send lock message error. response code: {}, body: {}",
                        response.getStatusCode(), JsonUtils.toJson(response.getBody()));
            }
        } catch (Exception e){
            logger.error("Send message error.", e);
        }

    }
}
