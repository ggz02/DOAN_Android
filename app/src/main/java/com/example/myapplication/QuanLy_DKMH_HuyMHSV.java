package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.Adapter.QuanLy_HuyMonHocSVDaDKAdapter;
import com.example.myapplication.DAO.DangKyLopMonHocDAO;
import com.example.myapplication.DAO.KhoaDAO;
import com.example.myapplication.DAO.SinhVienDAO;
import com.example.myapplication.Model.MonHoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QuanLy_DKMH_HuyMHSV extends AppCompatActivity {
    ListView lview;
    ArrayList<MonHoc> huyHocPhan;
    TextView txtLyDo;
    String maSV;
    String tenSV;
    String khoaSV;
    QuanLy_HuyMonHocSVDaDKAdapter myadapter;
    SharedPreferences luuHuydkhp;
    DangKyLopMonHocDAO dangKyLopMonHocDAO;
    TextView txtTGDK_Lai;
    TextView tVthbaoTGSVDK_lai;
    int selectedHocKi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanly_dkhp_huymhsv);

        lview = findViewById(R.id.lvHuyMH);
        txtLyDo = findViewById(R.id.txtLyDo);
        huyHocPhan = new ArrayList<>();
        txtTGDK_Lai = findViewById(R.id.txtTGDK_Lai);
        tVthbaoTGSVDK_lai = findViewById(R.id.tVthbaoTGSVDK_lai);

        luuHuydkhp = getSharedPreferences("HuyDangKyHocPhan", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        selectedHocKi = intent.getIntExtra("selectedHocKy", 0);
        SharedPreferences checktgDKHP_Lai = getSharedPreferences("checktgDKHP_Lai", Context.MODE_PRIVATE);
        int trongThoigianDKHP_Lai = checktgDKHP_Lai.getInt("trongThoigianDKHP_Lai", 0);

        SharedPreferences checktgDKHP = getSharedPreferences("checktgDKHP", Context.MODE_PRIVATE);
        int trongTGDKHP = checktgDKHP.getInt("trongTGDKHP", 0);

        Set<String> huyHPdachon = luuHuydkhp.getStringSet("HocPhanDaChon", new HashSet<>());
        dangKyLopMonHocDAO = new DangKyLopMonHocDAO(this);

        for (String course : huyHPdachon) {
            String[] courseDetails = course.split(";");
            String maMH = courseDetails[0];
            String tenMH = courseDetails[1];
            int soTinChi = Integer.parseInt(courseDetails[2]);
            String ngayBD = courseDetails[3];
            String ngayKT = courseDetails[4];
            huyHocPhan.add(new MonHoc(maMH, tenMH, soTinChi, ngayBD, ngayKT));
        }

        myadapter = new QuanLy_HuyMonHocSVDaDKAdapter(this, R.layout.quanly_dkhp_huymhsv_item, huyHocPhan);
        lview.setAdapter(myadapter);

        if(trongTGDKHP == 1 && selectedHocKi == 1){
            txtTGDK_Lai.setVisibility(View.VISIBLE);
            tVthbaoTGSVDK_lai.setVisibility(View.VISIBLE);
            txtTGDK_Lai.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK1_REG_START, CaiDatThoiGian.HK1_REG_END));
        } else  if (trongTGDKHP == 2 && selectedHocKi == 2) {
            txtTGDK_Lai.setVisibility(View.VISIBLE);
            tVthbaoTGSVDK_lai.setVisibility(View.VISIBLE);
            txtTGDK_Lai.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK2_REG_START, CaiDatThoiGian.HK2_REG_END));
        } else if (trongTGDKHP == 3 && selectedHocKi == 3) {
            txtTGDK_Lai.setVisibility(View.VISIBLE);
            tVthbaoTGSVDK_lai.setVisibility(View.VISIBLE);
            txtTGDK_Lai.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK3_REG_START, CaiDatThoiGian.HK3_REG_END));
        } else if (trongThoigianDKHP_Lai == 1 && selectedHocKi == 1) {
            txtTGDK_Lai.setVisibility(View.VISIBLE);
            tVthbaoTGSVDK_lai.setVisibility(View.VISIBLE);
            txtTGDK_Lai.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK1_REMIND_START, CaiDatThoiGian.HK1_REMIND_END));
        } else  if (trongThoigianDKHP_Lai == 2 && selectedHocKi == 2) {
            txtTGDK_Lai.setVisibility(View.VISIBLE);
            tVthbaoTGSVDK_lai.setVisibility(View.VISIBLE);
            txtTGDK_Lai.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK2_REMIND_START, CaiDatThoiGian.HK2_REMIND_END));
        } else if (trongThoigianDKHP_Lai == 3 && selectedHocKi == 3) {
            txtTGDK_Lai.setVisibility(View.VISIBLE);
            tVthbaoTGSVDK_lai.setVisibility(View.VISIBLE);
            txtTGDK_Lai.setText(CaiDatThoiGian.formatDateRange(CaiDatThoiGian.HK3_REMIND_START, CaiDatThoiGian.HK3_REMIND_END));
        } else {
            txtTGDK_Lai.setText("");
            txtTGDK_Lai.setVisibility(View.GONE);
            tVthbaoTGSVDK_lai.setVisibility(View.GONE);
        }

        Button btnXNHuyMHSV = findViewById(R.id.btnXNHuyMHSV);
        btnXNHuyMHSV.setOnClickListener(v -> xnHuyMH());

        Button btnQL = findViewById(R.id.btnQL);
        btnQL.setOnClickListener(v ->{
            if (huyHocPhan.isEmpty()) {
                setResult(RESULT_OK);
            }
            finish();
        });
    }
    private void xnHuyMH() {
        Intent intent = getIntent();
        maSV = intent.getStringExtra("MaSV");
        String txtTGDK_LaiContent = txtTGDK_Lai.getText().toString().trim();
        String content="";

        String lyDoText = txtLyDo.getText().toString().trim();

        if (!txtTGDK_LaiContent.isEmpty()) {
           content = "\nThời gian sinh viên đăng ký lại: " + txtTGDK_LaiContent;
        }

        DangKyLopMonHocDAO dangKyLopMonHocDAO = new DangKyLopMonHocDAO(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        SharedPreferences.Editor editor = luuHuydkhp.edit();
        builder.setTitle("Thông báo");
        if(huyHocPhan.isEmpty()){
            builder.setMessage("Bạn chưa chọn học phần nào để huỷ.\n\nHãy chọn học phần để hủy đăng ký");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        } else if (lyDoText.isEmpty()) {
            new CustomToast(this, "Viết lý do hủy học phần","Fail");
            txtLyDo.requestFocus();
        } else {
            StringBuilder hpHuy = new StringBuilder();

            for (MonHoc monHoc : huyHocPhan) {
                hpHuy.append(monHoc.getTenMH()).append("\n");
            }

            builder.setMessage("Lý do hủy: " + lyDoText + "\n\nXác nhận hủy các học phần: \n" + hpHuy.toString()+content);
            builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                for (MonHoc monHoc : huyHocPhan) {
                    dangKyLopMonHocDAO.deleteDangKyLopMonHoc(maSV, monHoc.getMaMH());
                }
                luuSVdcDKlai(maSV);
                checkMaSVAndSaveResult(maSV);

                editor.remove("HocPhanDaChon");
                editor.apply();

                sendEmail();

                new CustomToast(this, "Hủy học phần thành công","Success");
                setResult(RESULT_OK);
                finish();
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
            builder.show();
        }
    }
    private void luuSVdcDKlai(String maSV) {
        SharedPreferences luuSVdcDKlai = getSharedPreferences("luuSVdcDKlai", Context.MODE_PRIVATE);
        SharedPreferences.Editor reRegister = luuSVdcDKlai.edit();
        Set<String> maSVSet = luuSVdcDKlai.getStringSet("MaSVSet", new HashSet<>());
        maSVSet.add(maSV);
        reRegister.putStringSet("MaSVSet", maSVSet);
        reRegister.apply();
    }

    public void checkMaSVAndSaveResult(String maSV) {
        SharedPreferences luuSVdcDKlai = getSharedPreferences("luuSVdcDKlai", Context.MODE_PRIVATE);
        Set<String> maSVSet = luuSVdcDKlai.getStringSet("MaSVSet", new HashSet<>());
        boolean isAllowed = maSVSet.contains(maSV);

        SharedPreferences checkResult = getSharedPreferences("checkResult", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = checkResult.edit();
        editor.putBoolean("choPhepDKLai", isAllowed);
        editor.apply();
    }

    private void sendEmail() {
        Intent intentfab = getIntent();
        maSV = intentfab.getStringExtra("MaSV");
        tenSV = intentfab.getStringExtra("TenSV");
        khoaSV = intentfab.getStringExtra("KhoaSV");
        String txtTGDK_LaiContent = txtTGDK_Lai.getText().toString().trim();
        String content="";

        if (!txtTGDK_LaiContent.isEmpty()) {
            content = "\nThời gian sinh viên đăng ký lại: " + txtTGDK_LaiContent;
        }

        SinhVienDAO sinhVienDAO = new SinhVienDAO(this);
        String emailadd = sinhVienDAO.getEmailByMaSV(maSV);

        KhoaDAO khoaDAO = new KhoaDAO(this);
        String tenKhoa = khoaDAO.getTenKhoaByMaKhoa(khoaSV);

        if (emailadd == null) {
            new CustomToast(this, "Không tìm thấy email của sinh viên","Fail");
            return;
        }

        StringBuilder emailContent = new StringBuilder("Sinh viên: ").append(tenSV).append("\n");
        emailContent.append("Mã số sinh viên: ").append(maSV).append("\n");
        emailContent.append("Thuộc khoa: ").append(tenKhoa).append("\n\n");

        emailContent.append("Sinh viên đã bị hủy đăng ký các học phần với lý do: ").append(txtLyDo.getText().toString().trim()).append("\n\n");
        emailContent.append("Các học phần đã bị hủy:\n");
        for (MonHoc monHoc : huyHocPhan) {
            emailContent.append("Tên học phần: ").append(monHoc.getTenMH())
                    .append("\nSố tín chỉ: ").append(monHoc.getSoTinChi())
                    .append("\nNgày bắt đầu: ").append(monHoc.getNgayBD())
                    .append("\nNgày kết thúc: ").append(monHoc.getNgayKT())
                    .append("\n\n");
        }

        emailContent.append(content);

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, emailadd, "THÔNG BÁO HỦY ĐĂNG KÝ HỌC PHẦN", emailContent.toString());
        javaMailAPI.execute();
    }

}
