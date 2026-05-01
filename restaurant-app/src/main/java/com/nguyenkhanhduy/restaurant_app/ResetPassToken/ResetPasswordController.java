package com.nguyenkhanhduy.restaurant_app.ResetPassToken;


import com.nguyenkhanhduy.restaurant_app.Auth.AuthService;
import com.nguyenkhanhduy.restaurant_app.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/reset-password")
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;
    private final AuthService authService;

    public ResetPasswordController(ResetPasswordService resetPasswordService, AuthService authService) {
        this.resetPasswordService = resetPasswordService;
        this.authService = authService;
    }


    @PostMapping()
    public ResponseEntity<ApiResponse<String>> receiveEmail(@RequestParam String email){


        String result ="Request accepted";
        resetPasswordService.sendOTP(email);


        return ResponseEntity.ok(ApiResponse.success(result));
    }
    @PostMapping("verify-otp")
    public ResponseEntity<ApiResponse<OtpResponse>> receiveOTP(@RequestParam String otp){

        OtpResponse otpResponse = resetPasswordService.verifyOtp(otp);

        return ResponseEntity.ok(ApiResponse.success(otpResponse));
    }

    @PostMapping("reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        authService.resetPassword(request);

        return ResponseEntity.ok(ApiResponse.success("Request accepted"));
    }

}
