package com.workit.beancopy.bean;

import java.util.List;

public class UserDetailBO {

    private String address;

    private List<String> educationList;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<String> educationList) {
        this.educationList = educationList;
    }
}
