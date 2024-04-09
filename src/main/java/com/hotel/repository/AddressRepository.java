package com.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
