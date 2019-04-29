package com.aspirantlab.salesagent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class CustomerData extends MainActivity implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout relativeLayout;
    String cusname,pnumber,mailid,address,cusorders,cusdispatch,cuspaid;
    TextView custname,phnumber,emailid,addres,orders,dispatch,paid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.customer_data, null, false);
        drawer.addView(contentView, 0);
        custname = (TextView) findViewById(R.id.cusname);
        phnumber = (TextView) findViewById(R.id.phonenumber);
        emailid = (TextView) findViewById(R.id.mailid);
        addres = (TextView) findViewById(R.id.address);
        orders = (TextView) findViewById(R.id.orders);
        dispatch = (TextView) findViewById(R.id.dispatch);
        paid = (TextView) findViewById(R.id.paid);


        cusname = getIntent().getStringExtra("CusName");
        pnumber = getIntent().getStringExtra("Pnumber");
        mailid = getIntent().getStringExtra("Mailid");
        address = getIntent().getStringExtra("Address");
        cusorders = getIntent().getStringExtra("Orders");
        cusdispatch = getIntent().getStringExtra("Dispatch");
        cuspaid = getIntent().getStringExtra("Paid");

        custname.setText(cusname);
        phnumber.setText(pnumber);
        emailid.setText(mailid);
        addres.setText(address);
        orders.setText(cusorders);
        dispatch.setText(cusdispatch);
        paid.setText(cuspaid);

        relativeLayout = (RelativeLayout)findViewById(R.id.mainfile);
        relativeLayout.setVisibility(View.GONE);

        BarChart chart1 = (BarChart) findViewById(R.id.barchart1);
        BarChart chart2 = (BarChart) findViewById(R.id.barchart2);
        ArrayList<BarEntry>NoOfEmp = new ArrayList();
        ArrayList<BarEntry>NoOfEmp1 = new ArrayList();


        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));
        NoOfEmp.add(new BarEntry(148f, 5));
        NoOfEmp.add(new BarEntry(1501f, 6));
        NoOfEmp.add(new BarEntry(15f, 7));
        NoOfEmp.add(new BarEntry(1578f, 8));
        NoOfEmp.add(new BarEntry(1152f, 9));
        NoOfEmp.add(new BarEntry(165f, 10));
        NoOfEmp.add(new BarEntry(1695f, 11));

        NoOfEmp1.add(new BarEntry(1945f, 0));
        NoOfEmp1.add(new BarEntry(440f, 1));
        NoOfEmp1.add(new BarEntry(133f, 2));
        NoOfEmp1.add(new BarEntry(240f, 3));
        NoOfEmp1.add(new BarEntry(1369f, 4));
        NoOfEmp1.add(new BarEntry(148f, 5));
        NoOfEmp1.add(new BarEntry(1501f, 6));
        NoOfEmp1.add(new BarEntry(15f, 7));
        NoOfEmp1.add(new BarEntry(178f, 8));
        NoOfEmp1.add(new BarEntry(552f, 9));
        NoOfEmp1.add(new BarEntry(665f, 10));
        NoOfEmp1.add(new BarEntry(165f, 11));



        ArrayList<String>year = new ArrayList();

        year.add("Jan");
        year.add("Feb");
        year.add("Mar");
        year.add("Apr");
        year.add("May");
        year.add("Jun");
        year.add("Jul");
        year.add("Aug");
        year.add("Sep");
        year.add("Oct");
        year.add("Nov");
        year.add("Dec");

        BarDataSet bardataset1 = new BarDataSet(NoOfEmp, "Apparel");
        bardataset1.setColors(ColorTemplate.COLORFUL_COLORS);
        BarDataSet bardataset2 = new BarDataSet(NoOfEmp1, "Textiles");
        bardataset2.setColors(ColorTemplate.COLORFUL_COLORS);

        chart1.animateY(5000);
        chart2.animateY(5000);
        BarData data = new BarData(year,bardataset1);
        chart1.setData(data);
        BarData data1 = new BarData(year,bardataset2);
        chart2.setData(data1);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(getBaseContext(), CustomerList.class);
            startActivity(intent);
        }
    }

}
