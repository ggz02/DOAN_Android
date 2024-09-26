package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database;
import com.example.myapplication.Model.MonHoc;
import com.example.myapplication.Model.MonHocKhoa;
import com.example.myapplication.Model.MonHocTK;

import java.util.ArrayList;
import java.util.List;

public class MonHocDAO {
    private SQLiteDatabase database;
    private Database databaseHelper;

    public MonHocDAO(Context context) {
        databaseHelper = new Database(context);
        databaseHelper.open();
        database = databaseHelper.getDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public MonHoc getMonHoc(String maMH){
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getDatabase();
        }

        String[] columns = {
                "MaMH", "TenMH", "HocKi", "SoTinChi", "MaKhoa", "NgayBD", "NgayKT"
        };
        String selection = "MaMH = ?";
        String[] selectionArgs = { maMH };

        Cursor cursor = database.query("MonHoc", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String MaMH = cursor.getString(cursor.getColumnIndex("MaMH"));
            @SuppressLint("Range") String tenMH = cursor.getString(cursor.getColumnIndex("TenMH"));
            @SuppressLint("Range") int hocKi = cursor.getInt(cursor.getColumnIndex("HocKi"));
            @SuppressLint("Range") int soTinChi = cursor.getInt(cursor.getColumnIndex("SoTinChi"));
            @SuppressLint("Range") String maKhoa = cursor.getString(cursor.getColumnIndex("MaKhoa"));
            @SuppressLint("Range") String ngayBD = cursor.getString(cursor.getColumnIndex("NgayBD"));
            @SuppressLint("Range") String ngayKT = cursor.getString(cursor.getColumnIndex("NgayKT"));

            MonHoc monHoc = new MonHoc(MaMH, tenMH, hocKi, soTinChi, maKhoa, ngayBD, ngayKT);
            cursor.close();
            return monHoc;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

    public List<MonHocTK> getListMHTK() {
        List<MonHocTK> listMH = new ArrayList<>();

        String selectQuery = "SELECT mh.MaMH, mh.TenMH, mh.SoTinChi,count(sv.MaSV) as 'SoSV'\n " +
                "from SinhVien sv join DangKyLopMonHoc dklmh on sv.MaSV = dklmh.MaSV\n " +
                "join MonHoc mh on mh.MaMH = dklmh.MaMH\n " +
                "join Khoa k on sv.MaKhoa = k.MaKhoa\n " +
                "GROUP BY mh.MaMH, mh.TenMH " +
                "order by count(sv.MaSV) desc ";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maMH = cursor.getString(cursor.getColumnIndex("MaMH"));
                @SuppressLint("Range") String tenMH = cursor.getString(cursor.getColumnIndex("TenMH"));
                @SuppressLint("Range") String soTinChi = String.valueOf(cursor.getInt(cursor.getColumnIndex("SoTinChi")));
                @SuppressLint("Range") String SoSV = String.valueOf(cursor.getInt(cursor.getColumnIndex("SoSV")));

                MonHocTK monHoc = new MonHocTK(maMH,tenMH,soTinChi,SoSV);
                listMH.add(monHoc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listMH;
    }

    public List<MonHoc> getAllMocHoc() {
        List<MonHoc> listMH = new ArrayList<>();

        String selectQuery = "SELECT * FROM MonHoc";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maMH = cursor.getString(cursor.getColumnIndex("MaMH"));
                @SuppressLint("Range") String tenMH = cursor.getString(cursor.getColumnIndex("TenMH"));
                @SuppressLint("Range") int hocKi = Integer.parseInt(cursor.getString(cursor.getColumnIndex("HocKi")));
                @SuppressLint("Range") int soTinChi = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SoTinChi")));
                @SuppressLint("Range") String maKhoa = cursor.getString(cursor.getColumnIndex("MaKhoa"));
                @SuppressLint("Range") String ngayBD = cursor.getString(cursor.getColumnIndex("NgayBD"));
                @SuppressLint("Range") String ngayKT = cursor.getString(cursor.getColumnIndex("NgayKT"));
                MonHoc monHoc = new MonHoc(maMH,tenMH,hocKi,soTinChi,maKhoa,ngayBD,ngayKT);
                listMH.add(monHoc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listMH;
    }

    public ArrayList<MonHoc> getAllMonHocDKHP() {
        ArrayList<MonHoc> monHocList = new ArrayList<>();
        Cursor cursor = database.query("MonHoc", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maMH = cursor.getString(cursor.getColumnIndex("MaMH"));
                @SuppressLint("Range") String tenMH = cursor.getString(cursor.getColumnIndex("TenMH"));
                @SuppressLint("Range") int soTinChi = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SoTinChi")));
                @SuppressLint("Range") String ngayBD = cursor.getString(cursor.getColumnIndex("NgayBD"));
                @SuppressLint("Range") String ngayKT = cursor.getString(cursor.getColumnIndex("NgayKT"));
                MonHoc monHoc = new MonHoc(maMH, tenMH, soTinChi, ngayBD,ngayKT);
                monHocList.add(monHoc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return monHocList;
    }

    public ArrayList<MonHoc> getMonHocByHocKiAndMaKhoa(int hocKi, String maKhoa) {
        ArrayList<MonHoc> monHocList = new ArrayList<>();
        String selection = "HocKi = ? AND MaKhoa = ?";
        String[] selectionArgs = { String.valueOf(hocKi), maKhoa };
        Cursor cursor = null;

        try {
            cursor = database.query("MonHoc", null, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String maMH = cursor.getString(cursor.getColumnIndex("MaMH"));
                    @SuppressLint("Range") String tenMH = cursor.getString(cursor.getColumnIndex("TenMH"));
                    @SuppressLint("Range") int soTinChi = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SoTinChi")));
                    @SuppressLint("Range") String ngayBD = cursor.getString(cursor.getColumnIndex("NgayBD"));
                    @SuppressLint("Range") String ngayKT = cursor.getString(cursor.getColumnIndex("NgayKT"));
                    MonHoc monHoc = new MonHoc(maMH, tenMH, soTinChi, ngayBD, ngayKT);
                    monHocList.add(monHoc);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return monHocList;
    }

    public ArrayList<MonHoc> searchMonHocSVByTenMH(int hocKi, String tenMH, String maKhoa) {
        ArrayList<MonHoc> monHocList = new ArrayList<>();
        String selection = "HocKi = ? AND TenMH LIKE ? AND MaKhoa = ?";
        String likeKeyword = "%" + tenMH + "%";
        String[] selectionArgs = { String.valueOf(hocKi), likeKeyword, maKhoa };
        Cursor cursor = null;

        try {
            cursor = database.query("MonHoc", null, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String maMH = cursor.getString(cursor.getColumnIndex("MaMH"));
                    @SuppressLint("Range") String tenMHResult = cursor.getString(cursor.getColumnIndex("TenMH"));
                    @SuppressLint("Range") int soTinChi = cursor.getInt(cursor.getColumnIndex("SoTinChi"));
                    @SuppressLint("Range") String ngayBD = cursor.getString(cursor.getColumnIndex("NgayBD"));
                    @SuppressLint("Range") String ngayKT = cursor.getString(cursor.getColumnIndex("NgayKT"));
                    MonHoc monHoc = new MonHoc(maMH, tenMHResult, soTinChi, ngayBD, ngayKT);
                    monHocList.add(monHoc);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return monHocList;
    }

    public ArrayList<MonHoc> searchMonHocSVByMaMH(int hocKi, String maMH, String maKhoa) {
        ArrayList<MonHoc> monHocList = new ArrayList<>();
        String selection = "HocKi = ? AND MaMH LIKE ? AND MaKhoa = ?";
        String likeKeyword = "%" + maMH + "%";
        String[] selectionArgs = { String.valueOf(hocKi), likeKeyword, maKhoa };
        Cursor cursor = null;

        try {
            cursor = database.query("MonHoc", null, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String maMHResult = cursor.getString(cursor.getColumnIndex("MaMH"));
                    @SuppressLint("Range") String tenMH = cursor.getString(cursor.getColumnIndex("TenMH"));
                    @SuppressLint("Range") int soTinChi = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SoTinChi")));
                    @SuppressLint("Range") String ngayBD = cursor.getString(cursor.getColumnIndex("NgayBD"));
                    @SuppressLint("Range") String ngayKT = cursor.getString(cursor.getColumnIndex("NgayKT"));
                    MonHoc monHoc = new MonHoc(maMHResult, tenMH, soTinChi, ngayBD, ngayKT);
                    monHocList.add(monHoc);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return monHocList;
    }

    public List<MonHocKhoa> getAllMocHocKhoa() {
        List<MonHocKhoa> listMH = new ArrayList<>();

        String selectQuery = "SELECT * FROM MonHoc mh join Khoa k on mh.MaKhoa = k.MaKhoa";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maMH = cursor.getString(cursor.getColumnIndex("MaMH"));
                @SuppressLint("Range") String tenMH = cursor.getString(cursor.getColumnIndex("TenMH"));
                @SuppressLint("Range") int hocKi = Integer.parseInt(cursor.getString(cursor.getColumnIndex("HocKi")));
                @SuppressLint("Range") int soTinChi = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SoTinChi")));
                @SuppressLint("Range") String tenKhoa = cursor.getString(cursor.getColumnIndex("TenKhoa"));
                @SuppressLint("Range") String ngayBD = cursor.getString(cursor.getColumnIndex("NgayBD"));
                @SuppressLint("Range") String ngayKT = cursor.getString(cursor.getColumnIndex("NgayKT"));
                MonHocKhoa monHoc = new MonHocKhoa(maMH,tenMH,hocKi,soTinChi,tenKhoa,ngayBD,ngayKT);
                listMH.add(monHoc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listMH;
    }

    public void deleteMonHoc(String maMH) {
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getDatabase();
        }

        String whereClause = "MaMH = ?";
        String[] whereArgs = { maMH };

        database.delete("MonHoc", whereClause, whereArgs);
        database.close();
    }

    public void updateMonHoc(MonHoc monHoc) {
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getDatabase();
        }

        ContentValues values = new ContentValues();
        values.put("TenMH", monHoc.getTenMH());
        values.put("HocKi", monHoc.getHocKi());
        values.put("SoTinChi", monHoc.getSoTinChi());
        values.put("NgayBD",monHoc.getNgayBD());
        values.put("NgayKT",monHoc.getNgayKT());
        values.put("MaKhoa", monHoc.getMaKhoa());


        String whereClause = "MaMH = ?";
        String[] whereArgs = { monHoc.getMaMH() };

        database.update("MonHoc", values, whereClause, whereArgs);
        database.close();
    }

    public void addMonHoc(MonHoc monHoc) {
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getDatabase();
        }
        ContentValues values = new ContentValues();
        values.put("MaMH", monHoc.getMaMH());
        values.put("TenMH", monHoc.getTenMH());
        values.put("HocKi", monHoc.getHocKi());
        values.put("SoTinChi", monHoc.getSoTinChi());
        values.put("NgayBD",monHoc.getNgayBD());
        values.put("NgayKT",monHoc.getNgayKT());
        values.put("MaKhoa", monHoc.getMaKhoa());

        database.insert("MonHoc", null, values);
        database.close();
    }
}
