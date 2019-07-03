package com.shuheng.datacenter.entity;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Unit implements Serializable{


    private static final long serialVersionUID = -4369175685768200762L;

    private String unit_id;

    private String unit_p_id;

    private String unit_name_full;

    private String unit_name_abbreviation;

    private String unit_type;

    private String province;

    private String city;

    private String area;

    private String street;

    private String community;

    private String unit_add;

    private String unit_prop;

    private String del_flag;

    private String update_time;

    private String sortid;

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }


    public String getUnit_p_id() {
        return unit_p_id;
    }

    public void setUnit_p_id(String unit_p_id) {
        this.unit_p_id = unit_p_id;
    }

    public String getUnit_name_full() {
        return unit_name_full;
    }

    public void setUnit_name_full(String unit_name_full) {
        this.unit_name_full = unit_name_full;
    }

    public String getUnit_name_abbreviation() {
        if(StringUtils.isBlank(unit_name_abbreviation)){
            return unit_name_full;
        }
        return unit_name_abbreviation;
    }

    public void setUnit_name_abbreviation(String unit_name_abbreviation) {
        this.unit_name_abbreviation = unit_name_abbreviation;
    }

    public String getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(String unit_type) {
        this.unit_type = unit_type;
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

    public String getUnit_add() {
        return unit_add;
    }

    public void setUnit_add(String unit_add) {
        this.unit_add = unit_add;
    }

    public String getUnit_prop() {
        if("1".equals(this.unit_prop)){
            return "JG";
        }
        if("2".equals(this.unit_prop)){
            return "SYDW";
        }
        if("3".equals(this.unit_prop)){
            return "QY";
        }
        if("4".equals(this.unit_prop)){
            return "GX";
        }
        return "JG";
    }

    public void setUnit_prop(String unit_prop) {
        this.unit_prop = unit_prop;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }



    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getSortid() {
        return sortid;
    }

    public void setSortid(String sortid) {
        this.sortid = sortid;
    }
}
