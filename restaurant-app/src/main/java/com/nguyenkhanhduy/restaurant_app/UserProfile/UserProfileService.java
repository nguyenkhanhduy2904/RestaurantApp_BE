package com.nguyenkhanhduy.restaurant_app.UserProfile;


import com.nguyenkhanhduy.restaurant_app.Auth.GoogleAuth;
import com.nguyenkhanhduy.restaurant_app.Auth.LocalAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;


    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile getUserById(Integer id) {

        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));

        return userProfile;

    }

    public UserProfile createUserForLocal(LocalAuth data){
        UserProfile n = new UserProfile();
        n.setUserName(data.getUsername());
        n.setUserRole("CUSTOMER");
        n.setStatus("ACTIVE");

        UserProfile saved = userProfileRepository.save(n);
        return saved;
    }
    public UserProfile createAdminLocal(LocalAuth data){
        UserProfile n = new UserProfile();
        n.setUserName(data.getUsername());
        n.setUserRole("ADMIN");
        n.setStatus("ACTIVE");

        UserProfile saved = userProfileRepository.save(n);
        return saved;
    }

    public UserProfile createUserForGoogle(String email, String name){

        UserProfile profile = new UserProfile();
        profile.setUserEmail(email);
        profile.setUserName(name);
        profile.setStatus("ACTIVE");
        UserProfile savedProfile = userProfileRepository.save(profile);
        return savedProfile;
    }

    public List<UserProfile> getAllUserProfile() {
        List<UserProfile> ls = userProfileRepository.findAll();
        return  ls;
    }

    public UserProfile updateUserProfile(Integer id, UserProfile userProfile) {

        UserProfile existedProfile = userProfileRepository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));

        existedProfile.setUserName(userProfile.getUserName());
        existedProfile.setUserEmail(userProfile.getUserEmail());
        existedProfile.setUserAddress(userProfile.getUserAddress());
        existedProfile.setUserEmail(userProfile.getUserEmail());
        existedProfile.setUserPhone(userProfile.getUserPhone());
        existedProfile.setUserRole(userProfile.getUserRole());
        existedProfile.setStatus(userProfile.getStatus());

        return userProfileRepository.save(existedProfile);


    }

}
