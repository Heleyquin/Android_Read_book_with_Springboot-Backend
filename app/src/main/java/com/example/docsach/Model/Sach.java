package com.example.docsach.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class Sach implements Serializable {
    private long id;
    private String tenSach;
    private String nxb;
    private String urlImg;
    private String gioiThieu;
    private String ngayTao;
    private String ngayUpdate;
    private String ngayRaMat;
    private BigDecimal giaTien;
    private String urlFile;
    private boolean active;
    private QuanLy quanLy;
    private Set<TheLoai> theLoaiList;
    private Set<TacGia> tacGiaList;
    private Set<Reader> favors;
    private Set<Cmt> cmts;

    public Sach() {
    }
    public Sach(long id, String tenSach, String nxb, String urlImg, String gioiThieu, String ngayTao, String ngayUpdate, String ngayRaMat, BigDecimal giaTien, String urlFile, boolean active, QuanLy idQuanLy, Set<TheLoai> theLoaiSet, Set<TacGia> tacGias, Set<Reader> favors, Set<Cmt> cmts) {
        this.id = id;
        this.tenSach = tenSach;
        this.nxb = nxb;
        this.urlImg = urlImg;
        this.gioiThieu = gioiThieu;
        this.ngayTao = ngayTao;
        this.ngayUpdate = ngayUpdate;
        this.ngayRaMat = ngayRaMat;
        this.giaTien = giaTien;
        this.urlFile = urlFile;
        this.active = active;
        this.quanLy = idQuanLy;
        this.theLoaiList = theLoaiSet;
        this.tacGiaList = tacGias;
        this.favors = favors;
        this.cmts = cmts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgayUpdate() {
        return ngayUpdate;
    }

    public void setNgayUpdate(String ngayUpdate) {
        this.ngayUpdate = ngayUpdate;
    }

    public String getNgayRaMat() {
        return ngayRaMat;
    }

    public void setNgayRaMat(String ngayRaMat) {
        this.ngayRaMat = ngayRaMat;
    }

    public BigDecimal getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public QuanLy getIdQuanLy() {
        return quanLy;
    }

    public void setIdQuanLy(QuanLy idQuanLy) {
        this.quanLy = idQuanLy;
    }

    public Set<TheLoai> getTheLoaiSet() {
        return theLoaiList;
    }

    public void setTheLoaiSet(Set<TheLoai> theLoaiSet) {
        this.theLoaiList = theLoaiSet;
    }

    public Set<TacGia> getTacGias() {
        return tacGiaList;
    }

    public void setTacGias(Set<TacGia> tacGias) {
        this.tacGiaList = tacGias;
    }

    public Set<Reader> getFavors() {
        return favors;
    }

    public void setFavors(Set<Reader> favors) {
        this.favors = favors;
    }

    public Set<Cmt> getCmts() {
        return cmts;
    }

    public void setCmts(Set<Cmt> cmts) {
        this.cmts = cmts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sach sach = (Sach) o;
        return id == sach.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static final class SachBuilder {
        private Sach sach;

        public SachBuilder() {
            sach = new Sach();
        }

        public SachBuilder(Sach other) {
            this.sach = other;
        }

        public static SachBuilder aSach() {
            return new SachBuilder();
        }

        public SachBuilder withId(long id) {
            sach.setId(id);
            return this;
        }

        public SachBuilder withTenSach(String tenSach) {
            sach.setTenSach(tenSach);
            return this;
        }

        public SachBuilder withNxb(String nxb) {
            sach.setNxb(nxb);
            return this;
        }

        public SachBuilder withUrlImg(String urlImg) {
            sach.setUrlImg(urlImg);
            return this;
        }

        public SachBuilder withGioiThieu(String gioiThieu) {
            sach.setGioiThieu(gioiThieu);
            return this;
        }

        public SachBuilder withNgayTao(String ngayTao) {
            sach.setNgayTao(ngayTao);
            return this;
        }

        public SachBuilder withNgayUpdate(String ngayUpdate) {
            sach.setNgayUpdate(ngayUpdate);
            return this;
        }

        public SachBuilder withNgayRaMat(String ngayRaMat) {
            sach.setNgayRaMat(ngayRaMat);
            return this;
        }

        public SachBuilder withGiaTien(BigDecimal giaTien) {
            sach.setGiaTien(giaTien);
            return this;
        }

        public SachBuilder withUrlFile(String urlFile) {
            sach.setUrlFile(urlFile);
            return this;
        }

        public SachBuilder withActive(boolean active) {
            sach.setActive(active);
            return this;
        }

        public SachBuilder withIdQuanLy(QuanLy idQuanLy) {
            sach.setIdQuanLy(idQuanLy);
            return this;
        }

        public SachBuilder withTheLoaiSet(Set<TheLoai> theLoaiSet) {
            sach.setTheLoaiSet(theLoaiSet);
            return this;
        }

        public SachBuilder withTacGias(Set<TacGia> tacGias) {
            sach.setTacGias(tacGias);
            return this;
        }

        public SachBuilder withFavors(Set<Reader> favors) {
            sach.setFavors(favors);
            return this;
        }

        public SachBuilder withCmts(Set<Cmt> cmts) {
            sach.setCmts(cmts);
            return this;
        }

        public Sach build() {
            return sach;
        }
    }
}
