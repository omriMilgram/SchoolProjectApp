package com.example.myapplication;

public class UserDetails {

    // Variables representing the user's details
    private String userName;  // User's name
    private String userPwd;   // User's password
    private String userEmail; // User's email address
    private String userPhone; // User's phone number

    // Constructor that initializes all user details
    public UserDetails(String userName, String userPwd, String userEmail, String userPhone) {
        this.userName = userName;   // Initializing the user's name
        this.userPwd = userPwd;     // Initializing the user's password
        this.userEmail = userEmail; // Initializing the user's email
        this.userPhone = userPhone; // Initializing the user's phone number
    }

    // Constructor that initializes only the password and email (e.g., for login purposes)
    public UserDetails(String userPwd, String userEmail) {
        this.userPwd = userPwd;     // Initializing the user's password
        this.userEmail = userEmail; // Initializing the user's email
    }

    // Getter for the user's name
    public String getUserName() {
        return userName;
    }

    // Setter for the user's name
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter for the user's password
    public String getUserPwd() {
        return userPwd;
    }

    // Setter for the user's password
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    // Getter for the user's email
    public String getUserEmail() {
        return userEmail;
    }

    // Setter for the user's email
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // Getter for the user's phone number
    public String getUserPhone() {
        return userPhone;
    }

    // Setter for the user's phone number
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
