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
import com.example.myapplication.DAO.SinhVienDAO;
import com.example.myapplication.Model.Khoa;
import com.example.myapplication.Model.SinhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ThemSinhVien extends AppCompatActivity {

    final Calendar myCalender = Calendar.getInstance();
    private EditText hoTenInput;
    private EditText ngaySinhInput;
    private String valueGioiTinh;
    private CheckBox cbNam;
    private CheckBox cbNu;
    private EditText diaChiInput;
    private EditText dienThoaiInput;
    private EditText emailInput;
    private String valueKhoa;
    private Spinner spKhoa;
    private EditText taiKhoanInput;
    private EditText matKhauInput;
    private Button btnAdd;
    private Button btnReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sinh_vien);
        hoTenInput = findViewById(R.id.inputHoTen);
        ngaySinhInput = findViewById(R.id.inputNgaySinh);
        cbNam = findViewById(R.id.cbNam);
        cbNu = findViewById(R.id.cbNu);
        diaChiInput = findViewById(R.id.inputDiaChi);
        dienThoaiInput = findViewById(R.id.inputDienThoai);
        emailInput = findViewById(R.id.inputEmail);
        taiKhoanInput = findViewById(R.id.inputTaiKhoan);
        matKhauInput = findViewById(R.id.inputMatKhau);
        spKhoa = findViewById(R.id.spKhoa);

        btnAdd = findViewById(R.id.btnAdd);
        btnReturn = findViewById(R.id.btnReturn);

        KhoaDAO khoaDAO = new KhoaDAO(this);



        ngaySinhInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ThemSinhVien.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalender.set(Calendar.YEAR,year);
                        myCalender.set(Calendar.MONTH,month);
                        myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        String myFormat = "dd/MM/yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        ngaySinhInput.setText(dateFormat.format(myCalender.getTime()));

                    }
                }, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        cbNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbNam.isChecked()) {
                    valueGioiTinh = "Nam";
                    cbNu.setChecked(false);
                }
            }
        });

        cbNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbNu.isChecked()) {
                    valueGioiTinh = "Nữ";
                    cbNam.setChecked(false);
                }
            }
        });

        List<String> myList =  new ArrayList<>();
        List<Khoa> listKhoa = khoaDAO.getAllKhoa();
        for (Khoa khoa:listKhoa) {
            myList.add(khoa.getTenKhoa());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, myList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKhoa.setAdapter(adapter);

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

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemSinhVien.this, QuanLySinhVien.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valueHoTen = hoTenInput.getText().toString().trim();
                String valueNgaySinh = ngaySinhInput.getText().toString().trim();
                String valueDiaChi = diaChiInput.getText().toString().trim();
                String valueDienThoai = dienThoaiInput.getText().toString().trim();
                String valueEmail = emailInput.getText().toString().trim();
                String valueTaiKhoan = taiKhoanInput.getText().toString().trim();
                String valueMatKhau = matKhauInput.getText().toString().trim();

                if (valueHoTen.isEmpty() || valueNgaySinh.isEmpty() || valueDiaChi.isEmpty() || valueDienThoai.isEmpty() || valueEmail.isEmpty() || valueTaiKhoan.isEmpty() || valueMatKhau.isEmpty()) {
                    new CustomToast(ThemSinhVien.this, "Vui lòng nhập đầy đủ thông tin để tạo mới sinh viên","Fail");
                    return;
                }

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!valueEmail.matches(emailPattern)) {
                    new CustomToast(ThemSinhVien.this, "Email không hợp lệ","Fail");
                    return;
                }

                SinhVienDAO sinhVienDAO = new SinhVienDAO(ThemSinhVien.this);
                List<SinhVien> listSinhVien = sinhVienDAO.getAllSinhVien();
                String maSVLast = "";
                if (!listSinhVien.isEmpty()) {
                    SinhVien lastSinhVien = listSinhVien.get(listSinhVien.size() - 1);
                    maSVLast = lastSinhVien.getMaSV();
                }

                String splitMaSV = "";
                long newMaSVInt = 0;
                String newMaSVString = "";
                splitMaSV = maSVLast.substring(2);
                newMaSVInt = Integer.parseInt(splitMaSV);
                newMaSVInt++;
                if (newMaSVInt < 10){
                    newMaSVString = "SV00" + String.valueOf(newMaSVInt);
                }else {
                    newMaSVString = "SV0" + String.valueOf(newMaSVInt);
                }
                String valueMaKhoa = "";

                for (Khoa khoa:listKhoa
                     ) {
                    if (khoa.getTenKhoa().equals(valueKhoa.toString().trim())) {
                        valueMaKhoa = khoa.getMaKhoa();
                        break;
                    }
                }


                SinhVien sinhVien = new SinhVien(newMaSVString,valueHoTen,valueNgaySinh,valueGioiTinh,valueDiaChi,valueEmail,valueMaKhoa,valueTaiKhoan,valueMatKhau,valueDienThoai);
                sinhVienDAO.addSinhVien(sinhVien);
                new CustomToast(ThemSinhVien.this,"Thêm Sinh Viên Thành Công","Success");
                clearInput();
                Intent intent = new Intent(ThemSinhVien.this, QuanLySinhVien.class);
                startActivity(intent);
            }
        });





    }

    private void clearInput() {
        hoTenInput.getText().clear();
        ngaySinhInput.getText().clear();
        cbNam.setChecked(false);
        cbNu.setChecked(false);
        diaChiInput.getText().clear();
        dienThoaiInput.getText().clear();
        emailInput.getText().clear();
        spKhoa.setSelection(0);
        taiKhoanInput.getText().clear();
        matKhauInput.getText().clear();
    }
}
