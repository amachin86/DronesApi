package com.example.dronesapi.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import com.example.dronesapi.dao.Drone;
import com.example.dronesapi.dao.LoadMedication;
import com.example.dronesapi.dao.MedicalDelivery;
import com.example.dronesapi.dao.Medication;
import com.example.dronesapi.dto.request.DroneDeliveryRequest;
import com.example.dronesapi.dto.request.DroneGetBatteryRequest;
import com.example.dronesapi.dto.request.DroneRegisterRequest;
import com.example.dronesapi.dto.request.LoadDroneRequest;
import com.example.dronesapi.dto.response.*;
import com.example.dronesapi.repository.DroneDeliveryRepository;
import com.example.dronesapi.repository.DroneRepository;
import com.example.dronesapi.repository.LoadDroneRepository;
import com.example.dronesapi.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DroneSeriviceImpl implements DroneService {

    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private LoadDroneRepository loadDroneRepository;
    @Autowired
    private DroneDeliveryRepository droneDeliveryRepository;

    @Override
    public RegisterDroneResponse register(DroneRegisterRequest droneRequest) {
        Drone newdrone = new Drone();
        newdrone.setSerialNumber(droneRequest.getSerialNumber());
        newdrone.setModel(droneRequest.getModel());
        newdrone.setWeightLimit(droneRequest.getWeightLimit());
        newdrone.setBattery(droneRequest.getBattery());
        newdrone.setState(droneRequest.getState());
        droneRepository.save(newdrone);


        RegisterDroneResponse droneResponse = new RegisterDroneResponse();
        droneResponse.setResult("success");
        droneResponse.setSerialNumber(newdrone.getSerialNumber());
        droneResponse.setMessage("New Drone created successfully");
        droneResponse.setTimestamp(java.time.LocalDateTime.now());

        return droneResponse;
    }

    @Override
    public AvailableDroneResponse getAvailabeDrones() {
        String state = "IDLE";
        List<Drone> drones = droneRepository.findAllByState(state);
        return new AvailableDroneResponse("status", java.time.LocalDateTime.now(), drones);
    }

    @Override
    public DroneBatteryDetailsResponse getBateryLevel(DroneGetBatteryRequest drequest) {

        Drone newdrone = new Drone();
        DecimalFormat decFormat = new DecimalFormat("#%");
        DroneBatteryDetailsResponse batteryDetailsResponse = new DroneBatteryDetailsResponse();
        newdrone.setSerialNumber(drequest.getSerialNumber());
        Drone droneBattery = droneRepository.findBySerialNumber(newdrone.getSerialNumber());
        if (droneBattery == null) {
            throw new RuntimeException("Drone battery level details not found");
        }

        batteryDetailsResponse.setStatus("success");
        batteryDetailsResponse.setSerialNumber(droneBattery.getSerialNumber());
        batteryDetailsResponse.setBattery(decFormat.format(droneBattery.getBattery()));
        batteryDetailsResponse.setTimestamp(java.time.LocalDateTime.now());

        return batteryDetailsResponse;
    }

    @Override
    public DroneMedicationLoadRsponse getLoadedMedicationForADrone(String serialno) {

        LoadMedication loadMedication = loadDroneRepository.findByDrone(serialno);
        if(loadMedication==null) {
            throw new RuntimeException("No load Medication details for the specified drone");
        }
        DroneMedicationLoadRsponse droneLoad = new DroneMedicationLoadRsponse();
        droneLoad.setResult("success");
        droneLoad.setSerialNumber(loadMedication.getDrone().getSerialNumber());
        droneLoad.setTimestamp(java.time.LocalDateTime.now());
        droneLoad.setMedication(loadMedication.getMedication());

        return droneLoad;
    }

    @Override
    public LoadDroneResponse loadDrone(LoadDroneRequest loadRequest) {
        //preload data
        Medication medication1 = new Medication("WE232344","Covax",100,"image1");
        Medication medication2 = new Medication("WE232345","Meloxicam",150,"image2");
        Medication medication3 = new Medication("WE232346","Metformin",200,"image14");
        Medication medication4 = new Medication("WE232347","Acetaminophen",300,"image1854");
        Medication medication5 = new Medication("WE232348","Amoxicillin",400,"image190");
        Medication medication6 = new Medication("WE232349","Ativan",260,"image144");
        Medication medication7 = new Medication("WE2323510","Atorvastatin",180,"image19848");
        Medication medication8 = new Medication("WE2323511","Azithromycin",400,"image1090");
        Medication medication9 = new Medication("WE2323512","Zyloprim",400,"image1o0999");
        Medication medication10 = new Medication("WE2323513","Diprolene ",400,"image19383");
        medicationRepository.save(medication1);
        medicationRepository.save(medication2);
        medicationRepository.save(medication3);
        medicationRepository.save(medication4);
        medicationRepository.save(medication5);
        medicationRepository.save(medication6);
        medicationRepository.save(medication7);
        medicationRepository.save(medication8);
        medicationRepository.save(medication9);
        medicationRepository.save(medication10);

        droneRepository.setUpdateState("LOADING", loadRequest.getSerialNumber());
        Drone drone = droneRepository.findBySerialNumber(loadRequest.getSerialNumber());
        Medication medication = medicationRepository.findByCode(loadRequest.getCode());
        LoadMedication checkLoad = loadDroneRepository.findByCode(loadRequest.getCode());

        if(checkLoad != null) {
            throw new RuntimeException("Medication code has aready been loaded, try another one");

        }

        // validate before loading
        if (drone == null) {
            throw new RuntimeException("Drone specified does not exist");
        }

        if (medication == null) {
            throw new RuntimeException("Medication specified does not exist");
        }

        if (drone.getWeightLimit() < medication.getWeight()) {
            throw new RuntimeException("The Drone cannot load more than the weight limit");
        }
        // check battery
        if( drone.getBattery().compareTo(new BigDecimal(0.25)) < 0  ){
            throw new RuntimeException("The Drone cannot be loaded, battery below 25%");
        }
        // load
        LoadMedication loadMedication = new LoadMedication();
        loadMedication.setDrone(drone);
        loadMedication.setMedication(medication);
        loadMedication.setSource(loadRequest.getSource());
        loadMedication.setDestination(loadRequest.getDestination());
        loadMedication.setCreatedon(java.time.LocalDateTime.now());
        loadDroneRepository.save(loadMedication);
        droneRepository.setUpdateState("LOADED", loadRequest.getSerialNumber());

        LoadDroneResponse loadDroneResponse = new LoadDroneResponse();
        loadDroneResponse.setResult("success");
        loadDroneResponse.setSerialNumber(loadRequest.getSerialNumber());
        loadDroneResponse.setMessage("Drone Loaded successfully");
        loadDroneResponse.setTimestamp(java.time.LocalDateTime.now());

        return loadDroneResponse;
    }

    @Override
    public DeliverDroneResponse deliverLoad(DroneDeliveryRequest loadRequest) {
        droneRepository.setUpdateState("DELIVERING", loadRequest.getSerialNumber());
        LoadMedication loadMedication = loadDroneRepository.findByDrone(loadRequest.getSerialNumber());

        if(loadMedication==null) {
            throw new RuntimeException("Drone specifies is not loaded or does not exist");
        }

        MedicalDelivery newdelivery = new MedicalDelivery();
        newdelivery.setLoadMedication(loadMedication);
        newdelivery.setDeliveryTime(java.time.LocalDateTime.now());
        droneDeliveryRepository.save(newdelivery);
        droneRepository.setUpdateState("DELIVERED", loadRequest.getSerialNumber());

        DeliverDroneResponse deliverDroneResponse = new DeliverDroneResponse();
        deliverDroneResponse.setResult("success");
        deliverDroneResponse.setSerialNumber(loadRequest.getSerialNumber());
        deliverDroneResponse.setMessage("Delivered successfully");
        deliverDroneResponse.setTimestamp(java.time.LocalDateTime.now());

        return deliverDroneResponse;
    }
    @Override
    public void preLoadData() {
        Medication medication1 = new Medication("WE232344","Covax",100,"image1");
        Medication medication2 = new Medication("WE232345","Meloxicam",150,"image2");
        Medication medication3 = new Medication("WE232346","Metformin",200,"image14");
        Medication medication4 = new Medication("WE232347","Acetaminophen",300,"image1854");
        Medication medication5 = new Medication("WE232348","Amoxicillin",400,"image190");
        Medication medication6 = new Medication("WE232349","Ativan",260,"image144");
        Medication medication7 = new Medication("WE2323510","Atorvastatin",180,"image19848");
        Medication medication8 = new Medication("WE2323511","Azithromycin",400,"image1090");
        Medication medication9 = new Medication("WE2323512","Zyloprim",400,"image1o0999");
        Medication medication10 = new Medication("WE2323513","Diprolene ",400,"image19383");
        medicationRepository.save(medication1);
        medicationRepository.save(medication2);
        medicationRepository.save(medication3);
        medicationRepository.save(medication4);
        medicationRepository.save(medication5);
        medicationRepository.save(medication6);
        medicationRepository.save(medication7);
        medicationRepository.save(medication8);
        medicationRepository.save(medication9);
        medicationRepository.save(medication10);

    }
}
