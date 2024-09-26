package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainStudent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int dangkyhocphan = 0;
    private static final int tracuuhocphan = 1;
    private static final int lichsudkhocphan = 2;
    private int mCurrentFragment = dangkyhocphan;
    String maSV;
    String tenSV;
    String emailSV;
    TextView txtSV;
    TextView txtMSSV;
    TextView txtEmail;

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_student);

        SharedPreferences luuSV = getSharedPreferences("SV_info", Context.MODE_PRIVATE);
        maSV = luuSV.getString("MaSV", "");
        tenSV = luuSV.getString("TenSV", "");
        emailSV = luuSV.getString("Email", "");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtSV = headerView.findViewById(R.id.txtSV);
        txtMSSV = headerView.findViewById(R.id.txtMSSV);
        txtEmail = headerView.findViewById(R.id.txtEmail);

        txtSV.setText(tenSV);
        txtMSSV.setText(maSV);
        txtEmail.setText(emailSV);

        replaceFragment(new DangKyHocPhan());
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.nav_dkhp) {
            if(mCurrentFragment!=dangkyhocphan){
                replaceFragment(new DangKyHocPhan());
                mCurrentFragment = dangkyhocphan;
            }
        } else if (id==R.id.nav_tracuu){
            if(mCurrentFragment!=tracuuhocphan){
                replaceFragment(new TraCuuHocPhan());
                mCurrentFragment = tracuuhocphan;
            }
        }else if (id==R.id.nav_lsdkhp){
            if(mCurrentFragment!=lichsudkhocphan){
                replaceFragment(new LichSuDKHP());
                mCurrentFragment = lichsudkhocphan;
            }
        } else if(id==R.id.nav_dxuat){
            Intent intent = new Intent(MainStudent.this,MainActivity.class);
            startActivity(intent);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();

        // Cài title cho toolbar
        if (fragment instanceof DangKyHocPhan) {
            setTitle(((DangKyHocPhan) fragment).getTitle());
        } else if (fragment instanceof TraCuuHocPhan) {
            setTitle(((TraCuuHocPhan) fragment).getTitle());
        } else if (fragment instanceof LichSuDKHP) {
            setTitle(((LichSuDKHP) fragment).getTitle());
        } else if (fragment instanceof HocPhanDaDangKy) {
        setTitle(((HocPhanDaDangKy) fragment).getTitle());
        }
    }

}
