package com.demo.poc.service.impl;

import com.demo.poc.entity.Address;
import com.demo.poc.repository.AddressRepository;
import com.demo.poc.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Override
    public Address addAddress(Address address) {
        Address addressSavedToDB = addressRepository.save(address);
        return addressSavedToDB;
    }
}
