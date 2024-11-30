package com.example.docsach.Model;

import com.example.docsach.Model.Key.Key_BinhLuan;

import java.io.Serializable;

public class Cmt implements Serializable {
    private Key_BinhLuan id;
    private String noiDung;
    private Sach sachCmt;
    private Reader readerCmt;

    public Cmt() {
    }

    public Cmt(Key_BinhLuan id, String noiDung, Sach sachCmt, Reader readerCmt) {
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

    public static final class CmtBuilder {
        private Cmt cmt;

        public CmtBuilder() {
            cmt = new Cmt();
        }

        public CmtBuilder(Cmt other) {
            this.cmt = other;
        }

        public static CmtBuilder aCmt() {
            return new CmtBuilder();
        }

        public CmtBuilder withId(Key_BinhLuan id) {
            cmt.setId(id);
            return this;
        }

        public CmtBuilder withNoiDung(String noiDung) {
            cmt.setNoiDung(noiDung);
            return this;
        }

        public CmtBuilder withSachCmt(Sach sachCmt) {
            cmt.setSachCmt(sachCmt);
            return this;
        }

        public CmtBuilder withReaderCmt(Reader readerCmt) {
            cmt.setReaderCmt(readerCmt);
            return this;
        }

        public Cmt build() {
            return cmt;
        }
    }
}
