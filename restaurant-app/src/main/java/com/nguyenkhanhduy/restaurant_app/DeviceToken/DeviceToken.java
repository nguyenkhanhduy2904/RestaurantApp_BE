package com.nguyenkhanhduy.restaurant_app.DeviceToken;

import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfile;
import jakarta.persistence.*;

@Entity
@Table(name = "device_token")
public class DeviceToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;
    @Column(name = "fcm_token")
    private String fcmToken;


    public DeviceToken() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
