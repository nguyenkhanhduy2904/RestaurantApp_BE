package com.nguyenkhanhduy.restaurant_app.Auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfile;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfileRepository;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfileService;
import com.nguyenkhanhduy.restaurant_app.Utils.GoogleTokenVerifier;
import com.nguyenkhanhduy.restaurant_app.Utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileService userProfileService;

    @Autowired
    public AuthService(AuthRepository authRepository, UserProfileRepository userProfileRepository, UserProfileService userProfileService) {
        this.authRepository = authRepository;
        this.userProfileRepository = userProfileRepository;
        this.userProfileService = userProfileService;
    }


    public UserProfile authenticateLocalUser(LocalAuth data) {
        UserSignin userSignin = authRepository.findByUserName(data.getUsername()).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        Integer id = userSignin.getUserProfile().getUserId();

        String storedPassword = userSignin.getPasswordHashed();

        boolean ok = PasswordUtil.matches(data.getPassword(), storedPassword);

        if(ok){
            UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(()->new RuntimeException("Cant find user profile"));
            return userProfile;
        }
        else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    public UserProfile authenticateGoogleUser(GoogleAuth data) {

        GoogleIdToken.Payload payload = GoogleTokenVerifier.verify(data.getIdToken());

        String sub = payload.getSubject();
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        // fallback if name is null
        if (name == null || name.isEmpty()) {
            name = email.split("@")[0];
        }

        UserSignin userSignin = authRepository.findByProviderId(sub).orElse(null);

        if(userSignin!=null){
            return userProfileRepository.findById(userSignin.getUserProfile().getUserId()).orElseThrow(()-> new RuntimeException("profile not found"));
        }
        UserProfile savedProfile = userProfileService.createUserForGoogle(email, name);


        UserSignin signin = new UserSignin();
        signin.setAuthType("GOOGLE");
        signin.setProviderId(sub);
        signin.setUserProfile(savedProfile);

        authRepository.save(signin);

        //user name = null & password = null


        return savedProfile;

    }

    public UserProfile signUpAsLocal(LocalAuth data) {
        UserSignin userSignin = authRepository.findByUserName(data.getUsername()).orElse(null);
        if(userSignin!=null){
            throw new RuntimeException("Username already in used");
        }
        else{
//            UserProfile n = new UserProfile();
//            n.setUserName(data.getUsername());
//            n.setUserRole("CUSTOMER");
//
//            UserProfile saved = userProfileRepository.save(n);

            UserProfile saved = userProfileService.createUserForLocal(data);

            UserSignin signin = new UserSignin();
            signin.setAuthType("LOCAL");
            signin.setUserName(data.getUsername());
            signin.setPasswordHashed(PasswordUtil.hash(data.getPassword()));

            signin.setUserProfile(saved);

            authRepository.save(signin);
            return saved;

        }
    }
}
