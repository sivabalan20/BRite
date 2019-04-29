package com.aspirantlab.salesagent;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aspirantlab.salesagent.Services.DatePickerFragment;
import com.aspirantlab.salesagent.Services.GetURL;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ApparelsCustomerDetails extends MainActivity implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout relativeLayout;
    EditText dispatch,orderdate,comments;
    ImageView didat,ordat;
    Spinner selectcustomer;
    TextView shagentname;
    ArrayList<String> customernamelist;
    ArrayList<JSONObject> customerIDlist;
    String MY_PREFS_NAME;
    String User_ID;
    String Session_ID;
    String cusID,cusname;
    String ecusid,eorddate,edispdate,ecomment,ecusname,status="false";
    ArrayList<String> eordpronamelist,eordprocatglist,eordpromaincatglist,eordprodidlist,eordsize28,eordsize30,eordsize32,eordsize34,eordsize36,eordsize38,eordsize40,eordsize42,eordsize44,eordsize46,eordsize48,eordsizetype,eordqty,eordtqty,eordsleeve,eordcolor,eordcolimage,eordfit,eordleng,eordvarient;
    static String timeStr;
    Button submit;
    static Calendar cal;
    static Date cDate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.shirtcustomer_fragment, null, false);
        drawer.addView(contentView, 0);

        relativeLayout = (RelativeLayout)findViewById(R.id.mainfile);
        relativeLayout.setVisibility(View.GONE);

        orderdate = (EditText)findViewById(R.id.orderdate);
        dispatch = (EditText)findViewById(R.id.dispatch);
        comments = (EditText)findViewById(R.id.comments);

        ordat = (ImageView)findViewById(R.id.ordat);
        didat = (ImageView)findViewById(R.id.didat);
        selectcustomer = (Spinner)findViewById(R.id.spSelectPackage);
        shagentname = (TextView)findViewById(R.id.shagentlist);
        submit = (Button)findViewById(R.id.submit);

        customernamelist =new ArrayList();
        customerIDlist =  new ArrayList<JSONObject>();


        eordpronamelist = new ArrayList<>();
        eordprocatglist = new ArrayList<>();
        eordpromaincatglist = new ArrayList<>();
        eordprodidlist = new ArrayList<>();
        eordsize28 = new ArrayList<>();
        eordsize30 = new ArrayList<>();
        eordsize32 = new ArrayList<>();
        eordsize34 = new ArrayList<>();
        eordsize36 = new ArrayList<>();
        eordsize38 = new ArrayList<>();
        eordsize40 = new ArrayList<>();
        eordsize42 = new ArrayList<>();
        eordsize44 = new ArrayList<>();
        eordsize46 = new ArrayList<>();
        eordsize48 = new ArrayList<>();
        eordsizetype =new ArrayList<>();
        eordqty = new ArrayList<>();
        eordtqty = new ArrayList<>();
        eordsleeve = new ArrayList<>();
        eordcolor = new ArrayList<>();
        eordcolimage = new ArrayList<>();
        eordfit = new ArrayList<>();
        eordleng = new ArrayList<>();
        eordvarient = new ArrayList<>();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        User_ID = prefs.getString("userid", null);
        Session_ID = prefs.getString("sessionid", null);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ecusid = bundle.getString("ecid");
            eorddate = bundle.getString("eorderdate");
            edispdate = bundle.getString("edispachdate");
            ecomment = bundle.getString("ecomments");
            status = bundle.getString("estatus");
            ecusname = bundle.getString("ecusname");
            eordpronamelist = bundle.getStringArrayList("eordpronamelist");
            eordprocatglist = bundle.getStringArrayList("eordprocatglist");
            eordpromaincatglist = bundle.getStringArrayList("eordpromaincatglist");
            eordprodidlist = bundle.getStringArrayList("eordprodidlist");
            eordsize28 = bundle.getStringArrayList("eordsize28");
            eordsize30 = bundle.getStringArrayList("eordsize30");
            eordsize32 = bundle.getStringArrayList("eordsize32");
            eordsize34 = bundle.getStringArrayList("eordsize34");
            eordsize36 = bundle.getStringArrayList("eordsize36");
            eordsize38 = bundle.getStringArrayList("eordsize38");
            eordsize40 = bundle.getStringArrayList("eordsize40");
            eordsize42 = bundle.getStringArrayList("eordsize42");
            eordsize44 = bundle.getStringArrayList("eordsize44");
            eordsize46 = bundle.getStringArrayList("eordsize46");
            eordsize48 = bundle.getStringArrayList("eordsize48");
            eordsizetype = bundle.getStringArrayList("eordsizetype");
            eordqty = bundle.getStringArrayList("eordqty");
            eordtqty = bundle.getStringArrayList("eordtqty");
            eordsleeve = bundle.getStringArrayList("eordsleeve");
            eordcolor = bundle.getStringArrayList("eordcolor");
            eordcolimage = bundle.getStringArrayList("eordcolimage");
            eordfit = bundle.getStringArrayList("eordfit");
            eordleng = bundle.getStringArrayList("eordleng");
            eordvarient = bundle.getStringArrayList("eordvarient");




        }
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (status.equals("true")) {
            orderdate.setText(eorddate);
            dispatch.setText(edispdate);
            comments.setText(ecomment);
            submit.setText("Continue Order");

        }else if(status.equals("false")){

            orderdate.setText(df.format(c));
            dispatch.setText(df.format(c));
        }

        new Customerlist().execute();

        ordat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordershowDatePicker();
            }
        });
        didat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchshowDatePicker();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!String.valueOf(orderdate.getText()).equals("")) {
                    String comment = String.valueOf(comments.getText());
                    String odate = "";
                    String ddate = "";
                    if (status.equals("false")) {
                        String time = getTime();
                        odate = String.valueOf(orderdate.getText() + " " + time);
                        if(String.valueOf(dispatch.getText()).equals("")){
                            ddate ="";
                        }else {
                            ddate = String.valueOf(dispatch.getText() + " " + time);
                        }
                    } else {
                        odate = String.valueOf(orderdate.getText());
                        ddate = String.valueOf(dispatch.getText());
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString("cid", cusID);
                    bundle.putString("cusname", cusname);
                    bundle.putString("orderdate", odate);
                    bundle.putString("dispachdate", ddate);
                    bundle.putString("comments", comment);
                    bundle.putString("status", status);
                    bundle.putStringArrayList("ordpronamelist", eordpronamelist);
                    bundle.putStringArrayList("ordprocatglist", eordprocatglist);
                    bundle.putStringArrayList("ordpromaincatglist", eordpromaincatglist);
                    bundle.putStringArrayList("ordprodidlist", eordprodidlist);
                    bundle.putStringArrayList("ordsize28", eordsize28);
                    bundle.putStringArrayList("ordsize30", eordsize30);
                    bundle.putStringArrayList("ordsize32", eordsize32);
                    bundle.putStringArrayList("ordsize34", eordsize34);
                    bundle.putStringArrayList("ordsize36", eordsize36);
                    bundle.putStringArrayList("ordsize38", eordsize38);
                    bundle.putStringArrayList("ordsize40", eordsize40);
                    bundle.putStringArrayList("ordsize42", eordsize42);
                    bundle.putStringArrayList("ordsize44", eordsize44);
                    bundle.putStringArrayList("ordsize46", eordsize46);
                    bundle.putStringArrayList("ordsize48", eordsize48);
                    bundle.putStringArrayList("ordsizetype", eordsizetype);
                    bundle.putStringArrayList("ordqty", eordqty);
                    bundle.putStringArrayList("ordtqty", eordtqty);
                    bundle.putStringArrayList("ordsleeve", eordsleeve);
                    bundle.putStringArrayList("ordcolor", eordcolor);
                    bundle.putStringArrayList("ordcolimage", eordcolimage);
                    bundle.putStringArrayList("ordfit", eordfit);
                    bundle.putStringArrayList("ordleng", eordleng);
                    bundle.putStringArrayList("ordvarient", eordvarient);
                    Intent intent = new Intent(getBaseContext(), ApparelsOrderDetails.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Orderdate is empty", Toast.LENGTH_LONG).show();
                }


            }
        });


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }
    private void ordershowDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            orderdate.setText(String.valueOf(year) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(dayOfMonth));
        }
    };
    private void dispatchshowDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();

        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate1);
        date.show(getSupportFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondate1 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


            dispatch.setText(String.valueOf(year) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(dayOfMonth));
        }
    };



    class Customerlist extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ApparelsCustomerDetails.this);
            progressDialog.setMessage("Please wait...");
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
            progressDialog.dismiss();

            if(result!=""&& result!=null){
                try {
                    JSONObject data = new JSONObject(result);
                    JSONArray cuslist = data.getJSONArray("result");
                    for(int i=0; i <  cuslist.length();i++){
                        JSONObject list = cuslist.getJSONObject(i);
                        String customername = list.get("name").toString();
                        String customerID = list.get("id").toString();
                        customernamelist.add(customername);
                        customerIDlist.add(list);

                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ApparelsCustomerDetails.this, android.R.layout.simple_spinner_item, customernamelist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectcustomer.setAdapter(dataAdapter);
                    if (ecusname != null) {
                        int spinnerPosition = dataAdapter.getPosition(ecusname);
                        selectcustomer.setSelection(spinnerPosition);
                        ecusname=null;
                    }
                    dataAdapter.notifyDataSetChanged();
                    selectcustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String str = (String) parent.getItemAtPosition(position);
                            if (!str.equals("")) {
                                for (int i = 0; i < customerIDlist.size(); i++) {
                                    JSONObject list = customerIDlist.get(i);
                                    try {
                                        String cname = list.get("name").toString();
                                        if(cname.equals(str)) {

                                            cusname = list.get("name").toString();
                                            cusID = list.get("id").toString();
                                            String Shagent = list.get("shirt_user_name").toString();
                                            shagentname.setText(Shagent);

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                        }
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "customer list is empty", Toast.LENGTH_LONG).show();
                }

            }

        }

    }
    public static String getTime()
    {

        cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

        cDate = cal.getTime();
        timeStr = df.format(cDate);


        return timeStr;
    }


}
