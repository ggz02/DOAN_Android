package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.myapplication.DAO.DangKyLopMonHocDAO;
import com.example.myapplication.DAO.MonHocDAO;
import com.example.myapplication.DAO.SinhVienDAO;
import com.example.myapplication.Model.HocKiTK;
import com.example.myapplication.Model.MonHocTK;
import com.example.myapplication.Model.SinhVienTK;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ThongKe extends AppCompatActivity {

    private Spinner spinerThongKeTheo;
    private Button btnXuatBieuDo;
    private Button btnXoaBieuDo;

    private TableLayout tableSinhVien;
    private TableLayout tableHocKi;
    private TableLayout tableMonHoc;

    String valueSelected = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thong_ke);
        Toolbar toolbar = findViewById(R.id.toolbarThongKe);
        toolbar.setTitle("Thống Kê");
        setSupportActionBar(toolbar);

        btnXoaBieuDo = findViewById(R.id.btnXoaBieuDo);
        btnXuatBieuDo = findViewById(R.id.btnXuatBieuDo);
        spinerThongKeTheo = findViewById(R.id.spinnerTKTheo);

        tableMonHoc = findViewById(R.id.monHocTable);
        tableHocKi = findViewById(R.id.hocKiTable);
        tableSinhVien = findViewById(R.id.sinhVienTable);
        ArrayList<String> listThongKeTheo = new ArrayList<>();
        listThongKeTheo.add("-- Chọn --");
        listThongKeTheo.add("Sinh Viên");
        listThongKeTheo.add("Môn Học");
        listThongKeTheo.add("Học Kì");



        ArrayAdapter<String> adapterThongKe = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listThongKeTheo);
        adapterThongKe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerThongKeTheo.setAdapter(adapterThongKe);

        /*deleteScatterChartMH();*/
        deleteLineChart();
        deletePieChart();
        deleteBarChart();

        spinerThongKeTheo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*deleteScatterChartMH();*/
                deleteBarChart();
                deleteLineChart();
                deletePieChart();

                valueSelected = parent.getItemAtPosition(position).toString();
                switch (valueSelected) {
                    case "-- Chọn --":
                        tableHocKi.setVisibility(View.GONE);
                        tableMonHoc.setVisibility(View.GONE);
                        tableSinhVien.setVisibility(View.GONE);
                        break;
                    case "Sinh Viên":
                        tableHocKi.setVisibility(View.GONE);
                        tableMonHoc.setVisibility(View.GONE);
                        tableSinhVien.setVisibility(View.VISIBLE);
                        createTableSinhVien();

                        break;
                    case "Môn Học":
                        tableHocKi.setVisibility(View.GONE);
                        tableMonHoc.setVisibility(View.VISIBLE);
                        tableSinhVien.setVisibility(View.GONE);
                        createTableMonHoc();
                        break;
                    case "Học Kì":
                        tableHocKi.setVisibility(View.VISIBLE);
                        tableMonHoc.setVisibility(View.GONE);
                        tableSinhVien.setVisibility(View.GONE);
                        createTableHocKi();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnXuatBieuDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valueSelected.equals("Sinh Viên")) {
                    createLineChart();
                }else if (valueSelected.equals("Môn Học")) {
                    /*createScatterChartMonHoc();*/
                    createBarCharMH();
                } else if (valueSelected.equals("Học Kì")){
                    createPieChart();
                } else {
                    deletePieChart();
                    deletePieChart();
                }
            }
        });

        btnXoaBieuDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLineChart();
                /*deleteScatterChartMH();*/
                deleteBarChart();
                deletePieChart();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu_trangchu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.btnTrangChu2) {
            Intent intent = new Intent(ThongKe.this, TrangChu.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void createTableSinhVien() {
        SinhVienDAO sinhVienDAO = new SinhVienDAO(ThongKe.this);
        List<SinhVienTK> listSV = sinhVienDAO.getListSVTK();

        for (SinhVienTK sv: listSV) {
            TableRow tableRow = new TableRow(ThongKe.this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView colMaSV = new TextView(ThongKe.this);
            colMaSV.setText(sv.getMaSV());
            TableRow.LayoutParams paramsMaSV = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colMaSV.setLayoutParams(paramsMaSV);
            colMaSV.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colMaSV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colMaSV.setGravity(Gravity.LEFT);
            tableRow.addView(colMaSV);

            TextView colHoTen = new TextView(ThongKe.this);
            colHoTen.setText(sv.getHoTen());
            TableRow.LayoutParams paramsHoTen = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colHoTen.setLayoutParams(paramsHoTen);
            colHoTen.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colHoTen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colHoTen.setGravity(Gravity.LEFT);
            tableRow.addView(colHoTen);

            TextView colTenKhoa = new TextView(ThongKe.this);
            colTenKhoa.setText(sv.getTenKhoa());
            TableRow.LayoutParams paramsTenKhoa = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colTenKhoa.setLayoutParams(paramsTenKhoa);
            colTenKhoa.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colTenKhoa.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colTenKhoa.setGravity(Gravity.LEFT);
            tableRow.addView(colTenKhoa);

            TextView colSoTinChi = new TextView(ThongKe.this);
            colSoTinChi.setText(sv.getSoTinChi());
            TableRow.LayoutParams paramsSoTinChi = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colSoTinChi.setLayoutParams(paramsSoTinChi);
            colSoTinChi.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colSoTinChi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colSoTinChi.setGravity(Gravity.LEFT);
            tableRow.addView(colSoTinChi);

            tableSinhVien.addView(tableRow);
        }
    }

    public void createTableMonHoc() {
        MonHocDAO monHocDAO = new MonHocDAO(ThongKe.this);
        List<MonHocTK> listMH = monHocDAO.getListMHTK();

        for (MonHocTK mh: listMH) {
            TableRow tableRow = new TableRow(ThongKe.this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView colMaMH = new TextView(ThongKe.this);
            colMaMH.setText(mh.getMaMH());
            TableRow.LayoutParams paramsMaSV = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colMaMH.setLayoutParams(paramsMaSV);
            colMaMH.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colMaMH.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colMaMH.setGravity(Gravity.LEFT);
            tableRow.addView(colMaMH);

            TextView colTenMH = new TextView(ThongKe.this);
            colTenMH.setText(mh.getTenMH());
            TableRow.LayoutParams paramsHoTen = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colTenMH.setLayoutParams(paramsHoTen);
            colTenMH.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colTenMH.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colTenMH.setGravity(Gravity.LEFT);
            tableRow.addView(colTenMH);

            TextView colSoTC = new TextView(ThongKe.this);
            colSoTC.setText(mh.getSoTinChi());
            TableRow.LayoutParams paramsTenKhoa = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colSoTC.setLayoutParams(paramsTenKhoa);
            colSoTC.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colSoTC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colSoTC.setGravity(Gravity.LEFT);
            tableRow.addView(colSoTC);

            TextView colSoSV = new TextView(ThongKe.this);
            colSoSV.setText(mh.getSoSV());
            TableRow.LayoutParams paramsSoTinChi = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colSoSV.setLayoutParams(paramsSoTinChi);
            colSoSV.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colSoSV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colSoSV.setGravity(Gravity.LEFT);
            tableRow.addView(colSoSV);

            tableMonHoc.addView(tableRow);
        }
    }

    public void createTableHocKi(){
        DangKyLopMonHocDAO dangKyLopMonHocDAO = new DangKyLopMonHocDAO(ThongKe.this);
        List<HocKiTK> listHK = dangKyLopMonHocDAO.getListHKTK();

        for (HocKiTK hk: listHK) {
            TableRow tableRow = new TableRow(ThongKe.this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView colHocKi = new TextView(ThongKe.this);
            colHocKi.setText(hk.getHocKi());
            TableRow.LayoutParams paramsMaSV = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colHocKi.setLayoutParams(paramsMaSV);
            colHocKi.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colHocKi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colHocKi.setGravity(Gravity.LEFT);
            tableRow.addView(colHocKi);

            TextView colSoLuongSV = new TextView(ThongKe.this);
            colSoLuongSV.setText(hk.getSoLuongSV());
            TableRow.LayoutParams paramsHoTen = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colSoLuongSV.setLayoutParams(paramsHoTen);
            colSoLuongSV.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colSoLuongSV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colSoLuongSV.setGravity(Gravity.LEFT);
            tableRow.addView(colSoLuongSV);

            TextView colSoMonHoc = new TextView(ThongKe.this);
            colSoMonHoc.setText(hk.getSoMonHoc());
            TableRow.LayoutParams paramsTenKhoa = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4);
            colSoMonHoc.setLayoutParams(paramsTenKhoa);
            colSoMonHoc.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()),
                    10, 10, 10);
            colSoMonHoc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            colSoMonHoc.setGravity(Gravity.LEFT);
            tableRow.addView(colSoMonHoc);



            tableHocKi.addView(tableRow);
        }
    }


    public void createBarCharMH(){
        BarChart barChart = findViewById(R.id.barcharMH);  // Make sure the ID matches your layout
        barChart.setVisibility(View.VISIBLE);

        TextView titleChart = findViewById(R.id.titleChart);
        titleChart.setText("Biểu Đồ Thống Kê Theo Môn Học");
        titleChart.setVisibility(View.VISIBLE);

        MonHocDAO monHocDAO = new MonHocDAO(ThongKe.this);
        List<MonHocTK> listMH = monHocDAO.getListMHTK();

        List<String> nameMHX = new ArrayList<>();

        ArrayList<BarEntry> entries = new ArrayList<>();
        int index = 0;
        for (MonHocTK mh : listMH) {
            entries.add(new BarEntry(index, Float.valueOf(mh.getSoSV())));
            nameMHX.add(mh.getTenMH());
            index++;
            if (index == 5) {
                break;
            }
        }

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(8f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        BarDataSet dataSet = new BarDataSet(entries, "Số Tín Chỉ");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barData.setValueTextSize(12f);
        barData.setBarWidth(0.5f);

        barChart.getDescription().setEnabled(false);
        barChart.invalidate();

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(nameMHX));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(2f);
        barChart.getXAxis().setGranularityEnabled(true);


        barChart.setFitBars(true); // Make the x-axis fit exactly all bars
        barChart.animateY(1000);
    }

    public void deleteBarChart(){
        BarChart barChart = findViewById(R.id.barcharMH);
        barChart.setVisibility(View.GONE);

        TextView titleChart = findViewById(R.id.titleChart);
        titleChart.setVisibility(View.GONE);
    }

    public void createLineChart() {
        LineChart lineChart = findViewById(R.id.lineChartSV);
        lineChart.setVisibility(View.VISIBLE);



        TextView titleChart = findViewById(R.id.titleChart);
        titleChart.setText("Biểu Đồ Thống Kê Theo Sinh Viên");
        titleChart.setVisibility(View.VISIBLE);

        SinhVienDAO sinhVienDAO = new SinhVienDAO(ThongKe.this);
        List<SinhVienTK> listSV = sinhVienDAO.getListSVTK();

        ArrayList<Entry> entries = new ArrayList<>();
        int index = 0;
        for (SinhVienTK sv : listSV) {
            entries.add(new Entry(index, Float.parseFloat(sv.getSoTinChi())));
            index++;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Số Tín Chỉ");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setCircleColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(10f);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        lineChart.animateX(1000);
        lineChart.invalidate();
    }

    public void deleteLineChart() {
        LineChart lineChart = findViewById(R.id.lineChartSV);
        lineChart.setVisibility(View.GONE);

        TextView titleChart = findViewById(R.id.titleChart);
        titleChart.setVisibility(View.GONE);
    }

    public void createPieChart(){
        PieChart pieChart = findViewById(R.id.pieChartHocKi);
        pieChart.setVisibility(View.VISIBLE);

        TextView titleChart = findViewById(R.id.titleChart);
        titleChart.setText("Biểu Đồ Thống Kê Theo Học Kì");
        titleChart.setVisibility(View.VISIBLE);

        DangKyLopMonHocDAO hocKiDao = new DangKyLopMonHocDAO(this);
        List<HocKiTK> listHK = hocKiDao.getListHKTK();

        ArrayList<PieEntry> entries = new ArrayList<>();
        int index = 0;
        for (HocKiTK hk : listHK) {
            entries.add(new PieEntry(Float.parseFloat(hk.getSoLuongSV()),"Học Kì " + hk.getHocKi() ));
            index++;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Học Kì");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);
        dataSet.setSliceSpace(3f);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    public void deletePieChart(){
        PieChart pieChart = findViewById(R.id.pieChartHocKi);
        pieChart.setVisibility(View.GONE);

        TextView titleChart = findViewById(R.id.titleChart);
        titleChart.setText("Biểu Đồ Thống Kê Theo Học Kì");
        titleChart.setVisibility(View.GONE);
    }


}
