package com.example.docsach.Model;

import com.example.docsach.Model.Key.Key_CT_Goi;

import java.io.Serializable;

public class CT_Goi implements Serializable {
    private Key_CT_Goi id;
    private Sach sach;
    private GoiDangKy goiDangKy;
    private String thoiGian;

    public CT_Goi() {
    }

    public CT_Goi(Key_CT_Goi id, Sach sach, GoiDangKy goiDangKy, String thoiGian) {
        this.id = id;
        this.sach = sach;
        this.goiDangKy = goiDangKy;
        this.thoiGian = thoiGian;
    }

    public Key_CT_Goi getId() {
        return id;
    }

    public void setId(Key_CT_Goi id) {
        this.id = id;
    }

    public Sach getSach() {
        return sach;
    }

    public void setSach(Sach sach) {
        this.sach = sach;
    }

    public GoiDangKy getGoiDangKy() {
        return goiDangKy;
    }

    public void setGoiDangKy(GoiDangKy goiDangKy) {
        this.goiDangKy = goiDangKy;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public static final class CT_GoiBuilder {
        private CT_Goi cT_Goi;

        public CT_GoiBuilder() {
            cT_Goi = new CT_Goi();
        }

        public CT_GoiBuilder(CT_Goi other) {
            this.cT_Goi = other;
        }

        public static CT_GoiBuilder aCT_Goi() {
            return new CT_GoiBuilder();
        }

        public CT_GoiBuilder withId(Key_CT_Goi id) {
            cT_Goi.setId(id);
            return this;
        }

        public CT_GoiBuilder withSach(Sach sach) {
            cT_Goi.setSach(sach);
            return this;
        }

        public CT_GoiBuilder withGoiDangKy(GoiDangKy goiDangKy) {
            cT_Goi.setGoiDangKy(goiDangKy);
            return this;
        }

        public CT_GoiBuilder withThoiGian(String thoiGian) {
            cT_Goi.setThoiGian(thoiGian);
            return this;
        }

        public CT_Goi build() {
            return cT_Goi;
        }
    }
}
