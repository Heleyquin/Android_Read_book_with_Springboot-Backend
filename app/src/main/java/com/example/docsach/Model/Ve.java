package com.example.docsach.Model;

import java.io.Serializable;

public class Ve implements Serializable {
    private int idVe;
    private int id_ghe;
    private int idSuatChieu;
    private int idHoaDon;

    public Ve() {
    }

    public Ve(int idVe, int id_ghe, int idSuatChieu, int idHoaDon) {
        this.idVe = idVe;
        this.id_ghe = id_ghe;
        this.idSuatChieu = idSuatChieu;
        this.idHoaDon = idHoaDon;
    }

    public int getIdVe() {
        return idVe;
    }

    public void setIdVe(int idVe) {
        this.idVe = idVe;
    }

    public int getId_ghe() {
        return id_ghe;
    }

    public void setId_ghe(int id_ghe) {
        this.id_ghe = id_ghe;
    }

    public int getIdSuatChieu() {
        return idSuatChieu;
    }

    public void setIdSuatChieu(int idSuatChieu) {
        this.idSuatChieu = idSuatChieu;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }
}
