package com.example.myapplication.Model;

public class SinhVienTK {
    private String MaSV;
    private String HoTen;
    private String TenKhoa;
    private String SoTinChi;

    public SinhVienTK() {
    }

    public SinhVienTK(String maSV, String hoTen, String tenKhoa, String soTinChi) {
        MaSV = maSV;
        HoTen = hoTen;
        TenKhoa = tenKhoa;
        SoTinChi = soTinChi;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getTenKhoa() {
        return TenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        TenKhoa = tenKhoa;
    }

    public String getSoTinChi() {
        return SoTinChi;
    }

    public void setSoTinChi(String soTinChi) {
        SoTinChi = soTinChi;
    }
}
