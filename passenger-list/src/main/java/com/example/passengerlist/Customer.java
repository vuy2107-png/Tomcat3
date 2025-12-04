package com.example.passengerlist;

public class Customer {
    private String name;
    private String birthday;
    private String address;
    private String image;

    public Customer(String name, String birthday, String address, String image) {
        this.setName(name);
        this.setBirthday(birthday);
        this.setAddress(address);
        this.setImage(image);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}