package com.example.docsach.Model.DTO;

import com.example.docsach.Model.Key.Key_DanhGia;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;

import java.io.Serializable;

public class DanhGiaResponse implements Serializable {
    private Key_DanhGia id;
    private int point;
    private Sach sachRate;
    private Reader readerRate;
    private String thoiGianTao;
    private String thoiGianCapNhat;
    private String nhanXet;

    public DanhGiaResponse() {
    }

    public DanhGiaResponse(String nhanXet, String thoiGianCapNhat, String thoiGianTao, Reader readerRate, Sach sachRate, int point, Key_DanhGia id) {
        this.nhanXet = nhanXet;
        this.thoiGianCapNhat = thoiGianCapNhat;
        this.thoiGianTao = thoiGianTao;
        this.readerRate = readerRate;
        this.sachRate = sachRate;
        this.point = point;
        this.id = id;
    }

    public String getNhanXet() {
        return nhanXet;
    }

    public void setNhanXet(String nhanXet) {
        this.nhanXet = nhanXet;
    }

    public String getThoiGianCapNhat() {
        return thoiGianCapNhat;
    }

    public void setThoiGianCapNhat(String thoiGianCapNhat) {
        this.thoiGianCapNhat = thoiGianCapNhat;
    }

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(String thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public Reader getReaderRate() {
        return readerRate;
    }

    public void setReaderRate(Reader readerRate) {
        this.readerRate = readerRate;
    }

    public Sach getSachRate() {
        return sachRate;
    }

    public void setSachRate(Sach sachRate) {
        this.sachRate = sachRate;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Key_DanhGia getId() {
        return id;
    }

    public void setId(Key_DanhGia id) {
        this.id = id;
    }

    public static final class DanhGiaResponseBuilder {
        private DanhGiaResponse danhGiaResponse;

        public DanhGiaResponseBuilder() {
            danhGiaResponse = new DanhGiaResponse();
        }

        public DanhGiaResponseBuilder(DanhGiaResponse other) {
            this.danhGiaResponse = other;
        }

        public static DanhGiaResponseBuilder aDanhGiaResponse() {
            return new DanhGiaResponseBuilder();
        }

        public DanhGiaResponseBuilder withId(Key_DanhGia id) {
            danhGiaResponse.setId(id);
            return this;
        }

        public DanhGiaResponseBuilder withPoint(int point) {
            danhGiaResponse.setPoint(point);
            return this;
        }

        public DanhGiaResponseBuilder withSachRate(Sach sachRate) {
            danhGiaResponse.setSachRate(sachRate);
            return this;
        }

        public DanhGiaResponseBuilder withReaderRate(Reader readerRate) {
            danhGiaResponse.setReaderRate(readerRate);
            return this;
        }

        public DanhGiaResponseBuilder withThoiGianTao(String thoiGianTao) {
            danhGiaResponse.setThoiGianTao(thoiGianTao);
            return this;
        }

        public DanhGiaResponseBuilder withThoiGianCapNhat(String thoiGianCapNhat) {
            danhGiaResponse.setThoiGianCapNhat(thoiGianCapNhat);
            return this;
        }

        public DanhGiaResponseBuilder withNhanXet(String nhanXet) {
            danhGiaResponse.setNhanXet(nhanXet);
            return this;
        }

        public DanhGiaResponse build() {
            return danhGiaResponse;
        }
    }
}
