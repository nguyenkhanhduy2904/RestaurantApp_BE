package com.nguyenkhanhduy.restaurant_app.ResetPassToken;

import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfile;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfileRepository;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfileService;
import com.nguyenkhanhduy.restaurant_app.Utils.EmailService;
import com.nguyenkhanhduy.restaurant_app.Utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ResetPasswordService {

    @Autowired
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final UserProfileRepository userProfileRepository;
    private final EmailService emailService;

    public ResetPasswordService(ResetPasswordTokenRepository resetPasswordTokenRepository, UserProfileService userProfileService, UserProfileRepository userProfileRepository, EmailService emailService) {
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
        this.userProfileRepository = userProfileRepository;
        this.emailService = emailService;
    }


    public void sendOTP(String email) {

        UserProfile existedProfile = userProfileRepository.findByUserEmail(email).orElse(null);

        if (existedProfile == null) {
            return; // silent fail (good for security)
        }
        // create a random 8 char string(no special letter)
        StringUtil stringUtil = new StringUtil();

        String token = stringUtil.generateOTP();
        ResetPasswordToken newToken = new ResetPasswordToken(token, existedProfile);
        resetPasswordTokenRepository.save(newToken);
        //send to that email
        emailService.sendOTPEmail(email, token);

    }


    public OtpResponse verifyOtp(String otp) {

        ResetPasswordToken exsitedToken = resetPasswordTokenRepository.findByOtp(otp).orElse(null);

        if(exsitedToken==null || exsitedToken.isUsed() || exsitedToken.getExpiredAt().isBefore(LocalDateTime.now())){
            return new OtpResponse("Invalid OTP", null);
        }
        else{
            exsitedToken.setUsed(true);
            resetPasswordTokenRepository.save(exsitedToken);
            return new OtpResponse("Valid OTP", exsitedToken.getUserProfile().getUserId());
        }
    }
}
