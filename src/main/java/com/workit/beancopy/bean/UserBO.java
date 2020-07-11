package com.workit.beancopy.bean;

public class UserBO {
   private  String userName;

   private Integer age;

   private String  idCard;

   private String email;

   private String sex;

   private UserDetailBO userDetail;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public UserDetailBO getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetailBO userDetail) {
        this.userDetail = userDetail;
    }
}
