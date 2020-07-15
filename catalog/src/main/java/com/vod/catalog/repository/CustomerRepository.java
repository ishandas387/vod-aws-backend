package com.vod.catalog.repository;

import java.util.List;

import com.natyaguru.catalog.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
List<Customer> findByFirstName(String FirstName);
List<Customer> findAll();
}