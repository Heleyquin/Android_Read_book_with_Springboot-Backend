package com.example.docsach.Model.Key;

import java.io.Serializable;

public class Key_DanhGia implements Serializable {
    private Long idSach;
    private Long Id_DocGia;

    public Key_DanhGia() {
    }

    public Key_DanhGia(Long idSach, Long id_DocGia) {
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

    public static final class Key_DanhGiaBuilder {
        private Key_DanhGia key_DanhGia;

        public Key_DanhGiaBuilder() {
            key_DanhGia = new Key_DanhGia();
        }

        public Key_DanhGiaBuilder(Key_DanhGia other) {
            this.key_DanhGia = other;
        }

        public static Key_DanhGiaBuilder aKey_DanhGia() {
            return new Key_DanhGiaBuilder();
        }

        public Key_DanhGiaBuilder withIdSach(Long idSach) {
            key_DanhGia.setIdSach(idSach);
            return this;
        }

        public Key_DanhGiaBuilder withId_DocGia(Long Id_DocGia) {
            key_DanhGia.setId_DocGia(Id_DocGia);
            return this;
        }

        public Key_DanhGia build() {
            return key_DanhGia;
        }
    }
}
