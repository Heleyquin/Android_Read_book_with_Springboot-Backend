package com.example.docsach.Model;

import java.io.Serializable;
import java.util.Set;

public class TacGia implements Serializable {
    private long id;
    private String tenTG;
    private String hoTG;
    private boolean gioiTinh;
    private String ngaySinh;
    private String diaChi;
    private Set<Sach> sachs;

    public TacGia() {
    }

    public TacGia(long id, String tenTG, String hoTG, boolean gioiTinh, String ngaySinh, String diaChi, Set<Sach> sachs) {
        this.id = id;
        this.tenTG = tenTG;
        this.hoTG = hoTG;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.sachs = sachs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTenTG() {
        return tenTG;
    }

    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
    }

    public String getHoTG() {
        return hoTG;
    }

    public void setHoTG(String hoTG) {
        this.hoTG = hoTG;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Set<Sach> getSachs() {
        return sachs;
    }

    public void setSachs(Set<Sach> sachs) {
        this.sachs = sachs;
    }

    public static final class TacGiaBuilder {
        private TacGia tacGia;

        public TacGiaBuilder() {
            tacGia = new TacGia();
        }

        public TacGiaBuilder(TacGia other) {
            this.tacGia = other;
        }

        public static TacGiaBuilder aTacGia() {
            return new TacGiaBuilder();
        }

        public TacGiaBuilder withId(long id) {
            tacGia.setId(id);
            return this;
        }

        public TacGiaBuilder withTenTG(String tenTG) {
            tacGia.setTenTG(tenTG);
            return this;
        }

        public TacGiaBuilder withHoTG(String hoTG) {
            tacGia.setHoTG(hoTG);
            return this;
        }

        public TacGiaBuilder withGioiTinh(boolean gioiTinh) {
            tacGia.setGioiTinh(gioiTinh);
            return this;
        }

        public TacGiaBuilder withNgaySinh(String ngaySinh) {
            tacGia.setNgaySinh(ngaySinh);
            return this;
        }

        public TacGiaBuilder withDiaChi(String diaChi) {
            tacGia.setDiaChi(diaChi);
            return this;
        }

        public TacGiaBuilder withSachs(Set<Sach> sachs) {
            tacGia.setSachs(sachs);
            return this;
        }

        public TacGia build() {
            return tacGia;
        }
    }
}
