package com.nguyenkhanhduy.restaurant_app.UserProfile;


import com.nguyenkhanhduy.restaurant_app.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserProfile>>> getAllUserProfile(){

        List<UserProfile> ls = userProfileService.getAllUserProfile();
        return ResponseEntity.ok(ApiResponse.success(ls));

    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfile>> updateUserProfile(@PathVariable Integer id,@RequestBody UserProfile userProfile){

        UserProfile pro5 = userProfileService.updateUserProfile(id, userProfile);

        return ResponseEntity.ok(ApiResponse.success(pro5));

    }



}
