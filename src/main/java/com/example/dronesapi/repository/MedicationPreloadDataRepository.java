package com.example.dronesapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dronesapi.dao.Medication;

public interface MedicationPreloadDataRepository extends JpaRepository<Medication, String> {
}
