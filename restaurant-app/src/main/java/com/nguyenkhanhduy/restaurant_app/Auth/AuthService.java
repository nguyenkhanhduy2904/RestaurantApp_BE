package com.nguyenkhanhduy.restaurant_app.Auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfile;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfileRepository;
import com.nguyenkhanhduy.restaurant_app.Utils.GoogleTokenVerifier;
import com.nguyenkhanhduy.restaurant_app.Utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public AuthService(AuthRepository authRepository, UserProfileRepository userProfileRepository) {
        this.authRepository = authRepository;
        this.userProfileRepository = userProfileRepository;
    }


    public UserProfile authenticateLocalUser(LocalAuth data) {
        UserSignin userSignin = authRepository.findByUserName(data.getUsername()).orElseThrow(()-> new RuntimeException("User not found"));

        Integer id = userSignin.getUserProfile().getUserId();

        String storedPassword = userSignin.getPasswordHashed();

        boolean ok = PasswordUtil.matches(data.getPassword(), storedPassword);

        if(ok){
            UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(()->new RuntimeException("Cant find user profile"));
            return userProfile;
        }
        else {
            throw new RuntimeException("Wrong password");
        }
    }

    public UserProfile authenticateGoogleUser(GoogleAuth data) {

        GoogleIdToken.Payload payload = GoogleTokenVerifier.verify(data.getIdToken());

        String sub = payload.getSubject();
        String email = payload.getEmail();

        UserSignin userSignin = authRepository.findByProviderId(sub).orElse(null);

        if(userSignin!=null){
            return userProfileRepository.findById(userSignin.getUserProfile().getUserId()).orElseThrow(()-> new RuntimeException("profile not found"));
        }

        //Create new user profile
        //get that id


        UserProfile profile = new UserProfile();
        profile.setUserEmail(email);
        UserProfile savedProfile = userProfileRepository.save(profile);


        UserSignin signin = new UserSignin();
        signin.setAuthType("GOOGLE");
        signin.setProviderId(sub);
        signin.setUserProfile(savedProfile);

        authRepository.save(signin);

        //user name = null & password = null


        return savedProfile;





    }
}
