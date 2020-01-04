package com.stevealves.phonebooktp.contacto;

import com.stevealves.phonebooktp.R;

import java.io.Serializable;

public class Contacto implements Serializable {

    private String fullName;
    private String phoneNumber;
    private String email;
    private String birthdayDate;
    private int id;
    private int img;

    public Contacto(
            String fullName,
            String phoneNumber, String email, String birthdayDate, int id, int img) {
        this.img = img;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthdayDate = birthdayDate;
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
