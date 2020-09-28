package com.shuheng.datacenter.entity;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -6017726233553964820L;

    private String user_id;

    private String user_name;

    private String gender;

    private String unit_id;

    private String mobilephone;

    private String user_type;

    private String card_number;

    private String birthday;

    private String isdy;

    private String rd_time;

    private String nation;

    private String position;

    private String department;

    private String education;

    private String province;

    private String city;

    private String area;

    private String street;

    private String community;

    private String address;

    private String home_telphone;

    private String del_flag;

    private String report_flag;//是否发起居住地报到确认（不发起：0，发起确认：1）

    private String phone_type;

    private String update_time;

    private String person_group; //0工作人员 1部门领导 2分管领导3主要领导

    private String sortno;

    private String status;//居住地报道信息是否确认（0:未确认，1：已确认） 当report_flag和status的值都为1的时候表示该老干部已在居住地报到，并且信息已经经过确认

    private String interest;//兴趣爱好

    public String getPerson_group() {
        //在职用户 分组
        if("01".equals(this.user_type)){
            //01分管领导
            return "2";
        }
        if("02".equals(this.user_type)){
            //02部门领导
            return "1";
        }
        if("03".equals(this.user_type)){
            //03具体工作者
            return "0";
        }
        if("04".equals(this.user_type)){
            // 04主要领导
            return "3";
        }
        return "0";
    }

    public void setPerson_group(String person_group) {
        this.person_group = person_group;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    /**
     * 数据中心返回值201为离休202为退休
     * @return
     */
    public String getUser_type() {
        if("201".equals(this.user_type)){
            return "0";
        }
        if("202".equals(this.user_type)){
            return "1";
        }
        if("01".equals(this.user_type) || "02".equals(this.user_type) || "03".equals(this.user_type) || "04".equals(this.user_type)){
            return "2";
        }
        return "1";
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIsdy() {
        return isdy;
    }

    public void setIsdy(String isdy) {
        this.isdy = isdy;
    }

    public String getRd_time() {
        return rd_time;
    }

    public void setRd_time(String rd_time) {
        this.rd_time = rd_time;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHome_telphone() {
        //在职用户返回办公电话，离退休不保存
        if("01".equals(this.user_type) || "02".equals(this.user_type) || "03".equals(this.user_type) || "04".equals(this.user_type)){
            return home_telphone;
        }
        return null;
    }

    public void setHome_telphone(String home_telphone) {
        this.home_telphone = home_telphone;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getReport_flag() {
        return report_flag;
    }

    public void setReport_flag(String report_flag) {
        this.report_flag = report_flag;
    }

    public String getPhone_type() {
        return phone_type;
    }

    public void setPhone_type(String phone_type) {
        this.phone_type = phone_type;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSortno() {
        return sortno;
    }

    public void setSortno(String sortno) {
        this.sortno = sortno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

}
