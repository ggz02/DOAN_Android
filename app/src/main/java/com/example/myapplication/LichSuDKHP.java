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


import com.example.myapplication.Adapter.LichSuDKHPAdapter;
import com.example.myapplication.DAO.DangKyLopMonHocDAO;
import com.example.myapplication.Model.MonHoc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class LichSuDKHP extends Fragment {
    ListView lvLSDKHP;
    ArrayList<MonHoc> mylist;
    LichSuDKHPAdapter myadapter;
    DangKyLopMonHocDAO dklopmonHocDAO;
    String maSV;
    RadioGroup radioGroupLS;
    FloatingActionButton fabHuy_LSDKHP;
    RadioButton btnHK1LS;
    RadioButton btnHK2LS;
    RadioButton btnHK3LS;
    TextView txtChuaDK;
    TextView txtTGDky;
    TextView tvTgDky;
    int hocKyHienTai;
    int hocKyDK_lai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_lichsudkhp, container, false);

        lvLSDKHP = view.findViewById(R.id.lvLSDKHP);
        radioGroupLS = view.findViewById(R.id.radioGroupLS);
        btnHK1LS = view.findViewById(R.id.btnHK1LS);
        btnHK2LS = view.findViewById(R.id.btnHK2LS);
        btnHK3LS = view.findViewById(R.id.btnHK3LS);
        fabHuy_LSDKHP = view.findViewById(R.id.fabHuy_LSDKHP);
        txtChuaDK = view.findViewById(R.id.txtChuaDK);
        txtTGDky = view.findViewById(R.id.txtTGDky);
        tvTgDky = view.findViewById(R.id.tvTgDky);

        dklopmonHocDAO = new DangKyLopMonHocDAO(getActivity());

        SharedPreferences luuSV = getActivity().getSharedPreferences("SV_info", Context.MODE_PRIVATE);
        maSV = luuSV.getString("MaSV", "");

        mylist = new ArrayList<>();
        myadapter = new LichSuDKHPAdapter(getActivity(), R.layout.layout_lichsudkhp_item, mylist);
        lvLSDKHP.setAdapter(myadapter);

        SharedPreferences checktgDKHP = getActivity().getSharedPreferences("checktgDKHP", Context.MODE_PRIVATE);
        int trongTGDKHP = checktgDKHP.getInt("trongTGDKHP", 0);
        hocKyHienTai = checktgDKHP.getInt("trongTGDKHP", 0);
        SharedPreferences checktgDKHP_Lai =  getActivity().getSharedPreferences("checktgDKHP_Lai", Context.MODE_PRIVATE);
        int trongThoigianDKHP_Lai = checktgDKHP_Lai.getInt("trongThoigianDKHP_Lai", 0);
        hocKyDK_lai = checktgDKHP_Lai.getInt("trongThoigianDKHP_Lai", 0);

        if(trongTGDKHP > 0) {
            switch (trongTGDKHP) {
                case 1:
                    radioGroupLS.check(R.id.btnHK1LS);
                    updateListView(1);
                    new CustomToast(getActivity(), "Có thể hủy các học phần Học kỳ 1","Normal");
                    fabHuy_LSDKHP.setVisibility(View.VISIBLE);
                    tvTgDky.setVisibility(View.VISIBLE);
                    txtTGDky.setVisibility(View.VISIBLE);
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK1_REG_START, CaiDatThoiGian.HK1_REG_END));
                    break;
                case 2:
                    radioGroupLS.check(R.id.btnHK2LS);
                    updateListView(2);
                    new CustomToast(getActivity(), "Có thể hủy các học phần Học kỳ 2","Normal");
                    fabHuy_LSDKHP.setVisibility(View.VISIBLE);
                    tvTgDky.setVisibility(View.VISIBLE);
                    txtTGDky.setVisibility(View.VISIBLE);
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK2_REG_START, CaiDatThoiGian.HK2_REG_END));
                    break;
                case 3:
                    radioGroupLS.check(R.id.btnHK3LS);
                    updateListView(3);
                    new CustomToast(getActivity(), "Có thể hủy các học phần Học kỳ 3","Normal");
                    fabHuy_LSDKHP.setVisibility(View.VISIBLE);
                    tvTgDky.setVisibility(View.VISIBLE);
                    txtTGDky.setVisibility(View.VISIBLE);
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK3_REG_START, CaiDatThoiGian.HK3_REG_END));
                    break;
                default:
                    radioGroupLS.check(R.id.btnHK1LS);
                    updateListView(1);
                    new CustomToast(getActivity(), "Không còn trong thời hạn hủy học phần","Fail");
                    fabHuy_LSDKHP.setVisibility(View.GONE);
                    tvTgDky.setVisibility(View.GONE);
                    txtTGDky.setVisibility(View.GONE);
                    break;
            }
        } else if (trongThoigianDKHP_Lai > 0){
            maSV = luuSV.getString("MaSV", "No MaSV");
            boolean duocPhepDKLai = checkSV_dcDKLai(maSV);

            if (duocPhepDKLai) {
                if(trongThoigianDKHP_Lai == 1){
                    radioGroupLS.check(R.id.btnHK1LS);
                    updateListView(1);
                    new CustomToast(getActivity(), "Có thể hủy các học phần Học kỳ 1","Normal");
                    fabHuy_LSDKHP.setVisibility(View.VISIBLE);
                    tvTgDky.setVisibility(View.VISIBLE);
                    txtTGDky.setVisibility(View.VISIBLE);
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK1_REMIND_START, CaiDatThoiGian.HK1_REMIND_END));
                } else if (trongThoigianDKHP_Lai == 2){
                    radioGroupLS.check(R.id.btnHK2LS);
                    updateListView(2);
                    new CustomToast(getActivity(), "Có thể hủy các học phần Học kỳ 2","Normal");
                    fabHuy_LSDKHP.setVisibility(View.VISIBLE);
                    tvTgDky.setVisibility(View.VISIBLE);
                    txtTGDky.setVisibility(View.VISIBLE);
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK2_REMIND_START, CaiDatThoiGian.HK2_REMIND_END));
                } else if (trongThoigianDKHP_Lai == 3){
                    radioGroupLS.check(R.id.btnHK3LS);
                    updateListView(3);
                    new CustomToast(getActivity(), "Có thể hủy các học phần Học kỳ 3","Normal");
                    fabHuy_LSDKHP.setVisibility(View.VISIBLE);
                    tvTgDky.setVisibility(View.VISIBLE);
                    txtTGDky.setVisibility(View.VISIBLE);
                    txtTGDky.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK3_REMIND_START, CaiDatThoiGian.HK3_REMIND_END));
                } else {
                    radioGroupLS.check(R.id.btnHK1LS);
                    updateListView(1);
                    new CustomToast(getActivity(), "Không còn trong thời hạn hủy học phần","Fail");
                    fabHuy_LSDKHP.setVisibility(View.GONE);
                    tvTgDky.setVisibility(View.GONE);
                    txtTGDky.setVisibility(View.GONE);
                }
            } else {
                radioGroupLS.check(R.id.btnHK1LS);
                updateListView(1);
                new CustomToast(getActivity(), "Không còn trong thời hạn hủy học phần","Fail");
                fabHuy_LSDKHP.setVisibility(View.GONE);
                tvTgDky.setVisibility(View.GONE);
                txtTGDky.setVisibility(View.GONE);
            }
        } else {
            radioGroupLS.check(R.id.btnHK1LS);
            updateListView(1);
            new CustomToast(getActivity(), "Không còn trong thời hạn hủy học phần","Fail");
            fabHuy_LSDKHP.setVisibility(View.GONE);
            tvTgDky.setVisibility(View.GONE);
            txtTGDky.setVisibility(View.GONE);
        }

        radioGroupLS.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int hocKi = 1;
                if (checkedId == R.id.btnHK1LS) {
                    hocKi = 1;
                    hide(hocKi, hocKyHienTai);
                    hideDK_lai(hocKi, hocKyDK_lai, maSV);
                } else if (checkedId == R.id.btnHK2LS) {
                    hocKi = 2;
                    hide(hocKi, hocKyHienTai);
                    hideDK_lai(hocKi, hocKyDK_lai, maSV);
                } else if (checkedId == R.id.btnHK3LS) {
                    hocKi = 3;
                    hide(hocKi, hocKyHienTai);
                    hideDK_lai(hocKi, hocKyDK_lai, maSV);
                }
                updateListView(hocKi);
            }
        });

        fabHuy_LSDKHP.setOnClickListener(v -> {
            Fragment huyHPdadkFragment = new HuyHocPhanDaDK();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, huyHPdadkFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            if (huyHPdadkFragment instanceof HuyHocPhanDaDK) {
                getActivity().setTitle(((HuyHocPhanDaDK) huyHPdadkFragment).getTitle());
            }
        });

        return view;
    }

    private void hide(int btnRadio, int hocKi) {
     if(btnRadio != hocKi)  {
         fabHuy_LSDKHP.setVisibility(View.GONE);
         tvTgDky.setVisibility(View.GONE);
         txtTGDky.setVisibility(View.GONE);
     } else{
         fabHuy_LSDKHP.setVisibility(View.VISIBLE);
         tvTgDky.setVisibility(View.VISIBLE);
         txtTGDky.setVisibility(View.VISIBLE);
     }
    }
    public boolean checkSV_dcDKLai(String maSV) {
        SharedPreferences luuSVdcDKlai = getActivity().getSharedPreferences("luuSVdcDKlai", Context.MODE_PRIVATE);
        Set<String> maSVSet = luuSVdcDKlai.getStringSet("MaSVSet", new HashSet<>());

        return maSVSet.contains(maSV);
    }
    private void hideDK_lai(int btnRadio, int hocKiDk_Lai, String maSV) {
        if(btnRadio != hocKiDk_Lai)  {
            fabHuy_LSDKHP.setVisibility(View.GONE);
            tvTgDky.setVisibility(View.GONE);
            txtTGDky.setVisibility(View.GONE);
        } else{
            boolean duocPhepDKLai = checkSV_dcDKLai(maSV);
            if(duocPhepDKLai){
                fabHuy_LSDKHP.setVisibility(View.VISIBLE);
                tvTgDky.setVisibility(View.VISIBLE);
                txtTGDky.setVisibility(View.VISIBLE);
            }
            else {
                fabHuy_LSDKHP.setVisibility(View.GONE);
                tvTgDky.setVisibility(View.GONE);
                txtTGDky.setVisibility(View.GONE);
            }
        }
    }
    private void updateListView(int hocKi) {
        mylist.clear();
        List<MonHoc> monHocList = dklopmonHocDAO.getMonHocDaDangKy(maSV, hocKi);

        mylist.addAll(monHocList);
        myadapter.notifyDataSetChanged();

        if (mylist.isEmpty()) {
            lvLSDKHP.setVisibility(View.GONE);
            txtChuaDK.setVisibility(View.VISIBLE);
        } else {
            lvLSDKHP.setVisibility(View.VISIBLE);
            txtChuaDK.setVisibility(View.GONE);
        }
    }
    public String getTitle() {
        return "Lịch sử đăng ký học phần";
    }
}
