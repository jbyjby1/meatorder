package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.DailyChicken;
import com.htzg.meatorder.domain.DailyOrder;
import com.htzg.meatorder.domain.PersonOrder;

import java.util.List;
import java.util.Optional;

public interface ChickenService {

    public List<DailyChicken> queryDailyChicken();

    public List<DailyChicken> generateDailyChickenNumber();

    public String bingo(List<String> allPersons, int firstNumber ,int luckyNumber);
}
