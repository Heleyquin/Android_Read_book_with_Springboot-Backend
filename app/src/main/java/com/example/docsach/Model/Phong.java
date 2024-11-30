package com.example.docsach.Model;

import java.io.Serializable;

public class Phong implements Serializable {
    private int idPhong;
    private String tenPhong;
    private int id_rap;

    public Phong() {
    }

    public Phong(int idPhong, String tenPhong, int id_rap) {
        this.idPhong = idPhong;
        this.tenPhong = tenPhong;
        this.id_rap = id_rap;
    }

    public int getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(int idPhong) {
        this.idPhong = idPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public int getId_rap() {
        return id_rap;
    }

    public void setId_rap(int id_rap) {
        this.id_rap = id_rap;
    }
}
