package com.nguyenkhanhduy.restaurant_app.Utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordUtil {
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    // hash password
    public static String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // verify password
    public static boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
