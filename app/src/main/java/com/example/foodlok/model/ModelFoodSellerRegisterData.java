package com.example.foodlok.model;

public class ModelFoodSellerRegisterData {
    String OwnerName;
    String Address;
    String Email;
    String City;
    String State;
    String PostalCode;
    String Phone;
    String TelephoneNo;
    String LicenceNo;
    String Username;
    String Password;
    String Category;


    public ModelFoodSellerRegisterData(String ownerName, String address, String email, String city, String state, String postalCode, String phone, String telephoneNo, String licenceNo, String username, String password, String category) {
        this.OwnerName = ownerName;
        this.Address = address;
        this.Email = email;
        this.City = city;
        this.State = state;
        this.PostalCode = postalCode;
        this.Phone = phone;
        this.TelephoneNo = telephoneNo;
        this.LicenceNo = licenceNo;
        this.Username = username;
        this.Password = password;
        this.Category = category;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTelephoneNo() {
        return TelephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        TelephoneNo = telephoneNo;
    }

    public String getLicenceNo() {
        return LicenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        LicenceNo = licenceNo;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }
}
