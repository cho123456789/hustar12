package com.example.myapplication;

import java.io.Serializable;

public class Kid implements Serializable {
    public int kid_sn;
    public String user_id;
    public String kid_nm;
    public String kid_gend;
    public String kid_bir;
    public String kid_guard_nm;
    public String kid_guard_tel1;
    public String kid_guard_tel2;

    public String kid_img_url;


    public Kid() { }

    public int getKid_sn() {
        return kid_sn;
    }

    public void setKid_sn(int kid_sn) {
        this.kid_sn = kid_sn;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getKid_nm() {
        return kid_nm;
    }

    public void setKid_nm(String kid_nm) {
        this.kid_nm = kid_nm;
    }

    public String getKid_gend() {
        return kid_gend;
    }

    public void setKid_gend(String kid_gend) {
        this.kid_gend = kid_gend;
    }

    public String getKid_bir() {
        return kid_bir;
    }

    public void setKid_bir(String kid_bir) {
        this.kid_bir = kid_bir;
    }

    public String getKid_guard_nm() {
        return kid_guard_nm;
    }

    public void setKid_guard_nm(String kid_guard_nm) {
        this.kid_guard_nm = kid_guard_nm;
    }

    public String getKid_guard_tel1() {
        return kid_guard_tel1;
    }

    public void setKid_guard_tel1(String kid_guard_tel1) {
        this.kid_guard_tel1 = kid_guard_tel1;
    }

    public String getKid_guard_tel2() {
        return kid_guard_tel2;
    }

    public void setKid_guard_tel2(String kid_guard_tel2) {
        this.kid_guard_tel2 = kid_guard_tel2;
    }



    public String getKid_img_url() { return kid_img_url;}

    public void setKid_img_url(String kid_img_url) {this.kid_img_url = kid_img_url;}
}