package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.myapplication.Adapter.QuanLy_MonHocSVDaDKAdapter;
import com.example.myapplication.DAO.DangKyLopMonHocDAO;
import com.example.myapplication.Model.MonHoc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuanLy_DKMH_CTSV extends AppCompatActivity {
    private String maSV;
    private String tenSV;
    private String khoaSV;
    private ListView lvMH;
    private QuanLy_MonHocSVDaDKAdapter monHocSVDaDKAdapter;
    private FloatingActionButton fabhuydk;
    private Spinner spinner;
    private int selectedHocKy;
    TextView tVSVktdklai;
    TextView tVSVctdklai;
    int trongThoigianDKHP_Lai;
    int trongTGDKHP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanly_dkhp_ctsv);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        maSV = intent.getStringExtra("MaSV");
        tenSV = intent.getStringExtra("TenSV");
        khoaSV = intent.getStringExtra("KhoaSV");
        TextView txtinfoSV = findViewById(R.id.txtinfoSV);
        txtinfoSV.setText("Học phần sinh viên " + tenSV + " đã đăng ký");

        spinner = findViewById(R.id.spinner);
        lvMH = findViewById(R.id.lvMH);
        fabhuydk = findViewById(R.id.fabhuydk);
        tVSVktdklai = findViewById(R.id.tVSVktdklai);
        tVSVctdklai = findViewById(R.id.tVSVctdklai);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hoc_ky_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        SharedPreferences checktgDKHP_Lai = getSharedPreferences("checktgDKHP_Lai", Context.MODE_PRIVATE);
        trongThoigianDKHP_Lai = checktgDKHP_Lai.getInt("trongThoigianDKHP_Lai", 0);

        SharedPreferences checktgDKHP = getSharedPreferences("checktgDKHP", Context.MODE_PRIVATE);
        trongTGDKHP = checktgDKHP.getInt("trongTGDKHP", 0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedHocKy = position + 1;
                updateListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });




        fabhuydk.setOnClickListener(view -> {
            Intent intentfab = new Intent(QuanLy_DKMH_CTSV.this, QuanLy_DKMH_HuyMHSV.class);
            intentfab.putExtra("MaSV", maSV);
            intentfab.putExtra("TenSV", tenSV);
            intentfab.putExtra("KhoaSV", khoaSV);
            intentfab.putExtra("selectedHocKy", selectedHocKy);
            startActivityForResult(intentfab, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            checkHuyHPdachon();
            updateListView();
        }
    }

    private void updateListView() {
        DangKyLopMonHocDAO dklopmhdao = new DangKyLopMonHocDAO(QuanLy_DKMH_CTSV.this);
        List<MonHoc> danhSachMonHoc = dklopmhdao.getMonHocDaDangKy(maSV, selectedHocKy);

        //monHocSVDaDKAdapter = new QuanLy_MonHocSVDaDKAdapter(QuanLy_DKMH_CTSV.this, R.layout.quanly_dkhp_ctsv_item, new ArrayList<>(danhSachMonHoc), isCourseCancelled);
        monHocSVDaDKAdapter = new QuanLy_MonHocSVDaDKAdapter(QuanLy_DKMH_CTSV.this, R.layout.quanly_dkhp_ctsv_item, new ArrayList<>(danhSachMonHoc));
        lvMH.setAdapter(monHocSVDaDKAdapter);

        TextView txtRong = findViewById(R.id.txtRong);

        if (danhSachMonHoc.isEmpty()) {
            lvMH.setVisibility(View.GONE);
            txtRong.setVisibility(View.VISIBLE);
            tVSVktdklai.setVisibility(View.GONE);
            tVSVctdklai.setVisibility(View.GONE);
            fabhuydk.setVisibility(View.GONE);
        } else {
            if(trongThoigianDKHP_Lai == selectedHocKy || trongTGDKHP == selectedHocKy) {
                tVSVktdklai.setVisibility(View.GONE);
                tVSVctdklai.setVisibility(View.VISIBLE);
            } else {
                tVSVktdklai.setVisibility(View.VISIBLE);
                tVSVctdklai.setVisibility(View.GONE);
            }

            lvMH.setVisibility(View.VISIBLE);
            txtRong.setVisibility(View.GONE);
            fabhuydk.setVisibility(View.VISIBLE);
        }

        dklopmhdao.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu_trangchu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.btnTrangChu2) {
            Intent intent = new Intent(QuanLy_DKMH_CTSV.this, TrangChu.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void disableSpinnerItems() {
        spinner.setEnabled(false);
    }
    public void enableSpinnerItems() {
        spinner.setEnabled(true);
    }
    private void checkHuyHPdachon() {
        SharedPreferences luuHuydkhp = getSharedPreferences("HuyDangKyHocPhan", Context.MODE_PRIVATE);
        Set<String> huyHPdachon = luuHuydkhp.getStringSet("HocPhanDaChon", new HashSet<>());

        if (huyHPdachon.isEmpty()) {
            enableSpinnerItems();
            new CustomToast(this, "Không có học phần nào được chọn","Fail");
        } else {
            disableSpinnerItems();
        }
    }
}