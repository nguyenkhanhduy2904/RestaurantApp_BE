package com.nguyenkhanhduy.restaurant_app.Auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<UserSignin, Integer > {

    Optional<UserSignin> findByUserName(String userName);

    Optional<UserSignin> findByProviderId(String providerId);
}
