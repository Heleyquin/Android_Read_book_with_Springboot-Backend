package com.example.docsach.Model.DTO;

import com.example.docsach.Model.CT_Goi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class GoiDangKyResponse implements Serializable {
    private String maGoi;
    private String chuThich;
    private BigDecimal giaTien;
    private int thoiHan;
    private Set<CT_Goi> ctGoiSet;
    private boolean active;

    public GoiDangKyResponse() {
    }

    public GoiDangKyResponse(String maGoi, String chuThich, BigDecimal giaTien, int thoiHan, Set<CT_Goi> ctGoiSet, boolean active) {
        this.maGoi = maGoi;
        this.chuThich = chuThich;
        this.giaTien = giaTien;
        this.thoiHan = thoiHan;
        this.ctGoiSet = ctGoiSet;
        this.active = active;
    }

    public String getMaGoi() {
        return maGoi;
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

    public BigDecimal getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }

    public int getThoiHan() {
        return thoiHan;
    }

    public void setThoiHan(int thoiHan) {
        this.thoiHan = thoiHan;
    }

    public Set<CT_Goi> getCtGoiSet() {
        return ctGoiSet;
    }

    public void setCtGoiSet(Set<CT_Goi> ctGoiSet) {
        this.ctGoiSet = ctGoiSet;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
