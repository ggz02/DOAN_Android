package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.myapplication.Model.SinhVienKhoa;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SinhVienAdapter extends BaseAdapter {
    private Context context;
    private List<SinhVienKhoa> dataList;

    private List<SinhVienKhoa> originalList;
    private LayoutInflater inflater;

    public SinhVienAdapter(Context context, List<SinhVienKhoa> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.originalList = new ArrayList<>(dataList);
        this.inflater = LayoutInflater.from(context);
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    dataList = new ArrayList<>(originalList); // Reset to original list
                } else {
                    List<SinhVienKhoa> filtered = new ArrayList<>();
                    for (SinhVienKhoa sv : originalList) {
                        if (sv.getHoTen().toLowerCase().contains(charString.toLowerCase())) {
                            filtered.add(sv);
                        }
                    }
                    dataList = filtered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataList = (List<SinhVienKhoa>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
        }

        SinhVienKhoa sinhVienInfo = dataList.get(position);

        TextView tvMa = convertView.findViewById(R.id.tvMa);
        TextView tvHoTen = convertView.findViewById(R.id.tvTen);
        TextView tvNgaySinh = convertView.findViewById(R.id.tvNS);
        TextView tvGioiTinh = convertView.findViewById(R.id.tvGT);
        TextView tvDiaChi = convertView.findViewById(R.id.tvDC);
        TextView tvKhoa = convertView.findViewById(R.id.tvKhoa);
        TextView tvDienThoai = convertView.findViewById(R.id.tvDT);
        TextView tvTaiKhoan = convertView.findViewById(R.id.tvTK);
        TextView tvMatKhau = convertView.findViewById(R.id.tvMK);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);

        tvMa.setText(sinhVienInfo.getMaSV());
        tvHoTen.setText(sinhVienInfo.getHoTen());
        tvNgaySinh.setText("Ngày Sinh: " + sinhVienInfo.getNgaySinh());
        tvGioiTinh.setText("Giới Tính: "+ sinhVienInfo.getGioiTinh());
        tvDiaChi.setText("Địa Chỉ: "+  sinhVienInfo.getDiaChi());
        tvKhoa.setText("Khoa: "+  sinhVienInfo.getTenKhoa());
        tvDienThoai.setText("Số Điện Thoại: "+  sinhVienInfo.getDienThoai());
        tvTaiKhoan.setText("Tài Khoản: "+  sinhVienInfo.getTaiKhoan());
        tvMatKhau.setText( "Mật Khẩu: "+ sinhVienInfo.getMatKhau());
        tvEmail.setText("Email: "+ sinhVienInfo.getEmail());


        return convertView;
    }
}

