package com.example.docsach.Model.DTO;

import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class LichSuMuaRequest implements Serializable {
    private BigDecimal giaTien;
    private Sach sachBuy;
    private Reader readerBuy;

    public LichSuMuaRequest() {
    }

    public LichSuMuaRequest(BigDecimal giaTien, Sach sachBuy, Reader readerBuy) {
        this.giaTien = giaTien;
        this.sachBuy = sachBuy;
        this.readerBuy = readerBuy;
    }

    public BigDecimal getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }

    public Sach getSachBuy() {
        return sachBuy;
    }

    public void setSachBuy(Sach sachBuy) {
        this.sachBuy = sachBuy;
    }

    public Reader getReaderBuy() {
        return readerBuy;
    }

    public void setReaderBuy(Reader readerBuy) {
        this.readerBuy = readerBuy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LichSuMuaRequest that = (LichSuMuaRequest) o;
        return Objects.equals(giaTien, that.giaTien) && Objects.equals(sachBuy, that.sachBuy) && Objects.equals(readerBuy, that.readerBuy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giaTien, sachBuy, readerBuy);
    }

    public static final class LichSuMuaRequestBuilder {
        private LichSuMuaRequest lichSuMuaRequest;

        public LichSuMuaRequestBuilder() {
            lichSuMuaRequest = new LichSuMuaRequest();
        }

        public LichSuMuaRequestBuilder(LichSuMuaRequest other) {
            this.lichSuMuaRequest = other;
        }

        public static LichSuMuaRequestBuilder aLichSuMuaRequest() {
            return new LichSuMuaRequestBuilder();
        }

        public LichSuMuaRequestBuilder withGiaTien(BigDecimal giaTien) {
            lichSuMuaRequest.setGiaTien(giaTien);
            return this;
        }

        public LichSuMuaRequestBuilder withSachBuy(Sach sachBuy) {
            lichSuMuaRequest.setSachBuy(sachBuy);
            return this;
        }

        public LichSuMuaRequestBuilder withReaderBuy(Reader readerBuy) {
            lichSuMuaRequest.setReaderBuy(readerBuy);
            return this;
        }

        public LichSuMuaRequest build() {
            return lichSuMuaRequest;
        }
    }
}
