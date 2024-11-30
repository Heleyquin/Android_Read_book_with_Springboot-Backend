package com.example.docsach.Model;

import java.io.Serializable;

public class LuotDocSach implements Serializable {
    private Long id;
    private String ten_sach;
    private Long so_luot_doc;
    private String nam_ra_mat;
    private String tac_gia;

    public LuotDocSach() {
    }

    public LuotDocSach(Long id, String ten_sach, Long so_luot_doc, String nam_ra_mat, String tac_gia) {
        this.id = id;
        this.ten_sach = ten_sach;
        this.so_luot_doc = so_luot_doc;
        this.nam_ra_mat = nam_ra_mat;
        this.tac_gia = tac_gia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen_sach() {
        return ten_sach;
    }

    public void setTen_sach(String ten_sach) {
        this.ten_sach = ten_sach;
    }

    public Long getSo_luot_doc() {
        return so_luot_doc;
    }

    public void setSo_luot_doc(Long so_luot_doc) {
        this.so_luot_doc = so_luot_doc;
    }

    public String getNam_ra_mat() {
        return nam_ra_mat;
    }

    public void setNam_ra_mat(String nam_ra_mat) {
        this.nam_ra_mat = nam_ra_mat;
    }

    public String getTac_gia() {
        return tac_gia;
    }

    public void setTac_gia(String tac_gia) {
        this.tac_gia = tac_gia;
    }

    public static final class LuotDocSachBuilder {
        private LuotDocSach luotDocSach;

        public LuotDocSachBuilder() {
            luotDocSach = new LuotDocSach();
        }

        public LuotDocSachBuilder(LuotDocSach other) {
            this.luotDocSach = other;
        }

        public static LuotDocSachBuilder aLuotDocSach() {
            return new LuotDocSachBuilder();
        }

        public LuotDocSachBuilder withId(Long id) {
            luotDocSach.setId(id);
            return this;
        }

        public LuotDocSachBuilder withTen_sach(String ten_sach) {
            luotDocSach.setTen_sach(ten_sach);
            return this;
        }

        public LuotDocSachBuilder withSo_luot_doc(Long so_luot_doc) {
            luotDocSach.setSo_luot_doc(so_luot_doc);
            return this;
        }

        public LuotDocSachBuilder withNam_ra_mat(String nam_ra_mat) {
            luotDocSach.setNam_ra_mat(nam_ra_mat);
            return this;
        }

        public LuotDocSachBuilder withTac_gia(String tac_gia) {
            luotDocSach.setTac_gia(tac_gia);
            return this;
        }

        public LuotDocSach build() {
            return luotDocSach;
        }
    }
}
