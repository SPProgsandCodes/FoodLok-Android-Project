package com.example.foodlok.model;

public class ModelEditProfileData {
    public String profilePhoto, profileName, profession;


    public ModelEditProfileData(String profilePhoto, String profileName, String profession) {
        this.profilePhoto = profilePhoto;
        this.profileName = profileName;
        this.profession = profession;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
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
}
