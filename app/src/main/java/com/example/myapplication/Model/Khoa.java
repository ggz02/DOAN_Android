package com.example.myapplication.Model;

public class Khoa {
    private String MaKhoa;
    private String TenKhoa;
    public Khoa(){}

    public Khoa(String maKhoa, String tenKhoa) {
        this.MaKhoa = maKhoa;
        this.TenKhoa = tenKhoa;
    }

    public String getMaKhoa() {return this.MaKhoa;}

    public void setMaKhoa(String maKhoa) {
        this.MaKhoa = maKhoa;
    }

    public String getTenKhoa(){return this.TenKhoa;}

    public void setTenKhoa(String tenKhoa){
        this.TenKhoa = tenKhoa;
    }
}
