package com.aspirantlab.salesagent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aspirantlab.salesagent.Database.DatabaseHelper;
import com.aspirantlab.salesagent.Services.GetURL;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener  {
    String MY_PREFS_NAME ,User_ID,Session_ID,productstatus;
    Dialog dialog;
    View view;
    private Boolean isFabOpen = false;
    private FloatingActionButton mainfab,home,apparel,textile;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    DatabaseHelper databaseHelper;
    NetworkInfo netInfo;
    ConnectivityManager connManager;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(

                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        RelativeLayout header = (RelativeLayout) headerview.findViewById(R.id.dashboard);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
            }
        });
        connManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        netInfo = connManager.getActiveNetworkInfo();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        User_ID = prefs.getString("userid", null);
        Session_ID = prefs.getString("sessionid", null);
        productstatus = prefs.getString("productstatus", "null");
        databaseHelper = new DatabaseHelper(this);

        mainfab = (FloatingActionButton)findViewById(R.id.mainfab);
        home = (FloatingActionButton)findViewById(R.id.home);
        apparel = (FloatingActionButton)findViewById(R.id.apparel);
        textile = (FloatingActionButton)findViewById(R.id.textile);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.xml.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.xml.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.xml.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.xml.rotate_backward);
        mainfab.setOnClickListener(this);
        home.setOnClickListener(this);
        apparel.setOnClickListener(this);
        textile.setOnClickListener(this);

        if(netInfo != null) {
            if(!productstatus.equals("true")){
                new productlist().execute();
            }
        }


        BarChart chart = (BarChart) findViewById(R.id.barchart);
        ArrayList NoOfEmp = new ArrayList();

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



        ArrayList year = new ArrayList();

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

        BarDataSet bardataset1 = new BarDataSet(NoOfEmp, "Shirt");
        bardataset1.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.animateY(5000);
         BarData data = new BarData(year,bardataset1);
        chart.setData(data);


    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.mainfab:

                animateFAB();
                break;
            case R.id.home:

                Intent i=new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);

                break;
            case R.id.apparel:

                Intent a=new Intent(getBaseContext(),ApparelsOrderList.class);
                startActivity(a);

                break;
            case R.id.textile:

                Intent b=new Intent(getBaseContext(),TextilesOrderList.class);
                startActivity(b);

                break;
        }
    }
    public void animateFAB(){

        if(isFabOpen){

            mainfab.startAnimation(rotate_backward);
            home.startAnimation(fab_close);
            apparel.startAnimation(fab_close);
            textile.startAnimation(fab_close);
            home.setClickable(false);
            apparel.setClickable(false);
            textile.setClickable(false);
            isFabOpen = false;


        } else {

            mainfab.startAnimation(rotate_forward);
            home.startAnimation(fab_open);
            apparel.startAnimation(fab_open);
            textile.startAnimation(fab_open);
            home.setClickable(true);
            apparel.setClickable(true);
            textile.setClickable(true);
            isFabOpen = true;


        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_shirt) {
            Intent i=new Intent(getBaseContext(), ApparelsOrderList.class);
            startActivity(i);
        }
        else if (id == R.id.nav_dhoti) {
            Intent i=new Intent(getBaseContext(), TextilesOrderList.class);
            startActivity(i);
        }
//        else if (id == R.id.nav_orderform) {
//            Intent i=new Intent(getBaseContext(),OrderFormList.class);
//            startActivity(i);
//        }
        else if (id == R.id.nav_customer) {
            Intent i=new Intent(getBaseContext(),CustomerList.class);
            startActivity(i);
        }
        else if (id == R.id.nav_product) {
            Intent i=new Intent(getBaseContext(),ProductList.class);
            startActivity(i);
        }
        else if (id == R.id.nav_tools) {
            databaseHelper.removeproducttable();
            databaseHelper.removecustomertable();
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putBoolean("loginstatus",false);
            editor.putString("productstatus","null");
            editor.commit();
            editor.apply();
            Intent i=new Intent(getBaseContext(),LoginActivity.class);
            startActivity(i);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    class productlist extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Product data downloading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonParams = new JSONObject();
            JSONObject params1 = new JSONObject();
            JSONObject params2 = new JSONObject();
            JSONObject params3 = new JSONObject();
            JSONObject params4 = new JSONObject();
            JSONArray array = new JSONArray();


            HttpClient client = new DefaultHttpClient();

            // Prepare a request object
            HttpPost post = new HttpPost(GetURL.getproductlist());
            post.setHeader("Cookie","session_id=" + Session_ID);
            post.setHeader("Content-type", "application/json");
            try {
                params1.put("model", "product.template");
                params1.put("method", "get_product_temp_data");
                params1.put("kwargs", params2);
                params1.put("args",params3);


                jsonParams.put("id", "ID");
                jsonParams.put("method", "call");
                jsonParams.put("params", params1);
                jsonParams.put("jsonrpc", "2.0");

                StringEntity se = new StringEntity(jsonParams.toString());
                // post.setHeader("Content-length", "" + se.getContentLength()); //header has to be set after jsonparams are complete

                post.setEntity(se);

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // Execute the request
            try {
                HttpResponse response = client.execute(post);
                Log.d("log_response: ", response.getStatusLine().toString());

                // Get hold the response entity
                HttpEntity entity = response.getEntity();

                // if the response does not enclose the entity, there is no need
                // to worry about it

                if (entity != null) {
                    // a simple JSON Response read
                    InputStream instream = entity.getContent();


                    // convert content of response to bufferedreader
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            instream.close();
                        } catch (IOException exp) {
                            exp.printStackTrace();
                        }
                    }
                    result = sb.toString();
                    Log.d("Result of the Request: ", result);
                }


            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            if(result!=""&& result!=null){
                try {
                    JSONObject data = new JSONObject(result);
                    JSONArray cuslist = data.getJSONArray("result");
                    for(int i=0; i <  cuslist.length();i++){
                        JSONObject list = cuslist.getJSONObject(i);
                        databaseHelper.addProductsData(String.valueOf(list));
                    }
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("productstatus","true");
                    editor.commit();
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Product list is empty", Toast.LENGTH_LONG).show();
                }

            }
            new Customerlist().execute();
            progressDialog.dismiss();
        }

    }
    class Customerlist extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("customer data downloading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonParams = new JSONObject();
            JSONObject params1 = new JSONObject();
            JSONObject params2 = new JSONObject();
            JSONObject params3 = new JSONObject();
            JSONObject params4 = new JSONObject();
            JSONArray array = new JSONArray();


            HttpClient client = new DefaultHttpClient();

            // Prepare a request object
            HttpPost post = new HttpPost(GetURL.getcustomerlist());
            post.setHeader("Cookie","session_id=" + Session_ID);
            post.setHeader("Content-type", "application/json");
            try {
                params1.put("model", "res.partner");
                params1.put("method", "get_customer_data");
                params1.put("kwargs", params2);
                params2.put("context",params3);
                params3.put("lang", "en_US");
                params1.put("args",array);
                array.put(params4);
                params4.put("uid",User_ID);


                jsonParams.put("id", "ID");
                jsonParams.put("method", "call");
                jsonParams.put("params", params1);
                jsonParams.put("jsonrpc", "2.0");

                StringEntity se = new StringEntity(jsonParams.toString());
                // post.setHeader("Content-length", "" + se.getContentLength()); //header has to be set after jsonparams are complete

                post.setEntity(se);

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // Execute the request
            try {
                HttpResponse response = client.execute(post);
                Log.d("log_response: ", response.getStatusLine().toString());

                // Get hold the response entity
                HttpEntity entity = response.getEntity();

                // if the response does not enclose the entity, there is no need
                // to worry about it

                if (entity != null) {
                    // a simple JSON Response read
                    InputStream instream = entity.getContent();


                    // convert content of response to bufferedreader
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            instream.close();
                        } catch (IOException exp) {
                            exp.printStackTrace();
                        }
                    }
                    result = sb.toString();
                    Log.d("Result of the Request: ", result);
                }


            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if(result!=""&& result!=null){
                try {
                    JSONObject data = new JSONObject(result);
                    JSONArray cuslist = data.getJSONArray("result");
                    for(int i=0; i <  cuslist.length();i++){
                        JSONObject list = cuslist.getJSONObject(i);
                        databaseHelper.addcustomersData(String.valueOf(list));
                    }
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("productstatus","true");
                    editor.commit();
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "customer list is empty", Toast.LENGTH_LONG).show();
                }

            }
            progressDialog.dismiss();

        }

    }
}
