package com.example.champ.houseserviceadmin;

/**
 * Created by Champ on 12-08-2018.
 */

public class data_model {
    String city;
    String id;
    String w_id;

    public String getW_id() {
        return w_id;
    }

    public void setW_id(String w_id) {
        this.w_id = w_id;
    }

    String area;
    String category;
    String desc;
    String firstname;
    String lastname;
    String username;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String address;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getDesc() { return desc; }

    public void setDesc(String desc) { this.desc = desc; }

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category; }

    public String getArea() { return area; }

    public void setArea(String area) { this.area = area; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
