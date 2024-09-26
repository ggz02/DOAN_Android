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

import com.example.myapplication.CustomToast;
import com.example.myapplication.Model.MonHoc;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LichSuDKHPAdapter extends ArrayAdapter<MonHoc> {
    FragmentActivity context;
    int idlayout;
    ArrayList<MonHoc> mylist;
    private int hocKyhientai;
    private int trongThoigianDKHP_Lai;
    private String maSV;


    public LichSuDKHPAdapter(FragmentActivity context, int idlayout, ArrayList<MonHoc> mylist) {
        super(context, idlayout, mylist);
        this.context = context;
        this.idlayout = idlayout;
        this.mylist = mylist;
        SharedPreferences checktgDKHP = context.getSharedPreferences("checktgDKHP", Context.MODE_PRIVATE);
        this.hocKyhientai = checktgDKHP.getInt("trongTGDKHP", 0);
        SharedPreferences checktgDKHP_Lai =  context.getSharedPreferences("checktgDKHP_Lai", Context.MODE_PRIVATE);
        this.trongThoigianDKHP_Lai = checktgDKHP_Lai.getInt("trongThoigianDKHP_Lai", 0);
        SharedPreferences luuSV = context.getSharedPreferences("SV_info", Context.MODE_PRIVATE);
        this.maSV = luuSV.getString("MaSV", "No MaSV");
    }
    private boolean checkSV_dcDKLai(String maSV) {
        SharedPreferences luuSVdcDKlai = context.getSharedPreferences("luuSVdcDKlai", Context.MODE_PRIVATE);
        Set<String> maSVSet = luuSVdcDKlai.getStringSet("MaSVSet", new HashSet<>());

        return maSVSet.contains(maSV);
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

        RelativeLayout btnHuyLSDKHP = convertView.findViewById(R.id.btnHuyLSDKHP);

        if (monHoc.getHocKi() == hocKyhientai || monHoc.getHocKi() == trongThoigianDKHP_Lai && checkSV_dcDKLai(maSV)) {
            btnHuyLSDKHP.setVisibility(View.VISIBLE);
        } else {
            btnHuyLSDKHP.setVisibility(View.GONE);
        }

        btnHuyLSDKHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences luuSVhuydkhp = context.getSharedPreferences("SVHuyDangKyHocPhan", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = luuSVhuydkhp.edit();

                Set<String> huyDKHP = new HashSet<>(luuSVhuydkhp.getStringSet("huyDKHP", new HashSet<>()));
                String xoaHP = monHoc.getMaMH() + ";" + monHoc.getTenMH() + ";" + monHoc.getSoTinChi() + ";" + monHoc.getNgayBD() + ";" + monHoc.getNgayKT();
                huyDKHP.add(xoaHP);

                editor.putStringSet("huyDKHP", huyDKHP);
                editor.apply();

                new CustomToast(context, "Đã thêm học phần '" + monHoc.getTenMH() + "' vào danh sách hủy học phần","Success");
            }
        });

        return convertView;
    }
}
