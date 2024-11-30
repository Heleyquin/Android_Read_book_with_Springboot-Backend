package com.example.docsach.Model.DTO;

import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;

import java.io.Serializable;

public class LichSuDocRequest implements Serializable {
    private Reader idReader;
    private Sach sach;

    public LichSuDocRequest() {
    }

    public LichSuDocRequest(Reader idReader, Sach sach) {
        this.idReader = idReader;
        this.sach = sach;
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

    public static final class LichSuDocRequestBuilder {
        private LichSuDocRequest lichSuDocRequest;

        public LichSuDocRequestBuilder() {
            lichSuDocRequest = new LichSuDocRequest();
        }

        public LichSuDocRequestBuilder(LichSuDocRequest other) {
            this.lichSuDocRequest = other;
        }

        public static LichSuDocRequestBuilder aLichSuDocRequest() {
            return new LichSuDocRequestBuilder();
        }

        public LichSuDocRequestBuilder withIdReader(Reader idReader) {
            lichSuDocRequest.setIdReader(idReader);
            return this;
        }

        public LichSuDocRequestBuilder withSach(Sach sach) {
            lichSuDocRequest.setSach(sach);
            return this;
        }

        public LichSuDocRequest build() {
            return lichSuDocRequest;
        }
    }
}
