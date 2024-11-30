package com.example.docsach.Model;

import java.io.Serializable;

public class SuatChieu implements Serializable {
    private int idSuatChieu;
    private String gioBatDau;
    private String ngonNgu;
    private String ngayChieu;
    private String sub;
    private double gia;
    private int id_admin;
    private int id_phim;
    private int id_phong;

    public SuatChieu() {
    }

    public SuatChieu(int idSuatChieu, String gioBatDau, String ngonNgu, String ngayChieu, String sub, double gia, int id_admin, int id_phim, int idPhong) {
        this.idSuatChieu = idSuatChieu;
        this.gioBatDau = gioBatDau;
        this.ngonNgu = ngonNgu;
        this.ngayChieu = ngayChieu;
        this.sub = sub;
        this.gia = gia;
        this.id_admin = id_admin;
        this.id_phim = id_phim;
        id_phong = idPhong;
    }
    public int getId_phong() {
        return id_phong;
    }

    public void setId_phong(int id_phong) {
        this.id_phong = id_phong;
    }
    public int getIdSuatChieu() {
        return idSuatChieu;
    }

    public void setIdSuatChieu(int idSuatChieu) {
        this.idSuatChieu = idSuatChieu;
    }

    public String getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(String giaBatDau) {
        this.gioBatDau = giaBatDau;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public String getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(String ngayChieu) {
        this.ngayChieu = ngayChieu;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public int getId_phim() {
        return id_phim;
    }

    public void setId_phim(int id_phim) {
        this.id_phim = id_phim;
    }
}
