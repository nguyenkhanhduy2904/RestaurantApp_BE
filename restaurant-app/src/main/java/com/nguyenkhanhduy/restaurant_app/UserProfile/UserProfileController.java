package com.nguyenkhanhduy.restaurant_app.UserProfile;


import com.nguyenkhanhduy.restaurant_app.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;


    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfile>> getUserById(@PathVariable Integer id){

        UserProfile userProfile = userProfileService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(userProfile));
    }



}
