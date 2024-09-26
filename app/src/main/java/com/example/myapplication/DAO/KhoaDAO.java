package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database;
import com.example.myapplication.Model.Khoa;

import java.util.ArrayList;
import java.util.List;

public class KhoaDAO {
    private SQLiteDatabase database;
    private Database databaseHelper;

    public KhoaDAO(Context context) {
        databaseHelper = new Database(context);
        databaseHelper.open();
        database = databaseHelper.getDatabase();
    }

    public List<Khoa> getAllKhoa() {
        List<Khoa> listKhoa = new ArrayList<>();

        String selectQuery = "SELECT * FROM Khoa";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maKhoa = cursor.getString(cursor.getColumnIndex("MaKhoa"));
                @SuppressLint("Range") String tenKhoa = cursor.getString(cursor.getColumnIndex("TenKhoa"));
                Khoa khoa = new Khoa(maKhoa,tenKhoa);
                listKhoa.add(khoa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listKhoa;
    }

    public String getTenKhoaByMaKhoa(String maKhoa) {
        String tenKhoa = null;

        String selectQuery = "SELECT TenKhoa FROM Khoa WHERE MaKhoa = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{maKhoa});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("TenKhoa"));
            tenKhoa = name;
        }
        cursor.close();
        return tenKhoa;
    }
}
