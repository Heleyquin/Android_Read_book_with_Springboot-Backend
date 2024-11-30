package com.example.docsach.Model.DTO;

import java.io.Serializable;
import java.math.BigDecimal;

public class LichSuNapRequest implements Serializable {
    private long readerId;
    private BigDecimal tien;

    public LichSuNapRequest() {
    }

    public LichSuNapRequest(long readerId, BigDecimal tien) {
        this.readerId = readerId;
        this.tien = tien;
    }

    public long getReaderId() {
        return readerId;
    }

    public void setReaderId(long readerId) {
        this.readerId = readerId;
    }

    public BigDecimal getTien() {
        return tien;
    }

    public void setTien(BigDecimal tien) {
        this.tien = tien;
    }
}
