package com.nguyenkhanhduy.restaurant_app.DeviceToken;

import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfile;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class DeviceTokenService {
    private final DeviceTokenRepository deviceTokenRepository;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public DeviceTokenService(DeviceTokenRepository deviceTokenRepository, UserProfileRepository userProfileRepository) {
        this.deviceTokenRepository = deviceTokenRepository;
        this.userProfileRepository = userProfileRepository;
    }


    public void saveToken(DeviceTokenRequest deviceTokenRequest) {
        DeviceToken token = deviceTokenRepository
                .findByFcmToken(deviceTokenRequest.getFcmToken())
                .orElse(null);

        UserProfile user = userProfileRepository.findById(deviceTokenRequest.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (token != null) {
            // token exists
            if (!token.getUserProfile().getUserId().equals(user.getUserId())) {
                token.setUserProfile(user); // reassign
                deviceTokenRepository.save(token);
            }
            // else: same user = do nothing
        } else {
            // new token
            DeviceToken newToken = new DeviceToken();
            newToken.setFcmToken(deviceTokenRequest.getFcmToken());
            newToken.setUserProfile(user);
            deviceTokenRepository.save(newToken);
        }
    }
    public List<String> getAllTokenForUser(Integer userId){
        List<DeviceToken> ls = deviceTokenRepository.findByUserProfile_UserId(userId);

        List<String> tokens = ls.stream()
                .map(DeviceToken::getFcmToken)
                .filter(Objects::nonNull)
                .toList();

        return tokens;
    }
}
