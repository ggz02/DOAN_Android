package com.example.myapplication.Model;

public class MonHocKhoa {
    private String MaMH;
    private String TenMH;
    private int HocKi;
    private int SoTinChi;
    private String TenKhoa;
    private String NgayBD;
    private String NgayKT;

    public MonHocKhoa() {
    }

    public MonHocKhoa(String maMH, String tenMH, int hocKi, int soTinChi, String tenKhoa, String ngayBD, String ngayKT) {
        MaMH = maMH;
        TenMH = tenMH;
        HocKi = hocKi;
        SoTinChi = soTinChi;
        TenKhoa = tenKhoa;
        NgayBD = ngayBD;
        NgayKT = ngayKT;
    }

    public String getMaMH() {
        return MaMH;
    }

    public void setMaMH(String maMH) {
        MaMH = maMH;
    }

    public String getTenMH() {
        return TenMH;
    }

    public void setTenMH(String tenMH) {
        TenMH = tenMH;
    }

    public int getHocKi() {
        return HocKi;
    }

    public void setHocKi(int hocKi) {
        HocKi = hocKi;
    }

    public int getSoTinChi() {
        return SoTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        SoTinChi = soTinChi;
    }

    public String getTenKhoa() {
        return TenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        TenKhoa = tenKhoa;
    }

    public String getNgayBD() {
        return NgayBD;
    }

    public void setNgayBD(String ngayBD) {
        NgayBD = ngayBD;
    }

    public String getNgayKT() {
        return NgayKT;
    }

    public void setNgayKT(String ngayKT) {
        NgayKT = ngayKT;
    }

}
