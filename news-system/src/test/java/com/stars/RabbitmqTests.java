package com.stars;

import com.stars.service.RabbitmqOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitmqTests {
    @Autowired
    private RabbitmqOrderService rabbitmqOrderService;
    @Test
    void contextLoads() {
        rabbitmqOrderService.makeOrder("1", "1", 12);
    }
}
