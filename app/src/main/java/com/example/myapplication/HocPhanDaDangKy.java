package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.myapplication.Adapter.MonHocDaDangKyAdapter;
import com.example.myapplication.DAO.DangKyLopMonHocDAO;
import com.example.myapplication.DAO.KhoaDAO;
import com.example.myapplication.Model.DangKyLopMonHoc;
import com.example.myapplication.Model.MonHoc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class HocPhanDaDangKy extends Fragment {
    ListView lview;
    ArrayList<MonHoc> registeredCourses;
    MonHocDaDangKyAdapter myadapter;
    SharedPreferences luudkhp;
    DangKyLopMonHocDAO dangKyLopMonHocDAO;
    SharedPreferences luutotalSTC;
    int totalSoTinChi;
    int SoTinChiDaDK;
    String maSV;
    int trongTGDKHP;
    int  trongThoigianDKHP_Lai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hpdadangky, container, false);

        lview = view.findViewById(R.id.lv);
        registeredCourses = new ArrayList<>();
        totalSoTinChi = 0;

        SharedPreferences luuSV = getActivity().getSharedPreferences("SV_info", Context.MODE_PRIVATE);
        maSV = luuSV.getString("MaSV", "No MaSV");
        SharedPreferences checktgDKHP = getActivity().getSharedPreferences("checktgDKHP", Context.MODE_PRIVATE);
        trongTGDKHP = checktgDKHP.getInt("trongTGDKHP", 0);
        SharedPreferences checktgDKHP_Lai =  getActivity().getSharedPreferences("checktgDKHP_Lai", Context.MODE_PRIVATE);
        trongThoigianDKHP_Lai = checktgDKHP_Lai.getInt("trongThoigianDKHP_Lai", 0);

        luudkhp = getActivity().getSharedPreferences("DangKyHocPhan", Context.MODE_PRIVATE);

        Set<String> selectedCourses = luudkhp.getStringSet("SelectedCourses", new HashSet<>());
        dangKyLopMonHocDAO = new DangKyLopMonHocDAO(getActivity());

        if(trongTGDKHP > 0) {
            SoTinChiDaDK = dangKyLopMonHocDAO.getSoTinChiSVDaDangKy(maSV, trongTGDKHP);
        } else if(trongThoigianDKHP_Lai > 0) {
            if (getActivity() instanceof QuanLy_DKMH_HuyMHSV) {
                ((QuanLy_DKMH_HuyMHSV) getActivity()).checkMaSVAndSaveResult(maSV);
            }
            boolean duocPhepDKLai = getKQDK_Lai(getActivity());
            if (duocPhepDKLai) {
                SoTinChiDaDK = dangKyLopMonHocDAO.getSoTinChiSVDaDangKy(maSV,trongThoigianDKHP_Lai);
            } else {
                SoTinChiDaDK = dangKyLopMonHocDAO.getSoTinChiSVDaDangKy(maSV, trongTGDKHP);
            }

        } else SoTinChiDaDK = dangKyLopMonHocDAO.getSoTinChiSVDaDangKy(maSV, trongTGDKHP);

        for (String course : selectedCourses) {
            String[] courseDetails = course.split(";");
            String maMH = courseDetails[0];
            String tenMH = courseDetails[1];
            int soTinChi = Integer.parseInt(courseDetails[2]);
            String ngayBD = courseDetails[3];
            String ngayKT = courseDetails[4];
            registeredCourses.add(new MonHoc(maMH, tenMH, soTinChi, ngayBD, ngayKT));
            totalSoTinChi += soTinChi;
        }

        luutotalSTC = getActivity().getSharedPreferences("totalSTC", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = luutotalSTC.edit();
        editor.putInt("totalSoTinChi", totalSoTinChi);
        editor.apply();

        myadapter = new MonHocDaDangKyAdapter(getActivity(), R.layout.layout_hpdadangky_item, registeredCourses);
        lview.setAdapter(myadapter);

        Button btnXacNhan = view.findViewById(R.id.btnXacNhan);
        btnXacNhan.setOnClickListener(v -> xacnhanDK());

        Button btnQuayLai = view.findViewById(R.id.btnQuayLai);
        btnQuayLai.setOnClickListener(v -> quayLai());

        return view;
    }
    public boolean getKQDK_Lai(Context context) {
        SharedPreferences checkResult = context.getSharedPreferences("checkResult", Context.MODE_PRIVATE);
        return checkResult.getBoolean("choPhepDKLai", false);
    }


    public void updateTotalSoTinChi(int totalSoTinChi) {
        this.totalSoTinChi = totalSoTinChi;
    }
    private void quayLai() {
        Fragment DangKyHPFragment = new DangKyHocPhan();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, DangKyHPFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        if (DangKyHPFragment instanceof DangKyHocPhan) {
            getActivity().setTitle(((DangKyHocPhan) DangKyHPFragment).getTitle());
        }
    }
    private void xacnhanDK() {
        SharedPreferences.Editor editor = luudkhp.edit();
        String ngayDK = getCurrentDate();

        StringBuilder hpdkkthanhcong = new StringBuilder();
        StringBuilder hpdkthanhcong = new StringBuilder();

        for (MonHoc monHoc : registeredCourses) {
            if (dangKyLopMonHocDAO.SVdadangkyMH(maSV, monHoc.getMaMH())) {
                hpdkkthanhcong.append(monHoc.getTenMH()).append("\n");
            } else {
                hpdkthanhcong.append(monHoc.getTenMH()).append("\n");
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = "Thông báo";
        builder.setTitle(title);
        String message = "";

        if (hpdkkthanhcong.length() > 0) {
            message = "Hủy đăng ký các học phần này trước khi đăng ký: \n" + hpdkkthanhcong.toString();
            builder.setMessage(message);
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        } else if(hpdkkthanhcong.length() <= 0) {
            if (SoTinChiDaDK>0) {
                if(totalSoTinChi > 0 && SoTinChiDaDK + totalSoTinChi < 19 && SoTinChiDaDK + totalSoTinChi > 7){
                    message = "Xác nhận đăng ký các học phần: \n" + hpdkthanhcong.toString() + "\nSố tín chỉ đã đăng ký: " + SoTinChiDaDK + "\nSố tín chỉ đăng ký thêm: " + totalSoTinChi + "\nSố tín chỉ sau khi đăng ký thêm: " + (totalSoTinChi + SoTinChiDaDK);
                    builder.setMessage(message);
                    builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                        for (MonHoc monHoc : registeredCourses) {
                            DangKyLopMonHoc dangKyLopMonHoc = new DangKyLopMonHoc(maSV, monHoc.getMaMH(), ngayDK);
                            dangKyLopMonHocDAO.insertDangKyLopMonHoc(dangKyLopMonHoc);
                        }

                        editor.remove("SelectedCourses");
                        editor.apply();

                        sendEmail();

                        new CustomToast(getActivity(), "Đăng ký học phần thành công","Success");
                        dialog.dismiss();
                        quayLai();
                    });
                    builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
                    builder.show();
                } else if (SoTinChiDaDK + totalSoTinChi > 18){
                    message = "Bạn đã đăng ký quá 18 tín chỉ.\nHãy hủy bớt học phần đăng ký.\n\nSố tín chỉ đã đăng ký: " + SoTinChiDaDK + "\nSố tín chỉ đăng ký thêm: " + totalSoTinChi + "\nSố tín chỉ sau khi đăng ký thêm: " + (totalSoTinChi + SoTinChiDaDK);
                    builder.setMessage(message);
                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                    builder.show();
                } else if (SoTinChiDaDK + totalSoTinChi < 8){
                    message = "Bạn đã đăng ký ít hơn 8 tín chỉ.\nHãy đăng ký thêm học phần.\n\nSố tín chỉ đã đăng ký: " + SoTinChiDaDK + "\nSố tín chỉ đăng ký thêm: " + totalSoTinChi + "\nSố tín chỉ sau khi đăng ký thêm: " + (totalSoTinChi + SoTinChiDaDK);
                    builder.setMessage(message);
                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                    builder.show();
                } else if(totalSoTinChi  == 0){
                    message = "Bạn chưa chọn đăng ký học phần nào.\n\nSố tín chỉ đã đăng ký: " + SoTinChiDaDK + "\nSố tín chỉ đăng ký thêm: " + totalSoTinChi + "\nSố tín chỉ sau khi đăng ký thêm: " + (totalSoTinChi + SoTinChiDaDK);
                    builder.setMessage(message);
                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                    builder.show();
                }
            } else{
                if(totalSoTinChi == 0 || totalSoTinChi < 8) {
                    message = "Bạn đã đăng ký ít hơn 8 tín chỉ.\nHãy đăng ký thêm học phần.\n\nSố tín chỉ hiện tại: " + totalSoTinChi;
                    builder.setMessage(message);
                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                    builder.show();
                } else if (totalSoTinChi > 18){
                    message = "Bạn đã đăng ký quá 18 tín chỉ.\nHãy hủy bớt học phần.\n\nSố tín chỉ hiện tại: " + totalSoTinChi;
                    builder.setMessage(message);
                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                    builder.show();
                } else{
                    message = "Xác nhận đăng ký các học phần: \n" + hpdkthanhcong.toString()+"\nSố tín chỉ hiện tại: " + totalSoTinChi;
                    builder.setMessage(message);
                    builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                        for (MonHoc monHoc : registeredCourses) {
                            DangKyLopMonHoc dangKyLopMonHoc = new DangKyLopMonHoc(maSV, monHoc.getMaMH(), ngayDK);
                            dangKyLopMonHocDAO.insertDangKyLopMonHoc(dangKyLopMonHoc);
                        }

                        editor.remove("SelectedCourses");
                        editor.apply();

                        sendEmail();

                        new CustomToast(getActivity(), "Đăng ký học phần thành công","Success");
                        dialog.dismiss();
                        quayLai();
                    });
                    builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
                    builder.show();
                }
            }
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void sendEmail(){
        SharedPreferences luuSV = getActivity().getSharedPreferences("SV_info", Context.MODE_PRIVATE);
        String emailadd = luuSV.getString("Email", "Không có Email");
        String maSV = luuSV.getString("MaSV", "Không có MSSV");
        String tenSV = luuSV.getString("TenSV", "Không có tên SV");
        String maKhoa = luuSV.getString("MaKhoa", "Không có mã Khoa SV");

        KhoaDAO khoaDAO = new KhoaDAO(getActivity());
        String tenKhoa = khoaDAO.getTenKhoaByMaKhoa(maKhoa);
        List<MonHoc> hocPhanDaDK;
        DangKyLopMonHocDAO dangKyLopMonHocDAO = new DangKyLopMonHocDAO(getActivity());
        if (trongTGDKHP > 0 ){
            hocPhanDaDK = dangKyLopMonHocDAO.getMonHocDaDangKy(maSV, trongTGDKHP);
        } else
        {
            hocPhanDaDK = dangKyLopMonHocDAO.getMonHocDaDangKy(maSV, trongThoigianDKHP_Lai);
        }

        StringBuilder emailContent = new StringBuilder("Sinh viên: ").append(tenSV).append("\n");
        emailContent.append("Mã số sinh viên: ").append(maSV).append("\n");
        emailContent.append("Thuộc khoa: ").append(tenKhoa).append("\n\n");

        emailContent.append("Sinh viên đã đăng ký thành công các học phần: \n");
        for (MonHoc monHoc : hocPhanDaDK) {
            emailContent.append("Tên học phần: ").append(monHoc.getTenMH())
                    .append("\nSố tín chỉ: ").append(monHoc.getSoTinChi())
                    .append("\nNgày bắt đầu: ").append(monHoc.getNgayBD())
                    .append("\nNgày kết thúc: ").append(monHoc.getNgayKT())
                    .append("\n\n");
        }


        emailContent.append("Tổng số tín chỉ đã đăng ký: ").append(totalSoTinChi + SoTinChiDaDK);

        JavaMailAPI javaMailAPI = new JavaMailAPI(getActivity(), emailadd, "XÁC NHẬN ĐĂNG KÝ HỌC PHẦN", emailContent.toString());
        javaMailAPI.execute();
    }
    public String getTitle() {
        return "Học phần đã chọn đăng ký";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dangKyLopMonHocDAO.close();
    }
}
