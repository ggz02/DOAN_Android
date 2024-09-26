package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.SinhVienDAO;
import com.example.myapplication.Model.SinhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private Button btnDangNhap;
    private EditText inputTaiKhoan;
    private EditText inputMatKhau;
    private String valueRadioButton = "";

    private RadioButton rdSinhVien;
    private RadioButton rdAdmin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDangNhap = findViewById(R.id.btnlogin);
        inputTaiKhoan = findViewById(R.id.inputTK);
        inputMatKhau = findViewById(R.id.inputMK);
        rdSinhVien = findViewById(R.id.rdSinhVien);
        rdAdmin = findViewById(R.id.rdAdmin);


        SinhVienDAO sinhVienDAO = new SinhVienDAO(this);
        int trongTGDKHP = trongThoigianDKHP();
        SharedPreferences checktgDKHP = getSharedPreferences("checktgDKHP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = checktgDKHP.edit();
        editor.putInt("trongTGDKHP", trongTGDKHP);
        editor.apply();

        int trongThoigianDKHP_Lai = trongThoigianDKHP_Lai();
        SharedPreferences checktgDKHP_Lai = getSharedPreferences("checktgDKHP_Lai", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_lai = checktgDKHP_Lai .edit();
        editor_lai.putInt("trongThoigianDKHP_Lai", trongThoigianDKHP_Lai);
        editor_lai.apply();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valueTaiKhoan =  inputTaiKhoan.getText().toString().trim();

                String valueMatKhau = inputMatKhau.getText().toString().trim();
                if (!valueTaiKhoan.isEmpty() && !valueMatKhau.isEmpty() && !valueRadioButton.equals("")) {

                    if (valueRadioButton.equals("Sinh Viên")) {
                        List<SinhVien> listSV = sinhVienDAO.getAllSinhVien();

                        boolean checkLogin = false;
                        for (SinhVien sinhVien:listSV) {
                            if (sinhVien.getTaiKhoan().equals(valueTaiKhoan) && sinhVien.getMatKhau().equals(valueMatKhau)) {
                                checkLogin = true;
                                SharedPreferences luuSV = getSharedPreferences("SV_info", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = luuSV.edit();
                                editor.putString("MaSV", sinhVien.getMaSV());
                                editor.putString("TenSV", sinhVien.getHoTen());
                                editor.putString("MaKhoa", sinhVien.getMaKhoa());
                                editor.putString("Email", sinhVien.getEmail());
                                editor.apply();
                                break;
                            }
                        }
                        if (checkLogin) {
                            Intent intent = new Intent(MainActivity.this,MainStudent.class);
                            startActivity(intent);
                            onStop();
                        }else {
                            new CustomToast(MainActivity.this,"Tài khoản hoặc mật khẩu không chính xác","Fail");
                            clearContent();
                        }
                    }else {
                        boolean checkLogin = false;
                        if (valueTaiKhoan.equals("admin") && valueMatKhau.equals("123456")) {
                            checkLogin=true;
                        }

                        if (checkLogin) {
                            Intent intent = new Intent(MainActivity.this,TrangChu.class);
                            startActivity(intent);
                        }else {
                            new CustomToast(MainActivity.this,"Tài khoản hoặc mật khẩu không chính xác","Fail");
                            clearContent();
                        }


                    }
                }
                else {
                    new CustomToast(MainActivity.this,"Vui lòng cung cấp đầy đủ thông tin để đăng nhập","Fail");
                    clearContent();
                }
            }
        });
    }

    public void onClickRadio(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (view.getId() == R.id.rdSinhVien) {
            valueRadioButton = "Sinh Viên";

        } else {
            valueRadioButton = "Admin";

        }
    }


    public void onClickShowExitDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn thoát không ?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void clearContent(){
        inputTaiKhoan.getText().clear();
        inputMatKhau.getText().clear();
        rdSinhVien.setChecked(false);
        rdAdmin.setChecked(false);
        valueRadioButton = "";
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    public int trongThoigianDKHP() {
        String currentDateStr = getCurrentDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date currentDate = sdf.parse(currentDateStr);

            if ((currentDate.equals(CaiDatThoiGian.HK1_REG_START) || currentDate.equals(CaiDatThoiGian.HK1_REG_END) ||
                    (currentDate.after(CaiDatThoiGian.HK1_REG_START) && currentDate.before(CaiDatThoiGian.HK1_REG_END)))) {
                return 1;
            } else if ((currentDate.equals(CaiDatThoiGian.HK2_REG_START) || currentDate.equals(CaiDatThoiGian.HK2_REG_END) ||
                    (currentDate.after(CaiDatThoiGian.HK2_REG_START) && currentDate.before(CaiDatThoiGian.HK2_REG_END)))) {
                return 2;
            } else if ((currentDate.equals(CaiDatThoiGian.HK3_REG_START) || currentDate.equals(CaiDatThoiGian.HK3_REG_END) ||
                    (currentDate.after(CaiDatThoiGian.HK3_REG_START) && currentDate.before(CaiDatThoiGian.HK3_REG_END)))) {
                return 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
    public int trongThoigianDKHP_Lai() {
        String currentDateStr = getCurrentDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date currentDate = sdf.parse(currentDateStr);

            if ((currentDate.equals(CaiDatThoiGian.HK1_REMIND_START) || currentDate.equals(CaiDatThoiGian.HK1_REMIND_END) ||
                    (currentDate.after(CaiDatThoiGian.HK1_REMIND_START) && currentDate.before(CaiDatThoiGian.HK1_REMIND_END)))) {
                return 1;
            } else if ((currentDate.equals(CaiDatThoiGian.HK2_REMIND_START) || currentDate.equals(CaiDatThoiGian.HK2_REMIND_END) ||
                    (currentDate.after(CaiDatThoiGian.HK2_REMIND_START) && currentDate.before(CaiDatThoiGian.HK2_REMIND_END)))) {
                return 2;
            } else if ((currentDate.equals(CaiDatThoiGian.HK3_REMIND_START) || currentDate.equals(CaiDatThoiGian.HK3_REMIND_END) ||
                    (currentDate.after(CaiDatThoiGian.HK3_REMIND_START) && currentDate.before(CaiDatThoiGian.HK3_REMIND_END)))) {
                return 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
