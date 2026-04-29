package com.nguyenkhanhduy.restaurant_app.Utils;

public class FcmRequest {
    private Message message;

    public FcmRequest() {
    }

    public FcmRequest(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FcmRequest{" +
                "message=" + message +
                '}';
    }
}
