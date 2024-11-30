package com.example.docsach.Model;

import com.example.docsach.Model.Key.Key_CT_Goi_Dang_Ky;

import java.io.Serializable;
import java.math.BigDecimal;

public class CT_Dang_Ky implements Serializable {
    private Key_CT_Goi_Dang_Ky id;
    private Reader reader;
    private GoiDangKy goiDangKy;
    private BigDecimal giaTien;

    public CT_Dang_Ky() {
    }

    public CT_Dang_Ky(Key_CT_Goi_Dang_Ky id, Reader reader, GoiDangKy goiDangKy, BigDecimal giaTien) {
        this.id = id;
        this.reader = reader;
        this.goiDangKy = goiDangKy;
        this.giaTien = giaTien;
    }

    public Key_CT_Goi_Dang_Ky getId() {
        return id;
    }

    public void setId(Key_CT_Goi_Dang_Ky id) {
        this.id = id;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public GoiDangKy getGoiDangKy() {
        return goiDangKy;
    }

    public void setGoiDangKy(GoiDangKy goiDangKy) {
        this.goiDangKy = goiDangKy;
    }

    public BigDecimal getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }

    public static final class CT_Dang_KyBuilder {
        private CT_Dang_Ky cT_Dang_Ky;

        public CT_Dang_KyBuilder() {
            cT_Dang_Ky = new CT_Dang_Ky();
        }

        public CT_Dang_KyBuilder(CT_Dang_Ky other) {
            this.cT_Dang_Ky = other;
        }

        public static CT_Dang_KyBuilder aCT_Dang_Ky() {
            return new CT_Dang_KyBuilder();
        }

        public CT_Dang_KyBuilder withId(Key_CT_Goi_Dang_Ky id) {
            cT_Dang_Ky.setId(id);
            return this;
        }

        public CT_Dang_KyBuilder withReader(Reader reader) {
            cT_Dang_Ky.setReader(reader);
            return this;
        }

        public CT_Dang_KyBuilder withGoiDangKy(GoiDangKy goiDangKy) {
            cT_Dang_Ky.setGoiDangKy(goiDangKy);
            return this;
        }

        public CT_Dang_KyBuilder withGiaTien(BigDecimal giaTien) {
            cT_Dang_Ky.setGiaTien(giaTien);
            return this;
        }

        public CT_Dang_Ky build() {
            return cT_Dang_Ky;
        }
    }
}
