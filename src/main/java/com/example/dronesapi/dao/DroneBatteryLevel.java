package com.example.dronesapi.dao;

import java.math.BigDecimal;

public interface DroneBatteryLevel {
    String getSerialNumber();

    BigDecimal getBattery();
}
