package com.monk.coupon_management.service;

import org.springframework.stereotype.Service;

import com.monk.coupon_management.entity.Cart;
import com.monk.coupon_management.repo.CartRepository;
import com.monk.coupon_management.repo.CouponRepository;
import com.monk.coupon_management.entity.Coupon;


@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CouponRepository couponRepository;

    public CartService(CartRepository cartRepository, CouponRepository couponRepository) {
        this.cartRepository = cartRepository;
        this.couponRepository = couponRepository;
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart createCart(Cart cart) {
        cart.setTotalPrice(cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum());
        return cartRepository.save(cart);
    }

    public Cart applyCoupon(Long cartId, Long couponId) {
        Cart cart = getCartById(cartId);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        double discount = calculateDiscount(cart, coupon);
        cart.setTotalPrice(cart.getTotalPrice() - discount);
        return cartRepository.save(cart);
    }

    private double calculateDiscount(Cart cart, Coupon coupon) {
        // Basic implementation for Cart-wise and Product-wise coupons
        String type = coupon.getType();
        if ("cart-wise".equals(type)) {
            double threshold = Double.parseDouble(String.valueOf(coupon.getDetails().getOrDefault("threshold", "0")));
            double discount = Double.parseDouble(String.valueOf(coupon.getDetails().getOrDefault("discount", "0")));
            return cart.getTotalPrice() > threshold ? cart.getTotalPrice() * (discount / 100) : 0;
        } else if ("product-wise".equals(type)) {
            long productId = Long.parseLong(String.valueOf(coupon.getDetails().getOrDefault("product_id", "0")));
            double discount = Double.parseDouble(String.valueOf(coupon.getDetails().getOrDefault("discount", "0")));

            return cart.getItems().stream()
                    .filter(item -> item.getProductId() == productId)
                    .mapToDouble(item -> item.getPrice() * item.getQuantity() * (discount / 100))
                    .sum();
        }
        // Additional logic for BxGy here
        return 0;
    }
}
