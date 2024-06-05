package com.example.DLverificationservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DLverificationservice.Logentities.ApiLog;

public interface ApiLogRepository extends JpaRepository<ApiLog, Long>{

	
}
