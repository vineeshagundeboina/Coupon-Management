package com.monk.coupon_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.monk.coupon_management.entity.Coupon;
import com.monk.coupon_management.repo.CouponRepository;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(Long id, Coupon updatedCoupon) {
        Coupon coupon = getCouponById(id);
        coupon.setType(updatedCoupon.getType());
        coupon.setDetails(updatedCoupon.getDetails());
        coupon.setExpirationDate(updatedCoupon.getExpirationDate());
        return couponRepository.save(coupon);
    }

    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }
}
