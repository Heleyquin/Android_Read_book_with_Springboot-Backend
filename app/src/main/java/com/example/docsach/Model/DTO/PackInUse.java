package com.example.docsach.Model.DTO;

import java.io.Serializable;

public class PackInUse implements Serializable {
    private String maGoi;
    private String chuThich;
    private String thoiGianDangKy;
    private int thoiHan;
    private String expirDate;
    private Boolean active;

    public PackInUse() {
    }

    public PackInUse(String maGoi, String chuThich, String thoiGianDangKy, int thoiHan, String expirDate, Boolean active) {
        this.maGoi = maGoi;
        this.chuThich = chuThich;
        this.thoiGianDangKy = thoiGianDangKy;
        this.thoiHan = thoiHan;
        this.expirDate = expirDate;
        this.active = active;
    }

    public String getMaGoi() {
        return maGoi;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setMaGoi(String maGoi) {
        this.maGoi = maGoi;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }

    public String getThoiGianDangKy() {
        return thoiGianDangKy;
    }

    public void setThoiGianDangKy(String thoiGianDangKy) {
        this.thoiGianDangKy = thoiGianDangKy;
    }

    public int getThoiHan() {
        return thoiHan;
    }

    public void setThoiHan(int thoiHan) {
        this.thoiHan = thoiHan;
    }

    public String getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(String expirDate) {
        this.expirDate = expirDate;
    }
}
