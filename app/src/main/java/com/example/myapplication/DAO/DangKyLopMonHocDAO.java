package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database;
import com.example.myapplication.Model.DangKyLopMonHoc;
import com.example.myapplication.Model.HocKiTK;
import com.example.myapplication.Model.MonHoc;
import com.example.myapplication.Model.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class DangKyLopMonHocDAO {

    private SQLiteDatabase database;
    private Database databaseHelper;

    public DangKyLopMonHocDAO(Context context) {
        databaseHelper = new Database(context);
        databaseHelper.open();
        database = databaseHelper.getDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public long insertDangKyLopMonHoc(DangKyLopMonHoc dangKyLopMonHoc) {
        ContentValues values = new ContentValues();
        values.put("MaSV", dangKyLopMonHoc.getMaSV());
        values.put("MaMH", dangKyLopMonHoc.getMaMH());
        values.put("NgayDK", dangKyLopMonHoc.getNgayDK());

        long kq = database.insert("DangKyLopMonHoc", null, values);
        //database.close();

        if (kq <= 0)
        {
            return -1; //insert thất bại
        }
        return 1; //insert thành công
    }

    public boolean SVdadangkyMH(String maSV, String maMH) {
        String query = "SELECT 1 FROM DangKyLopMonHoc WHERE MaSV = ? AND MaMH = ?";
        Cursor cursor = database.rawQuery(query, new String[]{maSV, maMH});

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        return exists;
    }

    public List<SinhVien> getSinhVienDaDangKy() {
        List<SinhVien> listSV = new ArrayList<>();

        String query = "SELECT DISTINCT SinhVien.* FROM SinhVien INNER JOIN DangKyLopMonHoc ON SinhVien.MaSV = DangKyLopMonHoc.MaSV";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maSV = cursor.getString(cursor.getColumnIndex("MaSV"));
                @SuppressLint("Range") String tenSV = cursor.getString(cursor.getColumnIndex("HoTen"));
                @SuppressLint("Range") String ngaySinh = cursor.getString(cursor.getColumnIndex("NgaySinh"));
                @SuppressLint("Range") String gioiTinh = cursor.getString(cursor.getColumnIndex("GioiTinh"));
                @SuppressLint("Range") String diaChi = cursor.getString(cursor.getColumnIndex("DiaChi"));
                @SuppressLint("Range") String maKhoa = cursor.getString(cursor.getColumnIndex("MaKhoa"));
                @SuppressLint("Range") String dThoai = cursor.getString(cursor.getColumnIndex("DienThoai"));
                @SuppressLint("Range") String taiKhoan = cursor.getString(cursor.getColumnIndex("TaiKhoan"));
                @SuppressLint("Range") String matKhau = cursor.getString(cursor.getColumnIndex("MatKhau"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("Email"));
                SinhVien sinhVien = new SinhVien(maSV, tenSV, ngaySinh, gioiTinh, diaChi,email, maKhoa, taiKhoan, matKhau, dThoai);
                listSV.add(sinhVien);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listSV;
    }
    public List<SinhVien> getSinhVienChuaDangKy() {
        List<SinhVien> listSV = new ArrayList<>();

        String query = "SELECT * FROM SinhVien WHERE MaSV NOT IN (SELECT MaSV FROM DangKyLopMonHoc)";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maSV = cursor.getString(cursor.getColumnIndex("MaSV"));
                @SuppressLint("Range") String tenSV = cursor.getString(cursor.getColumnIndex("HoTen"));
                @SuppressLint("Range") String ngaySinh = cursor.getString(cursor.getColumnIndex("NgaySinh"));
                @SuppressLint("Range") String gioiTinh = cursor.getString(cursor.getColumnIndex("GioiTinh"));
                @SuppressLint("Range") String diaChi = cursor.getString(cursor.getColumnIndex("DiaChi"));
                @SuppressLint("Range") String maKhoa = cursor.getString(cursor.getColumnIndex("MaKhoa"));
                @SuppressLint("Range") String dThoai = cursor.getString(cursor.getColumnIndex("DienThoai"));
                @SuppressLint("Range") String taiKhoan = cursor.getString(cursor.getColumnIndex("TaiKhoan"));
                @SuppressLint("Range") String matKhau = cursor.getString(cursor.getColumnIndex("MatKhau"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("Email"));
                SinhVien sinhVien = new SinhVien(maSV, tenSV, ngaySinh, gioiTinh, diaChi,email, maKhoa, taiKhoan, matKhau, dThoai);
                listSV.add(sinhVien);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listSV;
    }

    public List<MonHoc> getMonHocDaDangKy(String maSV, int hocKy) {
        List<MonHoc> listMonHoc = new ArrayList<>();

        String query = "SELECT MonHoc.* FROM MonHoc INNER JOIN DangKyLopMonHoc ON MonHoc.MaMH = DangKyLopMonHoc.MaMH " +
                "WHERE DangKyLopMonHoc.MaSV = ? AND MonHoc.HocKi = ?";
        Cursor cursor = database.rawQuery(query, new String[]{maSV, String.valueOf(hocKy)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maMH = cursor.getString(cursor.getColumnIndex("MaMH"));
                @SuppressLint("Range") String tenMH = cursor.getString(cursor.getColumnIndex("TenMH"));
                @SuppressLint("Range") int hocKi = cursor.getInt(cursor.getColumnIndex("HocKi"));
                @SuppressLint("Range") int soTinChi = cursor.getInt(cursor.getColumnIndex("SoTinChi"));
                @SuppressLint("Range") String maKhoa = cursor.getString(cursor.getColumnIndex("MaKhoa"));
                @SuppressLint("Range") String ngayBD = cursor.getString(cursor.getColumnIndex("NgayBD"));
                @SuppressLint("Range") String ngayKT = cursor.getString(cursor.getColumnIndex("NgayKT"));
                MonHoc monHoc = new MonHoc(maMH, tenMH, hocKi, soTinChi, maKhoa, ngayBD, ngayKT);
                listMonHoc.add(monHoc);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listMonHoc;
    }
    public int deleteDangKyLopMonHoc(String maSV, String maMH) {
        return database.delete("DangKyLopMonHoc", "MaSV = ? AND MaMH = ?", new String[]{maSV, maMH});
    }

    public List<DangKyLopMonHoc> getAllDangKyLopMonHoc() {
        List<DangKyLopMonHoc> listDangKyLopMonHoc = new ArrayList<>();

        String selectQuery = "SELECT * FROM DangKyLopMonHoc"; // Điều chỉnh tên bảng nếu cần
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maSV = cursor.getString(cursor.getColumnIndex("MaSV"));
                @SuppressLint("Range") String maMH = cursor.getString(cursor.getColumnIndex("MaMH"));
                @SuppressLint("Range") String ngayDK = cursor.getString(cursor.getColumnIndex("NgayDK"));
                DangKyLopMonHoc dangKyLopMonHoc = new DangKyLopMonHoc(maSV,maMH,ngayDK);
                listDangKyLopMonHoc.add(dangKyLopMonHoc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listDangKyLopMonHoc;
    }

    public int getSoTinChiSVDaDangKy(String maSV, int hocKy) {
        int tongSoTinChi = 0;

        String query = "SELECT SUM(MonHoc.SoTinChi) as TongTinChi FROM MonHoc " +
                "INNER JOIN DangKyLopMonHoc ON MonHoc.MaMH = DangKyLopMonHoc.MaMH " +
                "WHERE DangKyLopMonHoc.MaSV = ? AND MonHoc.HocKi = ?";
        Cursor cursor = database.rawQuery(query, new String[]{maSV, String.valueOf(hocKy)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int tongTinChi = cursor.getInt(cursor.getColumnIndex("TongTinChi"));
            tongSoTinChi = tongTinChi;
        }

        cursor.close();
        return tongSoTinChi;
    }

    public List<HocKiTK> getListHKTK() {
        List<HocKiTK> listHK = new ArrayList<>();

        String selectQuery = "SELECT mh.HocKi, COUNT(DISTINCT sv.MaSV) AS SoLuongSinhVien, COUNT(Distinct dklmh.MaMH) as 'SoMonHoc'\n " +
                "FROM SinhVien sv \n " +
                "JOIN DangKyLopMonHoc dklmh ON sv.MaSV = dklmh.MaSV\n " +
                "JOIN MonHoc mh ON mh.MaMH = dklmh.MaMH\n " +
                "JOIN Khoa k ON sv.MaKhoa = k.MaKhoa\n " +
                "GROUP BY mh.HocKi ";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String HocKi = String.valueOf(cursor.getInt(cursor.getColumnIndex("HocKi")));
                @SuppressLint("Range") String SoLuongSV = String.valueOf(cursor.getInt(cursor.getColumnIndex("SoLuongSinhVien")));
                @SuppressLint("Range") String SoMonHoc = String.valueOf(cursor.getInt(cursor.getColumnIndex("SoMonHoc")));

                HocKiTK hocKi = new HocKiTK(HocKi,SoLuongSV,SoMonHoc);
                listHK.add(hocKi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listHK;
    }

}
