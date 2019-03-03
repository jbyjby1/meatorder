package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.DailyChicken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ChickenServiceImplTest {

    @Autowired
    ChickenService chickenService;

    @BeforeEach
    public void beforeEach(){

    }

    @Test
    public void queryDailyChicken() {
        List<DailyChicken> dailyChickens = chickenService.queryDailyChicken();
        assertEquals(2, dailyChickens.size());
    }

    @Test
    public void bingo(){
        List<String> names = new ArrayList<String>(){
            {
                add("tom");
                add("jack");
                add("lucy");
            }
        };

        String name = chickenService.bingo(names, 0, 0);
        assertEquals("tom", name);
        name = chickenService.bingo(names, 1, 0);
        assertEquals("jack", name);
        name = chickenService.bingo(names, 2, 0);
        assertEquals("lucy", name);

        name = chickenService.bingo(names, 0, 0);
        assertEquals("tom", name);
        name = chickenService.bingo(names, 0, 1);
        assertEquals("jack", name);
        name = chickenService.bingo(names, 0, 2);
        assertEquals("lucy", name);

        name = chickenService.bingo(names, 0, 119);
        assertEquals("lucy", name);
        name = chickenService.bingo(names, 1, 119);
        assertEquals("tom", name);
        name = chickenService.bingo(names, 2, 119);
        assertEquals("jack", name);

        names.add("Zeus");
        name = chickenService.bingo(names, 0, 270);
        assertEquals("lucy", name);
    }
}