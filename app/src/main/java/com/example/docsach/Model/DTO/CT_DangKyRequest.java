package com.example.docsach.Model.DTO;

import java.io.Serializable;
import java.math.BigDecimal;

public class CT_DangKyRequest implements Serializable {
    private String maGoi;
    private Long idReader;
    private BigDecimal giaTien;

    public CT_DangKyRequest() {
    }

    public CT_DangKyRequest(String maGoi, Long idReader, BigDecimal giaTien) {
        this.maGoi = maGoi;
        this.idReader = idReader;
        this.giaTien = giaTien;
    }

    public String getMaGoi() {
        return maGoi;
    }

    public void setMaGoi(String maGoi) {
        this.maGoi = maGoi;
    }

    public Long getIdReader() {
        return idReader;
    }

    public void setIdReader(Long idReader) {
        this.idReader = idReader;
    }

    public BigDecimal getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }
}
