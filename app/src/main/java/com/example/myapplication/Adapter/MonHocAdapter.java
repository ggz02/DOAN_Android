package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.myapplication.Model.MonHoc;
import com.example.myapplication.Model.MonHocKhoa;
import com.example.myapplication.Model.SinhVienKhoa;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MonHocAdapter extends BaseAdapter {
    private Context context;
    private List<MonHocKhoa> dataList;
    private List<MonHocKhoa> originalList;
    private LayoutInflater inflater;

    public MonHocAdapter(Context context, List<MonHocKhoa> dataList) {
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
                    List<MonHocKhoa> filtered = new ArrayList<>();
                    for (MonHocKhoa mh : originalList) {
                        if (mh.getTenMH().toLowerCase().contains(charString.toLowerCase())) {
                            filtered.add(mh);
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
                dataList = (List<MonHocKhoa>) results.values;
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
            convertView = inflater.inflate(R.layout.custom_list_item_mh, parent, false);
        }

        MonHocKhoa monHoc = dataList.get(position);
        TextView tvMa = convertView.findViewById(R.id.tvMa);
        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvKhoa = convertView.findViewById(R.id.tvKhoa);
        TextView tvHocKi = convertView.findViewById(R.id.tvHocKi);
        TextView tvSoTinChi = convertView.findViewById(R.id.tvSoTinChi);
        TextView tvNgayBD = convertView.findViewById(R.id.tvNBD);
        TextView tvNgayKT = convertView.findViewById(R.id.tvNKT);

        tvMa.setText(monHoc.getMaMH());
        tvTen.setText(monHoc.getTenMH());
        tvNgayBD.setText("Ngày Bắt Đầu: " + monHoc.getNgayBD());
        tvNgayKT.setText("Ngày Kết Thúc: "+ monHoc.getNgayKT());
        tvKhoa.setText("Khoa: "+  monHoc.getTenKhoa());
        tvHocKi.setText("Học Kì: Học Kì "+  monHoc.getHocKi());
        tvSoTinChi.setText("Số Tín Chỉ: "+  monHoc.getSoTinChi());


        return convertView;
    }
}
