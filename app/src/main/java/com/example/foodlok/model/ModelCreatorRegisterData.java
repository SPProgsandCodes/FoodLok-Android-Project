package com.example.foodlok.model;

public class ModelCreatorRegisterData {
    String ProfileName, profession, password, email, dob, phone, category;

    public ModelCreatorRegisterData(String profileName, String profession, String password, String email, String dob, String phone, String category) {
        this.ProfileName = profileName;
        this.profession = profession;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.category = category;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public void setProfileName(String channelName) {
        ProfileName = channelName;
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

    public void setCategory(String category){this.category = category;}

    public String getCategory(){return category;}
}
