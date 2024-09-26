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
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.DAO.KhoaDAO;
import com.example.myapplication.DAO.SinhVienDAO;
import com.example.myapplication.Model.Khoa;
import com.example.myapplication.Model.SinhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SuaSinhVien extends AppCompatActivity {

    final Calendar myCalender = Calendar.getInstance();
    private EditText hoTenInput;
    private EditText ngaySinhInput;
    private String valueGioiTinh ="";
    private CheckBox cbNam;
    private CheckBox cbNu;
    private EditText diaChiInput;
    private EditText dienThoaiInput;
    private EditText emailInput;
    private String valueKhoa ="";
    private Spinner spKhoa;
    private EditText taiKhoanInput;
    private EditText matKhauInput;
    private Button btnSave;
    private Button btnReturn;
    private String MASV = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_sinh_vien);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sửa Sinh Viên");

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
        btnSave = findViewById(R.id.btnSave);
        btnReturn = findViewById(R.id.btnReturn);

        SinhVienDAO sinhVienDAO = new SinhVienDAO(this);
        MASV = getIntent().getStringExtra("MASV");
        SinhVien sinhVienGet = sinhVienDAO.getSinhVien(MASV);

        hoTenInput.setText(sinhVienGet.getHoTen());
        ngaySinhInput.setText(sinhVienGet.getNgaySinh());
        if (sinhVienGet.getGioiTinh().toString().equals("Nam")) {
            cbNam.setChecked(true);
        }else {
            cbNu.setChecked(true);
        }

        diaChiInput.setText(sinhVienGet.getDiaChi());
        dienThoaiInput.setText(sinhVienGet.getDThoai());
        emailInput.setText(sinhVienGet.getEmail());
        taiKhoanInput.setText(sinhVienGet.getTaiKhoan());
        matKhauInput.setText(sinhVienGet.getMatKhau());


        KhoaDAO khoaDAO = new KhoaDAO(this);
        List<Khoa> listKhoa = khoaDAO.getAllKhoa();
        List<String> listTenKhoa =  new ArrayList<>();
        for (Khoa khoa:listKhoa) {
            listTenKhoa.add(khoa.getTenKhoa());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTenKhoa);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKhoa.setAdapter(adapter);


        String tenKhoa ="";
        for (Khoa khoa:listKhoa) {
            if (khoa.getMaKhoa().equals(sinhVienGet.getMaKhoa())) {
                tenKhoa = khoa.getTenKhoa();
                break;
            }
        }

        valueKhoa = tenKhoa;
        int position = adapter.getPosition(tenKhoa);
        spKhoa.setSelection(position);

        ngaySinhInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Parse the existing date value
                String valueNgaySinh = sinhVienGet.getNgaySinh();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                Calendar calendar = Calendar.getInstance();

                try {
                    Date date = dateFormat.parse(valueNgaySinh);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Set default date if parsing fails
                    calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                    calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                    calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                }

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SuaSinhVien.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);

                        // Update the TextView with the selected date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        ngaySinhInput.setText(dateFormat.format(selectedDate.getTime()));
                    }
                }, year, month, day);

                datePickerDialog.show();
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
                Intent intent = new Intent(SuaSinhVien.this, QuanLySinhVien.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
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
                    new CustomToast(SuaSinhVien.this, "Vui lòng nhập đầy đủ thông tin để cập nhật thông tin sinh viên","Fail");
                    return;
                }

                String valueMaKhoa = "";

                for (Khoa khoa:listKhoa
                ) {
                    if (khoa.getTenKhoa().equals(valueKhoa)) {
                        valueMaKhoa = khoa.getMaKhoa();
                        break;
                    }
                }

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!valueEmail.matches(emailPattern)) {
                    new CustomToast(SuaSinhVien.this, "Email không hợp lệ","Fail");
                    return;
                }


                SinhVien sinhVien = new SinhVien(MASV,valueHoTen,valueNgaySinh,valueGioiTinh,valueDiaChi,valueEmail,valueMaKhoa,valueTaiKhoan,valueMatKhau,valueDienThoai);
                sinhVienDAO.updateSinhVien(sinhVien);
                new CustomToast(SuaSinhVien.this,"Sửa Sinh Viên Thành Công","Success");
                clearInput();
                Intent intent = new Intent(SuaSinhVien.this, QuanLySinhVien.class);
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
