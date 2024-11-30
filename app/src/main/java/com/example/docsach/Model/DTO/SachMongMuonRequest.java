package com.example.docsach.Model.DTO;

import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;

import java.io.Serializable;

public class SachMongMuonRequest implements Serializable {
    private Reader readerWish;
    private Sach sachWish;

    public SachMongMuonRequest() {
    }

    public SachMongMuonRequest(Reader readerWish, Sach sachWish) {
        this.readerWish = readerWish;
        this.sachWish = sachWish;
    }

    public Reader getReaderWish() {
        return readerWish;
    }

    public void setReaderWish(Reader readerWish) {
        this.readerWish = readerWish;
    }

    public Sach getSachWish() {
        return sachWish;
    }

    public void setSachWish(Sach sachWish) {
        this.sachWish = sachWish;
    }
}
