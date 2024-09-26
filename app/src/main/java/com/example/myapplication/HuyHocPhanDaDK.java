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

import com.example.myapplication.Adapter.HuyHocPhanDaDKAdapter;
import com.example.myapplication.DAO.DangKyLopMonHocDAO;
import com.example.myapplication.DAO.KhoaDAO;
import com.example.myapplication.Model.MonHoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HuyHocPhanDaDK extends Fragment {
    ListView lvSVHuyHP;
    ArrayList<MonHoc> dsHPxoa;
    HuyHocPhanDaDKAdapter myadapter;
    SharedPreferences luuhuydkhp;
    DangKyLopMonHocDAO dangKyLopMonHocDAO;
    String maSV;
    int tongSTChuy;
    int SoTinChiDaDK;
    int trongTGDKHP;
    int trongThoigianDKHP_Lai;
    SharedPreferences luutongSTChuy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_huyhpdadk, container, false);

        SharedPreferences luuSV = getActivity().getSharedPreferences("SV_info", Context.MODE_PRIVATE);
        maSV = luuSV.getString("MaSV", "");
        SharedPreferences checktgDKHP = getActivity().getSharedPreferences("checktgDKHP", Context.MODE_PRIVATE);
        trongTGDKHP = checktgDKHP.getInt("trongTGDKHP", 0);

        SharedPreferences checktgDKHP_Lai =  getActivity().getSharedPreferences("checktgDKHP_Lai", Context.MODE_PRIVATE);
        trongThoigianDKHP_Lai = checktgDKHP_Lai.getInt("trongThoigianDKHP_Lai", 0);

        lvSVHuyHP = view.findViewById(R.id.lvSVHuyHP);
        dsHPxoa = new ArrayList<>();
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

        tongSTChuy = 0;

        luuhuydkhp = getActivity().getSharedPreferences("SVHuyDangKyHocPhan", Context.MODE_PRIVATE);
        Set<String> huyDKHP = luuhuydkhp.getStringSet("huyDKHP", new HashSet<>());


        for (String course : huyDKHP) {
            String[] courseDetails = course.split(";");
            String maMH = courseDetails[0];
            String tenMH = courseDetails[1];
            int soTinChi = Integer.parseInt(courseDetails[2]);
            String ngayBD = courseDetails[3];
            String ngayKT = courseDetails[4];
            dsHPxoa.add(new MonHoc(maMH, tenMH, soTinChi, ngayBD, ngayKT));
            tongSTChuy += soTinChi;
        }

        luutongSTChuy = getActivity().getSharedPreferences("tongSTChuy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = luutongSTChuy.edit();
        editor.putInt("tongSTChuy", tongSTChuy);
        editor.apply();


        myadapter = new HuyHocPhanDaDKAdapter(getActivity(), R.layout.layout_huyhpdadk_item, dsHPxoa);
        lvSVHuyHP.setAdapter(myadapter);

        Button btnXacNhan = view.findViewById(R.id.btnSVXNHuyHP);
        btnXacNhan.setOnClickListener(v -> xacnhanHuyDK());

        Button btnQuayLai = view.findViewById(R.id.btnSVQL);
        btnQuayLai.setOnClickListener(v -> quayLai());

        return view;
    }
    public void updateTotalSoTinChi(int tongSTChuy) {
        this.tongSTChuy = tongSTChuy;
    }
    public boolean getKQDK_Lai(Context context) {
        SharedPreferences checkResult = context.getSharedPreferences("checkResult", Context.MODE_PRIVATE);
        return checkResult.getBoolean("choPhepDKLai", false);
    }
    public String getTitle() {
        return "Học phần muốn hủy đăng ký";
    }
    private void xacnhanHuyDK(){
        int soTCconlai = SoTinChiDaDK - tongSTChuy;

        DangKyLopMonHocDAO dangKyLopMonHocDAO = new DangKyLopMonHocDAO(getActivity());
        SharedPreferences.Editor editor = luuhuydkhp.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        if(tongSTChuy==0){
            builder.setMessage("Bạn chưa chọn học phần nào để huỷ.\n\nHãy chọn học phần để hủy đăng ký");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        } else if(soTCconlai<8){
            StringBuilder hpHuy = new StringBuilder();

            for (MonHoc monHoc : dsHPxoa) {
                hpHuy.append(monHoc.getTenMH()).append("\n");
            }
            builder.setMessage("Nếu bạn hủy các học phần đã chọn, vui lòng đăng ký thêm các học phần khác để đáp ứng quy định đăng ký tối thiểu 8 tín chỉ trong 1 học kỳ.\n\nXác nhận hủy các học phần: \n" + hpHuy.toString()+"\nSố tín chỉ đã đăng ký: " + SoTinChiDaDK + "\nSố tín chỉ đã chọn hủy: " + tongSTChuy+"\nSố tín chỉ sau khi hủy: " + soTCconlai);
            builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                for (MonHoc monHoc : dsHPxoa) {
                    dangKyLopMonHocDAO.deleteDangKyLopMonHoc(maSV, monHoc.getMaMH());
                }
                editor.remove("huyDKHP");
                editor.apply();

                sendEmail();

                new CustomToast(getActivity(), "Hủy học phần thành công","Success");
                dialog.dismiss();
                quayLai();
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
            builder.show();
        }
        else {
            StringBuilder hpHuy = new StringBuilder();

            for (MonHoc monHoc : dsHPxoa) {
                hpHuy.append(monHoc.getTenMH()).append("\n");
            }
            builder.setMessage("Xác nhận hủy các học phần: \n" + hpHuy.toString()+"\nSố tín chỉ đã đăng ký: " + SoTinChiDaDK + "\nSố tín chỉ đã chọn hủy: " + tongSTChuy+"\nSố tín chỉ sau khi hủy: " + soTCconlai);
            builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                for (MonHoc monHoc : dsHPxoa) {
                    dangKyLopMonHocDAO.deleteDangKyLopMonHoc(maSV, monHoc.getMaMH());
                }
                editor.remove("huyDKHP");
                editor.apply();

                sendEmail();

                new CustomToast(getActivity(), "Hủy học phần thành công","Success");
                dialog.dismiss();
                quayLai();
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
            builder.show();
        }
    }
    private void quayLai() {
        Fragment lsDKHPFragment = new LichSuDKHP();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, lsDKHPFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        if (lsDKHPFragment instanceof LichSuDKHP) {
            getActivity().setTitle(((LichSuDKHP) lsDKHPFragment).getTitle());
        }
    }
    private void sendEmail(){
        SharedPreferences luuSV = getActivity().getSharedPreferences("SV_info", Context.MODE_PRIVATE);
        String emailadd = luuSV.getString("Email", "Không có Email");
        String maSV = luuSV.getString("MaSV", "Không có MSSV");
        String tenSV = luuSV.getString("TenSV", "Không có tên SV");
        String maKhoa = luuSV.getString("MaKhoa", "Không có mã Khoa SV");

        KhoaDAO khoaDAO = new KhoaDAO(getActivity());
        String tenKhoa = khoaDAO.getTenKhoaByMaKhoa(maKhoa);
        DangKyLopMonHocDAO dangKyLopMonHocDAO = new DangKyLopMonHocDAO(getActivity());
        List<MonHoc> hocPhanDaDK = dangKyLopMonHocDAO.getMonHocDaDangKy(maSV, trongTGDKHP);

        StringBuilder emailContent = new StringBuilder("Sinh viên: ").append(tenSV).append("\n");
        emailContent.append("Mã số sinh viên: ").append(maSV).append("\n");
        emailContent.append("Thuộc khoa: ").append(tenKhoa).append("\n\n");

        emailContent.append("Sinh viên đã huỷ đăng ký thành công các học phần:\n");
        for (MonHoc monHoc : dsHPxoa) {
            emailContent.append("Tên học phần: ").append(monHoc.getTenMH())
                    .append("\nSố tín chỉ: ").append(monHoc.getSoTinChi())
                    .append("\nNgày bắt đầu: ").append(monHoc.getNgayBD())
                    .append("\nNgày kết thúc: ").append(monHoc.getNgayKT())
                    .append("\n\n");
        }

        if(!hocPhanDaDK.isEmpty()){
            emailContent.append("Các học phần Sinh viên đã đăng ký hiện tại: \n");
            for (MonHoc monHoc : hocPhanDaDK) {
                emailContent.append("Tên học phần: ").append(monHoc.getTenMH())
                        .append("\nSố tín chỉ: ").append(monHoc.getSoTinChi())
                        .append("\nNgày bắt đầu: ").append(monHoc.getNgayBD())
                        .append("\nNgày kết thúc: ").append(monHoc.getNgayKT())
                        .append("\n\n");
            }
        }

        emailContent.append("Tổng số tín chỉ đã hủy đăng ký: ").append(tongSTChuy).append("\n");
        int soTCconlai = SoTinChiDaDK - tongSTChuy;
        emailContent.append("Tổng số tín chỉ đã đăng ký trong kỳ hiện tại sau khi hủy đăng ký: ").append(soTCconlai);
        if(soTCconlai<8){
            emailContent.append("\n\nSINH VIÊN NHANH CHÓNG ĐĂNG KÝ THÊM TÍN CHỈ ĐỂ ĐẢM BẢO ĐĂNG KÝ TỐI THIỂU 8 TÍN CHỈ TRONG 1 HỌC KỲ.");
        }

        JavaMailAPI javaMailAPI = new JavaMailAPI(getActivity(), emailadd, "XÁC NHẬN HỦY ĐĂNG KÝ HỌC PHẦN", emailContent.toString());
        javaMailAPI.execute();
    }

}