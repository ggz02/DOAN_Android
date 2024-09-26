package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.myapplication.Adapter.SinhVienAdapter;
import com.example.myapplication.DAO.SinhVienDAO;
import com.example.myapplication.Model.SinhVienKhoa;
import java.util.ArrayList;
import java.util.List;

public class QuanLySinhVien  extends AppCompatActivity {

    private GridView gridView;
    private SinhVienAdapter adapter;
    private List<SinhVienKhoa> listSV = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ql_sv);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Quản Lý Sinh Viên");
        setSupportActionBar(toolbar);
        loadSinhViens();
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
            Intent intent = new Intent(QuanLySinhVien.this, ThemSinhVien.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.btnTrangChu) {
            Intent intent = new Intent(QuanLySinhVien.this, TrangChu.class);
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
            String message = listSV.get(info.position).getMaSV();
            Intent intent = new Intent(QuanLySinhVien.this,SuaSinhVien.class).putExtra("MASV",message);
            startActivityForResult(intent,1);
            return true;
        } else if (itemId == R.id.btnXoa) {
            SinhVienDAO sinhVienDAO = new SinhVienDAO(QuanLySinhVien.this);
            sinhVienDAO.deleteSinhVien(listSV.get(info.position).getMaSV());
            loadSinhViens();
            new CustomToast(QuanLySinhVien.this,"Xóa Sinh viên Thành Công","Success");
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public void loadSinhViens() {
        SinhVienDAO sinhVienDAO = new SinhVienDAO(this);
        listSV = sinhVienDAO.getAllSinhVienKhoa();
        gridView = findViewById(R.id.gridViewSV);
        adapter = new SinhVienAdapter(this,listSV);
        gridView.setAdapter(adapter);
        registerForContextMenu(gridView);
    }

}
