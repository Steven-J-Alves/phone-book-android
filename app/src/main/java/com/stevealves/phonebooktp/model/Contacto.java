package com.stevealves.phonebooktp.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Contacto implements Serializable {

    private String fullName;
    private String phoneNumber;
    private String email;
    private String birthdayDate;
    private Bitmap img;

    private Double latitude;
    private Double longitude;

    private Boolean isFavorite = false;

    public Contacto(String fullName, String phoneNumber, String email, String birthdayDate, Bitmap img, double latitude, double longitude) {

        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthdayDate = birthdayDate;
        this.img = img;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
