package com.example.myapplication.Model;

public class HocKiTK {

    private String HocKi;
    private String SoLuongSV;
    private String SoMonHoc;

    public HocKiTK() {
    }

    public HocKiTK(String hocKi, String soLuongSV, String soMonHoc) {
        HocKi = hocKi;
        SoLuongSV = soLuongSV;
        SoMonHoc = soMonHoc;
    }

    public String getHocKi() {
        return HocKi;
    }

    public void setHocKi(String hocKi) {
        HocKi = hocKi;
    }

    public String getSoLuongSV() {
        return SoLuongSV;
    }

    public void setSoLuongSV(String soLuongSV) {
        SoLuongSV = soLuongSV;
    }

    public String getSoMonHoc() {
        return SoMonHoc;
    }

    public void setSoMonHoc(String soMonHoc) {
        SoMonHoc = soMonHoc;
    }
}
