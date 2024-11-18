package com.monk.coupon_management.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;
    
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "coupon_details", joinColumns = @JoinColumn(name = "coupon_id"))
    @MapKeyColumn(name = "key")
    @Column(name = "value")

    private Map<String, Object> details;  // Ensure this is a Map, not String

    private LocalDate expirationDate;

    // Getters and setters omitted for brevity
}