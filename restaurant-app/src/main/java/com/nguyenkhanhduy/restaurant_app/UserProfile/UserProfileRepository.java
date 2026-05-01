package com.nguyenkhanhduy.restaurant_app.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    List<UserProfile> findByUserRole(String userRole);
    Optional<UserProfile> findByUserEmail(String email);
}
