package com.cts.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.dtos.BikeDto;
import com.cts.dtos.CustomerDto;
import com.cts.entities.Bike;
import com.cts.entities.Customer;
import com.cts.service.BikeService;
import com.fasterxml.jackson.databind.ObjectMapper;

// Unit tests for the BikeController class.

@SpringBootTest
@AutoConfigureMockMvc
class BikeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private BikeService bikeService;

	@Autowired
	private ObjectMapper objectMapper;

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
        cus1 = new Customer(1L, "Arun",  "9876543201", "Shanti building", "123","st.thomas street","SRS Pg", "Chennai","TamilNadu", "123456");
        cus2 = new Customer(2L, "Shital", "9876543202", "Meera Building","124","Navalur street","Ice Cap", "Mangalore","Karnataka", "123457");
        cus3 = new Customer(3L, "Ram",   "9876543203", "Sahyadri Building", "125","Adyar","HP Petrol Pump", "Kasargod", "Kerala","123458");
        customers = new ArrayList<>();
        customers.add(cus1);
        customers.add(cus2);
        customers.add(cus3);
        
        customerDto1 = new CustomerDto(1L,"Arun",  "9876543201", "Shanti building", "123","st.thomas street","SRS Pg", "Chennai","TamilNadu", "123456");
        customerDto2 = new CustomerDto(2L,"Shital", "9876543202", "Meera Building","124","Navalur street","Ice Cap", "Mangalore","Karnataka", "123457");
        customerDto3 = new CustomerDto(3L,"Ram",   "9876543203", "Sahyadri Building", "125","Adyar","HP Petrol Pump", "Kasargod", "Kerala","123458");


        bike1 = new Bike(1L, "Honda", "CBR150R", "TN01AA1234", "MH01BA12345678901", "Mirror issue", 120.0, LocalDateTime.now(), LocalDate.now().plusDays(7), LocalDateTime.now(), LocalDateTime.now(), cus1);
        bike2 = new Bike(2L, "Yamaha", "R15", "TN02BB5678", "MH02BB98765432101", "Minor scratches", 1500.0, LocalDateTime.now(), LocalDate.now().plusDays(10), LocalDateTime.now(), LocalDateTime.now(), cus2);
        bike3 = new Bike(3L, "Suzuki", "Gixxer", "KA03CC9012", "MH03CC21345678901", "Engine noise", 3000.0, LocalDateTime.now(), LocalDate.now().plusDays(14), LocalDateTime.now(), LocalDateTime.now(), cus3);
        bikes = new ArrayList<>();
        bikes.add(bike1);
        bikes.add(bike2);
        bikes.add(bike3);

        bikeDto1 = new BikeDto(1L, "Honda", "CBR150R", "TN01AA1234", "MH01BA12345678901", "Mirror issues", 120.0, LocalDateTime.now(), LocalDate.now().plusDays(7), LocalDateTime.now(), LocalDateTime.now(), customerDto1);
        bikeDto2 = new BikeDto(2L, "Yamaha", "R15", "TN02BB5678", "MH02BB98765432101", "Minor scratches", 1500.0, LocalDateTime.now(), LocalDate.now().plusDays(10), LocalDateTime.now(), LocalDateTime.now(), customerDto2);
        bikeDto3 = new BikeDto(3L, "Suzuki", "Gixxer", "KA03CC9012", "MH03CC21345678901", "Engine noise", 3000.0, LocalDateTime.now(), LocalDate.now().plusDays(14), LocalDateTime.now(), LocalDateTime.now(), customerDto3);
    }


// Tests retrieving all bikes
	@Test
	@DisplayName("Get All Bikes")
	@WithMockUser
	void testGetAllBikes() throws Exception {
		when(bikeService.getAll()).thenReturn(List.of(bikeDto1,bikeDto2));
		mockMvc.perform(get("/api/bikes"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(2))
			.andExpect(jsonPath("$[0].bikeMake").value("Honda"));
	
	}
	

// Tests retrieving a bike by its ID
	@Test
	@DisplayName("Get Bike By ID")
	@WithMockUser
	void testGetBikeById_Success() throws Exception {
	    when(bikeService.getById(anyLong())).thenReturn(bikeDto1);

	    mockMvc.perform(get("/api/bikes/{id}",1L))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.bikeId",is(1))) 
	            .andExpect(jsonPath("$.bikeMake", is("Honda")))
	            .andExpect(jsonPath("$.modelName", is("CBR150R")));		
	}

	
// Tests saving a new bike
	@Test
	@DisplayName("Save Bike")
	@WithMockUser(roles = "ADMIN")
	void testSaveBike() throws Exception {
		when(bikeService.addBike(any(BikeDto.class))).thenReturn(bike2);

		var json = objectMapper.writeValueAsString(bike2);

		mockMvc.perform(post("/api/bikes/save").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.bikeMake").value("Yamaha"));
	}

//// Tests updating an existing bike 
	@Test
	@DisplayName("Update Bike")
	@WithMockUser(roles = "ADMIN")
	void testUpdateBike() throws Exception {
		when(bikeService.updateBike(anyLong(), any(BikeDto.class))).thenReturn(bike1);

		var json = objectMapper.writeValueAsString(bike1);

		mockMvc.perform(put("/api/bikes/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.bikeMake").value("Honda"));
	}

// Tests deleting a bike by its ID 
	@Test
	@DisplayName("Delete Bike")
	@WithMockUser(roles = "ADMIN")
	void testDeleteBike() throws Exception {
		doNothing().when(bikeService).deleteBike(1L);

		mockMvc.perform(delete("/api/bikes/1")).andExpect(status().isAccepted());
	}


}
