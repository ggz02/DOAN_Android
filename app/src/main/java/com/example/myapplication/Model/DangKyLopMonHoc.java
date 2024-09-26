package com.example.myapplication.Model;

public class DangKyLopMonHoc {
    private String MaSV;
    private String MaMH;
    private String NgayDK;

    public DangKyLopMonHoc() {
    }

    public DangKyLopMonHoc(String maSV, String maMH, String ngayDK) {
        MaSV = maSV;
        MaMH = maMH;
        NgayDK = ngayDK;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public String getMaMH() {
        return MaMH;
    }

    public void setMaMH(String maMH) {
        MaMH = maMH;
    }

    public String getNgayDK() {
        return NgayDK;
    }

    public void setNgayDK(String ngayDK) {
        NgayDK = ngayDK;
    }
}
