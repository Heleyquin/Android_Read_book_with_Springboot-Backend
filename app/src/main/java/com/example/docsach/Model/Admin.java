package com.example.docsach.Model;

import java.io.Serializable;

public class Admin implements Serializable {
    private int id_admin;
    private String ten;
    private String cccd;
    private String gioiTinh;
    private int id_acc;

    public Admin() {
    }

    public Admin(int id_admin, String ten, String cccd, String gioiTinh, int id_acc) {
        this.id_admin = id_admin;
        this.ten = ten;
        this.cccd = cccd;
        this.gioiTinh = gioiTinh;
        this.id_acc = id_acc;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getId_acc() {
        return id_acc;
    }

    public void setId_acc(int id_acc) {
        this.id_acc = id_acc;
    }
}
