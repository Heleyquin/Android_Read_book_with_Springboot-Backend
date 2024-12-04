package com.example.docsach.Model.DTO;

public class UserAccountRequest {
    private String email;
    private String tk;
    private String ho;
    private String ten;
    private String diaChi;
    private boolean gioiTinh;
    private int role;

    public UserAccountRequest() {
    }

    public UserAccountRequest(String email, String tk, String ho, String ten, String diaChi, boolean gioiTinh, int role) {
        this.email = email;
        this.tk = tk;
        this.ho = ho;
        this.ten = ten;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
