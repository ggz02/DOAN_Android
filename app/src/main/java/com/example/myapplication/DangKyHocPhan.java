package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.myapplication.Adapter.MonHocAdapterSV;
import com.example.myapplication.DAO.MonHocDAO;
import com.example.myapplication.Model.MonHoc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DangKyHocPhan extends Fragment {
    ListView lview;
    ArrayList<MonHoc> mylist;
    MonHocAdapterSV myadapter;
    MonHocDAO monHocDAO;
    String maKhoa;
    RadioGroup radioGroup;
    FloatingActionButton fab;
    RadioButton btnHK1;
    RadioButton btnHK2;
    RadioButton btnHK3;
    TextView txtChuaDenHan;
    TextView txtTGDky;
    TextView tvThoiGianDK;
    int trongThoigianDKHP_Lai;
    String maSV;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dkhp, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);
        btnHK1 = view.findViewById(R.id.btnHK1);
        btnHK2 = view.findViewById(R.id.btnHK2);
        btnHK3 = view.findViewById(R.id.btnHK3);
        txtChuaDenHan=view.findViewById(R.id.txtChuaDenHan);
        fab = view.findViewById(R.id.fab);
        txtTGDky=view.findViewById(R.id.txtTGDky);
        tvThoiGianDK=view.findViewById(R.id.tvThoiGianDK);

        monHocDAO = new MonHocDAO(getActivity());

        SharedPreferences luuSV = getActivity().getSharedPreferences("SV_info", Context.MODE_PRIVATE);
        maKhoa = luuSV.getString("MaKhoa", "");

        lview = view.findViewById(R.id.lv);
        mylist = new ArrayList<>();

        myadapter = new MonHocAdapterSV(getActivity(), R.layout.layout_dkhp_item, mylist);
        lview.setAdapter(myadapter);

        SharedPreferences checktgDKHP = getActivity().getSharedPreferences("checktgDKHP", Context.MODE_PRIVATE);
        int trongTGDKHP = checktgDKHP.getInt("trongTGDKHP", 0);
        SharedPreferences checktgDKHP_Lai =  getActivity().getSharedPreferences("checktgDKHP_Lai", Context.MODE_PRIVATE);
        trongThoigianDKHP_Lai = checktgDKHP_Lai.getInt("trongThoigianDKHP_Lai", 0);

        if(trongTGDKHP > 0){
            switch (trongTGDKHP) {
                case 1:
                    radioGroup.check(R.id.btnHK1);
                    updateListView(1);
                    btnHK2.setVisibility(View.GONE);
                    btnHK3.setVisibility(View.GONE);
                    new CustomToast(getActivity(), "Danh sách các học phần Học kỳ 1","Normal");
                    txtChuaDenHan.setVisibility(View.GONE);
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK1_REG_START, CaiDatThoiGian.HK1_REG_END));
                    fab.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    radioGroup.check(R.id.btnHK2);
                    updateListView(2);
                    btnHK1.setVisibility(View.GONE);
                    btnHK3.setVisibility(View.GONE);
                    new CustomToast(getActivity(), "Danh sách các học phần Học kỳ 2","Normal");
                    txtChuaDenHan.setVisibility(View.GONE);
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK2_REG_START, CaiDatThoiGian.HK2_REG_END));
                    fab.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    radioGroup.check(R.id.btnHK3);
                    updateListView(3);
                    btnHK2.setVisibility(View.GONE);
                    btnHK1.setVisibility(View.GONE);
                    new CustomToast(getActivity(), "Danh sách các học phần Học kỳ 3","Normal");
                    txtChuaDenHan.setVisibility(View.GONE);
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK3_REG_START, CaiDatThoiGian.HK3_REG_END));
                    fab.setVisibility(View.VISIBLE);
                    break;
                default:
                    btnHK1.setVisibility(View.GONE);
                    btnHK2.setVisibility(View.GONE);
                    btnHK3.setVisibility(View.GONE);
                    txtChuaDenHan.setVisibility(View.VISIBLE);
                    tvThoiGianDK.setVisibility(View.GONE);
                    txtTGDky.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                    break;
            }
        } else if(trongThoigianDKHP_Lai > 0){
            maSV = luuSV.getString("MaSV", "No MaSV");
            boolean duocPhepDKLai = checkSV_dcDKLai(maSV);

            if (duocPhepDKLai) {
                if(trongThoigianDKHP_Lai == 1){
                    btnHK1.setVisibility(View.VISIBLE);
                    radioGroup.check(R.id.btnHK1);
                    updateListView(1);
                    btnHK2.setVisibility(View.GONE);
                    btnHK3.setVisibility(View.GONE);
                    new CustomToast(getActivity(), "Danh sách các học phần Học kỳ 1","Normal");
                    txtChuaDenHan.setVisibility(View.GONE);
                    tvThoiGianDK.setText("Thời gian đăng ký lại: ");
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK1_REMIND_START, CaiDatThoiGian.HK1_REMIND_END));
                    fab.setVisibility(View.VISIBLE);
                } else if (trongThoigianDKHP_Lai == 2){
                    btnHK1.setVisibility(View.VISIBLE);
                    radioGroup.check(R.id.btnHK2);
                    updateListView(2);
                    btnHK1.setVisibility(View.GONE);
                    btnHK3.setVisibility(View.GONE);
                    new CustomToast(getActivity(), "Danh sách các học phần Học kỳ 2","Normal");
                    txtChuaDenHan.setVisibility(View.GONE);
                    tvThoiGianDK.setText("Thời gian đăng ký lại: ");
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK2_REMIND_START, CaiDatThoiGian.HK2_REMIND_END));
                    fab.setVisibility(View.VISIBLE);
                } else if (trongThoigianDKHP_Lai == 3){
                    btnHK1.setVisibility(View.VISIBLE);
                    radioGroup.check(R.id.btnHK3);
                    updateListView(3);
                    btnHK2.setVisibility(View.GONE);
                    btnHK1.setVisibility(View.GONE);
                    new CustomToast(getActivity(), "Danh sách các học phần Học kỳ 3","Normal");
                    txtChuaDenHan.setVisibility(View.GONE);
                    tvThoiGianDK.setText("Thời gian đăng ký lại: ");
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK3_REMIND_START, CaiDatThoiGian.HK3_REMIND_END));
                    fab.setVisibility(View.VISIBLE);
                } else {
                    btnHK1.setVisibility(View.GONE);
                    btnHK2.setVisibility(View.GONE);
                    btnHK3.setVisibility(View.GONE);
                    txtChuaDenHan.setVisibility(View.VISIBLE);
                    txtChuaDenHan.setText("CHƯA ĐẾN THỜI GIAN ĐĂNG KÝ LẠI. VUI LÒNG QUAY LẠI SAU");
                    tvThoiGianDK.setVisibility(View.GONE);
                    txtTGDky.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            } else {
                btnHK1.setVisibility(View.GONE);
                btnHK2.setVisibility(View.GONE);
                btnHK3.setVisibility(View.GONE);
                tvThoiGianDK.setVisibility(View.GONE);
                txtChuaDenHan.setVisibility(View.VISIBLE);
                txtTGDky.setVisibility(View.GONE);
                txtChuaDenHan.setText("SINH VIÊN KHÔNG ĐƯỢC PHÉP ĐĂNG KÝ LẠI. VUI LÒNG QUAY LẠI SAU");
                fab.setVisibility(View.GONE);
            }
        } else {
            btnHK1.setVisibility(View.GONE);
            btnHK2.setVisibility(View.GONE);
            btnHK3.setVisibility(View.GONE);
            tvThoiGianDK.setVisibility(View.GONE);
            txtTGDky.setVisibility(View.GONE);
            txtChuaDenHan.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(view1 -> {
            Fragment hocPhanDaDangKyFragment = new HocPhanDaDangKy();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, hocPhanDaDangKyFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            if (hocPhanDaDangKyFragment instanceof HocPhanDaDangKy) {
                getActivity().setTitle(((HocPhanDaDangKy) hocPhanDaDangKyFragment).getTitle());
            }
        });

        return view;
    }
    public boolean checkSV_dcDKLai(String maSV) {
        SharedPreferences luuSVdcDKlai = getActivity().getSharedPreferences("luuSVdcDKlai", Context.MODE_PRIVATE);
        Set<String> maSVSet = luuSVdcDKlai.getStringSet("MaSVSet", new HashSet<>());


        return maSVSet.contains(maSV);
    }

    private void updateListView(int hocKi) {
        mylist.clear();
        ArrayList<MonHoc> monHocList = monHocDAO.getMonHocByHocKiAndMaKhoa(hocKi, maKhoa);
        mylist.addAll(monHocList);
        myadapter.notifyDataSetChanged();
    }

    public boolean getKQDK_Lai(Context context) {
        SharedPreferences checkResult = context.getSharedPreferences("checkResult", Context.MODE_PRIVATE);
        return checkResult.getBoolean("choPhepDKLai", false);
    }

    public String getTitle() {
        return "Đăng ký học phần";
    }

    @Override
    public void onDestroy() {
        monHocDAO.close();
        super.onDestroy();
    }
}