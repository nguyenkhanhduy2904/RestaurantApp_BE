package com.nguyenkhanhduy.restaurant_app.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    List<UserProfile> findByUserRole(String userRole);
}
