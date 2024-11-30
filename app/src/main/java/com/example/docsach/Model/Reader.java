package com.example.docsach.Model;

import java.io.Serializable;
import java.util.Set;

public class Reader implements Serializable {
    private Long id;
    private String ten;
    private String ho;
    private boolean gioiTinh;
    private String ngayTao;
    private String email;
    private Set<LichSuDoc> lichSuDocs;
    private Set<Sach> sachs;
    private Set<CT_Dang_Ky> ctGoiDangKys;
    private Account idAccount;
    private Set<LichSuMua> ctLichSuMua;
    private Set<Sach_Mong_Muon> ctSachMongMuon;

    public Reader() {
    }

    public Reader(Long id, String ten, String ho, boolean gioiTinh, String ngayTao, String email, Set<LichSuDoc> lichSuDocs, Set<Sach> sachs, Set<CT_Dang_Ky> ctGoiDangKys, Account idAccount, Set<LichSuMua> ctLichSuMua, Set<Sach_Mong_Muon> ctSachMongMuon) {
        this.id = id;
        this.ten = ten;
        this.ho = ho;
        this.gioiTinh = gioiTinh;
        this.ngayTao = ngayTao;
        this.email = email;
        this.lichSuDocs = lichSuDocs;
        this.sachs = sachs;
        this.ctGoiDangKys = ctGoiDangKys;
        this.idAccount = idAccount;
        this.ctLichSuMua = ctLichSuMua;
        this.ctSachMongMuon = ctSachMongMuon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<LichSuDoc> getLichSuDocs() {
        return lichSuDocs;
    }

    public void setLichSuDocs(Set<LichSuDoc> lichSuDocs) {
        this.lichSuDocs = lichSuDocs;
    }

    public Set<Sach> getSachs() {
        return sachs;
    }

    public void setSachs(Set<Sach> sachs) {
        this.sachs = sachs;
    }

    public Set<CT_Dang_Ky> getCtGoiDangKys() {
        return ctGoiDangKys;
    }

    public void setCtGoiDangKys(Set<CT_Dang_Ky> ctGoiDangKys) {
        this.ctGoiDangKys = ctGoiDangKys;
    }

    public Account getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Account idAccount) {
        this.idAccount = idAccount;
    }

    public Set<LichSuMua> getCtLichSuMua() {
        return ctLichSuMua;
    }

    public void setCtLichSuMua(Set<LichSuMua> ctLichSuMua) {
        this.ctLichSuMua = ctLichSuMua;
    }

    public Set<Sach_Mong_Muon> getCtSachMongMuon() {
        return ctSachMongMuon;
    }

    public void setCtSachMongMuon(Set<Sach_Mong_Muon> ctSachMongMuon) {
        this.ctSachMongMuon = ctSachMongMuon;
    }

    public static final class ReaderBuilder {
        private Reader reader;

        public ReaderBuilder() {
            reader = new Reader();
        }

        public ReaderBuilder(Reader other) {
            this.reader = other;
        }

        public static ReaderBuilder aReader() {
            return new ReaderBuilder();
        }

        public ReaderBuilder withId(Long id) {
            reader.setId(id);
            return this;
        }

        public ReaderBuilder withTen(String ten) {
            reader.setTen(ten);
            return this;
        }

        public ReaderBuilder withHo(String ho) {
            reader.setHo(ho);
            return this;
        }

        public ReaderBuilder withGioiTinh(boolean gioiTinh) {
            reader.setGioiTinh(gioiTinh);
            return this;
        }

        public ReaderBuilder withNgayTao(String ngayTao) {
            reader.setNgayTao(ngayTao);
            return this;
        }

        public ReaderBuilder withEmail(String email) {
            reader.setEmail(email);
            return this;
        }

        public ReaderBuilder withLichSuDocs(Set<LichSuDoc> lichSuDocs) {
            reader.setLichSuDocs(lichSuDocs);
            return this;
        }

        public ReaderBuilder withSachs(Set<Sach> sachs) {
            reader.setSachs(sachs);
            return this;
        }

        public ReaderBuilder withCtGoiDangKys(Set<CT_Dang_Ky> ctGoiDangKys) {
            reader.setCtGoiDangKys(ctGoiDangKys);
            return this;
        }

        public ReaderBuilder withIdAccount(Account idAccount) {
            reader.setIdAccount(idAccount);
            return this;
        }

        public ReaderBuilder withCtLichSuMua(Set<LichSuMua> ctLichSuMua) {
            reader.setCtLichSuMua(ctLichSuMua);
            return this;
        }

        public ReaderBuilder withCtSachMongMuon(Set<Sach_Mong_Muon> ctSachMongMuon) {
            reader.setCtSachMongMuon(ctSachMongMuon);
            return this;
        }

        public Reader build() {
            return reader;
        }
    }
}
