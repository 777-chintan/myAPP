package com.example.myapp;

public class UserProfile {
    public String userAge;
    public String userPhoneNumber;
    public String userName;
    public String userType;
    public String userId;

    public UserProfile(String name,String age,String phone,String usertype,String id){
        this.userAge=age;
        this.userName=name;
        this.userPhoneNumber=phone;
        this.userId=id;
        this.userType=usertype;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserAge() {
        return userAge;
    }
    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }
    public void setUserPhoneNumber(String userPhoneNumber) { this.userPhoneNumber = userPhoneNumber; }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
}
