package com.example.myapplication.Model;

public class MonHoc {
    private String MaMH;
    private String TenMH;
    private int HocKi;
    private int SoTinChi;
    private String MaKhoa;
    private String NgayBD;
    private String NgayKT;

    public MonHoc() {
    }

    public MonHoc(String maMonHoc, String tenMonHoc, int soTinChi, String ngayBD, String ngayKT) {
        MaMH = maMonHoc;
        TenMH = tenMonHoc;
        SoTinChi = soTinChi;
        NgayBD = ngayBD;
        NgayKT = ngayKT;
    }



    public MonHoc(String maMH, String tenMH, int hocKi, int soTinChi, String maKhoa, String ngayBD, String ngayKT) {
        MaMH = maMH;
        TenMH = tenMH;
        HocKi = hocKi;
        SoTinChi = soTinChi;
        MaKhoa = maKhoa;
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

    public String getMaKhoa() {
        return MaKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        MaKhoa = maKhoa;
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
