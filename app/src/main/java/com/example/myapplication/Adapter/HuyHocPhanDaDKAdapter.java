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
import com.example.myapplication.HuyHocPhanDaDK;
import com.example.myapplication.Model.MonHoc;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HuyHocPhanDaDKAdapter extends ArrayAdapter<MonHoc> {
    FragmentActivity context;
    int idlayout;
    ArrayList<MonHoc> mylist;
    SharedPreferences luutongSTChuy;
    int tongSTChuy;

    public HuyHocPhanDaDKAdapter(FragmentActivity context, int idlayout, ArrayList<MonHoc> mylist) {
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

        RelativeLayout btnSVXoaHuyDKHPSV = convertView.findViewById(R.id.btnSVXoaHuyDKHPSV);

        btnSVXoaHuyDKHPSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylist.remove(position);
                notifyDataSetChanged();
                SharedPreferences luuSVhuydkhp = context.getSharedPreferences("SVHuyDangKyHocPhan", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = luuSVhuydkhp.edit();
                Set<String> huyDKHP = new HashSet<>(luuSVhuydkhp.getStringSet("huyDKHP", new HashSet<>()));
                String xoaHP = monHoc.getMaMH() + ";" + monHoc.getTenMH() + ";" + monHoc.getSoTinChi() + ";" + monHoc.getNgayBD() + ";" + monHoc.getNgayKT();
                huyDKHP.remove(xoaHP);

                editor.putStringSet("huyDKHP", huyDKHP);
                editor.apply();

                luutongSTChuy = context.getSharedPreferences("tongSTChuy", Context.MODE_PRIVATE);
                tongSTChuy = luutongSTChuy.getInt("tongSTChuy", 0);
                //Tính lại số tín chỉ đã hủy đăng ký
                tongSTChuy = tongSTChuy - monHoc.getSoTinChi();
                editor = luutongSTChuy.edit();
                editor.putInt("tongSTChuy", tongSTChuy);
                editor.apply();

                // Cập nhật totalSoTinChi trong Activity
                FragmentManager fragmentManager = context.getSupportFragmentManager();
                HuyHocPhanDaDK fragment = (HuyHocPhanDaDK) fragmentManager.findFragmentById(R.id.content_frame);
                if (fragment != null) {
                    fragment.updateTotalSoTinChi(tongSTChuy);
                }

                new CustomToast(context, "Không hủy học phần '" + monHoc.getTenMH() + "'","Fail");
            }
        });

        return convertView;
    }
}