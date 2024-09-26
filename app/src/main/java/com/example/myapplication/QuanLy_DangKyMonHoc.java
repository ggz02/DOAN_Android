package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.myapplication.Adapter.QuanLy_DangKyMonHocAdapter;
import com.example.myapplication.DAO.DangKyLopMonHocDAO;
import com.example.myapplication.Model.SinhVien;

import java.util.List;

public class QuanLy_DangKyMonHoc extends AppCompatActivity {
    private ListView lvSVdadkhp;
    private ListView lvSVchuadkhp;

    private DangKyLopMonHocDAO dangKyLopMonHocDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanly_dkhp);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        lvSVdadkhp = findViewById(R.id.lvSVdadkhp);
        lvSVchuadkhp = findViewById(R.id.lvSVchuadkhp);

        dangKyLopMonHocDAO = new DangKyLopMonHocDAO(this);

        List<SinhVien> sinhVienDaDangKyList = dangKyLopMonHocDAO.getSinhVienDaDangKy();
        List<SinhVien> sinhVienChuaDangKyList = dangKyLopMonHocDAO.getSinhVienChuaDangKy();

        QuanLy_DangKyMonHocAdapter daDangKyAdapter = new QuanLy_DangKyMonHocAdapter(this, R.layout.quanly_dkhp_item, sinhVienDaDangKyList);
        lvSVdadkhp.setAdapter(daDangKyAdapter);

        QuanLy_DangKyMonHocAdapter chuaDangKyAdapter = new QuanLy_DangKyMonHocAdapter(this, R.layout.quanly_dkhp_item, sinhVienChuaDangKyList);
        lvSVchuadkhp.setAdapter(chuaDangKyAdapter);

        lvSVdadkhp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SinhVien selectedItem = sinhVienDaDangKyList.get(position);

                Intent intent = new Intent(QuanLy_DangKyMonHoc.this, QuanLy_DKMH_CTSV.class);
                intent.putExtra("MaSV", selectedItem.getMaSV());
                intent.putExtra("TenSV", selectedItem.getHoTen());
                intent.putExtra("KhoaSV", selectedItem.getMaKhoa());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dangKyLopMonHocDAO.close();
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
            Intent intent = new Intent(QuanLy_DangKyMonHoc.this, TrangChu.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}