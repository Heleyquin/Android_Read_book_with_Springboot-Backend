package com.example.docsach.Model;

import java.io.Serializable;

public class HoaDon implements Serializable {
    private int idhoadon;
    private int id_kh;

    public HoaDon() {
    }

    public HoaDon(int idhoadon, int id_kh) {
        this.idhoadon = idhoadon;
        this.id_kh = id_kh;
    }

    public int getIdhoadon() {
        return idhoadon;
    }

    public void setIdhoadon(int idhoadon) {
        this.idhoadon = idhoadon;
    }

    public int getId_kh() {
        return id_kh;
    }

    public void setId_kh(int id_kh) {
        this.id_kh = id_kh;
    }
}
