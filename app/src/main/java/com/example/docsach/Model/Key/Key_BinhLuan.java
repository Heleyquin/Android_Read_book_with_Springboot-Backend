package com.example.docsach.Model.Key;

import java.io.Serializable;

public class Key_BinhLuan implements Serializable {
    private Long idSach;
    private Long Id_DocGia;
    private String thoiGianTao;

    public Key_BinhLuan() {
    }

    public Key_BinhLuan(Long idSach, Long id_DocGia, String thoiGianTao) {
        this.idSach = idSach;
        Id_DocGia = id_DocGia;
        this.thoiGianTao = thoiGianTao;
    }

    public Key_BinhLuan(Long idSach, Long id_DocGia) {
        this.idSach = idSach;
        Id_DocGia = id_DocGia;
    }

    public Long getIdSach() {
        return idSach;
    }

    public void setIdSach(Long idSach) {
        this.idSach = idSach;
    }

    public Long getId_DocGia() {
        return Id_DocGia;
    }

    public void setId_DocGia(Long id_DocGia) {
        Id_DocGia = id_DocGia;
    }

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(String thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public static final class Key_BinhLuanBuilder {
        private Key_BinhLuan key_BinhLuan;

        public Key_BinhLuanBuilder() {
            key_BinhLuan = new Key_BinhLuan();
        }

        public Key_BinhLuanBuilder(Key_BinhLuan other) {
            this.key_BinhLuan = other;
        }

        public static Key_BinhLuanBuilder aKey_BinhLuan() {
            return new Key_BinhLuanBuilder();
        }

        public Key_BinhLuanBuilder withIdSach(Long idSach) {
            key_BinhLuan.setIdSach(idSach);
            return this;
        }

        public Key_BinhLuanBuilder withId_DocGia(Long Id_DocGia) {
            key_BinhLuan.setId_DocGia(Id_DocGia);
            return this;
        }

        public Key_BinhLuanBuilder withThoiGianTao(String thoiGianTao) {
            key_BinhLuan.setThoiGianTao(thoiGianTao);
            return this;
        }

        public Key_BinhLuan build() {
            return key_BinhLuan;
        }
    }
}
