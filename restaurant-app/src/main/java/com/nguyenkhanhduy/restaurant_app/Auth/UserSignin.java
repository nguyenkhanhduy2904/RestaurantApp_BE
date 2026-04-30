package com.nguyenkhanhduy.restaurant_app.Auth;

import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfile;
import jakarta.persistence.*;

@Entity
@Table(name = "user_signin")
public class UserSignin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;   // internal PK


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile userProfile;
    @Column(name = "provider_id")
    private String providerId;
    @Column(name = "oath_type")
    private String authType;
    @Column(name = "username")
    private String userName;
    @Column(name = "password_hashed")
    private String passwordHashed;

    public UserSignin() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserSignin{" +
                "id=" + id +
                ", userProfile=" + userProfile +
                ", providerId='" + providerId + '\'' +
                ", authType='" + authType + '\'' +
                ", userName='" + userName + '\'' +
                ", passwordHashed='" + passwordHashed + '\'' +
                '}';
    }
}
