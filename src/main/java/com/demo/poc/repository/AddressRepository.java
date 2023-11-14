package com.demo.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.poc.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
