package com.example.dronesapi.repository;

import com.example.dronesapi.dao.MedicalDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneDeliveryRepository extends JpaRepository<MedicalDelivery, String> {
}
