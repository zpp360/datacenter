package com.shuheng.datacenter.entity;

import java.io.Serializable;

public class Region implements Serializable{


    private static final long serialVersionUID = 1333326140749804358L;

    private String region_id;

    private String region_name;

    private String region_code;

    private String parent_id;

    private String region_level;

    private String region_order;

    private String region_en_name;

    private String region_shortname_en;

    private String del_flag;

    private String update_time;

    private String office_flag;

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getRegion_level() {
        return region_level;
    }

    public void setRegion_level(String region_level) {
        this.region_level = region_level;
    }

    public String getRegion_order() {
        return region_order;
    }

    public void setRegion_order(String region_order) {
        this.region_order = region_order;
    }

    public String getRegion_en_name() {
        return region_en_name;
    }

    public void setRegion_en_name(String region_en_name) {
        this.region_en_name = region_en_name;
    }

    public String getRegion_shortname_en() {
        return region_shortname_en;
    }

    public void setRegion_shortname_en(String region_shortname_en) {
        this.region_shortname_en = region_shortname_en;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getOffice_flag() {
        return office_flag;
    }

    public void setOffice_flag(String office_flag) {
        this.office_flag = office_flag;
    }
}
