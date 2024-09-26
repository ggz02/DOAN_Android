package com.example.myapplication.Adapter;

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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import com.example.myapplication.CustomToast;
import com.example.myapplication.HocPhanDaDangKy;
import com.example.myapplication.Model.MonHoc;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MonHocDaDangKyAdapter extends ArrayAdapter<MonHoc> {
    FragmentActivity context;
    int idlayout;
    ArrayList<MonHoc> mylist;
    SharedPreferences luutotalSTC;
    int totalSoTinChi;

    public MonHocDaDangKyAdapter(FragmentActivity context, int idlayout, ArrayList<MonHoc> mylist) {
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

        RelativeLayout btnHuyDKHP = convertView.findViewById(R.id.btnHuyDKHP);

        btnHuyDKHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the course from the list
                mylist.remove(position);
                notifyDataSetChanged();

                // Update shared preferences
                SharedPreferences luudkhp = context.getSharedPreferences("DangKyHocPhan", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = luudkhp.edit();
                Set<String> selectedCourses = new HashSet<>(luudkhp.getStringSet("SelectedCourses", new HashSet<>()));

                String courseToRemove = monHoc.getMaMH() + ";" + monHoc.getTenMH() + ";" + monHoc.getSoTinChi() + ";" + monHoc.getNgayBD() + ";" + monHoc.getNgayKT();
                selectedCourses.remove(courseToRemove);

                editor.putStringSet("SelectedCourses", selectedCourses);
                editor.apply();

                luutotalSTC = context.getSharedPreferences("totalSTC", Context.MODE_PRIVATE);
                totalSoTinChi = luutotalSTC.getInt("totalSoTinChi", 0);
                //Tính lại số tín chỉ đã đăng ký
                totalSoTinChi = totalSoTinChi - monHoc.getSoTinChi();
                editor = luutotalSTC.edit();
                editor.putInt("totalSoTinChi", totalSoTinChi);
                editor.apply();

                // Cập nhật totalSoTinChi trong Fragment
                FragmentManager fragmentManager = context.getSupportFragmentManager();
                HocPhanDaDangKy fragment = (HocPhanDaDangKy) fragmentManager.findFragmentById(R.id.content_frame);
                if (fragment != null) {
                    fragment.updateTotalSoTinChi(totalSoTinChi);
                }


                new CustomToast(context, "Đã hủy đăng ký học phần '" + monHoc.getTenMH() + "'","Success");
            }
        });

        return convertView;
    }
}