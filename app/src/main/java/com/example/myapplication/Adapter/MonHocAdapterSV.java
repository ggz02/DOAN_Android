package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.CustomToast;
import com.example.myapplication.Model.MonHoc;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MonHocAdapterSV extends ArrayAdapter<MonHoc> {
    Activity context;
    int idlayout;
    ArrayList<MonHoc> mylist;

    public MonHocAdapterSV(Activity context, int idlayout, ArrayList<MonHoc> mylist) {
        super(context, idlayout, mylist);
        this.context = context;
        this.idlayout = idlayout;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(idlayout, null);
        MonHoc monHoc = mylist.get(position);

        TextView txtMaMH = convertView.findViewById(R.id.txtMaMH);
        TextView txtTenMH = convertView.findViewById(R.id.txtTenMH);
        TextView txtSTC = convertView.findViewById(R.id.txtSTC);
        TextView txtNgayBD = convertView.findViewById(R.id.txtNgayBD);
        TextView txtNgayKT = convertView.findViewById(R.id.txtNgayKT);

        txtMaMH.setText(monHoc.getMaMH());
        txtTenMH.setText(monHoc.getTenMH());
        txtSTC.setText(String.valueOf(monHoc.getSoTinChi()));
        txtNgayBD.setText(monHoc.getNgayBD());
        txtNgayKT.setText(monHoc.getNgayKT());

        RelativeLayout btnDKHP = convertView.findViewById(R.id.btnDKHP);

        btnDKHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences luudkhp = context.getSharedPreferences("DangKyHocPhan", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = luudkhp.edit();
                Set<String> selectedCourses = luudkhp.getStringSet("SelectedCourses", new HashSet<>());
                String courseDetails = monHoc.getMaMH() + ";" + monHoc.getTenMH() + ";" + monHoc.getSoTinChi() + ";" + monHoc.getNgayBD() + ";" + monHoc.getNgayKT();
                selectedCourses.add(courseDetails);
                editor.putStringSet("SelectedCourses", selectedCourses);
                editor.apply();

                new CustomToast(context, "Đã thêm học phần '" + monHoc.getTenMH() + "' vào danh sách đăng ký","Success");
            }
        });

        return convertView;
    }
}
