package com.htzg.meatorder.service;

import com.htzg.meatorder.dao.DailyChickenMapper;
import com.htzg.meatorder.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChickenServiceImpl implements ChickenService {

    @Autowired
    DailyChickenMapper dailyChickenMapper;

    @Autowired
    OrderService orderService;

    /**
     * 查询今天的吃鸡选手
     * @return
     */
    @Override
    public List<DailyChicken> queryDailyChicken() {
        DailyChickenExample dailyChickenExample = new DailyChickenExample();
        DailyChickenExample.Criteria criteria = dailyChickenExample.createCriteria();
        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = start.plus(Duration.ofDays(1)).truncatedTo(ChronoUnit.DAYS);
        criteria.andCreateTimeBetween(start, end);
        List<DailyChicken> dailyChickens =  dailyChickenMapper.selectByExample(dailyChickenExample);
        if(CollectionUtils.isEmpty(dailyChickens)){
            //如果今天尚未生成吃鸡选手，则生成一下
            dailyChickens = this.generateDailyChickenNumber();
        }
        //查询今天所有的订餐人员作为奖池
        List<String> persons = orderService.queryDailyOrderPersons();
        if(CollectionUtils.isEmpty(persons)){
            //今天尚未有人订单，直接返回
            return dailyChickens;
        }
        //根据顺序获取到吃鸡选手，填入用户名
        int firstNumber = 0;
        for (DailyChicken currentChicken : dailyChickens){
            int currentNumber = currentChicken.getChickenNumber();
            String luckyPerson = bingo(persons, firstNumber, currentNumber);
            firstNumber = persons.indexOf(luckyPerson);
            if(firstNumber == persons.size() - 1){
                firstNumber = 0;
            }
            currentChicken.setChickenName(luckyPerson);
            if(persons.size() > 1){
                persons.remove(luckyPerson);
            }
        }
        return dailyChickens;
    }

    /**
     * 抽奖
     * @param allPersons 所有人名字的集合
     * @param firstNumber 开始抽奖的起点，从1开始
     * @param luckyNumber 幸运数字，从1开始
     * @return
     */
    @Override
    public String bingo(List<String> allPersons, int firstNumber ,int luckyNumber){
        int size = allPersons.size();
        int offset = luckyNumber % size;
        firstNumber += offset;
        if(firstNumber >= size){
            firstNumber = firstNumber % size;
        }
        return allPersons.get(firstNumber);
    }

//    public boolean getDailyChickenNumber() {
//        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
//        LocalDateTime end = start.plus(Duration.ofDays(1)).truncatedTo(ChronoUnit.DAYS);
//        RsAllOrders rsAllOrders = orderService.queryAllOrders(start, end);
//        Set<String> persons = rsAllOrders.getPersonOrders().stream().map(personOrder ->
//                personOrder.getUsername()).collect(Collectors.toSet());
//        if(CollectionUtils.isEmpty(persons)){
//            return false;
//        }
//        Object[] obj =persons.toArray();
//        String chicken = (String)obj[(int)(Math.random()*obj.length)];
//        DailyChicken dailyChicken = new DailyChicken();
//        dailyChicken.setChickenName(chicken);
//        dailyChicken.setCreateTime(LocalDateTime.now());
//        int result = dailyChickenMapper.insert(dailyChicken);
//        return result == 1;
//    }

    /**
     * 生成今天的吃鸡选手
     * @return
     */
    @Transactional
    @Override
    public List<DailyChicken> generateDailyChickenNumber() {
        LocalDateTime now = LocalDateTime.now();
        List<DailyChicken> dailyChickens = new ArrayList<>();
        for (ChickenType chickenType : ChickenType.values()){
            int chickenNumber = (int)(Math.random() * 500);
            DailyChicken dailyChicken = new DailyChicken();
            dailyChicken.setChickenNumber(chickenNumber);
            dailyChicken.setChickenType(chickenType);
            dailyChicken.setCreateTime(now);
            int result = dailyChickenMapper.insert(dailyChicken);
            dailyChickens.add(dailyChicken);
        }
        return dailyChickens;
    }
}
