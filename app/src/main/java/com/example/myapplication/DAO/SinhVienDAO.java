package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database;
import com.example.myapplication.Model.SinhVien;
import com.example.myapplication.Model.SinhVienKhoa;
import com.example.myapplication.Model.SinhVienTK;

import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO {

    private SQLiteDatabase database;
    private Database databaseHelper;

    public SinhVienDAO(Context context) {
        databaseHelper = new Database(context);
        databaseHelper.open();
        database = databaseHelper.getDatabase();
    }

    public String getEmailByMaSV(String maSV) {
        String email = null;
        String selectQuery = "SELECT Email FROM SinhVien WHERE MaSV = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{maSV});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String emailFromCursor = cursor.getString(cursor.getColumnIndex("Email"));
            email = emailFromCursor;
        }
        cursor.close();
        database.close();
        return email;
    }

    public List<SinhVien> getAllSinhVien() {
        List<SinhVien> listSV = new ArrayList<>();

        String selectQuery = "SELECT * FROM SinhVien";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maSV = cursor.getString(cursor.getColumnIndex("MaSV"));
                @SuppressLint("Range") String tenSV = cursor.getString(cursor.getColumnIndex("HoTen"));
                @SuppressLint("Range") String ngaySinh = cursor.getString(cursor.getColumnIndex("NgaySinh"));
                @SuppressLint("Range") String gioiTinh = cursor.getString(cursor.getColumnIndex("GioiTinh"));
                @SuppressLint("Range") String diaChi = cursor.getString(cursor.getColumnIndex("DiaChi"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("Email"));
                @SuppressLint("Range") String maKhoa = cursor.getString(cursor.getColumnIndex("MaKhoa"));
                @SuppressLint("Range") String dThoai = cursor.getString(cursor.getColumnIndex("DienThoai"));
                @SuppressLint("Range") String taiKhoan = cursor.getString(cursor.getColumnIndex("TaiKhoan"));
                @SuppressLint("Range") String matKhau = cursor.getString(cursor.getColumnIndex("MatKhau"));
                SinhVien sinhVien = new SinhVien(maSV,tenSV,ngaySinh,gioiTinh,diaChi,email,maKhoa,taiKhoan,matKhau,dThoai);
                listSV.add(sinhVien);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listSV;
    }

    public List<SinhVienTK> getListSVTK() {
        List<SinhVienTK> listSV = new ArrayList<>();

        String selectQuery = "SELECT sv.MaSV, sv.HoTen, k.TenKhoa, sum(mh.SoTinChi) as 'SoTinChi' \n" +
                "from SinhVien sv join DangKyLopMonHoc dklmh on sv.MaSV = dklmh.MaSV \n" +
                "join MonHoc mh on mh.MaMH = dklmh.MaMH \n" +
                "join Khoa k on sv.MaKhoa = k.MaKhoa \n" +
                "GROUP BY sv.MaSV, sv.HoTen, k.TenKhoa ";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maSV = cursor.getString(cursor.getColumnIndex("MaSV"));
                @SuppressLint("Range") String tenSV = cursor.getString(cursor.getColumnIndex("HoTen"));
                @SuppressLint("Range") String tenKhoa = cursor.getString(cursor.getColumnIndex("TenKhoa"));
                @SuppressLint("Range") String soTinChi = String.valueOf(cursor.getInt(cursor.getColumnIndex("SoTinChi")));

                SinhVienTK sinhVien = new SinhVienTK(maSV,tenSV,tenKhoa,soTinChi);
                listSV.add(sinhVien);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listSV;
    }

    public SinhVien getSinhVien(String masv){
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getDatabase();
        }

        String[] columns = {
                "MaSV", "HoTen", "NgaySinh", "GioiTinh", "DiaChi", "Email", "MaKhoa", "TaiKhoan", "MatKhau", "DienThoai"
        };
        String selection = "MaSV = ?";
        String[] selectionArgs = { masv };

        Cursor cursor = database.query("SinhVien", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String maSinhVien = cursor.getString(cursor.getColumnIndex("MaSV"));
            @SuppressLint("Range") String hoTen = cursor.getString(cursor.getColumnIndex("HoTen"));
            @SuppressLint("Range") String ngaySinh = cursor.getString(cursor.getColumnIndex("NgaySinh"));
            @SuppressLint("Range") String gioiTinh = cursor.getString(cursor.getColumnIndex("GioiTinh"));
            @SuppressLint("Range") String diaChi = cursor.getString(cursor.getColumnIndex("DiaChi"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("Email"));
            @SuppressLint("Range") String maKhoa = cursor.getString(cursor.getColumnIndex("MaKhoa"));
            @SuppressLint("Range") String taiKhoan = cursor.getString(cursor.getColumnIndex("TaiKhoan"));
            @SuppressLint("Range") String matKhau = cursor.getString(cursor.getColumnIndex("MatKhau"));
            @SuppressLint("Range") String dienThoai = cursor.getString(cursor.getColumnIndex("DienThoai"));

            SinhVien sinhVien = new SinhVien(maSinhVien, hoTen, ngaySinh, gioiTinh, diaChi, email, maKhoa, taiKhoan, matKhau, dienThoai);
            cursor.close();
            return sinhVien;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

    public List<SinhVienKhoa> getAllSinhVienKhoa() {
        List<SinhVienKhoa> listSV = new ArrayList<>();

        String selectQuery = "SELECT * FROM SinhVien sv JOIN Khoa k on sv.MaKhoa = k.MaKhoa";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maSV = cursor.getString(cursor.getColumnIndex("MaSV"));
                @SuppressLint("Range") String tenSV = cursor.getString(cursor.getColumnIndex("HoTen"));
                @SuppressLint("Range") String ngaySinh = cursor.getString(cursor.getColumnIndex("NgaySinh"));
                @SuppressLint("Range") String gioiTinh = cursor.getString(cursor.getColumnIndex("GioiTinh"));
                @SuppressLint("Range") String diaChi = cursor.getString(cursor.getColumnIndex("DiaChi"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("Email"));
                @SuppressLint("Range") String tenKhoa = cursor.getString(cursor.getColumnIndex("TenKhoa"));
                @SuppressLint("Range") String dThoai = cursor.getString(cursor.getColumnIndex("DienThoai"));
                @SuppressLint("Range") String taiKhoan = cursor.getString(cursor.getColumnIndex("TaiKhoan"));
                @SuppressLint("Range") String matKhau = cursor.getString(cursor.getColumnIndex("MatKhau"));
                SinhVienKhoa sinhVien = new SinhVienKhoa(maSV,tenSV,ngaySinh,gioiTinh,diaChi,email,tenKhoa,taiKhoan,matKhau,dThoai);
                listSV.add(sinhVien);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listSV;
    }

    public void deleteSinhVien(String maSV) {
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getDatabase();
        }

        String whereClause = "MaSV = ?";
        String[] whereArgs = { maSV };

        database.delete("SinhVien", whereClause, whereArgs);
        database.close();
    }

    public void updateSinhVien(SinhVien sinhVien) {
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getDatabase();
        }

        ContentValues values = new ContentValues();
        values.put("HoTen", sinhVien.getHoTen());
        values.put("NgaySinh", sinhVien.getNgaySinh());
        values.put("GioiTinh", sinhVien.getGioiTinh());
        values.put("DiaChi", sinhVien.getDiaChi());
        values.put("Email", sinhVien.getEmail());
        values.put("MaKhoa", sinhVien.getMaKhoa());
        values.put("TaiKhoan", sinhVien.getTaiKhoan());
        values.put("MatKhau", sinhVien.getMatKhau());
        values.put("DienThoai", sinhVien.getDThoai());

        String whereClause = "MaSV = ?";
        String[] whereArgs = { sinhVien.getMaSV() };

        database.update("SinhVien", values, whereClause, whereArgs);
        database.close();
    }

    public void addSinhVien(SinhVien sinhVien) {
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getDatabase();
        }
        ContentValues values = new ContentValues();
        values.put("MaSV", sinhVien.getMaSV());
        values.put("HoTen", sinhVien.getHoTen());
        values.put("NgaySinh", sinhVien.getNgaySinh());
        values.put("GioiTinh", sinhVien.getGioiTinh());
        values.put("DiaChi", sinhVien.getDiaChi());
        values.put("Email",sinhVien.getEmail());
        values.put("MaKhoa", sinhVien.getMaKhoa());
        values.put("TaiKhoan", sinhVien.getTaiKhoan());
        values.put("MatKhau", sinhVien.getMatKhau());
        values.put("DienThoai", sinhVien.getDThoai());


        database.insert("SinhVien", null, values);
        database.close();
    }
}
