package com.nguyenkhanhduy.restaurant_app.DeviceToken;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/token")
public class DeviceTokenController {

    private final DeviceTokenService deviceTokenService;


    public DeviceTokenController(DeviceTokenService deviceTokenService) {
        this.deviceTokenService = deviceTokenService;
    }



    @PostMapping
    public ResponseEntity<Void> registerToken(@RequestBody DeviceTokenRequest deviceTokenRequest){

        deviceTokenService.saveToken(deviceTokenRequest);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteToken(@RequestParam String token){
        deviceTokenService.deleteToken(token);
        return ResponseEntity.noContent().build();
    }
}
