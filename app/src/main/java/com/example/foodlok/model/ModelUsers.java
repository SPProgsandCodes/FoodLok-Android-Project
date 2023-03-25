package com.example.foodlok.model;

public class ModelUsers {
    public static String category;
    String profileName;
    String profession;
    String password;
    String email;
    String dob;
    String phone;
    String ownerName;
    String address;
    String city;
    String state;
    String licenceNo;
    String postalCode;
    String telephoneNo;
    String username;
    String bio;
    private String coverPhoto;
    private String profilePhoto;

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    // Default Constructor
    public ModelUsers() {
    }

    // For Creator user
    public ModelUsers(String profileName, String password, String email, String dob, String phone, String profession, String category, String bio) {
        this.profileName = profileName;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.profession = profession;
        this.bio = bio;
        this.category = category;
    }

    // For FoodSeller User
    public ModelUsers(String email, String phone, String ownerName, String address, String city, String state, String licenceNo, String postalCode, String telephoneNo, String username, String category) {
        this.email = email;
        this.phone = phone;
        this.ownerName = ownerName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.licenceNo = licenceNo;
        this.postalCode = postalCode;
        this.telephoneNo = telephoneNo;
        this.username = username;
        this.category = category;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
