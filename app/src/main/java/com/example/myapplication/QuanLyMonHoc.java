package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Adapter.MonHocAdapter;
import com.example.myapplication.Adapter.SinhVienAdapter;
import com.example.myapplication.DAO.MonHocDAO;
import com.example.myapplication.DAO.SinhVienDAO;
import com.example.myapplication.Model.MonHoc;
import com.example.myapplication.Model.MonHocKhoa;
import com.example.myapplication.Model.SinhVienKhoa;

import java.util.ArrayList;
import java.util.List;

public class QuanLyMonHoc extends AppCompatActivity {

    private GridView gridViewMH;
    private MonHocAdapter adapter;
    private List<MonHocKhoa> listMH = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quanly_monhoc);

        Toolbar toolbar = findViewById(R.id.toolbarMH);
        toolbar.setTitle("Quản Lý Môn Học");
        setSupportActionBar(toolbar);
        loadMonHocs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();

        if (searchView != null) {
            searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.btnThem) {
            Intent intent = new Intent(QuanLyMonHoc.this, ThemMonHoc.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.btnTrangChu) {
            Intent intent = new Intent(QuanLyMonHoc.this, TrangChu.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemId = item.getItemId();
        if (itemId == R.id.btnSua) {
            String message = listMH.get(info.position).getMaMH();
            Intent intent = new Intent(QuanLyMonHoc.this,SuaMonHoc.class).putExtra("MAMH",message);
            startActivityForResult(intent,1);
            return true;
        } else if (itemId == R.id.btnXoa) {
            MonHocDAO monHocDAO = new MonHocDAO(QuanLyMonHoc.this);
            monHocDAO.deleteMonHoc(listMH.get(info.position).getMaMH());
            new CustomToast(QuanLyMonHoc.this,"Xóa Môn Học Thành Công","Success");
            loadMonHocs();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public void loadMonHocs() {
        MonHocDAO monHocDAO = new MonHocDAO(this);
        listMH = monHocDAO.getAllMocHocKhoa();
        gridViewMH = findViewById(R.id.gridViewMH);
        adapter = new MonHocAdapter(this,listMH);
        gridViewMH.setAdapter(adapter);
        registerForContextMenu(gridViewMH);
    }
}
