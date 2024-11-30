package com.example.docsach.Model;

import java.io.Serializable;

public class Phim implements Serializable {
    private int idPhim;
    private String anh;
    private String ten;
    private String quocGia;
    private String namPhatHanh;
    private String trangthai;
    private int thoiLuong;
    private String moTa;
    private boolean doTuoi;
    private int id_admin;

    public Phim() {
    }

    public Phim(int idPhim, String anh, String ten, String quocGia, String namPhatHanh, String trangthai, int thoiLuong, String moTa, boolean doTuoi, int id_admin) {
        this.idPhim = idPhim;
        this.anh = anh;
        this.ten = ten;
        this.quocGia = quocGia;
        this.namPhatHanh = namPhatHanh;
        this.trangthai = trangthai;
        this.thoiLuong = thoiLuong;
        this.moTa = moTa;
        this.doTuoi = doTuoi;
        this.id_admin = id_admin;
    }

    public int getIdPhim() {
        return idPhim;
    }

    public void setIdPhim(int idPhim) {
        this.idPhim = idPhim;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getNamPhatHanh() {
        return namPhatHanh;
    }

    public void setNamPhatHanh(String namPhatHanh) {
        this.namPhatHanh = namPhatHanh;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public boolean isDoTuoi() {
        return doTuoi;
    }

    public void setDoTuoi(boolean doTuoi) {
        this.doTuoi = doTuoi;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }
}
