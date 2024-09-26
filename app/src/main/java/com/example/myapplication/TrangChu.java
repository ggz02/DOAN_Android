package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TrangChu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menuHome);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.menuHome) {
                    startActivity(new Intent(TrangChu.this, TrangChu.class));
                    return true;
                }else {
                    startActivity(new Intent(TrangChu.this, UserAdmin.class));
                    return true;
                }

            }
        });

    }


    public void onClickQuanLySV(View view) {
        Intent intent = new Intent(TrangChu.this, QuanLySinhVien.class);
        startActivity(intent);
    }

    public void onClickQuanLyMH(View view) {
        Intent intent = new Intent(TrangChu.this, QuanLyMonHoc.class);
        startActivity(intent);
    }

    public void onClickThongKe(View view) {
        Intent intent = new Intent(TrangChu.this, ThongKe.class);
        startActivity(intent);
    }

    public void onClickQuanLyDKMH(View view) {
        Intent intent = new Intent(TrangChu.this, QuanLy_DangKyMonHoc.class);
        startActivity(intent);
    }

}
