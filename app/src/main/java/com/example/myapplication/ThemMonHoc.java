package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.KhoaDAO;
import com.example.myapplication.DAO.MonHocDAO;
import com.example.myapplication.DAO.SinhVienDAO;
import com.example.myapplication.Model.Khoa;
import com.example.myapplication.Model.MonHoc;
import com.example.myapplication.Model.MonHocKhoa;
import com.example.myapplication.Model.SinhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ThemMonHoc extends AppCompatActivity {
    final Calendar myCalender = Calendar.getInstance();

    private EditText maMHInput;
    private EditText tenMHInput;
    private EditText ngayBDInput;
    private int valueHocKi;
    private Spinner spHocKi;
    private EditText soTinChiInput;
    private EditText ngayKTInput;
    private String valueKhoa;
    private Spinner spKhoa;
    private Button btnAdd;
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_monhoc);

        maMHInput = findViewById(R.id.inputMaMH);
        tenMHInput = findViewById(R.id.inputTenMH);
        ngayBDInput = findViewById(R.id.inputNgayBD);
        ngayKTInput = findViewById(R.id.inputNgayKT);
        spHocKi = findViewById(R.id.spHocKi);
        soTinChiInput = findViewById(R.id.inputSoTinChi);
        spKhoa = findViewById(R.id.spKhoa);
        btnAdd = findViewById(R.id.btnAddMH);
        btnReturn = findViewById(R.id.btnReturnMH);

        ngayBDInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ThemMonHoc.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalender.set(Calendar.YEAR,year);
                        myCalender.set(Calendar.MONTH,month);
                        myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        String myFormat = "dd/MM/yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        ngayBDInput.setText(dateFormat.format(myCalender.getTime()));

                    }
                }, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ngayKTInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ThemMonHoc.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalender.set(Calendar.YEAR,year);
                        myCalender.set(Calendar.MONTH,month);
                        myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        String myFormat = "dd/MM/yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        ngayKTInput.setText(dateFormat.format(myCalender.getTime()));

                    }
                }, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        KhoaDAO khoaDAO = new KhoaDAO(this);
        List<String> myList =  new ArrayList<>();
        List<Khoa> listKhoa = khoaDAO.getAllKhoa();
        for (Khoa khoa:listKhoa) {
            myList.add(khoa.getTenKhoa());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, myList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKhoa.setAdapter(adapter);

        ArrayList<String> listHK = new ArrayList<>();
        listHK.add("Học Kì 1");
        listHK.add("Học Kì 2");
        listHK.add("Học Kì 3");

        ArrayAdapter<String> adapterHK = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listHK);
        adapterHK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHocKi.setAdapter(adapterHK);



        // Thiết lập OnItemSelectedListener
        spKhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueKhoa = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spHocKi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueHocKi = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemMonHoc.this, QuanLyMonHoc.class);
                startActivity(intent);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maMH = maMHInput.getText().toString().trim();
                String tenMH = tenMHInput.getText().toString().trim();
                String soTinChiStr = soTinChiInput.getText().toString().trim();
                String ngayBD = ngayBDInput.getText().toString().trim();
                String ngayKT = ngayKTInput.getText().toString().trim();

                if (maMH.isEmpty() || tenMH.isEmpty() || soTinChiStr.isEmpty() || ngayBD.isEmpty() || ngayKT.isEmpty()) {
                    new CustomToast(ThemMonHoc.this, "Vui lòng cung cấp đầy đủ thông tin để tạo môn học","Fail");
                    return;
                }

                int soTinChi;
                try {
                    soTinChi = Integer.parseInt(soTinChiStr);
                } catch (NumberFormatException e) {
                    new CustomToast(ThemMonHoc.this, "Số tín chỉ không hợp lệ","Fail");
                    return;
                }

                MonHocDAO monHocDAO = new MonHocDAO(ThemMonHoc.this);
                String maKhoa = null;

                for (Khoa khoa : listKhoa) {
                    if (khoa.getTenKhoa().equals(valueKhoa.toString().trim())) {
                        maKhoa = khoa.getMaKhoa();
                        break;
                    }
                }

                for (MonHocKhoa mh : monHocDAO.getAllMocHocKhoa()) {
                    if (mh.getMaMH().equals(maMH)) {
                        new CustomToast(ThemMonHoc.this, "Mã môn học đã tồn tại","Fail");
                        return;
                    }
                }

                if (soTinChi <= 0) {
                    new CustomToast(ThemMonHoc.this, "Vui lòng nhập số tín chỉ lớn hơn không","Fail");
                } else {
                    MonHoc monHoc = new MonHoc(maMH, tenMH, valueHocKi, soTinChi, maKhoa, ngayBD, ngayKT);
                    monHocDAO.addMonHoc(monHoc);
                    new CustomToast(ThemMonHoc.this, "Thêm Môn Học Thành Công","Success");
                    clearInput();
                    Intent intent = new Intent(ThemMonHoc.this, QuanLyMonHoc.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void clearInput() {
        tenMHInput.getText().clear();
        ngayBDInput.getText().clear();
        ngayKTInput.getText().clear();
        spHocKi.setSelection(0);
        spKhoa.setSelection(0);
        soTinChiInput.getText().clear();
    }


}
