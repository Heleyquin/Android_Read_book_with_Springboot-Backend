package com.example.docsach.Model.DTO;

import com.example.docsach.Model.Key.Key_BinhLuan;

import java.io.Serializable;

public class CmtRequest implements Serializable {
    private Key_BinhLuan id;
    private String noiDung;

    public CmtRequest() {
    }

    public CmtRequest(Key_BinhLuan id, String noiDung) {
        this.id = id;
        this.noiDung = noiDung;
    }

    public Key_BinhLuan getId() {
        return id;
    }

    public void setId(Key_BinhLuan id) {
        this.id = id;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
