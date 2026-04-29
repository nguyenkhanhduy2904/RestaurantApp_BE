package com.nguyenkhanhduy.restaurant_app.DeviceToken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Integer> {
    Optional<DeviceToken> findByFcmToken(String fcmToken);

    void deleteByFcmToken(String fcmToken);

    List<DeviceToken> findByUserProfile_UserId(Integer userId);
}
