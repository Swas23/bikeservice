package com.cts.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dtos.BikeDto;
import com.cts.dtos.CustomerDto;
import com.cts.entities.Bike;
import com.cts.entities.Customer;
import com.cts.exceptions.BikesNotFoundException;
import com.cts.repositories.BikeRepository;

// Implementation of the BikeService interface.
// Handles business logic for bike operations like add, update, delete, and fetch.

@Service
public class BikeServiceImpl implements BikeService {

	@Autowired
	private BikeRepository bikeRepository;

	// Gets all bikes and converts them to BikeDto list.
	
	@Override
	public List<BikeDto> getAll() {
		List<Bike> bikes = bikeRepository.findAll();
		List<BikeDto> bikeDtos = new ArrayList<>();
		for (Bike bike : bikes) {
			CustomerDto customerDto;
            if (bike.getCustomer() != null) {
                customerDto = new CustomerDto(bike.getCustomer().getCustomerId(),
                		bike.getCustomer().getCustomerName(),
                        bike.getCustomer().getPhoneNumber(),
                        bike.getCustomer().getAddress(),
                        bike.getCustomer().getHouseNo(),
                        bike.getCustomer().getStreet(),
                        bike.getCustomer().getLandmark(),
                        bike.getCustomer().getCity(),
                        bike.getCustomer().getState(),
                        bike.getCustomer().getPincode());
            } else {
                customerDto = null;
            }
			BikeDto bikeDto = new BikeDto(
					bike.getBikeId(),
					bike.getBikeMake(),
					bike.getModelName(),
					bike.getBikeRegistrationNumber(),
					bike.getBikeChasisNumber(),
					bike.getKnownIssues(),
					bike.getCost(),
					bike.getGivenDate(),
					bike.getExpectedDeliveryDate(),
					bike.getCreatedDateTime(),
					bike.getUpdateDateTime(),
					customerDto);
			bikeDtos.add(bikeDto);
		}

		return bikeDtos;

	}

	// Gets a bike by its ID and converts it to BikeDto.
	
	@Override
	public BikeDto getById(long id) {
		Bike bike = bikeRepository.findById(id)
				.orElseThrow(() -> new BikesNotFoundException("Bike wtih id : " + id + " not found"));
		if (bike != null) {
			CustomerDto customerDto;
			if (bike.getCustomer() != null) {
                customerDto = new CustomerDto(id, bike.getCustomer().getCustomerName(),
                       bike.getCustomer().getPhoneNumber(),
                       bike.getCustomer().getAddress(),
                       bike.getCustomer().getHouseNo(),
                       bike.getCustomer().getStreet(),
                       bike.getCustomer().getLandmark(),
                       bike.getCustomer().getCity(),
                       bike.getCustomer().getState(),
                       bike.getCustomer().getPincode());
           } else {
               customerDto = null;
           }
			BikeDto bikeDto = new BikeDto(
					id,
					bike.getBikeMake(),
					bike.getModelName(),
					bike.getBikeRegistrationNumber(),
					bike.getBikeChasisNumber(),
					bike.getKnownIssues(),
					bike.getCost(),
					bike.getGivenDate(),
					bike.getExpectedDeliveryDate(),
					bike.getCreatedDateTime(),
					bike.getUpdateDateTime(),
					customerDto);
			return bikeDto;
		}
		return null;
	}
	
	// Adds a new bike using the given BikeDto.
    // Also creates and sets customer details.

	@Override
	public Bike addBike(BikeDto bikeDto) {
		Bike bike = new Bike();
		bike.setBikeMake(bikeDto.getBikeMake());
		bike.setModelName(bikeDto.getModelName());
		bike.setBikeRegistrationNumber(bikeDto.getBikeRegistrationNumber());
		bike.setBikeChasisNumber(bikeDto.getBikeChasisNumber());
		bike.setKnownIssues(bikeDto.getKnownIssues());
		bike.setCost(bikeDto.getCost());
		bike.setGivenDate(bikeDto.getGivenDate());
		bike.setExpectedDeliveryDate(bikeDto.getExpectedDeliveryDate());
		bike.setCreatedDateTime(LocalDateTime.now());
		bike.setUpdateDateTime(LocalDateTime.now());
		
            Customer customer = new Customer();
            customer.setCustomerName(bikeDto.getCustomerDto().getCustomerName());
            customer.setPhoneNumber(bikeDto.getCustomerDto().getPhoneNumber());
            customer.setAddress(bikeDto.getCustomerDto().getAddress());
            customer.setHouseNo(bikeDto.getCustomerDto().getHouseNo());
            customer.setStreet(bikeDto.getCustomerDto().getStreet());
            customer.setLandmark(bikeDto.getCustomerDto().getLandmark());
            customer.setCity(bikeDto.getCustomerDto().getCity());
            customer.setState(bikeDto.getCustomerDto().getState());
            customer.setPincode(bikeDto.getCustomerDto().getPincode());
            bike.setCustomer(customer);
        
		return bikeRepository.save(bike);
	}

	// Updates an existing bike by ID using the given BikeDto.
	// Updates customer details if provided.

	@Override
	public Bike updateBike(long id, BikeDto bikeDto) {
		
		
		if(!bikeRepository.existsById(id)) {
			throw new BikesNotFoundException("Bike with id : "+id+ "not found");
		}
		var bike = bikeRepository.findById(id).get();
		
		
		if(bikeDto.getBikeMake()!=null) {
			bike.setBikeMake(bikeDto.getBikeMake());
		}
		
		if(bikeDto.getModelName()!=null) {
			bike.setModelName(bikeDto.getModelName());
		}
		if(bikeDto.getBikeRegistrationNumber()!=null) {
			bike.setBikeRegistrationNumber(bikeDto.getBikeRegistrationNumber());
		}
		
		if(bikeDto.getBikeChasisNumber()!=null) {
			bike.setBikeChasisNumber(bikeDto.getBikeChasisNumber());
		}
		if(bikeDto.getKnownIssues()!=null) {
			bike.setKnownIssues(bikeDto.getKnownIssues());
		}
		if(bikeDto.getCost()!=0) {
			bike.setCost(bikeDto.getCost());
		}
		if(bikeDto.getGivenDate()!=null) {
			bike.setGivenDate(bikeDto.getGivenDate());
		}
		if(bikeDto.getExpectedDeliveryDate()!=null) {
			bike.setExpectedDeliveryDate(bikeDto.getExpectedDeliveryDate());
		}
//		if(bikeDto.getCreatedDateTime()!=null) {
//			bike.setCreatedDateTime(bikeDto.getCreatedDateTime());
//		}
			bike.setUpdateDateTime(LocalDateTime.now());
			
		if(bikeDto.getCustomerDto()!=null ) {
			var customer1 = bike.getCustomer();
			
			
		if(customer1.getCustomerId()==bikeDto.getCustomerDto().getCustomerId())
		{
			if(bikeDto.getCustomerDto().getCustomerName()!=null) {
				customer1.setCustomerName(bikeDto.getCustomerDto().getCustomerName());
			}
			if(bikeDto.getCustomerDto().getPhoneNumber()!=null) {
	            customer1.setPhoneNumber(bikeDto.getCustomerDto().getPhoneNumber());
			}
			if(bikeDto.getCustomerDto().getAddress()!=null) {
	            customer1.setAddress(bikeDto.getCustomerDto().getAddress());
			}
			if(bikeDto.getCustomerDto().getHouseNo()!=null) {
	            customer1.setHouseNo(bikeDto.getCustomerDto().getHouseNo());
			}
			if(bikeDto.getCustomerDto().getStreet()!=null) {
	            customer1.setStreet(bikeDto.getCustomerDto().getStreet());
			}
			if(bikeDto.getCustomerDto().getLandmark()!=null) {
	            customer1.setLandmark(bikeDto.getCustomerDto().getLandmark());
			}
			if(bikeDto.getCustomerDto().getCity()!=null) {
	            customer1.setCity(bikeDto.getCustomerDto().getCity());
			}
			if(bikeDto.getCustomerDto().getState()!=null) {
	            customer1.setState(bikeDto.getCustomerDto().getState());
			}
			if(bikeDto.getCustomerDto().getPincode()!=null) {
	            customer1.setPincode(bikeDto.getCustomerDto().getPincode());
			}
		}
		else {
			Customer customer = new Customer();
			if(bikeDto.getCustomerDto().getCustomerName()!=null) {
	            customer.setCustomerName(bikeDto.getCustomerDto().getCustomerName());
			}
			if(bikeDto.getCustomerDto().getPhoneNumber()!=null) {
	            customer.setPhoneNumber(bikeDto.getCustomerDto().getPhoneNumber());
			}
			if(bikeDto.getCustomerDto().getAddress()!=null) {
	            customer.setAddress(bikeDto.getCustomerDto().getAddress());
			}
			if(bikeDto.getCustomerDto().getHouseNo()!=null) {
	            customer.setHouseNo(bikeDto.getCustomerDto().getHouseNo());
			}
			if(bikeDto.getCustomerDto().getStreet()!=null) {
	            customer.setStreet(bikeDto.getCustomerDto().getStreet());
			}
			if(bikeDto.getCustomerDto().getLandmark()!=null) {
	            customer.setLandmark(bikeDto.getCustomerDto().getLandmark());
			}
			if(bikeDto.getCustomerDto().getCity()!=null) {
	            customer.setCity(bikeDto.getCustomerDto().getCity());
			}
			if(bikeDto.getCustomerDto().getState()!=null) {
	            customer.setState(bikeDto.getCustomerDto().getState());
			}
			if(bikeDto.getCustomerDto().getPincode()!=null) {
	            customer.setPincode(bikeDto.getCustomerDto().getPincode());
			}
	            bike.setCustomer(customer);
		}	
		}
		return bikeRepository.save(bike);
	}


	// Deletes a bike by its ID.
	
	@Override
	public void deleteBike(long id) {
		if(!bikeRepository.existsById(id)) {
			throw new BikesNotFoundException("Bike with id "+id+" not found");
		}
		bikeRepository.deleteById(id);
	}

}
