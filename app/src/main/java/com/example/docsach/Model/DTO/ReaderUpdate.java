package com.example.docsach.Model.DTO;

import com.example.docsach.Model.Account;
import com.example.docsach.Model.Key.Key_CT_Goi_Dang_Ky;

import java.io.Serializable;
import java.util.Set;

public class ReaderUpdate implements Serializable {
    private Long id;
    private String ten;
    private String ho;
    private boolean gioiTinh;
    private String ngayTao;
    private String email;
    private Set<Long> lichSuDocs;
    private Set<Long> sachs;
    private Set<Key_CT_Goi_Dang_Ky> ctGoiDangKys;
    private Account idAccount;
    private Set<Long> ctLichSuMua;
    private Set<Long> ctSachMongMuon;

    public ReaderUpdate() {
    }

    public ReaderUpdate(Long id, String ten, String ho, boolean gioiTinh, String ngayTao, String email, Set<Long> lichSuDocs, Set<Long> sachs, Set<Key_CT_Goi_Dang_Ky> ctGoiDangKys, Account idAccount, Set<Long> ctLichSuMua, Set<Long> ctSachMongMuon) {
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

    public Set<Long> getLichSuDocs() {
        return lichSuDocs;
    }

    public void setLichSuDocs(Set<Long> lichSuDocs) {
        this.lichSuDocs = lichSuDocs;
    }

    public Set<Long> getSachs() {
        return sachs;
    }

    public void setSachs(Set<Long> sachs) {
        this.sachs = sachs;
    }

    public Set<Key_CT_Goi_Dang_Ky> getCtGoiDangKys() {
        return ctGoiDangKys;
    }

    public void setCtGoiDangKys(Set<Key_CT_Goi_Dang_Ky> ctGoiDangKys) {
        this.ctGoiDangKys = ctGoiDangKys;
    }

    public Account getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Account idAccount) {
        this.idAccount = idAccount;
    }

    public Set<Long> getCtLichSuMua() {
        return ctLichSuMua;
    }

    public void setCtLichSuMua(Set<Long> ctLichSuMua) {
        this.ctLichSuMua = ctLichSuMua;
    }

    public Set<Long> getCtSachMongMuon() {
        return ctSachMongMuon;
    }

    public void setCtSachMongMuon(Set<Long> ctSachMongMuon) {
        this.ctSachMongMuon = ctSachMongMuon;
    }
}
