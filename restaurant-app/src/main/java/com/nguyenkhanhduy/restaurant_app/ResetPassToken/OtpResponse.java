package com.nguyenkhanhduy.restaurant_app.ResetPassToken;

public class OtpResponse {
    private String message;
    private Integer userId;

    public OtpResponse(String message, Integer userId) {
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
