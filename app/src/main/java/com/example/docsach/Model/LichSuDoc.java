package com.example.docsach.Model;

import java.io.Serializable;

public class LichSuDoc implements Serializable {
    private long id;
    private Reader idReader;
    private Sach sach;
    private String thoiGian;

    public LichSuDoc() {
    }

    public LichSuDoc(long id, Reader idReader, Sach sach, String thoiGian) {
        this.id = id;
        this.idReader = idReader;
        this.sach = sach;
        this.thoiGian = thoiGian;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Reader getIdReader() {
        return idReader;
    }

    public void setIdReader(Reader idReader) {
        this.idReader = idReader;
    }

    public Sach getSach() {
        return sach;
    }

    public void setSach(Sach sach) {
        this.sach = sach;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public static final class LichSuDocBuilder {
        private LichSuDoc lichSuDoc;

        public LichSuDocBuilder() {
            lichSuDoc = new LichSuDoc();
        }

        public LichSuDocBuilder(LichSuDoc other) {
            this.lichSuDoc = other;
        }

        public static LichSuDocBuilder aLichSuDoc() {
            return new LichSuDocBuilder();
        }

        public LichSuDocBuilder withId(long id) {
            lichSuDoc.setId(id);
            return this;
        }

        public LichSuDocBuilder withIdReader(Reader idReader) {
            lichSuDoc.setIdReader(idReader);
            return this;
        }

        public LichSuDocBuilder withSach(Sach sach) {
            lichSuDoc.setSach(sach);
            return this;
        }

        public LichSuDocBuilder withThoiGian(String thoiGian) {
            lichSuDoc.setThoiGian(thoiGian);
            return this;
        }

        public LichSuDoc build() {
            return lichSuDoc;
        }
    }
}
