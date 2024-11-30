package com.example.docsach.Model;

import java.io.Serializable;

public class QuanLy implements Serializable {
    private long id;
    private String ho;
    private String ten;
    private boolean gioiTinh;
    private String email;
    private Account account;
    private String ngayTao;

    public QuanLy() {
    }

    public QuanLy(long id, String ho, String ten, boolean gioiTinh, String email, Account idAccount, String ngayTao) {
        this.id = id;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.account = idAccount;
        this.ngayTao = ngayTao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getIdAccount() {
        return account;
    }

    public void setIdAccount(Account idAccount) {
        this.account = idAccount;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public static final class QuanLyBuilder {
        private QuanLy quanLy;

        public QuanLyBuilder() {
            quanLy = new QuanLy();
        }

        public QuanLyBuilder(QuanLy other) {
            this.quanLy = other;
        }

        public static QuanLyBuilder aQuanLy() {
            return new QuanLyBuilder();
        }

        public QuanLyBuilder withId(long id) {
            quanLy.setId(id);
            return this;
        }

        public QuanLyBuilder withHo(String ho) {
            quanLy.setHo(ho);
            return this;
        }

        public QuanLyBuilder withTen(String ten) {
            quanLy.setTen(ten);
            return this;
        }

        public QuanLyBuilder withGioiTinh(boolean gioiTinh) {
            quanLy.setGioiTinh(gioiTinh);
            return this;
        }

        public QuanLyBuilder withEmail(String email) {
            quanLy.setEmail(email);
            return this;
        }

        public QuanLyBuilder withIdAccount(Account idAccount) {
            quanLy.setIdAccount(idAccount);
            return this;
        }

        public QuanLyBuilder withNgayTao(String ngayTao) {
            quanLy.setNgayTao(ngayTao);
            return this;
        }

        public QuanLy build() {
            return quanLy;
        }
    }
}
