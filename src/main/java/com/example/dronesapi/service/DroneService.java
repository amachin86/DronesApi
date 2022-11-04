package com.example.dronesapi.service;

import com.example.dronesapi.dto.request.DroneDeliveryRequest;
import com.example.dronesapi.dto.request.DroneGetBatteryRequest;
import com.example.dronesapi.dto.request.DroneRegisterRequest;
import com.example.dronesapi.dto.request.LoadDroneRequest;
import com.example.dronesapi.dto.response.*;
import org.springframework.stereotype.Component;


@Component
public interface DroneService {

    RegisterDroneResponse register(DroneRegisterRequest drone);

    DroneBatteryDetailsResponse getBateryLevel(DroneGetBatteryRequest drequest) throws Exception;

    DroneMedicationLoadRsponse getLoadedMedicationForADrone(String serialno);

    AvailableDroneResponse getAvailabeDrones();

    LoadDroneResponse loadDrone(LoadDroneRequest loadRequest);

    DeliverDroneResponse deliverLoad(DroneDeliveryRequest loadRequest);

    void preLoadData();


}
