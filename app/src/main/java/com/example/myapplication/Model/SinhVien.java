package com.example.myapplication.Model;

public class SinhVien {

    private String MaSV;
    private String HoTen;
    private String NgaySinh;
    private String GioiTinh;
    private String DiaChi;

    private String Email;
    private String MaKhoa;
    private String TaiKhoan;
    private String MatKhau;
    private String DienThoai;

    public SinhVien(){}

    public SinhVien(String maSV,String hoTen,String ngaySinh,String gioiTinh,String diaChi,String email, String maKhoa, String taiKhoan, String matKhau, String dienThoai) {
        this.MaSV = maSV;
        this.HoTen = hoTen;
        this.NgaySinh = ngaySinh;
        this.GioiTinh = gioiTinh;
        this.DiaChi = diaChi;
        this.Email = email;
        this.MaKhoa = maKhoa;
        this.TaiKhoan = taiKhoan;
        this.MatKhau = matKhau;
        this.DienThoai = dienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMaSV(){
        return this.MaSV;
    }

    public void setMaSV(String maSV){
        this.MaSV = maSV;
    }

    public String getHoTen(){
        return this.HoTen;
    }

    public void setHoTen(String hoTen) {
        this.HoTen = hoTen;
    }

    public void setNgaySinh(String ngaySinh) {
        this.NgaySinh = ngaySinh;
    }

    public String getNgaySinh() {
        return this.NgaySinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.GioiTinh = gioiTinh;
    }

    public String getGioiTinh() {
        return this.GioiTinh;
    }

    public void setDiaChi(String diaChi) {
        this.DiaChi = diaChi;
    }

    public String getDiaChi() {
        return this.DiaChi;
    }

    public void setMaKhoa(String maKhoa) {
        this.MaKhoa = maKhoa;
    }

    public String getMaKhoa() {
        return this.MaKhoa;
    }

    public void setDThoai(String dThoai) {
        this.DienThoai = dThoai;
    }

    public String getDThoai() {
        return this.DienThoai;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.TaiKhoan = taiKhoan;
    }

    public String getTaiKhoan() {
        return this.TaiKhoan;
    }

    public void setMatKhau(String matKhau) {
        this.MatKhau = matKhau;
    }

    public String getMatKhau() {
        return this.MatKhau;
    }
}
