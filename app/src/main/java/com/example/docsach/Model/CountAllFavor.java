package com.example.docsach.Model;

import java.io.Serializable;

public class CountAllFavor implements Serializable {
    private Long id;
    private String tenSach;
    private Long favor_count;
    private String nam_ra_mat;
    private String tac_gia;

    public CountAllFavor() {
    }

    public CountAllFavor(Long id, String tenSach, Long favor_count, String nam_ra_mat, String tac_gia) {
        this.id = id;
        this.tenSach = tenSach;
        this.favor_count = favor_count;
        this.nam_ra_mat = nam_ra_mat;
        this.tac_gia = tac_gia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public Long getFavor_count() {
        return favor_count;
    }

    public void setFavor_count(Long favor_count) {
        this.favor_count = favor_count;
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

    public static final class CountAllFavorBuilder {
        private CountAllFavor countAllFavor;

        public CountAllFavorBuilder() {
            countAllFavor = new CountAllFavor();
        }

        public CountAllFavorBuilder(CountAllFavor other) {
            this.countAllFavor = other;
        }

        public static CountAllFavorBuilder aCountAllFavor() {
            return new CountAllFavorBuilder();
        }

        public CountAllFavorBuilder withId(Long id) {
            countAllFavor.setId(id);
            return this;
        }

        public CountAllFavorBuilder withTenSach(String tenSach) {
            countAllFavor.setTenSach(tenSach);
            return this;
        }

        public CountAllFavorBuilder withFavor_count(Long favor_count) {
            countAllFavor.setFavor_count(favor_count);
            return this;
        }

        public CountAllFavorBuilder withNam_ra_mat(String nam_ra_mat) {
            countAllFavor.setNam_ra_mat(nam_ra_mat);
            return this;
        }

        public CountAllFavorBuilder withTac_gia(String tac_gia) {
            countAllFavor.setTac_gia(tac_gia);
            return this;
        }

        public CountAllFavor build() {
            return countAllFavor;
        }
    }
}
