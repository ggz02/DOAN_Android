package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.myapplication.Adapter.TraCuuHocPhanAdapter;
import com.example.myapplication.DAO.MonHocDAO;
import com.example.myapplication.Model.MonHoc;

import java.util.ArrayList;

public class TraCuuHocPhan extends Fragment {
    private Spinner spinnerHK;
    private int selectedHocKy;
    private Spinner spinnerLTC;
    TextView thongtinHP;
    Button btnTCHPhan;
    String maKhoa;
    private ListView lvTraCuuHocPhan;
    private TraCuuHocPhanAdapter traCuuHocPhanAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tracuuhp, container, false);

        spinnerHK = view.findViewById(R.id.spinnerHK);
        ArrayAdapter<CharSequence> adapterHK = ArrayAdapter.createFromResource(getActivity(),
                R.array.hoc_ky_array, android.R.layout.simple_spinner_item);
        adapterHK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHK.setAdapter(adapterHK);

        spinnerHK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedHocKy = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        spinnerLTC = view.findViewById(R.id.spinnerLTC);
        ArrayAdapter<CharSequence> adapterLTC = ArrayAdapter.createFromResource(getActivity(),
                R.array.loaitracuu_array, android.R.layout.simple_spinner_item);
        adapterLTC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLTC.setAdapter(adapterLTC);

        thongtinHP = view.findViewById(R.id.txtThongtinHP);
        btnTCHPhan = view.findViewById(R.id.btnTCHPhan);
        lvTraCuuHocPhan = view.findViewById(R.id.lvTraCuuHocPhan);

        btnTCHPhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMonHoc();
            }
        });

        return view;
    }
    public String getTitle() {
        return "Tra cứu học phần";
    }
    private void searchMonHoc() {
        SharedPreferences luuSV = getActivity().getSharedPreferences("SV_info", Context.MODE_PRIVATE);
        maKhoa = luuSV.getString("MaKhoa", "");

        String hocKiStr = spinnerHK.getSelectedItem().toString();
        String loaiTraCuuStr = spinnerLTC.getSelectedItem().toString();
        String keyword = thongtinHP.getText().toString();

        if (hocKiStr.isEmpty() || loaiTraCuuStr.isEmpty() || keyword.isEmpty()) {
            new CustomToast(getActivity(), "Vui lòng nhập đầy đủ thông tin","Fail");
            return;
        }

        MonHocDAO monHocDAO = new MonHocDAO(getActivity());
        ArrayList<MonHoc> monHocList;

        if (loaiTraCuuStr.equals("Mã học phần")) {
            monHocList = monHocDAO.searchMonHocSVByMaMH(selectedHocKy, keyword, maKhoa);
        } else {
            monHocList = monHocDAO.searchMonHocSVByTenMH(selectedHocKy, keyword, maKhoa);
        }

        if (monHocList.isEmpty()) {
            new CustomToast(getActivity(), "Không tìm thấy học phần nào.","Normal");
        } else {
            traCuuHocPhanAdapter = new TraCuuHocPhanAdapter(getActivity(), R.layout.layout_tracuuhp_item, monHocList);
            lvTraCuuHocPhan.setAdapter(traCuuHocPhanAdapter);
        }

        monHocDAO.close();
    }

}