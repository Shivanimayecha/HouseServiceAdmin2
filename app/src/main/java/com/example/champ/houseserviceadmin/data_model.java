package com.example.champ.houseserviceadmin;

/**
 * Created by Champ on 12-08-2018.
 */

public class data_model {
    String city;
    String id;
    String area;
    String category;
    String desc;
    String firstname;
    String lastname;

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
