package com.example.myapplication.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.myapplication.Model.SinhVien;
import com.example.myapplication.R;

import java.util.List;

public class QuanLy_DangKyMonHocAdapter extends ArrayAdapter<SinhVien> {
    Activity context;
    int idlayout;
    List<SinhVien> sinhVienList;

    public QuanLy_DangKyMonHocAdapter(Activity context, int idlayout, List<SinhVien> sinhVienList) {
        super(context, idlayout, sinhVienList);
        this.context = context;
        this.idlayout = idlayout;
        this.sinhVienList = sinhVienList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(idlayout, null);

        SinhVien sinhVien = sinhVienList.get(position);

        TextView tvMaSV = convertView.findViewById(R.id.tvMaSV);
        TextView tvHoTen = convertView.findViewById(R.id.tvHoTen);
        TextView tvMaKhoa = convertView.findViewById(R.id.tvMaKhoa);

        tvMaSV.setText(sinhVien.getMaSV());
        tvHoTen.setText(sinhVien.getHoTen());
        tvMaKhoa.setText(sinhVien.getMaKhoa());

        return convertView;
    }
}
