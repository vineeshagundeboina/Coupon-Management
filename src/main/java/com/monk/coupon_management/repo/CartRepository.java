package com.monk.coupon_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monk.coupon_management.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{

}
