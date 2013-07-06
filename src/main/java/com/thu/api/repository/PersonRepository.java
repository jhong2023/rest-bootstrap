package com.thu.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thu.api.domain.Person;

public interface PersonRepository 
	extends JpaRepository<Person, Long>{
	
	List<Person> findByName(String name);
	
	List<Person> findByAddressCity(String city);
}
