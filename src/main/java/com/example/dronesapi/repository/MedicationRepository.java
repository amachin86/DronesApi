package com.example.dronesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dronesapi.dao.Medication;


public interface MedicationRepository extends JpaRepository<Medication, String> {

    @Query(value = "SELECT * from medication e where e.code =:id ", nativeQuery = true) // using @query with
    Medication findByCode(@Param("id") String id);

}
