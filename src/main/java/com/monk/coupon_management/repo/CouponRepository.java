package com.monk.coupon_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monk.coupon_management.entity.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

}
