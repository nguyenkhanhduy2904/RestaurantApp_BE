package com.nguyenkhanhduy.restaurant_app.Order;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository< Order, Integer> {
    List<Order> findByUserProfile_UserId(Integer userId);
}
