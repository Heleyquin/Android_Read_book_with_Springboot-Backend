package com.example.docsach.Model.Key;

import java.io.Serializable;

public class Key_CT_Goi implements Serializable {
    private Long idSach;
    private String maGoi;

    public Key_CT_Goi() {
    }

    public Key_CT_Goi(Long idSach, String maGoi) {
        this.idSach = idSach;
        this.maGoi = maGoi;
    }

    public Long getIdSach() {
        return idSach;
    }

    public void setIdSach(Long idSach) {
        this.idSach = idSach;
    }

    public String getMaGoi() {
        return maGoi;
    }

    public void setMaGoi(String maGoi) {
        this.maGoi = maGoi;
    }

    public static final class Key_CT_GoiBuilder {
        private Key_CT_Goi key_CT_Goi;

        public Key_CT_GoiBuilder() {
            key_CT_Goi = new Key_CT_Goi();
        }

        public Key_CT_GoiBuilder(Key_CT_Goi other) {
            this.key_CT_Goi = other;
        }

        public static Key_CT_GoiBuilder aKey_CT_Goi() {
            return new Key_CT_GoiBuilder();
        }

        public Key_CT_GoiBuilder withIdSach(Long idSach) {
            key_CT_Goi.setIdSach(idSach);
            return this;
        }

        public Key_CT_GoiBuilder withMaGoi(String maGoi) {
            key_CT_Goi.setMaGoi(maGoi);
            return this;
        }

        public Key_CT_Goi build() {
            return key_CT_Goi;
        }
    }
}
