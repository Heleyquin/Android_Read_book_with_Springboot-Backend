package com.example.docsach.Model;

import java.io.Serializable;
import java.util.Set;

public class TheLoai implements Serializable {
    private long id;
    private String tenTL;
    private Set<Sach> sachs;

    public TheLoai() {
    }

    public TheLoai(long id, String tenTL, Set<Sach> sachs) {
        this.id = id;
        this.tenTL = tenTL;
        this.sachs = sachs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTenTL() {
        return tenTL;
    }

    public void setTenTL(String tenTL) {
        this.tenTL = tenTL;
    }

    public Set<Sach> getSachs() {
        return sachs;
    }

    public void setSachs(Set<Sach> sachs) {
        this.sachs = sachs;
    }

    public static final class TheLoaiBuilder {
        private TheLoai theLoai;

        public TheLoaiBuilder() {
            theLoai = new TheLoai();
        }

        public TheLoaiBuilder(TheLoai other) {
            this.theLoai = other;
        }

        public static TheLoaiBuilder aTheLoai() {
            return new TheLoaiBuilder();
        }

        public TheLoaiBuilder withId(long id) {
            theLoai.setId(id);
            return this;
        }

        public TheLoaiBuilder withTenTL(String tenTL) {
            theLoai.setTenTL(tenTL);
            return this;
        }

        public TheLoaiBuilder withSachs(Set<Sach> sachs) {
            theLoai.setSachs(sachs);
            return this;
        }

        public TheLoai build() {
            return theLoai;
        }
    }
}
