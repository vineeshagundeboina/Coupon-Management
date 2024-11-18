package com.monk.coupon_management.test;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.coupon_management.entity.Cart;
import com.monk.coupon_management.entity.CartItem;


@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateCart() throws Exception {
        Cart cart = new Cart();
        cart.setItems(Arrays.asList(
            new CartItem(1L, 2, 100.0), // Product 1, Quantity 2, Price 100 each
            new CartItem(2L, 1, 50.0)   // Product 2, Quantity 1, Price 50 each
        ));

        mockMvc.perform(post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(250.0));
    }
}
