package com.nguyenkhanhduy.restaurant_app.Test;

import com.nguyenkhanhduy.restaurant_app.Utils.FcmService;
import com.nguyenkhanhduy.restaurant_app.Utils.GoogleTokenVerifier;
import com.nguyenkhanhduy.restaurant_app.Utils.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class TestController {

    private final FcmService fcmService;

    @Autowired
    public TestController(FcmService fcmService) {
        this.fcmService = fcmService;
    }


    @GetMapping("api/v1/test")
    public String HelloWorld(){
        return "Hello World";
    }

    @GetMapping("api/v1/test-fcm-bg/{token}")
    public String testFcmBG(@PathVariable String token){
        fcmService.sendMessage(
                token,
                new Notification("Test Title", "Hello from backend"),
                Map.of("type", "TEST")
        );
        return "Sent";
    }
        @GetMapping("api/v1/test-fcm-fg/{token}")
        public String testFcmFG(@PathVariable String token){
            fcmService.sendMessage(
                    token,
    //                new Notification("Test Title", "Hello from backend"),
                    null,
                    Map.of(
                            "title", "Test Title",
                            "body", "Hello from backend",
                            "type", "TEST"
                    )
            );
            return "Sent";
        }
}
