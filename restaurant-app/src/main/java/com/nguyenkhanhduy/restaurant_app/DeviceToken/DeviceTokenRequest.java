package com.nguyenkhanhduy.restaurant_app.DeviceToken;

public class DeviceTokenRequest {
    private Integer userId;
    private String fcmToken;

    public DeviceTokenRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
