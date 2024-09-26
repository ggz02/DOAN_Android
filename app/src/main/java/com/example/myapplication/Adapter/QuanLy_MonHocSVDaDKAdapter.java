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
import com.example.myapplication.QuanLy_DKMH_CTSV;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QuanLy_MonHocSVDaDKAdapter extends ArrayAdapter<MonHoc> {
    Activity context;
    int idlayout;
    ArrayList<MonHoc> mylist;
    //boolean isCourseCancelled;

    public QuanLy_MonHocSVDaDKAdapter(Activity context, int idlayout, ArrayList<MonHoc> mylist) {
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
        notifyDataSetChanged();

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

        RelativeLayout btnHuyDKHPSV = convertView.findViewById(R.id.btnHuyDKHPSV);

        btnHuyDKHPSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences luuHuydkhp = context.getSharedPreferences("HuyDangKyHocPhan", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = luuHuydkhp.edit();
                Set<String> huyHPdachon = luuHuydkhp.getStringSet("HocPhanDaChon", new HashSet<>());
                String courseDetails = monHoc.getMaMH() + ";" + monHoc.getTenMH() + ";" + monHoc.getSoTinChi() + ";" + monHoc.getNgayBD() + ";" + monHoc.getNgayKT();
                huyHPdachon.add(courseDetails);
                editor.putStringSet("HocPhanDaChon", huyHPdachon);
                editor.apply();

                new CustomToast(context, "Đã thêm học phần '" + monHoc.getTenMH() + "' vào danh sách hủy học phần","Success");
                //isCourseCancelled = true;
                disableSpinnerItems();
//                if (onCourseCancelledListener != null) {
//                    onCourseCancelledListener.onCourseCancelled();
//                }
            }
        });

        return convertView;
    }

    private void disableSpinnerItems() {
        if (context instanceof QuanLy_DKMH_CTSV) {
            ((QuanLy_DKMH_CTSV) context).disableSpinnerItems();
        }
    }
}