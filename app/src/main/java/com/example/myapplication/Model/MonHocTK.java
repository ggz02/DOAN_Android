package com.example.myapplication.Model;

public class MonHocTK {
    private String MaMH;
    private String TenMH;
    private String SoTinChi;
    private String SoSV;

    public MonHocTK() {
    }

    public MonHocTK(String maMH, String tenMH, String soTinChi, String soSV) {
        MaMH = maMH;
        TenMH = tenMH;
        SoTinChi = soTinChi;
        SoSV = soSV;
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

    public String getSoTinChi() {
        return SoTinChi;
    }

    public void setSoTinChi(String soTinChi) {
        SoTinChi = soTinChi;
    }

    public String getSoSV() {
        return SoSV;
    }

    public void setSoSV(String soSV) {
        SoSV = soSV;
    }
}
