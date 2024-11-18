package com.monk.coupon_management.test;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.monk.coupon_management.entity.Coupon;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.coupon_management.repo.CouponRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateCoupon() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();

    	String detailsJson = "{\"threshold\":100, \"discount\":10}";

    	// Convert JSON string to Map
    	Map<String, Object> detailsMap = objectMapper.readValue(detailsJson, Map.class);

    	// Set the Map to the details field
    	Coupon coupon = new Coupon();
    	coupon.setDetails(detailsMap);
    	
    	
       // Coupon coupon = new Coupon();
        coupon.setType("cart-wise");
       // coupon.setDetails("{\"threshold\":100, \"discount\":10}");
        coupon.setExpirationDate(LocalDate.of(2024, 12, 31));

        mockMvc.perform(post("/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coupon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.type").value("cart-wise"));
    }

    @Test
    public void shouldGetAllCoupons() throws Exception {
        mockMvc.perform(get("/coupons"))
                .andExpect(status().isOk());
    }
}

