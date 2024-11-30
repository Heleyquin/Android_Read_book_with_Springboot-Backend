package com.example.docsach.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class LichSuMua implements Serializable {
    private long id;
    private String thoiGianMua;
    private BigDecimal giaTien;
    private Sach sachBuy;
    private Reader readerBuy;

    public LichSuMua() {
    }

    public LichSuMua(long id, String thoiGianMua, BigDecimal giaTien, Sach sachBuy, Reader readerBuy) {
        this.id = id;
        this.thoiGianMua = thoiGianMua;
        this.giaTien = giaTien;
        this.sachBuy = sachBuy;
        this.readerBuy = readerBuy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThoiGianMua() {
        return thoiGianMua;
    }

    public void setThoiGianMua(String thoiGianMua) {
        this.thoiGianMua = thoiGianMua;
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

    public static final class LichSuMuaBuilder {
        private LichSuMua lichSuMua;

        public LichSuMuaBuilder() {
            lichSuMua = new LichSuMua();
        }

        public LichSuMuaBuilder(LichSuMua other) {
            this.lichSuMua = other;
        }

        public static LichSuMuaBuilder aLichSuMua() {
            return new LichSuMuaBuilder();
        }

        public LichSuMuaBuilder withId(long id) {
            lichSuMua.setId(id);
            return this;
        }

        public LichSuMuaBuilder withThoiGianMua(String thoiGianMua) {
            lichSuMua.setThoiGianMua(thoiGianMua);
            return this;
        }

        public LichSuMuaBuilder withGiaTien(BigDecimal giaTien) {
            lichSuMua.setGiaTien(giaTien);
            return this;
        }

        public LichSuMuaBuilder withSachBuy(Sach sachBuy) {
            lichSuMua.setSachBuy(sachBuy);
            return this;
        }

        public LichSuMuaBuilder withReaderBuy(Reader readerBuy) {
            lichSuMua.setReaderBuy(readerBuy);
            return this;
        }

        public LichSuMua build() {
            return lichSuMua;
        }
    }
}
