package com.cts.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.dtos.BikeDto;
import com.cts.dtos.CustomerDto;
import com.cts.entities.Bike;
import com.cts.entities.Customer;
import com.cts.exceptions.BikesNotFoundException;
import com.cts.repositories.BikeRepository;

// Unit tests for BikeServiceImpl.

@SpringBootTest
class BikeServiceImplTest {
	
	@Mock
	BikeRepository bikeRepository;
	@InjectMocks
	BikeServiceImpl bikeServiceImpl;
	
	
	private Customer cus1;
    private Customer cus2;
    private Customer cus3;
    private List<Customer> customers;
    private Bike bike1;
    private Bike bike2;
    private Bike bike3;
    private List<Bike> bikes;
    private BikeDto bikeDto1;
    private BikeDto bikeDto2;
    private BikeDto bikeDto3;
    private CustomerDto customerDto1;
    private CustomerDto customerDto2;
    private CustomerDto customerDto3;
    
    // Initializes test data before each test case.
    
	@BeforeEach
    void init() {
        cus1 = new Customer(1, "Arun",  "9876543201", "Shanti building", "123","st.thomas street","SRS Pg", "Chennai","TamilNadu", "123456");
        cus2 = new Customer(2, "Shital", "9876543202", "Meera Building","124","Navalur street","Ice Cap", "Mangalore","Karnataka", "123457");
        cus3 = new Customer(3, "Ram",   "9876543203", "Sahyadri Building", "125","Adyar","HP Petrol Pump", "Kasargod", "Kerala","123458");
        customers = new ArrayList<>();
        customers.add(cus1);
        customers.add(cus2);
        customers.add(cus3);
        
        customerDto1 = new CustomerDto(1,"Arun",  "9876543201", "Shanti building", "123","st.thomas street","SRS Pg", "Chennai","TamilNadu", "123456");
        customerDto2 = new CustomerDto(2,"Shital", "9876543202", "Meera Building","124","Navalur street","Ice Cap", "Mangalore","Karnataka", "123457");
        customerDto3 = new CustomerDto(3,"Ram",   "9876543203", "Sahyadri Building", "125","Adyar","HP Petrol Pump", "Kasargod", "Kerala","123458");


        bike1 = new Bike(1, "Honda", "CBR150R", "TN01AA1234", "MH01BA1234567890", "Mirror issue", 120.0, LocalDateTime.now(), LocalDate.now().plusDays(7), LocalDateTime.now(), LocalDateTime.now(), cus1);
        bike2 = new Bike(2, "Yamaha", "R15", "TN02BB5678", "MH02BB9876543210", "Minor scratches", 1500.0, LocalDateTime.now(), LocalDate.now().plusDays(10), LocalDateTime.now(), LocalDateTime.now(), cus2);
        bike3 = new Bike(3, "Suzuki", "Gixxer", "KA03CC9012", "MH03CC2345678901", "Engine noise", 3000.0, LocalDateTime.now(), LocalDate.now().plusDays(14), LocalDateTime.now(), LocalDateTime.now(), cus3);
        bikes = new ArrayList<>();
        bikes.add(bike1);
        bikes.add(bike2);
        bikes.add(bike3);

        bikeDto1 = new BikeDto(1, "Honda", "CBR150R", "TN01AA1234", "MH01BA1234567890", "Mirror issues", 120.0, LocalDateTime.now(), LocalDate.now().plusDays(7), LocalDateTime.now(), LocalDateTime.now(), customerDto1);
        bikeDto2 = new BikeDto(2, "Yamaha", "R15", "TN02BB5678", "MH02BB9876543210", "Minor scratches", 1500.0, LocalDateTime.now(), LocalDate.now().plusDays(10), LocalDateTime.now(), LocalDateTime.now(), customerDto2);
        bikeDto3 = new BikeDto(3, "Suzuki", "Gixxer", "KA03CC9012", "MH03CC2345678901", "Engine noise", 3000.0, LocalDateTime.now(), LocalDate.now().plusDays(14), LocalDateTime.now(), LocalDateTime.now(), customerDto3);
    }

	// Tests fetching all bikes from the repository.
	
	@Test
	void testGetAll() {
		when(bikeRepository.findAll()).thenReturn(bikes);
		
		var result = bikeServiceImpl.getAll();
		
		assertEquals(3, result.size());
		assertEquals("Honda", result.get(0).getBikeMake());
		verify(bikeRepository,times(1)).findAll();
	}

	// Tests fetching a bike by ID.
	
	@Test
	void testGetById() {
		when(bikeRepository.findById(anyLong())).thenReturn(Optional.of(bike2));
		
		var result = bikeServiceImpl.getById(2);
		assertNotNull(result);
		assertEquals("Yamaha", result.getBikeMake());
		verify(bikeRepository,times(1)).findById(anyLong());
	}

	// Tests adding a new bike
	
	@Test
	void testAddBike() {
		when(bikeRepository.save(Mockito.any(Bike.class))).thenReturn(bike3);
		
		var result = bikeServiceImpl.addBike(bikeDto3);
		assertNotNull(result);
		
		assertEquals(bike3, result);
		assertEquals("Suzuki", result.getBikeMake());
		assertEquals(cus3, result.getCustomer());
		verify(bikeRepository,times(1)).save(Mockito.any(Bike.class));
	
	}

	// Tests updating an existing bike.
	
	@Test
	void testUpdateBike() {
		when(bikeRepository.existsById(anyLong())).thenReturn(true);
		when(bikeRepository.findById(anyLong())).thenReturn(Optional.of(bike2));
		 
		when(bikeRepository.save(Mockito.any(Bike.class))).thenReturn(bike3);
		
		var result = bikeServiceImpl.updateBike(2, bikeDto3);
		
		assertEquals(bike3,result);
		assertEquals("Suzuki", result.getBikeMake());
		verify(bikeRepository,times(1)).existsById(anyLong());
		verify(bikeRepository,times(1)).findById(anyLong());
		verify(bikeRepository,times(1)).save(Mockito.any(Bike.class));
		
	}
	
	// Tests updating a bike that does not exist.
	
	@Test
	void testUpdateWhenBikeNotFound() {
		when(bikeRepository.existsById(anyLong())).thenReturn(false);
		assertThrows(BikesNotFoundException.class, ()->bikeServiceImpl.updateBike(1, bikeDto1));
		
		verify(bikeRepository,times(1)).existsById(anyLong());
		verify(bikeRepository,never()).findById(anyLong());
		verify(bikeRepository,never()).save(Mockito.any(Bike.class));
	}

	// Tests deleting a bike by ID.
	
	@Test
	void testDeleteBike() {
		when(bikeRepository.existsById(anyLong())).thenReturn(true);
		bikeServiceImpl.deleteBike(2);
		
		verify(bikeRepository,times(1)).deleteById(anyLong());
	}
	
	// Tests deleting a bike that does not exist.

	@Test
	void testDeleteWhenBikeNotFound() {
		when(bikeRepository.existsById(anyLong())).thenReturn(false);
		assertThrows(BikesNotFoundException.class,()->bikeServiceImpl.deleteBike(2));
		verify(bikeRepository,times(1)).existsById(anyLong());
		verify(bikeRepository,never()).deleteById(anyLong());
	}

}
