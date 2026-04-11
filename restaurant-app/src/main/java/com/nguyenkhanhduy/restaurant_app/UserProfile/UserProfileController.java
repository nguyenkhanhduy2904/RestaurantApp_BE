package com.nguyenkhanhduy.restaurant_app.UserProfile;


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
    public ResponseEntity<UserProfile> getUserById(@PathVariable Integer id){

        UserProfile userProfile = userProfileService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(userProfile);
    }



}
