package com.example.docsach.Model.DTO;

import com.example.docsach.Model.Key.Key_DanhGia;

import java.io.Serializable;

public class DanhGiaRequest implements Serializable {
    private Key_DanhGia id;
    private int point;
    private String nhanXet;

    public DanhGiaRequest() {
    }

    public DanhGiaRequest(Key_DanhGia id, int point, String nhanXet) {
        this.id = id;
        this.point = point;
        this.nhanXet = nhanXet;
    }

    public Key_DanhGia getId() {
        return id;
    }

    public void setId(Key_DanhGia id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getNhanXet() {
        return nhanXet;
    }

    public void setNhanXet(String nhanXet) {
        this.nhanXet = nhanXet;
    }
}
