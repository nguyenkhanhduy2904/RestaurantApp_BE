package com.nguyenkhanhduy.restaurant_app.Auth;


import com.nguyenkhanhduy.restaurant_app.Response.ApiResponse;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/local")
    public ResponseEntity<ApiResponse<UserProfile>> signInAsLocal(@RequestBody LocalAuth data){
        UserProfile userProfile = authService.authenticateLocalUser(data);
        return ResponseEntity.ok(ApiResponse.success(userProfile));
    }

    @PostMapping("/local/signup")
    public ResponseEntity<ApiResponse<UserProfile>> signUpAsLocal(@RequestBody LocalAuth data){
        UserProfile userProfile = authService.signUpAsLocal(data);
        return ResponseEntity.ok(ApiResponse.success(userProfile));
    }



    @PostMapping("/google")
    public ResponseEntity<ApiResponse<UserProfile>> signInAsGoogle(@RequestBody GoogleAuth data){
        UserProfile userProfile = authService.authenticateGoogleUser(data);
        return ResponseEntity.ok(ApiResponse.success(userProfile));
    }


}
