package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.DAO.KhoaDAO;
import com.example.myapplication.DAO.MonHocDAO;
import com.example.myapplication.Model.Khoa;
import com.example.myapplication.Model.MonHoc;
import com.example.myapplication.Model.SinhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SuaMonHoc extends AppCompatActivity {

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
    private Button btnSaveMH;
    private Button btnReturnMH;

    private String MAMH = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upadate_mon_hoc);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sửa Môn Học");

        maMHInput = findViewById(R.id.inputMaMH);
        tenMHInput = findViewById(R.id.inputTenMH);
        ngayBDInput = findViewById(R.id.inputNgayBD);
        ngayKTInput = findViewById(R.id.inputNgayKT);
        spHocKi = findViewById(R.id.spHocKi);
        soTinChiInput = findViewById(R.id.inputSoTinChi);
        spKhoa = findViewById(R.id.spKhoa);
        btnSaveMH = findViewById(R.id.btnSaveMH);
        btnReturnMH = findViewById(R.id.btnReturnMH);

        MonHocDAO monHocDAO = new MonHocDAO(this);
        MAMH = getIntent().getStringExtra("MAMH");
        MonHoc monHocGet = monHocDAO.getMonHoc(MAMH);

        maMHInput.setText(monHocGet.getMaMH());
        tenMHInput.setText(monHocGet.getTenMH());
        ngayKTInput.setText(monHocGet.getNgayKT());
        ngayBDInput.setText(monHocGet.getNgayBD());
        soTinChiInput.setText(String.valueOf(monHocGet.getSoTinChi()));

        maMHInput.setEnabled(false);
        maMHInput.setInputType(InputType.TYPE_NULL);
        maMHInput.setFocusable(false);



        ngayBDInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueNgayBD = monHocGet.getNgayBD();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                Calendar calendar = Calendar.getInstance();

                try {
                    Date date = dateFormat.parse(valueNgayBD);
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(SuaMonHoc.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);

                        // Update the TextView with the selected date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        ngayBDInput.setText(dateFormat.format(selectedDate.getTime()));
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        ngayKTInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueNgayKT = monHocGet.getNgayKT();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                Calendar calendar = Calendar.getInstance();

                try {
                    Date date = dateFormat.parse(valueNgayKT);
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(SuaMonHoc.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);

                        // Update the TextView with the selected date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        ngayKTInput.setText(dateFormat.format(selectedDate.getTime()));
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

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
            if (khoa.getMaKhoa().equals(monHocGet.getMaKhoa())) {
                tenKhoa = khoa.getTenKhoa();
                break;
            }
        }
        valueKhoa = tenKhoa;
        int positionKhoa = adapter.getPosition(tenKhoa);
        spKhoa.setSelection(positionKhoa);

        ArrayList<String> listHK = new ArrayList<>();
        listHK.add("Học Kì 1");
        listHK.add("Học Kì 2");
        listHK.add("Học Kì 3");

        ArrayAdapter<String> adapterHK = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listHK);
        adapterHK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHocKi.setAdapter(adapterHK);

        String valueHK = "Học Kì " + String.valueOf(monHocGet.getHocKi());
        int positionHK = adapterHK.getPosition(valueHK);
        spHocKi.setSelection(positionHK);


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


        btnReturnMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuaMonHoc.this, QuanLyMonHoc.class);
                startActivity(intent);
            }
        });

        btnSaveMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueMaMH = maMHInput.getText().toString().trim();
                String valueTenMH = tenMHInput.getText().toString().trim();
                int valueSoTinChi;
                String valueNgayBD = ngayBDInput.getText().toString().trim();
                String valueNgayKT = ngayKTInput.getText().toString().trim();

                if (valueMaMH.isEmpty() || valueTenMH.isEmpty() || soTinChiInput.getText().toString().trim().isEmpty() || valueNgayBD.isEmpty() || valueNgayKT.isEmpty()) {
                    new CustomToast(SuaMonHoc.this, "Vui lòng cung cấp đầy đủ thông tin để sửa thông tin môn học","Fail");
                    return;
                }

                MonHocDAO monHocDAO = new MonHocDAO(SuaMonHoc.this);

                String valueMaKhoa = "";

                for (Khoa khoa:listKhoa
                ) {
                    if (khoa.getTenKhoa().equals(valueKhoa.toString().trim())) {
                        valueMaKhoa = khoa.getMaKhoa();
                        break;
                    }
                }

                try {
                    valueSoTinChi = Integer.parseInt(soTinChiInput.getText().toString().trim());
                } catch (NumberFormatException e) {
                    new CustomToast(SuaMonHoc.this, "Số tín chỉ không hợp lệ","Fail");
                    return;
                }

                if (valueSoTinChi <= 0) {
                    new CustomToast(SuaMonHoc.this, "Vui lòng nhập số tín chỉ lớn hơn không","Fail");
                } else {


                    MonHoc monHoc = new MonHoc(valueMaMH, valueTenMH, valueHocKi, valueSoTinChi, valueMaKhoa, valueNgayBD, valueNgayKT);
                    monHocDAO.updateMonHoc(monHoc);
                     new CustomToast(SuaMonHoc.this, "Sửa Môn Học Thành Công","Success");
                    clearInput();
                    Intent intent = new Intent(SuaMonHoc.this, QuanLyMonHoc.class);
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
