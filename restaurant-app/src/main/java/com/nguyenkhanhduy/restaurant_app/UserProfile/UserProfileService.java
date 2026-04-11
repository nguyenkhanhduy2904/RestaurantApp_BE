package com.nguyenkhanhduy.restaurant_app.UserProfile;


import com.nguyenkhanhduy.restaurant_app.Auth.GoogleAuth;
import com.nguyenkhanhduy.restaurant_app.Auth.LocalAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        UserProfile saved = userProfileRepository.save(n);
        return saved;
    }

    public UserProfile createUserForGoogle(String email){

        UserProfile profile = new UserProfile();
        profile.setUserEmail(email);
        UserProfile savedProfile = userProfileRepository.save(profile);
        return savedProfile;
    }
}
