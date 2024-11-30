package com.example.docsach.Model.Key;

import java.io.Serializable;

public class Key_CT_Goi_Dang_Ky implements Serializable {
    private Long idDocGia;
    private String maGoi;
    private String thoiGianDangKy;

    public Key_CT_Goi_Dang_Ky() {
    }

    public Key_CT_Goi_Dang_Ky(Long idDocGia, String maGoi, String thoiGianDangKy) {
        this.idDocGia = idDocGia;
        this.maGoi = maGoi;
        this.thoiGianDangKy = thoiGianDangKy;
    }

    public Long getIdDocGia() {
        return idDocGia;
    }

    public void setIdDocGia(Long idDocGia) {
        this.idDocGia = idDocGia;
    }

    public String getMaGoi() {
        return maGoi;
    }

    public void setMaGoi(String maGoi) {
        this.maGoi = maGoi;
    }

    public String getThoiGianDangKy() {
        return thoiGianDangKy;
    }

    public void setThoiGianDangKy(String thoiGianDangKy) {
        this.thoiGianDangKy = thoiGianDangKy;
    }

    public static final class Key_CT_Goi_Dang_KyBuilder {
        private Key_CT_Goi_Dang_Ky key_CT_Goi_Dang_Ky;

        public Key_CT_Goi_Dang_KyBuilder() {
            key_CT_Goi_Dang_Ky = new Key_CT_Goi_Dang_Ky();
        }

        public Key_CT_Goi_Dang_KyBuilder(Key_CT_Goi_Dang_Ky other) {
            this.key_CT_Goi_Dang_Ky = other;
        }

        public static Key_CT_Goi_Dang_KyBuilder aKey_CT_Goi_Dang_Ky() {
            return new Key_CT_Goi_Dang_KyBuilder();
        }

        public Key_CT_Goi_Dang_KyBuilder withIdDocGia(Long idDocGia) {
            key_CT_Goi_Dang_Ky.setIdDocGia(idDocGia);
            return this;
        }

        public Key_CT_Goi_Dang_KyBuilder withMaGoi(String maGoi) {
            key_CT_Goi_Dang_Ky.setMaGoi(maGoi);
            return this;
        }

        public Key_CT_Goi_Dang_KyBuilder withThoiGianDangKy(String thoiGianDangKy) {
            key_CT_Goi_Dang_Ky.setThoiGianDangKy(thoiGianDangKy);
            return this;
        }

        public Key_CT_Goi_Dang_Ky build() {
            return key_CT_Goi_Dang_Ky;
        }
    }
}
