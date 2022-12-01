package com.stars;

import com.stars.common.AnalogData.Peach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class injectionTests {
    @Autowired // 写入
    private Peach peach;
    @Test
    void contextLoads() {
        System.out.println("测试桃子类：-------" + peach);
    }
}
