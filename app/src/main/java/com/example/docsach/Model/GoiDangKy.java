package com.example.docsach.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class GoiDangKy implements Serializable {
    private String maGoi;
    private String chuThich;
    private BigDecimal giaTien;
    private int thoiHan;
    private Set<CT_Goi> ctGoiSet;
    private boolean active;

    public GoiDangKy() {
    }

    public GoiDangKy(String maGoi, String chuThich, BigDecimal giaTien, int thoiHan, Set<CT_Goi> ctGoiSet, boolean active) {
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

    public static final class GoiDangKyBuilder {
        private GoiDangKy goiDangKy;

        public GoiDangKyBuilder() {
            goiDangKy = new GoiDangKy();
        }

        public GoiDangKyBuilder(GoiDangKy other) {
            this.goiDangKy = other;
        }

        public static GoiDangKyBuilder aGoiDangKy() {
            return new GoiDangKyBuilder();
        }

        public GoiDangKyBuilder withMaGoi(String maGoi) {
            goiDangKy.setMaGoi(maGoi);
            return this;
        }

        public GoiDangKyBuilder withChuThich(String chuThich) {
            goiDangKy.setChuThich(chuThich);
            return this;
        }

        public GoiDangKyBuilder withGiaTien(BigDecimal giaTien) {
            goiDangKy.setGiaTien(giaTien);
            return this;
        }

        public GoiDangKyBuilder withThoiHan(int thoiHan) {
            goiDangKy.setThoiHan(thoiHan);
            return this;
        }

        public GoiDangKyBuilder withCtGoiSet(Set<CT_Goi> ctGoiSet) {
            goiDangKy.setCtGoiSet(ctGoiSet);
            return this;
        }

        public GoiDangKyBuilder withActive(boolean active) {
            goiDangKy.setActive(active);
            return this;
        }

        public GoiDangKy build() {
            return goiDangKy;
        }
    }
}
