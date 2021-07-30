package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Properties;



public interface PropRepository extends JpaRepository<Properties, Long> {
	
	
	// Change 2
	List<Properties> findByStateContaining(String state);
	
	List<Properties> findByGmailContaining(String gmail);
}

