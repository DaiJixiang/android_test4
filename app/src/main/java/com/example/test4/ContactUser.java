package com.example.test4;

public class ContactUser {
    private Integer _id;
    private String name;
    private String tel;

    public ContactUser(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "ContactUser{" + "_id=" + _id + ", name='" + name + '\'' + ", tel='" + tel + '\'' + '}';
    }
}
