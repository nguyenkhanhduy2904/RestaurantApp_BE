package com.nguyenkhanhduy.restaurant_app.Utils;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    private String token;
    private Notification notification;
    private Map<String, String> data;

    public Message() {
    }

    public Message(String token, Notification notification, Map<String, String> data) {
        this.token = token;
        this.notification = notification;
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
