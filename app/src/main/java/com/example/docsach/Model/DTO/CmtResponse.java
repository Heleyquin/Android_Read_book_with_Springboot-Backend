package com.example.docsach.Model.DTO;

import com.example.docsach.Model.Key.Key_BinhLuan;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;

import java.io.Serializable;

public class CmtResponse implements Serializable {
    private Key_BinhLuan id;
    private String noiDung;
    private Sach sachCmt;
    private Reader readerCmt;

    public CmtResponse() {
    }

    public CmtResponse(Key_BinhLuan id, String noiDung, Sach sachCmt, Reader readerCmt) {
        this.id = id;
        this.noiDung = noiDung;
        this.sachCmt = sachCmt;
        this.readerCmt = readerCmt;
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

    public Sach getSachCmt() {
        return sachCmt;
    }

    public void setSachCmt(Sach sachCmt) {
        this.sachCmt = sachCmt;
    }

    public Reader getReaderCmt() {
        return readerCmt;
    }

    public void setReaderCmt(Reader readerCmt) {
        this.readerCmt = readerCmt;
    }
}
