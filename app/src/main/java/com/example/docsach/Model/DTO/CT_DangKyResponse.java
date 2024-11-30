package com.example.docsach.Model.DTO;

import com.example.docsach.Model.GoiDangKy;
import com.example.docsach.Model.Key.Key_CT_Goi_Dang_Ky;
import com.example.docsach.Model.Reader;

import java.io.Serializable;
import java.math.BigDecimal;

public class CT_DangKyResponse implements Serializable {
    private Key_CT_Goi_Dang_Ky id;
    private Reader reader;
    private GoiDangKy goiDangKy;
    private BigDecimal giaTien;

    public CT_DangKyResponse() {
    }

    public CT_DangKyResponse(Key_CT_Goi_Dang_Ky id, Reader reader, GoiDangKy goiDangKy, BigDecimal giaTien) {
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
}
