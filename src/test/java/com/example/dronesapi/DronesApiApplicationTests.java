package com.example.dronesapi;

import com.example.dronesapi.controller.DroneController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)
@SpringBootTest
class DroneApplicationTest {

    @Autowired
    DroneController droneMainController;

    @Test
    void contextLoads() {
        Assertions.assertThat(droneMainController).isNot(null);

    }
}
