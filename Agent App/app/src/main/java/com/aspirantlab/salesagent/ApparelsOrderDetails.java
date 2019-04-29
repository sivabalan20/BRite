package com.aspirantlab.salesagent;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspirantlab.salesagent.Database.DatabaseHelper;
import com.aspirantlab.salesagent.Services.GetURL;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

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
import java.util.HashSet;
import java.util.List;


public class ApparelsOrderDetails  extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener ,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    RelativeLayout relativeLayout;
    RelativeLayout saveproduct,backfrag,submit,varientclose,tempsave;
    String MY_PREFS_NAME,User_ID;
    static String Session_ID;
    ArrayList <JSONObject>productlist,alldbproduct;
    ArrayList<String> ordwsplist;
    ArrayList<String> ordmrplist;
    ArrayList<String> selectedproidlist,ordpronamelist,ordprocatglist,ordpromaincatglist,ordprodidlist,ordvarient,ordsize28,ordsize30,ordsize32,ordsize34,ordsize36,ordsize38,ordsize40,ordsize42,ordsize44,ordsize46,ordsize48,ordsizetype,ordqty,ordsleeve,ordcolor,ordcolimage,ordfit,ordleng;
    ArrayList<String> colorlist;
    ArrayList<JSONObject> orderfrmainlist;
    ArrayList<String> productnamelist,produimagelist,productidlist,apparelprolist,wsplist,mrplist,multicolor,productsubcatge;
    ArrayList<String> fullproductlist,fullproduimagelist,fullprocololsit,fullproductidlist,fullwsplist,fullmrplist,fullmulticolor,fullproductsubcatge;
    JSONArray toporderlist,bottomorderlist;
    ArrayList<String> ordtqty,procololist;
    JSONObject sleevewsp,fitwsp,lengthwsp,sizewsp;
    JSONObject sleevemrp,fitmrp,lengthmrp,sizemrp;
    RecyclerView dialoguelistview,orderfromlistview,colorlistview,colorvarientView,tilevarimageview;
    LinearLayoutManager layoutManager2,layoutManager3;
    GridLayoutManager layoutManager1,layoutManager4,layoutManager5;
    ArrayAdapter adapter;
    Dialog dialog,colordialog,varientdialog;
    Button tops,bottoms,colorclose;
    boolean[] itemChecked,colorChecked,varientchecked;
    String size = "0",savestatus="false",viewstatus="gridview",topbotstatus="",singleproaddsta="",multiproaddsta="",tileproaddsta="",gridproaddsta="",gridmainprosta="",status ="false",cusID,custname,odate,ddate,comment,dproid,dpname,dpcatge,dpwsp,dpmrp;
    int totalitems = 0,totalpics = 0,colorboxpos;
    ImageView selectproduct,mainviews,gridview,listview,filter;
    TableLayout trousertable;
    TextView pertotitems,pertotpieces,hhand,fhand;
    CheckBox hscheckbox,fscheckbox,hsvarientbox,fsvarientbox;
    AutoCompleteTextView addpro;
    ArrayList<String>imagevarientlist,sleevelist,colorcodelist,coloridlist,fitlist,lenglist,sizelist,sizekeylist,coloraddlist,fullcoloraddlist,selectedcolor,selectedcolimg;
    Button fit1,fit2,fit3,fit4,fit5,slev1,slev2,slev3,slev4,slev5,leng1,leng2,leng3,leng4,leng5;
    TextView tsize28,tsize30,tsize32,tsize34,tsize36,tsize38,tsize40,tsize42,tsize44,tsize46,tsize48,tqty;
    TextView wsp28,wsp30,wsp32,wsp34,wsp36,wsp38,wsp40,wsp42,wsp44,wsp46,wsp48,wqty;
    TextView mrp28,mrp30,mrp32,mrp34,mrp36,mrp38,mrp40,mrp42,mrp44,mrp46,mrp48,mqty;
    TextView size28,size30,size32,size34,size36,size38,size40,size42,size44,size46,size48,qty,cqty;
    String minustatus="false",cqtystatus="",mainproductid,mainproductname,mainproductcatge,mainproductmaincatge,mainopenstatus,maincheckingstatus,mainsizebasecost;
    int mainpostions,mainwsp,mainmrp;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
    TextView edittext,cusname;
    ImageView minus,clear;
    ArrayList<String>catgefilterlist,brandfilterlist,fitaddlist,sleeveaddlist,lengthaddlist;
    HashSet<String>hashcatge,hashbrand;
    Spinner selectcatge,selectbrand;
    AlertDialog.Builder builder;
    EditText inputSearch;
    String brandvalue = "";
    String catgevalue = "",emptystatus="false";
    JSONArray pricelist;
    private Location mylocation;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
    String latitude = "0.0";
    String longitude = "0.0";
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    NetworkInfo netInfo;
    ConnectivityManager connManager;
    DatabaseHelper databaseHelper;
    @SuppressLint("ResourceAsColor")
    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.shirtcreatorder_fragment, null, false);
        drawer.addView(contentView, 0);

        relativeLayout = (RelativeLayout)findViewById(R.id.mainfile);
        relativeLayout.setVisibility(View.GONE);

        setUpGClient();

        selectproduct = (ImageView)findViewById(R.id.selectproductimage);
        saveproduct = (RelativeLayout) findViewById(R.id.saveproduct);
        backfrag = (RelativeLayout) findViewById(R.id.back);
        pertotitems = (TextView)findViewById(R.id.pertotitems);
        pertotpieces = (TextView)findViewById(R.id.pertotpieces);
        addpro = (AutoCompleteTextView)findViewById(R.id.proauto);
        cusname = (TextView)findViewById(R.id.cusname);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        User_ID = prefs.getString("userid", null);
        Session_ID = prefs.getString("sessionid", null);

        connManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        netInfo = connManager.getActiveNetworkInfo();

        databaseHelper = new DatabaseHelper(this);


        backfrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("ecid", cusID);
                bundle.putString("ecusname", custname);
                bundle.putString("eorderdate", odate);
                bundle.putString("edispachdate", ddate);
                bundle.putString("ecomments", comment);
                bundle.putString("estatus", "true");
                bundle.putStringArrayList("eordpronamelist",ordpronamelist);
                bundle.putStringArrayList("eordprocatglist",ordprocatglist);
                bundle.putStringArrayList("eordpromaincatglist",ordpromaincatglist);
                bundle.putStringArrayList("eordprodidlist",ordprodidlist);
                bundle.putStringArrayList("eordsize28",ordsize28);
                bundle.putStringArrayList("eordsize30",ordsize30);
                bundle.putStringArrayList("eordsize32",ordsize32);
                bundle.putStringArrayList("eordsize34",ordsize34);
                bundle.putStringArrayList("eordsize36",ordsize36);
                bundle.putStringArrayList("eordsize38",ordsize38);
                bundle.putStringArrayList("eordsize40",ordsize40);
                bundle.putStringArrayList("eordsize42",ordsize42);
                bundle.putStringArrayList("eordsize44",ordsize44);
                bundle.putStringArrayList("eordsize46",ordsize46);
                bundle.putStringArrayList("eordsize48",ordsize48);
                bundle.putStringArrayList("eordsizetype",ordsizetype);
                bundle.putStringArrayList("eordqty",ordqty);
                bundle.putStringArrayList("eordtqty",ordtqty);
                bundle.putStringArrayList("eordsleeve",ordsleeve);
                bundle.putStringArrayList("eordcolor",ordcolor);
                bundle.putStringArrayList("eordcolimage",ordcolimage);
                bundle.putStringArrayList("eordfit",ordfit);
                bundle.putStringArrayList("eordleng",ordleng);
                bundle.putStringArrayList("eordvarient",ordvarient);

                Intent intent = new Intent(getBaseContext(),ApparelsCustomerDetails.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        productlist = new ArrayList<>();
        alldbproduct  = new ArrayList<>();
        productnamelist = new ArrayList<>();
        procololist = new ArrayList<>();
        fullprocololsit = new ArrayList<>();
        produimagelist = new ArrayList<>();
        fullproduimagelist = new ArrayList<>();
        wsplist = new ArrayList<>();
        mrplist = new ArrayList<>();
        fullwsplist = new ArrayList<>();
        fullmrplist = new ArrayList<>();
        multicolor = new ArrayList<>();
        fullmulticolor = new ArrayList<>();
        productsubcatge =  new ArrayList<>();
        fullproductsubcatge = new ArrayList<>();

        productidlist = new ArrayList<>();
        fullproductlist = new ArrayList<>();
        fullproductidlist = new ArrayList<>();
        apparelprolist = new ArrayList<>();
        fullcoloraddlist = new ArrayList<>();



        toporderlist = new JSONArray();
        bottomorderlist = new JSONArray();

        sleevewsp = new JSONObject();
        fitwsp = new JSONObject();
        lengthwsp = new JSONObject();
        sizewsp = new JSONObject();

        sleevemrp = new JSONObject();
        fitmrp = new JSONObject();
        lengthmrp = new JSONObject();
        sizemrp  = new JSONObject();

        catgefilterlist = new ArrayList<>();
        brandfilterlist = new ArrayList<>();
        hashcatge = new HashSet<String>();
        hashbrand = new HashSet<String>();

        orderfrmainlist = new ArrayList<JSONObject>();
        ordpronamelist = new ArrayList<>();
        ordprocatglist = new ArrayList<>();
        ordpromaincatglist = new ArrayList<>();
        ordwsplist = new ArrayList<>();
        ordmrplist = new ArrayList<>();
        ordprodidlist = new ArrayList<>();
        ordsize28 = new ArrayList<>();
        ordsize30 = new ArrayList<>();
        ordsize32 = new ArrayList<>();
        ordsize34 = new ArrayList<>();
        ordsize36 = new ArrayList<>();
        ordsize38 = new ArrayList<>();
        ordsize40 = new ArrayList<>();
        ordsize42 = new ArrayList<>();
        ordsize44 = new ArrayList<>();
        ordsize46 = new ArrayList<>();
        ordsize48 = new ArrayList<>();
        ordsizetype =new ArrayList<>();
        ordqty = new ArrayList<>();
        ordtqty = new ArrayList<>();
        ordsleeve = new ArrayList<>();
        ordcolor = new ArrayList<>();
        ordcolimage = new ArrayList<>();
        ordfit = new ArrayList<>();
        ordleng = new ArrayList<>();
        ordvarient = new ArrayList<>();

        if(netInfo !=null) {
            new productlist().execute();
        }else{
            getproductdatafromdb();
        }


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cusID = bundle.getString("cid");
            odate = bundle.getString("orderdate");
            ddate = bundle.getString("dispachdate");
            comment = bundle.getString("comments");
            status = bundle.getString("status");
            custname = bundle.getString("cusname");

        }
        cusname.setText(custname);
        if(status.equals("true")){
            ordpronamelist = bundle.getStringArrayList("ordpronamelist");
            ordprocatglist = bundle.getStringArrayList("ordprocatglist");
            ordpromaincatglist = bundle.getStringArrayList("ordpromaincatglist");
            ordprodidlist = bundle.getStringArrayList("ordprodidlist");
            ordsize28 = bundle.getStringArrayList("ordsize28");
            ordsize30 = bundle.getStringArrayList("ordsize30");
            ordsize32 = bundle.getStringArrayList("ordsize32");
            ordsize34 = bundle.getStringArrayList("ordsize34");
            ordsize36 = bundle.getStringArrayList("ordsize36");
            ordsize38 = bundle.getStringArrayList("ordsize38");
            ordsize40 = bundle.getStringArrayList("ordsize40");
            ordsize42 = bundle.getStringArrayList("ordsize42");
            ordsize44 = bundle.getStringArrayList("ordsize44");
            ordsize46 = bundle.getStringArrayList("ordsize46");
            ordsize48 = bundle.getStringArrayList("ordsize48");
            ordsizetype = bundle.getStringArrayList("ordsizetype");
            ordqty = bundle.getStringArrayList("ordqty");
            ordtqty = bundle.getStringArrayList("ordtqty");
            ordsleeve = bundle.getStringArrayList("ordsleeve");
            ordcolor = bundle.getStringArrayList("ordcolor");
            ordcolimage = bundle.getStringArrayList("ordcolimage");
            ordfit = bundle.getStringArrayList("ordfit");
            ordleng = bundle.getStringArrayList("ordleng");
            ordvarient = bundle.getStringArrayList("ordvarient");
        }




        addpro.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String) parent.getItemAtPosition(position);
                singleproaddsta = "";
                for (int k = 0; k < productlist.size(); k++) {
                    JSONObject plist = productlist.get(k);
                    try {
                        String pid = plist.get("id").toString();
                        String ordplist = plist.get("name").toString();
                        if (selection.equals(ordplist)) {
                            for(int i=0;i<ordprodidlist.size();i++){
                                if(pid.equals(ordprodidlist.get(i))){
                                    String colorbased= plist.get("color_shade").toString();
                                    String fitbased= plist.get("fit_based").toString();
                                    String sleeveBased= plist.get("sleeve_based").toString();
                                    String lengthBased= plist.get("length_based").toString();
                                    if (colorbased.equals("true")|| fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")){
                                        String ordpclist = plist.get("product_categ").toString();
                                        String ordmpclist = plist.get("categ_types").toString();
                                        ordpronamelist.add(ordplist);
                                        ordprocatglist.add(ordpclist);
                                        ordpromaincatglist.add(ordmpclist);
                                        ordprodidlist.add(pid);
                                        ordsize28.add("");
                                        ordsize30.add("");
                                        ordsize32.add("");
                                        ordsize34.add("");
                                        ordsize36.add("");
                                        ordsize38.add("");
                                        ordsize40.add("");
                                        ordsize42.add("");
                                        ordsize44.add("");
                                        ordsize46.add("");
                                        ordsize48.add("");
                                        ordsizetype.add("");
                                        ordqty.add("");
                                        ordtqty.add("");
                                        ordsleeve.add("");
                                        ordcolor.add("");
                                        ordcolimage.add("");
                                        ordfit.add("");
                                        ordleng.add("");
                                        orderfrmainlist.add(plist);
                                        if (colorbased.equals("true")|| fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")){
                                            ordvarient.add("true");
                                        }else{
                                            ordvarient.add("false");
                                        }
                                        singleproaddsta = "true";
                                        break;
                                    }else{
                                        Toast.makeText(getApplicationContext(), "This Product is Already added", Toast.LENGTH_LONG).show();
                                        singleproaddsta = "false";
                                        addpro.setText("");
                                    }
                                }
                            }
                            if(singleproaddsta.equals("")) {
                                String ordpclist = plist.get("product_categ").toString();
                                String ordmpclist = plist.get("categ_types").toString();
                                String colorbased = plist.get("color_shade").toString();
                                String fitbased = plist.get("fit_based").toString();
                                String sleeveBased = plist.get("sleeve_based").toString();
                                String lengthBased = plist.get("length_based").toString();
                                ordpronamelist.add(ordplist);
                                ordprocatglist.add(ordpclist);
                                ordpromaincatglist.add(ordmpclist);
                                ordprodidlist.add(pid);
                                ordsize28.add("");
                                ordsize30.add("");
                                ordsize32.add("");
                                ordsize34.add("");
                                ordsize36.add("");
                                ordsize38.add("");
                                ordsize40.add("");
                                ordsize42.add("");
                                ordsize44.add("");
                                ordsize46.add("");
                                ordsize48.add("");
                                ordsizetype.add("");
                                ordqty.add("");
                                ordtqty.add("");
                                ordsleeve.add("");
                                ordcolor.add("");
                                ordcolimage.add("");
                                ordfit.add("");
                                ordleng.add("");
                                orderfrmainlist.add(plist);
                                if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                    ordvarient.add("true");
                                } else {
                                    ordvarient.add("false");

                                }
                            }
                          break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                addpro.setText("");
                orderfromlistview = (RecyclerView) findViewById(R.id.seleprolist);
                orderfromlistview.setHasFixedSize(true);
                layoutManager2 = new LinearLayoutManager(getApplicationContext());
                orderfromlistview.setLayoutManager(layoutManager2);
                TrouserAdapter trouserAdapter = new TrouserAdapter();
                orderfromlistview.setAdapter(trouserAdapter);

                totalitems =  ordprodidlist.size();
                pertotitems.setText(String.valueOf(totalitems));
            }
        });


        saveproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (isGPSEnabled == false) {
                    getMyLocation();
                } else {
                    if(mylocation!=null) {
                        double lat = mylocation.getLatitude();
                        double lng = mylocation.getLongitude();
                        latitude = Double.toString(lat);
                        longitude = Double.toString(lng);
                    }
                    if (latitude.equals("0.0") && longitude.equals("0.0")) {
                        Toast.makeText(getApplicationContext(), "GPS value is 0.0", Toast.LENGTH_SHORT).show();
                    }else {
                    if (ordprodidlist.size() != 0) {
                        if (savestatus.equals("false")) {
                            for (int i = 0; i < ordprodidlist.size(); i++) {
                                String mcatge = ordpromaincatglist.get(i);
                                String pid = ordprodidlist.get(i);
                                String size28 = ordsize28.get(i);
                                String size30 = ordsize30.get(i);
                                String size32 = ordsize32.get(i);
                                String size34 = ordsize34.get(i);
                                String size36 = ordsize36.get(i);
                                String size38 = ordsize38.get(i);
                                String size40 = ordsize40.get(i);
                                String size42 = ordsize42.get(i);
                                String size44 = ordsize44.get(i);
                                String size46 = ordsize46.get(i);
                                String size48 = ordsize48.get(i);
                                String qty = ordqty.get(i);
                                String sizetype = ordsizetype.get(i);
                                String color = ordcolor.get(i);
                                String colimage = ordcolimage.get(i);
                                String fit = ordfit.get(i);
                                String sleeve = ordsleeve.get(i);
                                String leng = ordleng.get(i);


                                String tqty = ordtqty.get(i);
                                if (tqty.equals("") || tqty.equals("0")) {

                                } else {
                                    if (mcatge.equals("Tops")) {
                                        JSONArray jsonArray = new JSONArray();
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("product_id", pid);
                                            jsonObject.put("sleeve_type", sleeve);
                                            jsonObject.put("size_28", size28);
                                            jsonObject.put("size_30", size30);
                                            jsonObject.put("size_32", size32);
                                            jsonObject.put("size_34", size34);
                                            jsonObject.put("size_36", size36);
                                            jsonObject.put("size_38", size38);
                                            jsonObject.put("size_40", size40);
                                            jsonObject.put("size_42", size42);
                                            jsonObject.put("size_44", size44);
                                            jsonObject.put("size_46", size46);
                                            jsonObject.put("size_48", size48);
                                            jsonObject.put("size_00", qty);
                                            jsonObject.put("size_type", sizetype);
                                            jsonObject.put("color_type", color);
                                            jsonObject.put("color_image", colimage);
                                            jsonObject.put("fit_type", fit);
                                            jsonObject.put("sleeve_type", sleeve);
                                            jsonObject.put("length_type", leng);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(0);
                                        jsonArray.put(0);
                                        jsonArray.put(jsonObject);
                                        toporderlist.put(jsonArray);
                                    } else if (mcatge.equals("Bottoms")) {
                                        JSONArray jsonArray = new JSONArray();
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("product_id", pid);
                                            jsonObject.put("sleeve_type", sleeve);
                                            jsonObject.put("size_28", size28);
                                            jsonObject.put("size_30", size30);
                                            jsonObject.put("size_32", size32);
                                            jsonObject.put("size_34", size34);
                                            jsonObject.put("size_36", size36);
                                            jsonObject.put("size_38", size38);
                                            jsonObject.put("size_40", size40);
                                            jsonObject.put("size_42", size42);
                                            jsonObject.put("size_44", size44);
                                            jsonObject.put("size_46", size46);
                                            jsonObject.put("size_48", size48);
                                            jsonObject.put("size_00", qty);
                                            jsonObject.put("size_type", sizetype);
                                            jsonObject.put("color_type", color);
                                            jsonObject.put("color_image", colimage);
                                            jsonObject.put("fit_type", fit);
                                            jsonObject.put("sleeve_type", sleeve);
                                            jsonObject.put("length_type", leng);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(0);
                                        jsonArray.put(0);
                                        jsonArray.put(jsonObject);
                                        bottomorderlist.put(jsonArray);
                                    }
                                }
                            }
                        }

                        if (toporderlist.length() != 0 || bottomorderlist.length() != 0) {
                            savestatus = "true";
                            new productsave().execute();
                        } else {
                            savestatus = "false";
                            Toast.makeText(getApplicationContext(), "Empty value can't be save", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Empty order can't be save", Toast.LENGTH_LONG).show();
                    }
                }
             }
            }

        });
        selectproduct.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onClick(View v) {
                selectedproidlist = new ArrayList<>();
                productnamelist = new ArrayList<>();
                productidlist = new ArrayList<>();
                wsplist = new ArrayList<>();
                mrplist = new ArrayList<>();
                produimagelist = new ArrayList<>();
                procololist = new ArrayList<>();
                multicolor = new ArrayList<>();
                productsubcatge = new ArrayList<>();

                productnamelist.addAll(fullproductlist);
                productidlist.addAll(fullproductidlist);
                wsplist.addAll(fullwsplist);
                mrplist.addAll(fullmrplist);
                mrplist.addAll(fullmrplist);
                procololist.addAll(fullprocololsit);
                produimagelist.addAll(fullproduimagelist);
                multicolor.addAll(fullmulticolor);
                productsubcatge.addAll(fullproductsubcatge);
                itemChecked = new boolean[productidlist.size()];

                dialog = new Dialog(ApparelsOrderDetails.this,android.R.style.Theme_Light);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.shirtorderform_dialog);
                mainviews =(ImageView)dialog.findViewById(R.id.views);
                filter =(ImageView)dialog.findViewById(R.id.filter);
                tops = (Button)dialog.findViewById(R.id.tops);
                bottoms = (Button)dialog.findViewById(R.id.bottoms);
                inputSearch = (EditText)dialog.findViewById(R.id.inputSearch);

                addTextListener();

                viewstatus = "gridview";
                tops.setTextColor(getResources().getColor(R.color.colorAccent));
                tops.setBackgroundResource(R.drawable.buttonstyle_background);
                bottoms.setTextColor(getResources().getColor(R.color.colorBlack));
                listadapter("Tops");


                dialog.show();
                submit = (RelativeLayout)dialog.findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        totalpics =0;
                        for (int i = 0; i < selectedproidlist.size(); i++) {
                            for (int k = 0; k < productlist.size(); k++) {
                                JSONObject plist = productlist.get(k);
                                try {

                                    String pid = plist.get("id").toString();
                                    if (selectedproidlist.get(i).equals(pid)) {
                                        String ordplist = plist.get("name").toString();
                                        String ordpclist = plist.get("product_categ").toString();
                                        String ordmpclist = plist.get("categ_types").toString();
                                        String colorbased= plist.get("color_shade").toString();
                                        String fitbased= plist.get("fit_based").toString();
                                        String sleeveBased= plist.get("sleeve_based").toString();
                                        String lengthBased= plist.get("length_based").toString();

                                        ordpronamelist.add(ordplist);
                                        ordprocatglist.add(ordpclist);
                                        ordpromaincatglist.add(ordmpclist);
                                        ordprodidlist.add(pid);
                                        ordsize28.add("");
                                        ordsize30.add("");
                                        ordsize32.add("");
                                        ordsize34.add("");
                                        ordsize36.add("");
                                        ordsize38.add("");
                                        ordsize40.add("");
                                        ordsize42.add("");
                                        ordsize44.add("");
                                        ordsize46.add("");
                                        ordsize48.add("");
                                        ordsizetype.add("");
                                        ordqty.add("");
                                        ordtqty.add("");
                                        ordsleeve.add("");
                                        ordcolor.add("");
                                        ordcolimage.add("");
                                        ordfit.add("");
                                        ordleng.add("");
                                        orderfrmainlist.add(plist);
                                        if (colorbased.equals("true")|| fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")){
                                            ordvarient.add("true");
                                        }else{
                                            ordvarient.add("false");
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                        orderfromlistview = (RecyclerView)findViewById(R.id.seleprolist);
                        orderfromlistview.setHasFixedSize(true);
                        layoutManager2 = new LinearLayoutManager(getApplicationContext());
                        orderfromlistview.setLayoutManager(layoutManager2);
                        TrouserAdapter trouserAdapter = new TrouserAdapter();
                        orderfromlistview.setAdapter(trouserAdapter);

                        dialog.dismiss();
                        totalitems =  ordprodidlist.size();
                        pertotitems.setText(String.valueOf(totalitems));
                        for(int i=0;i<ordtqty.size();i++){
                            String val  = ordtqty.get(i);
                            if(!val.equals("")){
                                totalpics = totalpics + Integer.parseInt(val);
                            }
                        }
                        pertotpieces.setText(String.valueOf(totalpics));
                    }

                });
                mainviews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(ApparelsOrderDetails.this, mainviews);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.popup_views, popup.getMenu());

                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {

                                switch (item.getItemId()) {
                                    case R.id.tile:
                                        viewstatus = "tileview";
                                        productnamelist = new ArrayList<>();
                                        productidlist = new ArrayList<>();
                                        wsplist = new ArrayList<>();
                                        mrplist = new ArrayList<>();
                                        produimagelist = new ArrayList<>();
                                        procololist = new ArrayList<>();
                                        multicolor = new ArrayList<>();
                                        productsubcatge = new ArrayList<>();

                                        productnamelist.addAll(fullproductlist);
                                        productidlist.addAll(fullproductidlist);
                                        wsplist.addAll(fullwsplist);
                                        mrplist.addAll(fullmrplist);
                                        produimagelist.addAll(fullproduimagelist);
                                        procololist.addAll(fullprocololsit);
                                        multicolor.addAll(fullmulticolor);
                                        productsubcatge.addAll(fullproductsubcatge);

                                        tops.setTextColor(getResources().getColor(R.color.colorAccent));
                                        tops.setBackgroundResource(R.drawable.buttonstyle_background);
                                        bottoms.setTextColor(getResources().getColor(R.color.colorBlack));
                                        bottoms.setBackgroundResource(R.drawable.top_bottom_button);
                                        listadapter("Tops");

                                        return true;
                                    case R.id.grid:
                                        viewstatus = "gridview";
                                        productnamelist = new ArrayList<>();
                                        productidlist = new ArrayList<>();
                                        wsplist = new ArrayList<>();
                                        mrplist = new ArrayList<>();
                                        produimagelist = new ArrayList<>();
                                        procololist = new ArrayList<>();
                                        multicolor = new ArrayList<>();
                                        productsubcatge = new ArrayList<>();

                                        productnamelist.addAll(fullproductlist);
                                        productidlist.addAll(fullproductidlist);
                                        wsplist.addAll(fullwsplist);
                                        mrplist.addAll(fullmrplist);
                                        produimagelist.addAll(fullproduimagelist);
                                        procololist.addAll(fullprocololsit);
                                        multicolor.addAll(fullmulticolor);
                                        productsubcatge.addAll(fullproductsubcatge);

                                        tops.setTextColor(getResources().getColor(R.color.colorAccent));
                                        tops.setBackgroundResource(R.drawable.buttonstyle_background);
                                        bottoms.setTextColor(getResources().getColor(R.color.colorBlack));
                                        bottoms.setBackgroundResource(R.drawable.top_bottom_button);
                                        listadapter("Tops");
                                        return true;
                                    case R.id.list:
                                        viewstatus="listview";
                                        productnamelist = new ArrayList<>();
                                        productidlist = new ArrayList<>();
                                        wsplist = new ArrayList<>();
                                        mrplist = new ArrayList<>();
                                        produimagelist = new ArrayList<>();
                                        procololist = new ArrayList<>();
                                        multicolor = new ArrayList<>();
                                        productsubcatge = new ArrayList<>();


                                        productnamelist.addAll(fullproductlist);
                                        productidlist.addAll(fullproductidlist);
                                        wsplist.addAll(fullwsplist);
                                        mrplist.addAll(fullmrplist);
                                        produimagelist.addAll(fullproduimagelist);
                                        procololist.addAll(fullprocololsit);
                                        multicolor.addAll(fullmulticolor);
                                        productsubcatge.addAll(fullproductsubcatge);

                                        tops.setTextColor(getResources().getColor(R.color.colorAccent));
                                        tops.setBackgroundResource(R.drawable.buttonstyle_background);
                                        bottoms.setTextColor(getResources().getColor(R.color.colorBlack));
                                        bottoms.setBackgroundResource(R.drawable.top_bottom_button);
                                        listadapter("Tops");
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });

                        popup.show();//showing popup menu

                     }
                });
                tops.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tops.setTextColor(getResources().getColor(R.color.colorAccent));
                        tops.setBackgroundResource(R.drawable.buttonstyle_background);
                        bottoms.setTextColor(getResources().getColor(R.color.colorBlack));
                        bottoms.setBackgroundResource(R.drawable.top_bottom_button);
                        listadapter("Tops");
                    }
                });
                bottoms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tops.setTextColor(getResources().getColor(R.color.colorBlack));
                        tops.setBackgroundResource(R.drawable.top_bottom_button);
                        bottoms.setTextColor(getResources().getColor(R.color.colorAccent));
                        bottoms.setBackgroundResource(R.drawable.buttonstyle_background);
                        listadapter("Bottoms");
                    }
                });
                filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openfilterbox();
                    }
                });

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

    public void openfilterbox(){

         brandvalue = "";
         catgevalue = "";
        final Dialog filterdialog = new Dialog(ApparelsOrderDetails.this);
        filterdialog.setContentView(R.layout.filter_dialog);
        RelativeLayout apply = (RelativeLayout)filterdialog.findViewById(R.id.filterclose);
        ImageView close = (ImageView)filterdialog.findViewById(R.id.close);
        selectcatge = (Spinner)filterdialog.findViewById(R.id.categoryfilter);
        selectbrand = (Spinner)filterdialog.findViewById(R.id.brandfilter);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ApparelsOrderDetails.this, android.R.layout.simple_spinner_item, catgefilterlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectcatge.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
        selectcatge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                if (!str.equals("")) {
                    catgevalue = str;
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(ApparelsOrderDetails.this, android.R.layout.simple_spinner_item, brandfilterlist);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectbrand.setAdapter(dataAdapter1);
        dataAdapter1.notifyDataSetChanged();
        selectbrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                if (!str.equals("")) {
                    brandvalue = str;
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterdialog.dismiss();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productnamelist = new ArrayList<>();
                productidlist = new ArrayList<>();
                wsplist = new ArrayList<>();
                mrplist = new ArrayList<>();
                produimagelist = new ArrayList<>();
                procololist = new ArrayList<>();
                multicolor = new ArrayList<>();
                productsubcatge = new ArrayList<>();
                for(int i=0;i< productlist.size();i++){
                    String checkstatus = "false";
                    JSONObject plist= productlist.get(i);
                    try {
                        String productcatege = plist.get("categ_types").toString();
                        if(productcatege.equals(topbotstatus)){
                            String produsubcatge = plist.get("product_categ").toString();
                            String probrand= plist.get("brand_id").toString();
                            String productname = plist.get("name").toString();
                            String productid = plist.get("id").toString();
                            String prodimage = plist.get("image_url").toString();
                            String prodwsp = plist.get("wsp").toString();
                            String prodmrp = plist.get("mrp").toString();
                            String colorbased = plist.get("color_shade").toString();
                            String fitbased = plist.get("fit_based").toString();
                            String sleeveBased = plist.get("sleeve_based").toString();
                            String lengthBased = plist.get("length_based").toString();
                            if(catgevalue.equals("") && !brandvalue.equals("")) {
                                if (probrand.equals(brandvalue)) {
                                    if(ordprodidlist.size() != 0){
                                        for(int a=0;a<ordprodidlist.size();a++) {
                                            if (productid.equals(ordprodidlist.get(a))) {
                                                productnamelist.add(productname);
                                                productidlist.add(productid);
                                                wsplist.add(prodwsp);
                                                mrplist.add(prodmrp);
                                                produimagelist.add(GetURL.getServerip() + prodimage);
                                                productsubcatge.add(produsubcatge);
                                                procololist.add("#AA4098");
                                                if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                                    multicolor.add("true");
                                                } else {
                                                    multicolor.add("false");
                                                }
                                                hashcatge.add(produsubcatge);
                                                hashbrand.add(probrand);
                                                checkstatus = "true";
                                                break;
                                            }

                                        }
                                        if(checkstatus.equals("false")) {
                                            productnamelist.add(productname);
                                            productidlist.add(productid);
                                            wsplist.add(prodwsp);
                                            mrplist.add(prodmrp);
                                            produimagelist.add(GetURL.getServerip() + prodimage);
                                            productsubcatge.add(produsubcatge);
                                            procololist.add("#000000");
                                            if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                                multicolor.add("true");
                                            } else {
                                                multicolor.add("false");
                                            }
                                            hashcatge.add(produsubcatge);
                                            hashbrand.add(probrand);
                                        }
                                    }else {

                                        productnamelist.add(productname);
                                        productidlist.add(productid);
                                        wsplist.add(prodwsp);
                                        mrplist.add(prodmrp);
                                        produimagelist.add(GetURL.getServerip() + prodimage);
                                        productsubcatge.add(produsubcatge);
                                        procololist.add("#000000");
                                        if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                            multicolor.add("true");
                                        } else {
                                            multicolor.add("false");
                                        }
                                        hashcatge.add(produsubcatge);
                                        hashbrand.add(probrand);
                                    }
                                }
                            }else if(brandvalue.equals("") && !catgevalue.equals("")){
                                if (produsubcatge.equals(catgevalue)) {
                                    if(ordprodidlist.size() != 0){
                                        for(int a=0;a<ordprodidlist.size();a++) {
                                            if (productid.equals(ordprodidlist.get(a))) {
                                                productnamelist.add(productname);
                                                productidlist.add(productid);
                                                wsplist.add(prodwsp);
                                                mrplist.add(prodmrp);
                                                produimagelist.add(GetURL.getServerip() + prodimage);
                                                productsubcatge.add(produsubcatge);
                                                procololist.add("#AA4098");
                                                if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                                    multicolor.add("true");
                                                } else {
                                                    multicolor.add("false");
                                                }
                                                hashcatge.add(produsubcatge);
                                                hashbrand.add(probrand);
                                                checkstatus = "true";
                                                break;
                                            }

                                        }
                                        if(checkstatus.equals("false")) {
                                            productnamelist.add(productname);
                                            productidlist.add(productid);
                                            wsplist.add(prodwsp);
                                            mrplist.add(prodmrp);
                                            produimagelist.add(GetURL.getServerip() + prodimage);
                                            productsubcatge.add(produsubcatge);
                                            procololist.add("#000000");
                                            if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                                multicolor.add("true");
                                            } else {
                                                multicolor.add("false");
                                            }
                                            hashcatge.add(produsubcatge);
                                            hashbrand.add(probrand);
                                        }
                                    }else {

                                        productnamelist.add(productname);
                                        productidlist.add(productid);
                                        wsplist.add(prodwsp);
                                        mrplist.add(prodmrp);
                                        produimagelist.add(GetURL.getServerip() + prodimage);
                                        productsubcatge.add(produsubcatge);
                                        procololist.add("#000000");
                                        if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                            multicolor.add("true");
                                        } else {
                                            multicolor.add("false");
                                        }
                                        hashcatge.add(produsubcatge);
                                        hashbrand.add(probrand);
                                    }
                                }
                            }else if(brandvalue.equals("") && catgevalue.equals("")){

                                if(ordprodidlist.size() != 0){
                                    for(int a=0;a<ordprodidlist.size();a++) {
                                        if (productid.equals(ordprodidlist.get(a))) {
                                            productnamelist.add(productname);
                                            productidlist.add(productid);
                                            wsplist.add(prodwsp);
                                            mrplist.add(prodmrp);
                                            produimagelist.add(GetURL.getServerip() + prodimage);
                                            productsubcatge.add(produsubcatge);
                                            procololist.add("#AA4098");
                                            if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                                multicolor.add("true");
                                            } else {
                                                multicolor.add("false");
                                            }
                                            hashcatge.add(produsubcatge);
                                            hashbrand.add(probrand);
                                            checkstatus = "true";
                                            break;
                                        }

                                    }
                                    if(checkstatus.equals("false")) {
                                        productnamelist.add(productname);
                                        productidlist.add(productid);
                                        wsplist.add(prodwsp);
                                        mrplist.add(prodmrp);
                                        produimagelist.add(GetURL.getServerip() + prodimage);
                                        productsubcatge.add(produsubcatge);
                                        procololist.add("#000000");
                                        if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                            multicolor.add("true");
                                        } else {
                                            multicolor.add("false");
                                        }
                                        hashcatge.add(produsubcatge);
                                        hashbrand.add(probrand);
                                    }
                                }else {

                                    productnamelist.add(productname);
                                    productidlist.add(productid);
                                    wsplist.add(prodwsp);
                                    mrplist.add(prodmrp);
                                    produimagelist.add(GetURL.getServerip() + prodimage);
                                    productsubcatge.add(produsubcatge);
                                    procololist.add("#000000");
                                    if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                        multicolor.add("true");
                                    } else {
                                        multicolor.add("false");
                                    }
                                    hashcatge.add(produsubcatge);
                                    hashbrand.add(probrand);
                                }
                            }else{
                                if (produsubcatge.equals(catgevalue) && probrand.equals(brandvalue)) {
                                    if(ordprodidlist.size() != 0){
                                        for(int a=0;a<ordprodidlist.size();a++) {
                                            if (productid.equals(ordprodidlist.get(a))) {
                                                productnamelist.add(productname);
                                                productidlist.add(productid);
                                                wsplist.add(prodwsp);
                                                mrplist.add(prodmrp);
                                                produimagelist.add(GetURL.getServerip() + prodimage);
                                                productsubcatge.add(produsubcatge);
                                                procololist.add("#AA4098");
                                                if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                                    multicolor.add("true");
                                                } else {
                                                    multicolor.add("false");
                                                }
                                                hashcatge.add(produsubcatge);
                                                hashbrand.add(probrand);
                                                checkstatus = "true";
                                                break;
                                            }

                                        }
                                        if(checkstatus.equals("false")) {
                                            productnamelist.add(productname);
                                            productidlist.add(productid);
                                            wsplist.add(prodwsp);
                                            mrplist.add(prodmrp);
                                            produimagelist.add(GetURL.getServerip() + prodimage);
                                            productsubcatge.add(produsubcatge);
                                            procololist.add("#000000");
                                            if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                                multicolor.add("true");
                                            } else {
                                                multicolor.add("false");
                                            }
                                            hashcatge.add(produsubcatge);
                                            hashbrand.add(probrand);
                                        }
                                    }else {

                                        productnamelist.add(productname);
                                        productidlist.add(productid);
                                        wsplist.add(prodwsp);
                                        mrplist.add(prodmrp);
                                        produimagelist.add(GetURL.getServerip() + prodimage);
                                        productsubcatge.add(produsubcatge);
                                        procololist.add("#000000");
                                        if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                            multicolor.add("true");
                                        } else {
                                            multicolor.add("false");
                                        }
                                        hashcatge.add(produsubcatge);
                                        hashbrand.add(probrand);
                                    }
                                }
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if(viewstatus.equals("tileview")) {
                    dialoguelistview = (RecyclerView) dialog.findViewById(R.id.prolistView);
                    dialoguelistview.setHasFixedSize(true);
                    layoutManager1 = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
                    dialoguelistview.setLayoutManager(layoutManager1);
                    TileAdapter tileAdapter = new TileAdapter(productidlist,productnamelist,produimagelist,productsubcatge,procololist,multicolor);
                    dialoguelistview.setAdapter(tileAdapter);
                }else if(viewstatus.equals("gridview")) {
                    dialoguelistview = (RecyclerView) dialog.findViewById(R.id.prolistView);
                    dialoguelistview.setHasFixedSize(true);
                    layoutManager1 = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
                    dialoguelistview.setLayoutManager(layoutManager1);
                    GridAdapter gridAdapter = new GridAdapter(productidlist,productnamelist,wsplist,mrplist,produimagelist,productsubcatge,procololist,multicolor);
                    dialoguelistview.setAdapter(gridAdapter);
                } else if (viewstatus.equals("listview")) {
                    itemChecked = new boolean[productidlist.size()];
                    dialoguelistview = (RecyclerView) dialog.findViewById(R.id.prolistView);
                    dialoguelistview.setHasFixedSize(true);
                    layoutManager2 = new LinearLayoutManager(getApplicationContext());
                    dialoguelistview.setLayoutManager(layoutManager2);
                    ListAdapter listAdapter = new ListAdapter(productidlist,productnamelist,multicolor);
                    dialoguelistview.setAdapter(listAdapter);
                }
                filterdialog.dismiss();
            }
        });
        filterdialog.show();

    }
    public void addTextListener(){

        inputSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                ArrayList<String>filpronamelist = new ArrayList<>();
                ArrayList<String>filproidlist = new ArrayList<>();
                ArrayList<String>filwsplist = new ArrayList<>();
                ArrayList<String>filmrplist = new ArrayList<>();
                ArrayList<String>filproimglist = new ArrayList<>();
                ArrayList<String>filtprocatglist = new ArrayList<>();
                ArrayList<String>filtprocollist = new ArrayList<>();
                ArrayList<String>filtpromcollist = new ArrayList<>();


                for (int i = 0; i < productnamelist.size(); i++) {

                    final String text = productnamelist.get(i).toLowerCase();
                    if (text.contains(query)) {
                        filpronamelist.add(productnamelist.get(i));
                        filproidlist.add(productidlist.get(i));
                        filwsplist.add(wsplist.get(i));
                        filmrplist.add(mrplist.get(i));
                        filproimglist.add(produimagelist.get(i));
                        filtprocatglist.add(productsubcatge.get(i));
                        filtprocollist.add(procololist.get(i));
                        filtpromcollist.add(multicolor.get(i));
                    }
                }
                if(viewstatus.equals("tileview")) {
                    dialoguelistview = (RecyclerView) dialog.findViewById(R.id.prolistView);
                    dialoguelistview.setHasFixedSize(true);
                    layoutManager1 = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
                    dialoguelistview.setLayoutManager(layoutManager1);
                    TileAdapter tileAdapter = new TileAdapter(filproidlist,filpronamelist,filproimglist,filtprocatglist,filtprocollist,filtpromcollist);
                    dialoguelistview.setAdapter(tileAdapter);
                }else if(viewstatus.equals("gridview")) {
                    dialoguelistview = (RecyclerView) dialog.findViewById(R.id.prolistView);
                    dialoguelistview.setHasFixedSize(true);
                    layoutManager1 = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
                    dialoguelistview.setLayoutManager(layoutManager1);
                    GridAdapter gridAdapter = new GridAdapter(filproidlist,filpronamelist,filwsplist,filmrplist,filproimglist,filtprocatglist,filtprocollist,filtpromcollist);
                    dialoguelistview.setAdapter(gridAdapter);
                } else if (viewstatus.equals("listview")) {
                    itemChecked = new boolean[filproidlist.size()];
                    dialoguelistview = (RecyclerView) dialog.findViewById(R.id.prolistView);
                    dialoguelistview.setHasFixedSize(true);
                    layoutManager2 = new LinearLayoutManager(getApplicationContext());
                    dialoguelistview.setLayoutManager(layoutManager2);
                    ListAdapter listAdapter = new ListAdapter(filproidlist,filpronamelist,filtpromcollist);
                    dialoguelistview.setAdapter(listAdapter);
                }


            }
        });
    }
    public void listadapter(String data){
        topbotstatus = data;
        productnamelist = new ArrayList<>();
        productidlist = new ArrayList<>();
        wsplist = new ArrayList<>();
        mrplist = new ArrayList<>();
        produimagelist = new ArrayList<>();
        procololist = new ArrayList<>();
        multicolor = new ArrayList<>();
        productsubcatge = new ArrayList<>();
        hashcatge = new HashSet<>();
        hashbrand = new HashSet<>();
        catgefilterlist = new ArrayList<>();
        brandfilterlist = new ArrayList<>();
        catgefilterlist.add("");
        brandfilterlist.add("");
        for(int i=0;i< productlist.size();i++){
            String checkstatus = "false";
            JSONObject plist= productlist.get(i);
            try {
                String productcatege = plist.get("categ_types").toString();
                String productid = plist.get("id").toString();
                String productname = plist.get("name").toString();
                String prodimage = plist.get("image_url").toString();
                String prodwsp = plist.get("wsp").toString();
                String prodmrp = plist.get("mrp").toString();
                String colorbased = plist.get("color_shade").toString();
                String fitbased = plist.get("fit_based").toString();
                String sleeveBased = plist.get("sleeve_based").toString();
                String lengthBased = plist.get("length_based").toString();
                String produsubcatge = plist.get("product_categ").toString();
                String probrand = plist.get("brand_id").toString();
                if(productcatege.equals(data)){
                    if(ordprodidlist.size() != 0){
                        for(int a=0;a<ordprodidlist.size();a++) {
                            if (productid.equals(ordprodidlist.get(a))) {
                                productnamelist.add(productname);
                                productidlist.add(productid);
                                wsplist.add(prodwsp);
                                mrplist.add(prodmrp);
                                produimagelist.add(GetURL.getServerip() + prodimage);
                                productsubcatge.add(produsubcatge);
                                procololist.add("#AA4098");
                                if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                    multicolor.add("true");
                                } else {
                                    multicolor.add("false");
                                }
                                hashcatge.add(produsubcatge);
                                hashbrand.add(probrand);
                                checkstatus = "true";
                                break;
                            }

                        }
                        if(checkstatus.equals("false")) {
                            productnamelist.add(productname);
                            productidlist.add(productid);
                            wsplist.add(prodwsp);
                            mrplist.add(prodmrp);
                            produimagelist.add(GetURL.getServerip() + prodimage);
                            productsubcatge.add(produsubcatge);
                            procololist.add("#000000");
                            if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                                multicolor.add("true");
                            } else {
                                multicolor.add("false");
                            }
                            hashcatge.add(produsubcatge);
                            hashbrand.add(probrand);
                        }
                    }else {

                        productnamelist.add(productname);
                        productidlist.add(productid);
                        wsplist.add(prodwsp);
                        mrplist.add(prodmrp);
                        produimagelist.add(GetURL.getServerip() + prodimage);
                        productsubcatge.add(produsubcatge);
                        procololist.add("#000000");
                        if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                            multicolor.add("true");
                        } else {
                            multicolor.add("false");
                        }
                        hashcatge.add(produsubcatge);
                        hashbrand.add(probrand);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        catgefilterlist.addAll(hashcatge);
        brandfilterlist.addAll(hashbrand);
        if(viewstatus.equals("tileview")) {
            dialoguelistview = (RecyclerView) dialog.findViewById(R.id.prolistView);
            dialoguelistview.setHasFixedSize(true);
            layoutManager1 = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
            dialoguelistview.setLayoutManager(layoutManager1);
            TileAdapter tileAdapter = new TileAdapter(productidlist,productnamelist,produimagelist,productsubcatge,procololist,multicolor);
            dialoguelistview.setAdapter(tileAdapter);
        } else if(viewstatus.equals("gridview")) {
            dialoguelistview = (RecyclerView) dialog.findViewById(R.id.prolistView);
            dialoguelistview.setHasFixedSize(true);
            layoutManager1 = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
            dialoguelistview.setLayoutManager(layoutManager1);
            GridAdapter gridAdapter = new GridAdapter(productidlist,productnamelist,wsplist,mrplist,produimagelist,productsubcatge,procololist,multicolor);
            dialoguelistview.setAdapter(gridAdapter);
        } else if (viewstatus.equals("listview")) {
            itemChecked = new boolean[productidlist.size()];
            dialoguelistview = (RecyclerView) dialog.findViewById(R.id.prolistView);
            dialoguelistview.setHasFixedSize(true);
            layoutManager2 = new LinearLayoutManager(getApplicationContext());
            dialoguelistview.setLayoutManager(layoutManager2);
            ListAdapter listAdapter = new ListAdapter(productidlist,productnamelist,multicolor);
            dialoguelistview.setAdapter(listAdapter);
        }
    }


    class TileAdapter extends RecyclerView.Adapter<TileAdapter.ViewHolder>  {
        ImageView proimage;
        ArrayList<String>adpronamelist;
        ArrayList<String>adproidlist;
        ArrayList<String>adproimagelist;
        ArrayList<String>adprocololist;
        ArrayList<String>adpromcololist;
        ArrayList<String>adprosubcatgelist;
        private TileAdapter(ArrayList<String>proid,ArrayList<String>proname,ArrayList<String>proimg,ArrayList<String>prosucat,ArrayList<String>procolo,ArrayList<String>promucolo) {
            adproidlist = proid;
            adpronamelist = proname;
            adproimagelist = proimg;
            adprocololist = procolo;
            adpromcololist = promucolo;
            adprosubcatgelist = prosucat;
        }

        @Override
        public TileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tilepro_list, parent, false);

            return new TileAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final TileAdapter.ViewHolder holder, final int position) {
            holder.setIsRecyclable(false);
            holder.productname.setText(adpronamelist.get(position));
            holder.cvPlaces.setTag(position);
            holder.proselcimage.setColorFilter(Color.parseColor(adprocololist.get(position)), PorterDuff.Mode.SRC_IN);

            if(!adproimagelist.get(position).equals("")){
                RequestOptions options = new RequestOptions()
                        .error(R.drawable.person);

                Glide.with(getApplicationContext())
                        .load(Headers.getUrlWithHeaders(adproimagelist.get(position)))
                        .apply(options)
                        .into(proimage);
            }

            holder.cvPlaces.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tileproaddsta="";
                    int pos = (int) holder.cvPlaces.getTag();

                        imagevarientlist = new ArrayList<>();
                        sleevelist = new ArrayList<>();
                        fitlist = new ArrayList<>();
                        lenglist = new ArrayList<>();
                        colorcodelist = new ArrayList<>();
                        coloraddlist = new ArrayList<>();
                        coloridlist = new ArrayList<>();
                        sizelist = new ArrayList<>();
                        sizekeylist = new ArrayList<>();
                        selectedcolor = new ArrayList<>();
                        selectedcolimg = new ArrayList<>();
                        fullcoloraddlist = new ArrayList<>();
                        pricelist  = new JSONArray();
                        String pid = adproidlist.get(position);
                        for (int x = 0; x < ordprodidlist.size(); x++) {
                            if (pid.equals(ordprodidlist.get(x))) {
                                if (adpromcololist.get(position).equals("true")) {

                                    String pname = adpronamelist.get(position);
                                    String pcatge = adprosubcatgelist.get(position);

                                    for (int i = 0; i < productlist.size(); i++) {
                                        JSONObject plist = productlist.get(i);
                                        try {
                                            String proid = plist.getString("id");
                                            if (pid.equals(proid)) {
                                                String sizebasecost = plist.get("size_based_cost").toString();
                                                String pmcatge = plist.get("categ_types").toString();
                                                String value = plist.get("attribute_dict").toString();
                                                String imagelist = plist.get("varient_image_url").toString();
                                                if(!imagelist.equals("null")){
                                                    JSONArray imglist = plist.getJSONArray("varient_image_url");
                                                    if (imglist.length() != 0) {

                                                        for (int a = 0; a < imglist.length(); a++) {
                                                            imagevarientlist.add(GetURL.getServerip()+String.valueOf(imglist.get(a)));
                                                        }
                                                    }
                                                }
                                                JSONObject jsonObject = new JSONObject(value);
                                                JSONArray slelist = jsonObject.getJSONArray("sleeve_list");
                                                if (slelist.length() != 0) {

                                                    for (int a = 0; a < slelist.length(); a++) {
                                                        sleevelist.add(String.valueOf(slelist.get(a)));
                                                    }
                                                }
                                                JSONArray fitslist = jsonObject.getJSONArray("fit_list");
                                                if (fitslist.length() != 0) {

                                                    for (int c = 0; c < fitslist.length(); c++) {
                                                        fitlist.add(String.valueOf(fitslist.get(c)));
                                                    }
                                                }
                                                JSONArray lenlist = jsonObject.getJSONArray("length_list");
                                                if (lenlist.length() != 0) {

                                                    for (int c = 0; c < lenlist.length(); c++) {
                                                        lenglist.add(String.valueOf(lenlist.get(c)));
                                                    }
                                                }
                                                JSONArray sizeslist = jsonObject.getJSONArray("size_list");
                                                if (sizeslist.length() != 0) {

                                                    for (int c = 0; c < sizeslist.length(); c++) {
                                                        sizelist.add(String.valueOf(sizeslist.get(c)));
                                                    }
                                                }
                                                JSONArray sizekeyslist = jsonObject.getJSONArray("size_key_list");
                                                if (sizekeyslist.length() != 0) {

                                                    for (int c = 0; c < sizekeyslist.length(); c++) {
                                                        sizekeylist.add(String.valueOf(sizekeyslist.get(c)));
                                                    }
                                                }
                                                JSONArray colimagelist = jsonObject.getJSONArray("color_swatch_list");
                                                if (colimagelist.length() != 0) {

                                                    for (int b = 0; b < colimagelist.length(); b++) {
                                                        colorcodelist.add(GetURL.getServerip()+ String.valueOf(colimagelist.get(b)));
                                                        coloraddlist.add("#AA4098");
                                                        fullcoloraddlist.add("#AA4098");
                                                    }
                                                }
                                                JSONArray colidlist = jsonObject.getJSONArray("color_list");
                                                if (colidlist.length() != 0) {

                                                    for (int c = 0; c < colidlist.length(); c++) {
                                                        coloridlist.add(String.valueOf(colidlist.get(c)));
                                                    }
                                                }
                                                pricelist = jsonObject.getJSONArray("price_list");

                                                openvarientbox(pid, pname, pcatge, pmcatge, sizebasecost, "tile", 0);


                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    tileproaddsta="true";
                                } else {
                                    Toast.makeText(getApplicationContext(), "This Product is Already added", Toast.LENGTH_LONG).show();
                                    tileproaddsta="false";
                                }
                                break;
                            }
                        }
                        if (tileproaddsta.equals("")) {
                            adprocololist.remove(pos);
                            adprocololist.add(pos, "#AA4098");
                            for(int a=0;a<productidlist.size();a++){
                                if(pid.equals(productidlist.get(a))){
                                    procololist.remove(a);
                                    procololist.add(a, "#AA4098");
                                }
                            }
                            holder.proselcimage.setColorFilter(Color.parseColor(adprocololist.get(position)), PorterDuff.Mode.SRC_IN);
                            String pname = adpronamelist.get(position);
                            String pcatge = adprosubcatgelist.get(position);

                            for (int i = 0; i < productlist.size(); i++) {
                                JSONObject plist = productlist.get(i);
                                try {
                                    String proid = plist.getString("id");
                                    if (pid.equals(proid)) {
                                        String sizebasecost = plist.get("size_based_cost").toString();
                                        String pmcatge = plist.get("categ_types").toString();
                                        String value = plist.get("attribute_dict").toString();
                                        String imagelist = plist.get("varient_image_url").toString();
                                        if(!imagelist.equals("null")){
                                            JSONArray imglist = plist.getJSONArray("varient_image_url");
                                            if (imglist.length() != 0) {

                                                for (int a = 0; a < imglist.length(); a++) {
                                                    imagevarientlist.add(GetURL.getServerip()+String.valueOf(imglist.get(a)));
                                                }
                                            }
                                        }
                                        JSONObject jsonObject = new JSONObject(value);
                                        JSONArray slelist = jsonObject.getJSONArray("sleeve_list");
                                        if (slelist.length() != 0) {

                                            for (int a = 0; a < slelist.length(); a++) {
                                                sleevelist.add(String.valueOf(slelist.get(a)));
                                            }
                                        }
                                        JSONArray fitslist = jsonObject.getJSONArray("fit_list");
                                        if (fitslist.length() != 0) {

                                            for (int c = 0; c < fitslist.length(); c++) {
                                                fitlist.add(String.valueOf(fitslist.get(c)));
                                            }
                                        }
                                        JSONArray lenlist = jsonObject.getJSONArray("length_list");
                                        if (lenlist.length() != 0) {

                                            for (int c = 0; c < lenlist.length(); c++) {
                                                lenglist.add(String.valueOf(lenlist.get(c)));
                                            }
                                        }
                                        JSONArray sizeslist = jsonObject.getJSONArray("size_list");
                                        if (sizeslist.length() != 0) {

                                            for (int c = 0; c < sizeslist.length(); c++) {
                                                sizelist.add(String.valueOf(sizeslist.get(c)));
                                            }
                                        }
                                        JSONArray sizekeyslist = jsonObject.getJSONArray("size_key_list");
                                        if (sizekeyslist.length() != 0) {

                                            for (int c = 0; c < sizekeyslist.length(); c++) {
                                                sizekeylist.add(String.valueOf(sizekeyslist.get(c)));
                                            }
                                        }
                                        JSONArray colimagelist = jsonObject.getJSONArray("color_swatch_list");
                                        if (colimagelist.length() != 0) {

                                            for (int b = 0; b < colimagelist.length(); b++) {
                                                colorcodelist.add(GetURL.getServerip()+String.valueOf(colimagelist.get(b)));
                                                coloraddlist.add("#AA4098");
                                                fullcoloraddlist.add("#AA4098");
                                            }
                                        }
                                        JSONArray colidlist = jsonObject.getJSONArray("color_list");
                                        if (colidlist.length() != 0) {

                                            for (int c = 0; c < colidlist.length(); c++) {
                                                coloridlist.add(String.valueOf(colidlist.get(c)));
                                            }
                                        }
                                        pricelist = jsonObject.getJSONArray("price_list");

                                        openvarientbox(pid, pname, pcatge, pmcatge, sizebasecost, "tile", 0);


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                }
            });
        }



        @Override
        public int getItemCount() {
            return adpronamelist.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView productname;
            CardView cvPlaces;
            ImageView proselcimage;
            public ViewHolder(View itemView) {
                super(itemView);

                productname = (TextView) itemView.findViewById(R.id.plname);
                cvPlaces = (CardView) itemView.findViewById(R.id.cvPlaces);
                proselcimage = (ImageView) itemView.findViewById(R.id.proselcimage);
                proimage = (ImageView) itemView.findViewById(R.id.pimage);

            }
        }
    }

    class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>  {
        ImageView proimage;
        ArrayList<String>adpronamelist;
        ArrayList<String>adproidlist;
        ArrayList<String>adprowsplist;
        ArrayList<String>adpromrplist;
        ArrayList<String>adproimagelist;
        ArrayList<String>adprosubcatgelist;
        ArrayList<String>adprocololist;
        ArrayList<String>adpromcololist;
        private GridAdapter(ArrayList<String>proid,ArrayList<String>proname,ArrayList<String>prowsp,ArrayList<String>promrp,ArrayList<String>proimg,ArrayList<String>prosucat,ArrayList<String>procolo,ArrayList<String>promucolo) {
            adproidlist = proid;
            adpronamelist = proname;
            adprowsplist = prowsp;
            adpromrplist = promrp;
            adproimagelist = proimg;
            adprosubcatgelist = prosucat;
            adprocololist = procolo;
            adpromcololist = promucolo;
        }

        @Override
        public GridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderformpro_list, parent, false);

            return new GridAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final GridAdapter.ViewHolder holder, final int position) {
            holder.setIsRecyclable(false);
            holder.productname.setText(adpronamelist.get(position));
            holder.pwsp.setText(adprowsplist.get(position));
            holder.pmrp.setText(adpromrplist.get(position));
            holder.pcatge.setText(adprosubcatgelist.get(position));
            holder.cvPlaces.setTag(position);
            holder.proselcimage.setColorFilter(Color.parseColor(adprocololist.get(position)), PorterDuff.Mode.SRC_IN);

            if(adpromcololist.get(position).equals("true")){
                holder.multicolor.setVisibility(View.VISIBLE);
            }else{
                holder.multicolor.setVisibility(View.GONE);
            }
            if(!adproimagelist.get(position).equals("")){
                RequestOptions options = new RequestOptions()
                        .error(R.drawable.person);

                Glide.with(getApplicationContext())
                        .load(Headers.getUrlWithHeaders(adproimagelist.get(position)))
                        .apply(options)
                        .into(proimage);
            }

            holder.cvPlaces.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gridproaddsta="";
                    int pos = (int) holder.cvPlaces.getTag();
                        sleevelist = new ArrayList<>();
                        fitlist = new ArrayList<>();
                        lenglist = new ArrayList<>();
                        colorcodelist = new ArrayList<>();
                        coloraddlist = new ArrayList<>();
                        coloridlist = new ArrayList<>();
                        sizelist = new ArrayList<>();
                        sizekeylist = new ArrayList<>();
                        selectedcolor = new ArrayList<>();
                        selectedcolimg = new ArrayList<>();
                        fullcoloraddlist = new ArrayList<>();
                        pricelist  = new JSONArray();
                        String pid = adproidlist.get(position);
                        for (int x = 0; x < ordprodidlist.size(); x++) {
                            if (pid.equals(ordprodidlist.get(x))) {
                                if (adpromcololist.get(position).equals("true")) {

                                    String pname = adpronamelist.get(position);
                                    String pcatge = adprosubcatgelist.get(position);

                                    for (int i = 0; i < productlist.size(); i++) {
                                        JSONObject plist = productlist.get(i);
                                        try {
                                            String proid = plist.getString("id");
                                            if (pid.equals(proid)) {
                                                String sizebasecost = plist.get("size_based_cost").toString();
                                                String pmcatge = plist.get("categ_types").toString();
                                                String value = plist.get("attribute_dict").toString();
                                                JSONObject jsonObject = new JSONObject(value);
                                                JSONArray slelist = jsonObject.getJSONArray("sleeve_list");
                                                if (slelist.length() != 0) {

                                                    for (int a = 0; a < slelist.length(); a++) {
                                                        sleevelist.add(String.valueOf(slelist.get(a)));
                                                    }
                                                }
                                                JSONArray fitslist = jsonObject.getJSONArray("fit_list");
                                                if (fitslist.length() != 0) {

                                                    for (int c = 0; c < fitslist.length(); c++) {
                                                        fitlist.add(String.valueOf(fitslist.get(c)));
                                                    }
                                                }
                                                JSONArray lenlist = jsonObject.getJSONArray("length_list");
                                                if (lenlist.length() != 0) {

                                                    for (int c = 0; c < lenlist.length(); c++) {
                                                        lenglist.add(String.valueOf(lenlist.get(c)));
                                                    }
                                                }
                                                JSONArray sizeslist = jsonObject.getJSONArray("size_list");
                                                if (sizeslist.length() != 0) {

                                                    for (int c = 0; c < sizeslist.length(); c++) {
                                                        sizelist.add(String.valueOf(sizeslist.get(c)));
                                                    }
                                                }
                                                JSONArray sizekeyslist = jsonObject.getJSONArray("size_key_list");
                                                if (sizekeyslist.length() != 0) {

                                                    for (int c = 0; c < sizekeyslist.length(); c++) {
                                                        sizekeylist.add(String.valueOf(sizekeyslist.get(c)));
                                                    }
                                                }
                                                JSONArray colimagelist = jsonObject.getJSONArray("color_swatch_list");
                                                if (colimagelist.length() != 0) {

                                                    for (int b = 0; b < colimagelist.length(); b++) {
                                                        colorcodelist.add(GetURL.getServerip()+ String.valueOf(colimagelist.get(b)));
                                                        coloraddlist.add("#AA4098");
                                                        fullcoloraddlist.add("#AA4098");
                                                    }
                                                }
                                                JSONArray colidlist = jsonObject.getJSONArray("color_list");
                                                if (colidlist.length() != 0) {

                                                    for (int c = 0; c < colidlist.length(); c++) {
                                                        coloridlist.add(String.valueOf(colidlist.get(c)));
                                                    }
                                                }
                                                pricelist = jsonObject.getJSONArray("price_list");

                                                openvarientbox(pid, pname, pcatge, pmcatge, sizebasecost, "grid", 0);


                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    gridproaddsta="true";
                                } else {
                                    Toast.makeText(getApplicationContext(), "This Product is Already added", Toast.LENGTH_LONG).show();
                                    gridproaddsta="false";
                                }
                               break;
                            }
                        }
                        if (gridproaddsta.equals("")) {
                            adprocololist.remove(pos);
                            adprocololist.add(pos, "#AA4098");
                            for(int a=0;a<productidlist.size();a++){
                                if(pid.equals(productidlist.get(a))){
                                    procololist.remove(a);
                                    procololist.add(a, "#AA4098");
                                }
                            }
                            holder.proselcimage.setColorFilter(Color.parseColor(adprocololist.get(position)), PorterDuff.Mode.SRC_IN);
                            String pname = adpronamelist.get(position);
                            String pcatge = adprosubcatgelist.get(position);

                            for (int i = 0; i < productlist.size(); i++) {
                                JSONObject plist = productlist.get(i);
                                try {
                                    String proid = plist.getString("id");
                                    if (pid.equals(proid)) {
                                        String sizebasecost = plist.get("size_based_cost").toString();
                                        String pmcatge = plist.get("categ_types").toString();
                                        String value = plist.get("attribute_dict").toString();
                                        JSONObject jsonObject = new JSONObject(value);
                                        JSONArray slelist = jsonObject.getJSONArray("sleeve_list");
                                        if (slelist.length() != 0) {

                                            for (int a = 0; a < slelist.length(); a++) {
                                                sleevelist.add(String.valueOf(slelist.get(a)));
                                            }
                                        }
                                        JSONArray fitslist = jsonObject.getJSONArray("fit_list");
                                        if (fitslist.length() != 0) {

                                            for (int c = 0; c < fitslist.length(); c++) {
                                                fitlist.add(String.valueOf(fitslist.get(c)));
                                            }
                                        }
                                        JSONArray lenlist = jsonObject.getJSONArray("length_list");
                                        if (lenlist.length() != 0) {

                                            for (int c = 0; c < lenlist.length(); c++) {
                                                lenglist.add(String.valueOf(lenlist.get(c)));
                                            }
                                        }
                                        JSONArray sizeslist = jsonObject.getJSONArray("size_list");
                                        if (sizeslist.length() != 0) {

                                            for (int c = 0; c < sizeslist.length(); c++) {
                                                sizelist.add(String.valueOf(sizeslist.get(c)));
                                            }
                                        }
                                        JSONArray sizekeyslist = jsonObject.getJSONArray("size_key_list");
                                        if (sizekeyslist.length() != 0) {

                                            for (int c = 0; c < sizekeyslist.length(); c++) {
                                                sizekeylist.add(String.valueOf(sizekeyslist.get(c)));
                                            }
                                        }
                                        JSONArray colimagelist = jsonObject.getJSONArray("color_swatch_list");
                                        if (colimagelist.length() != 0) {

                                            for (int b = 0; b < colimagelist.length(); b++) {
                                                colorcodelist.add(GetURL.getServerip()+String.valueOf(colimagelist.get(b)));
                                                coloraddlist.add("#AA4098");
                                                fullcoloraddlist.add("#AA4098");
                                            }
                                        }
                                        JSONArray colidlist = jsonObject.getJSONArray("color_list");
                                        if (colidlist.length() != 0) {

                                            for (int c = 0; c < colidlist.length(); c++) {
                                                coloridlist.add(String.valueOf(colidlist.get(c)));
                                            }
                                        }
                                        pricelist = jsonObject.getJSONArray("price_list");

                                         openvarientbox(pid, pname, pcatge, pmcatge, sizebasecost, "grid", 0);


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                }
            });
        }



        @Override
        public int getItemCount() {
            return adpronamelist.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView productname,pwsp,pmrp,pcatge;
            CardView cvPlaces;
            ImageView proselcimage,multicolor;
            public ViewHolder(View itemView) {
                super(itemView);

                productname = (TextView) itemView.findViewById(R.id.plname);
                pwsp = (TextView) itemView.findViewById(R.id.pwsp);
                pmrp = (TextView) itemView.findViewById(R.id.pmrp);
                cvPlaces = (CardView) itemView.findViewById(R.id.cvPlaces);
                proselcimage = (ImageView) itemView.findViewById(R.id.proselcimage);
                proimage = (ImageView) itemView.findViewById(R.id.pimage);
                multicolor = (ImageView) itemView.findViewById(R.id.multicolor);
                pcatge = (TextView) itemView.findViewById(R.id.pcatge);
            }
        }
    }

    class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>  {

        ArrayList<String>adpronamelist;
        ArrayList<String>adproidlist;
        ArrayList<String>adpromcololist;
        private ListAdapter(ArrayList<String>proid,ArrayList<String>proname,ArrayList<String>promcolo) {
            adpronamelist = proname;
            adproidlist = proid;
            adpromcololist = promcolo;

        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview, parent, false);

            return new ListAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
            holder.setIsRecyclable(false);
            holder.productname.setText(adpronamelist.get(position));
            holder.checkbox.setChecked(itemChecked[position]);
            if(selectedproidlist.size()!=0){
                for(int i=0;i<selectedproidlist.size();i++){
                    if(selectedproidlist.get(i).equals(adproidlist.get(position))){
                        itemChecked[position] = true;
                        holder.checkbox.setChecked(itemChecked[position]);
                    }
                }
            }
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                        multiproaddsta="";
                       for(int i=0;i<ordprodidlist.size();i++){
                           if(adproidlist.get(position).equals(ordprodidlist.get(i))){
                               if(adpromcololist.get(position).equals("true")){
                                   selectedproidlist.add(adproidlist.get(position));
                                   multiproaddsta="true";
                                   itemChecked[position] = true;
                                   break;
                               }else{
                                   Toast.makeText(getApplicationContext(), "This Product is Already added", Toast.LENGTH_LONG).show();
                                   multiproaddsta="false";
                                   itemChecked[position] = false;
                                   holder.checkbox.setChecked(itemChecked[position]);
                               }
                           }
                       }
                       if(multiproaddsta.equals("")) {
                           selectedproidlist.add(adproidlist.get(position));
                           itemChecked[position] = true;
                       }
                    }else{
                        selectedproidlist.remove(adproidlist.get(position));
                        itemChecked[position] = false;
                    }

                }
            });
        }



        @Override
        public int getItemCount() {
            return adpronamelist.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView productname;
            CheckBox checkbox;
            public ViewHolder(View itemView) {
                super(itemView);
                checkbox = (CheckBox) itemView.findViewById(R.id.plcheckbox);
                productname = (TextView) itemView.findViewById(R.id.plname);
            }
        }
    }

    class TrouserAdapter extends RecyclerView.Adapter<TrouserAdapter.ViewHolder> {
        ImageView shade;
        private TrouserAdapter() {
        }

        @Override
        public TrouserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shirtfragment_list, parent, false);
            return new TrouserAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final TrouserAdapter.ViewHolder holder, final int position) {
            holder.setIsRecyclable(false);
            holder.pname.setText(ordpronamelist.get(position));
            holder.pcatge.setText(ordprocatglist.get(position));
            holder.tqty.setText(ordtqty.get(position));
            holder.sleeve.setText(ordsleeve.get(position));
            holder.sizetype.setText(ordsizetype.get(position));
            holder.fit.setText(ordfit.get(position));
            holder.leng.setText(ordleng.get(position));
            holder.size28.setText(ordsize28.get(position));
            holder.size30.setText(ordsize30.get(position));
            holder.size32.setText(ordsize32.get(position));
            holder.size34.setText(ordsize34.get(position));
            holder.size36.setText(ordsize36.get(position));
            holder.size38.setText(ordsize38.get(position));
            holder.size40.setText(ordsize40.get(position));
            holder.size42.setText(ordsize42.get(position));
            holder.size44.setText(ordsize44.get(position));
            holder.size46.setText(ordsize46.get(position));
            holder.size48.setText(ordsize48.get(position));
            holder.size28.setTag(position);
            holder.size30.setTag(position);
            holder.size32.setTag(position);
            holder.size34.setTag(position);
            holder.size36.setTag(position);
            holder.size38.setTag(position);
            holder.size40.setTag(position);
            holder.size42.setTag(position);
            holder.size44.setTag(position);
            holder.size46.setTag(position);
            holder.size48.setTag(position);
            holder.sizetype.setTag(position);
            holder.qty.setTag(position);
            holder.sleeve.setTag(position);
            holder.prodelete.setTag(position);
            holder.pvariant.setTag(position);

            if(!ordcolimage.get(position).equals("")){


                Glide.with(getApplicationContext())
                        .load(Headers.getUrlWithHeaders(ordcolimage.get(position)))
                        .into(shade);

            }

            holder.size28.setEnabled(false);
            //holder.size28.setFocusable(false);
            holder.size28.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size30.setEnabled(false);
           // holder.size30.setFocusable(false);
            holder.size30.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size32.setEnabled(false);
           // holder.size32.setFocusable(false);
            holder.size32.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size34.setEnabled(false);
           // holder.size34.setFocusable(false);
            holder.size34.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size36.setEnabled(false);
           // holder.size36.setFocusable(false);
            holder.size36.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size38.setEnabled(false);
           // holder.size38.setFocusable(false);
            holder.size38.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size40.setEnabled(false);
           // holder.size40.setFocusable(false);
            holder.size40.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size42.setEnabled(false);
           // holder.size42.setFocusable(false);
            holder.size42.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size44.setEnabled(false);
          //  holder.size44.setFocusable(false);
            holder.size44.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size46.setEnabled(false);
            //holder.size46.setFocusable(false);
            holder.size46.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size48.setEnabled(false);
           // holder.size48.setFocusable(false);
            holder.size48.setBackground(getResources().getDrawable(R.drawable.hide_text_style));


            if(ordqty.get(position).equals("")) {
                holder.qty.setEnabled(false);
               // holder.qty.setFocusable(false);
                holder.qty.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            }else{
                holder.qty.setEnabled(true);
               // holder.qty.setFocusable(true);
                holder.qty.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
            }

            String vid = ordprodidlist.get(position);
            for (int i = 0; i < productlist.size(); i++) {
                JSONObject plist = productlist.get(i);
                try {
                    String proid = plist.getString("id");
                    if (vid.equals(proid)) {
                        String value = plist.get("attribute_dict").toString();
                        JSONObject jsonObject = new JSONObject(value);
                        JSONArray sizekeyslist = jsonObject.getJSONArray("size_key_list");
                        if (sizekeyslist.length() != 0) {

                            for (int c = 0; c < sizekeyslist.length(); c++) {
                                String getkey = (String.valueOf(sizekeyslist.get(c)));
                                if(getkey.equals("size_28")){
                                    holder.size28.setEnabled(true);
                                   // holder.size28.setFocusable(true);
                                    holder.size28.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_30")){
                                    holder.size30.setEnabled(true);
                                   // holder.size30.setFocusable(true);
                                    holder.size30.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_32")){
                                    holder.size32.setEnabled(true);
                                   // holder.size32.setFocusable(true);
                                    holder.size32.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_34")){
                                    holder.size34.setEnabled(true);
                                   // holder.size34.setFocusable(true);
                                    holder.size34.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_36")){
                                    holder.size36.setEnabled(true);
                                    //holder.size36.setFocusable(true);
                                    holder.size36.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_38")){
                                    holder.size38.setEnabled(true);
                                    //holder.size38.setFocusable(true);
                                    holder.size38.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_40")){
                                    holder.size40.setEnabled(true);
                                   // holder.size40.setFocusable(true);
                                    holder.size40.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_42")){
                                    holder.size42.setEnabled(true);
                                   // holder.size42.setFocusable(true);
                                    holder.size42.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_44")){
                                    holder.size44.setEnabled(true);
                                   // holder.size44.setFocusable(true);
                                    holder.size44.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_46")){
                                    holder.size46.setEnabled(true);
                                    //holder.size46.setFocusable(true);
                                    holder.size46.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_48")){
                                    holder.size48.setEnabled(true);
                                    //holder.size48.setFocusable(true);
                                    holder.size48.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }
                                else if(getkey.equals("size_00")){
                                    holder.qty.setEnabled(true);
                                    //holder.qty.setFocusable(true);
                                    holder.qty.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                }

                            }
                        }
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(ordvarient.get(position).equals("true")){
                holder.pvariant.setVisibility(View.VISIBLE);
            }
            holder.pvariant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) holder.pvariant.getTag();
                    if(ordvarient.get(pos).equals("true")) {

                        sleevelist = new ArrayList<>();
                        fitlist = new ArrayList<>();
                        lenglist = new ArrayList<>();
                        colorcodelist = new ArrayList<>();
                        coloraddlist = new ArrayList<>();
                        coloridlist = new ArrayList<>();
                        sizelist = new ArrayList<>();
                        sizekeylist = new ArrayList<>();
                        selectedcolor = new ArrayList<>();
                        selectedcolimg = new ArrayList<>();
                        fullcoloraddlist = new ArrayList<>();
                        pricelist  = new JSONArray();

                        String pid = ordprodidlist.get(pos);
                        String pname = ordpronamelist.get(pos);
                        String pcatge = ordprocatglist.get(pos);
                        String pmcatge = ordpromaincatglist.get(pos);
                        for (int i = 0; i < productlist.size(); i++) {
                            JSONObject plist = productlist.get(i);
                            try {
                                String proid = plist.getString("id");
                                if (pid.equals(proid)) {
                                    String sizebasecost = plist.get("size_based_cost").toString();
                                    String value = plist.get("attribute_dict").toString();
                                    JSONObject jsonObject = new JSONObject(value);
                                    JSONArray slelist = jsonObject.getJSONArray("sleeve_list");
                                    if (slelist.length() != 0) {

                                        for (int a = 0; a < slelist.length(); a++) {
                                            sleevelist.add(String.valueOf(slelist.get(a)));
                                        }
                                    }
                                    JSONArray fitslist = jsonObject.getJSONArray("fit_list");
                                    if (fitslist.length() != 0) {

                                        for (int c = 0; c < fitslist.length(); c++) {
                                            fitlist.add(String.valueOf(fitslist.get(c)));
                                        }
                                    }
                                    JSONArray lenlist = jsonObject.getJSONArray("length_list");
                                    if (lenlist.length() != 0) {

                                        for (int c = 0; c < lenlist.length(); c++) {
                                            lenglist.add(String.valueOf(lenlist.get(c)));
                                        }
                                    }
                                    JSONArray sizeslist = jsonObject.getJSONArray("size_list");
                                    if (sizeslist.length() != 0) {

                                        for (int c = 0; c < sizeslist.length(); c++) {
                                            sizelist.add(String.valueOf(sizeslist.get(c)));
                                        }
                                    }
                                    JSONArray sizekeyslist = jsonObject.getJSONArray("size_key_list");
                                    if (sizekeyslist.length() != 0) {

                                        for (int c = 0; c < sizekeyslist.length(); c++) {
                                            sizekeylist.add(String.valueOf(sizekeyslist.get(c)));
                                        }
                                    }
                                    JSONArray colimagelist = jsonObject.getJSONArray("color_swatch_list");
                                    if (colimagelist.length() != 0) {

                                        for (int b = 0; b < colimagelist.length(); b++) {
                                            colorcodelist.add(GetURL.getServerip()+String.valueOf(colimagelist.get(b)));
                                            coloraddlist.add("#AA4098");
                                            fullcoloraddlist.add("#AA4098");
                                        }
                                    }
                                    JSONArray colidlist = jsonObject.getJSONArray("color_list");
                                    if (colidlist.length() != 0) {

                                        for (int c = 0; c < colidlist.length(); c++) {
                                            coloridlist.add(String.valueOf(colidlist.get(c)));
                                        }
                                    }
                                    pricelist = jsonObject.getJSONArray("price_list");
                                    openvarientbox(pid, pname, pcatge,pmcatge,sizebasecost,"list",pos);


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            });
            holder.prodelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) holder.prodelete.getTag();
                    ordpronamelist.remove(pos);
                    ordprocatglist.remove(pos);
                    ordpromaincatglist.remove(pos);
                    ordprodidlist.remove(pos);
                    ordsize28.remove(pos);
                    ordsize30.remove(pos);
                    ordsize32.remove(pos);
                    ordsize34.remove(pos);
                    ordsize36.remove(pos);
                    ordsize38.remove(pos);
                    ordsize40.remove(pos);
                    ordsize42.remove(pos);
                    ordsize44.remove(pos);
                    ordsize46.remove(pos);
                    ordsize48.remove(pos);
                    ordsizetype.remove(pos);
                    ordqty.remove(pos);
                    ordtqty.remove(pos);
                    ordsleeve.remove(pos);
                    ordcolor.remove(pos);
                    ordcolimage.remove(pos);
                    ordfit.remove(pos);
                    ordleng.remove(pos);
                    ordvarient.remove(mainpostions);

                    orderfromlistview = (RecyclerView) findViewById(R.id.seleprolist);
                    orderfromlistview.setHasFixedSize(true);
                    layoutManager2 = new LinearLayoutManager(getApplicationContext());
                    orderfromlistview.setLayoutManager(layoutManager2);
                    TrouserAdapter trouserAdapter = new TrouserAdapter();
                    orderfromlistview.setAdapter(trouserAdapter);
                    totalitems =  ordprodidlist.size();
                    pertotitems.setText(String.valueOf(totalitems));
                    if(ordtqty.size()!=0) {
                        totalpics =0;
                        for (int i = 0; i < ordtqty.size(); i++) {
                            String val = ordtqty.get(i);
                            if (!val.equals("")) {
                                totalpics = totalpics + Integer.parseInt(val);
                            }
                        }
                        pertotpieces.setText(String.valueOf(totalpics));
                    }
                }
            });

            holder.size28.setText(ordsize28.get(position));
            holder.size28.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }
                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size28.getTag();
                    ordsize28.remove(position1);
                    ordsize28.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));
                }
            });
            holder.size30.setText(ordsize30.get(position));
            holder.size30.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }
                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size30.getTag();
                    ordsize30.remove(position1);
                    ordsize30.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));
                }
            });
            holder.size32.setText(ordsize32.get(position));
            holder.size32.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }

                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size32.getTag();
                    ordsize32.remove(position1);
                    ordsize32.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));
                }
            });
            holder.size34.setText(ordsize34.get(position));
            holder.size34.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }

                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size34.getTag();
                    ordsize34.remove(position1);
                    ordsize34.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));

                }
            });
            holder.size36.setText(ordsize36.get(position));
            holder.size36.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }

                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size36.getTag();
                    ordsize36.remove(position1);
                    ordsize36.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));

                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));

                }
            });
            holder.size38.setText(ordsize38.get(position));
            holder.size38.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }

                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size38.getTag();
                    ordsize38.remove(position1);
                    ordsize38.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));

                }
            });
            holder.size40.setText(ordsize40.get(position));
            holder.size40.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }

                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size40.getTag();
                    ordsize40.remove(position1);
                    ordsize40.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));

                }
            });
            holder.size42.setText(ordsize42.get(position));
            holder.size42.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }

                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size42.getTag();
                    ordsize42.remove(position1);
                    ordsize42.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));

                }
            });
            holder.size44.setText(ordsize44.get(position));
            holder.size44.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }
                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size44.getTag();
                    ordsize44.remove(position1);
                    ordsize44.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));
                }
            });
            holder.size46.setText(ordsize46.get(position));
            holder.size46.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }

                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size46.getTag();
                    ordsize46.remove(position1);
                    ordsize46.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));

                }
            });
            holder.size48.setText(ordsize48.get(position));
            holder.size48.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }

                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.size48.getTag();
                    ordsize48.remove(position1);
                    ordsize48.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));

                }
            });
            holder.qty.setText(ordqty.get(position));
            holder.qty.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = "";
                }

                @Override
                public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                    size = editable.toString();
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    int position1 = (int) holder.qty.getTag();
                    ordqty.remove(position1);
                    ordqty.add(position1,size);
                    int value = (ordsize28.get(position1).equals("")? 0: Integer.parseInt(ordsize28.get(position1)))+
                            (ordsize30.get(position1).equals("")? 0: Integer.parseInt(ordsize30.get(position1)))+
                            (ordsize32.get(position1).equals("")? 0: Integer.parseInt(ordsize32.get(position1)))+
                            (ordsize34.get(position1).equals("")? 0: Integer.parseInt(ordsize34.get(position1)))+
                            (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
                            (ordsize48.get(position1).equals("")? 0: Integer.parseInt(ordsize48.get(position1)))+
                            (ordqty.get(position1).equals("")? 0: Integer.parseInt(ordqty.get(position1)));
                    ordtqty.remove(position1);
                    ordtqty.add(position1,String.valueOf(value));
                    totalpics = 0;
                    holder.tqty.setText(ordtqty.get(position1));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));

                }
            });


        }



        @Override
        public int getItemCount() {

            return ordpronamelist.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView pcatge,tqty,sleeve,leng,fit,sizetype;
            EditText size28,size30,size32,size34,size36,size38,size40,size42,size44,size46,size48,qty;
            ImageView prodelete,pvariant;
            TextView pname;
            public ViewHolder(View itemView) {
                super(itemView);
                pcatge = (TextView) itemView.findViewById(R.id.plcatg);
                tqty = (TextView) itemView.findViewById(R.id.tqty);
                fit = (TextView) itemView.findViewById(R.id.fit);
                leng = (TextView) itemView.findViewById(R.id.leng);
                shade = (ImageView) itemView.findViewById(R.id.shade);
                sizetype = (TextView) itemView.findViewById(R.id.sizetype);
                sleeve = (TextView) itemView.findViewById(R.id.sleeve);
                size28 = (EditText) itemView.findViewById(R.id.size28);
                size30 = (EditText) itemView.findViewById(R.id.size30);
                size32 = (EditText) itemView.findViewById(R.id.size32);
                size34 = (EditText) itemView.findViewById(R.id.size34);
                size36 = (EditText) itemView.findViewById(R.id.size36);
                size38 = (EditText) itemView.findViewById(R.id.size38);
                size40 = (EditText) itemView.findViewById(R.id.size40);
                size42 = (EditText) itemView.findViewById(R.id.size42);
                size44 = (EditText) itemView.findViewById(R.id.size44);
                size46 = (EditText) itemView.findViewById(R.id.size46);
                size48 = (EditText) itemView.findViewById(R.id.size48);
                qty = (EditText) itemView.findViewById(R.id.qty);
                prodelete = (ImageView) itemView.findViewById(R.id.prodelete);
                pvariant = (ImageView) itemView.findViewById(R.id.pvariant);
                pname = (TextView) itemView.findViewById(R.id.plname);


            }
        }
    }

    class productlist extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ApparelsOrderDetails.this);
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
                        String productname = list.get("name").toString();
                        String productid = list.get("id").toString();
                        String producatge = list.get("form_types").toString();
                        String produsubcatge = list.get("product_categ").toString();
                        String prodimage = list.get("image_url").toString();
                        String prodwsp= list.get("wsp").toString();
                        String prodmrp= list.get("mrp").toString();
                        String colorbased= list.get("color_shade").toString();
                        String fitbased= list.get("fit_based").toString();
                        String sleeveBased= list.get("sleeve_based").toString();
                        String lengthBased= list.get("length_based").toString();

                        if(producatge.equals("Apparel")){
                            apparelprolist.add(productname);
                            productnamelist.add(productname);
                            fullproductlist.add(productname);
                            productsubcatge.add(produsubcatge);
                            fullproductsubcatge.add(produsubcatge);
                            productidlist.add(productid);
                            fullproductidlist.add(productid);
                            wsplist.add(prodwsp);
                            mrplist.add(prodmrp);
                            fullwsplist.add(prodwsp);
                            fullmrplist.add(prodmrp);
                            produimagelist.add(GetURL.getServerip()+prodimage);
                            fullproduimagelist.add(GetURL.getServerip()+prodimage);
                            procololist.add("#000000");
                            fullprocololsit.add("#000000");
                            productlist.add(list);
                            if (colorbased.equals("true")|| fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")){
                                multicolor.add("true");
                                fullmulticolor.add("true");
                            }else{
                                multicolor.add("false");
                                fullmulticolor.add("false");
                            }

                        }
                    }

                    itemChecked = new boolean[productidlist.size()];
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (ApparelsOrderDetails.this,android.R.layout.select_dialog_item, apparelprolist);
                    addpro.setThreshold(2);
                    addpro.setAdapter(adapter);

                    if(status.equals("true")) {
                        orderfromlistview = (RecyclerView) findViewById(R.id.seleprolist);
                        orderfromlistview.setHasFixedSize(true);
                        layoutManager2 = new LinearLayoutManager(getApplicationContext());
                        orderfromlistview.setLayoutManager(layoutManager2);
                        TrouserAdapter trouserAdapter = new TrouserAdapter();
                        orderfromlistview.setAdapter(trouserAdapter);

                        totalitems = ordprodidlist.size();
                        pertotitems.setText(String.valueOf(totalitems));

                        for (int i = 0; i < ordtqty.size(); i++) {
                            String val = ordtqty.get(i);
                            if (!val.equals("")) {
                                totalpics = totalpics + Integer.parseInt(val);
                            }
                        }
                        pertotpieces.setText(String.valueOf(totalpics));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Product list is empty", Toast.LENGTH_LONG).show();
                }

            }

        }

    }
    public void openvarientbox(String pid,String pname,String pcatge,String pmcatge,String basecost,String status,int postions){
      mainproductid = pid;
      mainproductname = pname;
      mainproductcatge = pcatge;
      mainproductmaincatge = pmcatge;
      mainopenstatus = status;
      mainsizebasecost = basecost;
      mainpostions = postions;
      maincheckingstatus = "0";
      cqtystatus="";
//      mainwsp = mrp;
//      mainwsp = wsp;

        fitaddlist = new ArrayList<>();
        sleeveaddlist = new ArrayList<>();
        lengthaddlist = new ArrayList<>();

        if(mainopenstatus.equals("tile")){
            varientdialog = new Dialog(ApparelsOrderDetails.this, android.R.style.Theme_Light);
            varientdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            varientdialog.setContentView(R.layout.tilecolorvarient_dialog);
        }else {
            varientdialog = new Dialog(ApparelsOrderDetails.this, android.R.style.Theme_Light);
            varientdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            varientdialog.setContentView(R.layout.colorvarient_dialog);
        }
        minus = (ImageView)varientdialog.findViewById(R.id.minus);
        minus.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        clear = (ImageView)varientdialog.findViewById(R.id.clear);

        b1 = (Button)varientdialog.findViewById(R.id.b1);
        b2 = (Button)varientdialog.findViewById(R.id.b2);
        b3 = (Button)varientdialog.findViewById(R.id.b3);
        b4 = (Button)varientdialog.findViewById(R.id.b4);
        b5 = (Button)varientdialog.findViewById(R.id.b5);
        b6 = (Button)varientdialog.findViewById(R.id.b6);
        b7 = (Button)varientdialog.findViewById(R.id.b7);
        b8 = (Button)varientdialog.findViewById(R.id.b8);
        b9 = (Button)varientdialog.findViewById(R.id.b9);
        b0 = (Button)varientdialog.findViewById(R.id.b0);

        fit1 = (Button)varientdialog.findViewById(R.id.fit1);
        fit2 = (Button)varientdialog.findViewById(R.id.fit2);
        fit3 = (Button)varientdialog.findViewById(R.id.fit3);
        fit4 = (Button)varientdialog.findViewById(R.id.fit4);
        fit5 = (Button)varientdialog.findViewById(R.id.fit5);

        slev1 = (Button)varientdialog.findViewById(R.id.slev1);
        slev2 = (Button)varientdialog.findViewById(R.id.slev2);
        slev3 = (Button)varientdialog.findViewById(R.id.slev3);
        slev4 = (Button)varientdialog.findViewById(R.id.slev4);
        slev5 = (Button)varientdialog.findViewById(R.id.slev5);

        leng1 = (Button)varientdialog.findViewById(R.id.len1);
        leng2 = (Button)varientdialog.findViewById(R.id.len2);
        leng3 = (Button)varientdialog.findViewById(R.id.len3);
        leng4 = (Button)varientdialog.findViewById(R.id.len4);
        leng5 = (Button)varientdialog.findViewById(R.id.len5);
        leng5 = (Button)varientdialog.findViewById(R.id.len5);

        tsize28 = (TextView)varientdialog.findViewById(R.id.tsize28);
        tsize30 = (TextView)varientdialog.findViewById(R.id.tsize30);
        tsize32 = (TextView)varientdialog.findViewById(R.id.tsize32);
        tsize34 = (TextView)varientdialog.findViewById(R.id.tsize34);
        tsize36 = (TextView)varientdialog.findViewById(R.id.tsize36);
        tsize38 = (TextView)varientdialog.findViewById(R.id.tsize38);
        tsize40 = (TextView)varientdialog.findViewById(R.id.tsize40);
        tsize42 = (TextView)varientdialog.findViewById(R.id.tsize42);
        tsize44 = (TextView)varientdialog.findViewById(R.id.tsize44);
        tsize46 = (TextView)varientdialog.findViewById(R.id.tsize46);
        tsize48 = (TextView)varientdialog.findViewById(R.id.tsize48);
        tqty = (TextView)varientdialog.findViewById(R.id.tqty);

        wsp28 = (TextView)varientdialog.findViewById(R.id.wsp28);
        wsp30 = (TextView)varientdialog.findViewById(R.id.wsp30);
        wsp32 = (TextView)varientdialog.findViewById(R.id.wsp32);
        wsp34 = (TextView)varientdialog.findViewById(R.id.wsp34);
        wsp36 = (TextView)varientdialog.findViewById(R.id.wsp36);
        wsp38 = (TextView)varientdialog.findViewById(R.id.wsp38);
        wsp40 = (TextView)varientdialog.findViewById(R.id.wsp40);
        wsp42 = (TextView)varientdialog.findViewById(R.id.wsp42);
        wsp44 = (TextView)varientdialog.findViewById(R.id.wsp44);
        wsp46 = (TextView)varientdialog.findViewById(R.id.wsp46);
        wsp48 = (TextView)varientdialog.findViewById(R.id.wsp48);
        wqty = (TextView)varientdialog.findViewById(R.id.wqty);

        mrp28 = (TextView)varientdialog.findViewById(R.id.mrp28);
        mrp30 = (TextView)varientdialog.findViewById(R.id.mrp30);
        mrp32 = (TextView)varientdialog.findViewById(R.id.mrp32);
        mrp34 = (TextView)varientdialog.findViewById(R.id.mrp34);
        mrp36 = (TextView)varientdialog.findViewById(R.id.mrp36);
        mrp38 = (TextView)varientdialog.findViewById(R.id.mrp38);
        mrp40 = (TextView)varientdialog.findViewById(R.id.mrp40);
        mrp42 = (TextView)varientdialog.findViewById(R.id.mrp42);
        mrp44 = (TextView)varientdialog.findViewById(R.id.mrp44);
        mrp46 = (TextView)varientdialog.findViewById(R.id.mrp46);
        mrp48 = (TextView)varientdialog.findViewById(R.id.mrp48);
        mqty = (TextView)varientdialog.findViewById(R.id.mqty);

        size28 = (TextView)varientdialog.findViewById(R.id.size28);
        size30 = (TextView)varientdialog.findViewById(R.id.size30);
        size32 = (TextView)varientdialog.findViewById(R.id.size32);
        size34 = (TextView)varientdialog.findViewById(R.id.size34);
        size36 = (TextView)varientdialog.findViewById(R.id.size36);
        size38 = (TextView)varientdialog.findViewById(R.id.size38);
        size40 = (TextView)varientdialog.findViewById(R.id.size40);
        size42 = (TextView)varientdialog.findViewById(R.id.size42);
        size44 = (TextView)varientdialog.findViewById(R.id.size44);
        size46 = (TextView)varientdialog.findViewById(R.id.size46);
        size48 = (TextView)varientdialog.findViewById(R.id.size48);
        qty = (TextView)varientdialog.findViewById(R.id.qty);
        cqty = (TextView)varientdialog.findViewById(R.id.cqty);

        varientclose = (RelativeLayout)varientdialog.findViewById(R.id.varientclose);
        tempsave = (RelativeLayout)varientdialog.findViewById(R.id.tempsave);



        if(sleevelist.size()!=0) {
            for (int a = 0; a < sleevelist.size(); a++) {
                String val= sleevelist.get(a);
                if(a==0){
                    slev1.setVisibility(View.VISIBLE);
                    slev1.setText(val);
                    slev1.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==1){
                    slev2.setVisibility(View.VISIBLE);
                    slev2.setText(val);
                    slev2.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==2){
                    slev3.setVisibility(View.VISIBLE);
                    slev3.setText(val);
                    slev3.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==3){
                    slev4.setVisibility(View.VISIBLE);
                    slev4.setText(val);
                    slev4.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==4){
                    slev5.setVisibility(View.VISIBLE);
                    slev5.setText(val);
                    slev5.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        }
        if(fitlist.size()!=0) {
            for (int a = 0; a < fitlist.size(); a++) {
                String val= fitlist.get(a);
                if(a==0){
                    fit1.setVisibility(View.VISIBLE);
                    fit1.setText(val);
                    fit1.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==1){
                    fit2.setVisibility(View.VISIBLE);
                    fit2.setText(val);
                    fit2.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==2){
                    fit3.setVisibility(View.VISIBLE);
                    fit3.setText(val);
                    fit3.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==3){
                    fit4.setVisibility(View.VISIBLE);
                    fit4.setText(val);
                    fit4.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==4){
                    fit5.setVisibility(View.VISIBLE);
                    fit5.setText(val);
                    fit5.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        }
        if(lenglist.size()!=0) {
            for (int a = 0; a < lenglist.size(); a++) {
                String val= lenglist.get(a);
                if(a==0){
                    leng1.setVisibility(View.VISIBLE);
                    leng1.setText(val);
                    leng1.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==1){
                    leng2.setVisibility(View.VISIBLE);
                    leng2.setText(val);
                    leng2.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==2){
                    leng3.setVisibility(View.VISIBLE);
                    leng3.setText(val);
                    leng3.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==3){
                    leng4.setVisibility(View.VISIBLE);
                    leng4.setText(val);
                    leng4.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                if(a==4){
                    leng5.setVisibility(View.VISIBLE);
                    leng5.setText(val);
                    leng5.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        }
        if(sizekeylist.size()!=0) {
            for (int a = 0; a < sizekeylist.size(); a++) {
                String val= sizekeylist.get(a);
                String key= sizelist.get(a);
                if(val.equals("size_28")){
                    size28.setVisibility(View.VISIBLE);
                    tsize28.setVisibility(View.VISIBLE);
                    wsp28.setVisibility(View.VISIBLE);
                    mrp28.setVisibility(View.VISIBLE);
//                    wsp28.setText(mainwsp);
//                    mrp28.setText(mainmrp);

                    tsize28.setText(key);
                }
                if(val.equals("size_30")){
                    size30.setVisibility(View.VISIBLE);
                    tsize30.setVisibility(View.VISIBLE);
                    wsp30.setVisibility(View.VISIBLE);
                    mrp30.setVisibility(View.VISIBLE);

//                    wsp30.setText(mainwsp);
//                    mrp30.setText(mainmrp);
                    tsize30.setText(key);
                }
                if(val.equals("size_32")){
                    size32.setVisibility(View.VISIBLE);
                    tsize32.setVisibility(View.VISIBLE);
                    wsp32.setVisibility(View.VISIBLE);
                    mrp32.setVisibility(View.VISIBLE);

//                    wsp32.setText(mainwsp);
//                    mrp32.setText(mainmrp);
                    tsize32.setText(key);
                }
                if(val.equals("size_34")){
                    size34.setVisibility(View.VISIBLE);
                    tsize34.setVisibility(View.VISIBLE);
                    wsp34.setVisibility(View.VISIBLE);
                    mrp34.setVisibility(View.VISIBLE);

//                    wsp34.setText(mainwsp);
//                    mrp34.setText(mainmrp);
                    tsize34.setText(key);
                }
                if(val.equals("size_36")){
                    size36.setVisibility(View.VISIBLE);
                    tsize36.setVisibility(View.VISIBLE);
                    wsp36.setVisibility(View.VISIBLE);
                    mrp36.setVisibility(View.VISIBLE);

//                    wsp36.setText(mainwsp);
//                    mrp36.setText(mainmrp);
                    tsize36.setText(key);
                }
                if(val.equals("size_38")){
                    size38.setVisibility(View.VISIBLE);
                    tsize38.setVisibility(View.VISIBLE);
                    wsp38.setVisibility(View.VISIBLE);
                    mrp38.setVisibility(View.VISIBLE);

//                    wsp38.setText(mainwsp);
//                    mrp38.setText(mainmrp);
                    tsize38.setText(key);
                }
                if(val.equals("size_40")){
                    size40.setVisibility(View.VISIBLE);
                    tsize40.setVisibility(View.VISIBLE);
                    wsp40.setVisibility(View.VISIBLE);
                    mrp40.setVisibility(View.VISIBLE);

//                    wsp40.setText(mainwsp);
//                    mrp40.setText(mainmrp);
                    tsize40.setText(key);
                }
                if(val.equals("size_42")){
                    size42.setVisibility(View.VISIBLE);
                    tsize42.setVisibility(View.VISIBLE);
                    wsp42.setVisibility(View.VISIBLE);
                    mrp42.setVisibility(View.VISIBLE);

//                    wsp42.setText(mainwsp);
//                    mrp42.setText(mainmrp);
                    tsize42.setText(key);
                }
                if(val.equals("size_44")){
                    size44.setVisibility(View.VISIBLE);
                    tsize44.setVisibility(View.VISIBLE);
                    wsp44.setVisibility(View.VISIBLE);
                    mrp44.setVisibility(View.VISIBLE);

//                    wsp44.setText(mainwsp);
//                    mrp44.setText(mainmrp);
                    tsize44.setText(key);
                }
                if(val.equals("size_46")){
                    size46.setVisibility(View.VISIBLE);
                    tsize46.setVisibility(View.VISIBLE);
                    wsp46.setVisibility(View.VISIBLE);
                    mrp46.setVisibility(View.VISIBLE);
//                    wsp46.setText(mainwsp);
//                    mrp46.setText(mainmrp);
                    tsize46.setText(key);
                }
                if(val.equals("size_48")){
                    size48.setVisibility(View.VISIBLE);
                    tsize48.setVisibility(View.VISIBLE);
                    wsp48.setVisibility(View.VISIBLE);
                    mrp48.setVisibility(View.VISIBLE);
//                    wsp48.setText(mainwsp);
//                    mrp48.setText(mainmrp);
                    tsize48.setText(key);
                }
                if(val.equals("size_00")){
                    qty.setVisibility(View.VISIBLE);
                    tqty.setVisibility(View.VISIBLE);
                    wqty.setVisibility(View.VISIBLE);
                    mqty.setVisibility(View.VISIBLE);
//                    wqty.setText(mainwsp);
//                    mqty.setText(mainmrp);
                    tqty.setText(key);
                }
            }
        }
        if(mainproductmaincatge.equals("Tops")){
            if(sleevelist.size()==0){
                if(pricelist.length()!=0) {
                    for (int i = 0; i < pricelist.length(); i++) {
                        try {
                            JSONObject list = pricelist.getJSONObject(i);
                            String skey = list.get("size_key").toString();
                            String wsp = list.get("wsp").toString();
                            String mrp = list.get("mrp").toString();
                            if(skey.equals("size_28")){
                                wsp28.setText(wsp);
                                mrp28.setText(mrp);
                            }else if(skey.equals("size_30")){
                                wsp30.setText(wsp);
                                mrp30.setText(mrp);
                            }
                            else if(skey.equals("size_32")){
                                wsp32.setText(wsp);
                                mrp32.setText(mrp);
                            }
                            else if(skey.equals("size_36")){
                                wsp36.setText(wsp);
                                mrp36.setText(mrp);
                            }
                            else if(skey.equals("size_38")){
                                wsp38.setText(wsp);
                                mrp38.setText(mrp);
                            }
                            else if(skey.equals("size_40")){
                                wsp40.setText(wsp);
                                mrp40.setText(mrp);
                            }
                            else if(skey.equals("size_42")){
                                wsp42.setText(wsp);
                                mrp42.setText(mrp);
                            }
                            else if(skey.equals("size_44")){
                                wsp44.setText(wsp);
                                mrp44.setText(mrp);
                            }
                            else if(skey.equals("size_46")){
                                wsp46.setText(wsp);
                                mrp46.setText(mrp);
                            }
                            else if(skey.equals("size_48")){
                                wsp48.setText(wsp);
                                mrp48.setText(mrp);
                            }
                            else if(skey.equals("size_00")){
                                wqty.setText(wsp);
                                mqty.setText(mrp);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else{

            }

        }else if(mainproductmaincatge.equals("Bottoms")){
            if(lenglist.size()==0){
                if(pricelist.length()!=0) {
                    for (int i = 0; i < pricelist.length(); i++) {
                        try {
                            JSONObject list = pricelist.getJSONObject(i);
                            String skey = list.get("size_key").toString();
                            String wsp = list.get("wsp").toString();
                            String mrp = list.get("mrp").toString();
                            if(skey.equals("size_28")){
                                wsp28.setText(wsp);
                                mrp28.setText(mrp);
                            }else if(skey.equals("size_30")){
                                wsp30.setText(wsp);
                                mrp30.setText(mrp);
                            }
                            else if(skey.equals("size_32")){
                                wsp32.setText(wsp);
                                mrp32.setText(mrp);
                            }
                            else if(skey.equals("size_36")){
                                wsp36.setText(wsp);
                                mrp36.setText(mrp);
                            }
                            else if(skey.equals("size_38")){
                                wsp38.setText(wsp);
                                mrp38.setText(mrp);
                            }
                            else if(skey.equals("size_40")){
                                wsp40.setText(wsp);
                                mrp40.setText(mrp);
                            }
                            else if(skey.equals("size_42")){
                                wsp42.setText(wsp);
                                mrp42.setText(mrp);
                            }
                            else if(skey.equals("size_44")){
                                wsp44.setText(wsp);
                                mrp44.setText(mrp);
                            }
                            else if(skey.equals("size_46")){
                                wsp46.setText(wsp);
                                mrp46.setText(mrp);
                            }
                            else if(skey.equals("size_48")){
                                wsp48.setText(wsp);
                                mrp48.setText(mrp);
                            }
                            else if(skey.equals("size_00")){
                                wqty.setText(wsp);
                                mqty.setText(mrp);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else{

            }
        }

         if(mainopenstatus.equals("tile")){
             tilevarimageview = (RecyclerView) varientdialog.findViewById(R.id.varientimageView);
             tilevarimageview.setHasFixedSize(true);
             layoutManager5 = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.HORIZONTAL, false);
             tilevarimageview.setLayoutManager(layoutManager5);
             TileimageAdapter tileimageAdapter = new TileimageAdapter();
             tilevarimageview.setAdapter(tileimageAdapter);

             colorvarientView = (RecyclerView) varientdialog.findViewById(R.id.colorvarientView);
             colorvarientView.setHasFixedSize(true);
             layoutManager4 = new GridLayoutManager(getApplicationContext(),3,GridLayoutManager.VERTICAL,false);
             colorvarientView.setLayoutManager(layoutManager4);
             VarientAdapter varientAdapter = new VarientAdapter();
             colorvarientView.setAdapter(varientAdapter);

         }else {
             colorvarientView = (RecyclerView) varientdialog.findViewById(R.id.colorvarientView);
             colorvarientView.setHasFixedSize(true);
             layoutManager4 = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.VERTICAL, false);
             colorvarientView.setLayoutManager(layoutManager4);
             VarientAdapter varientAdapter = new VarientAdapter();
             colorvarientView.setAdapter(varientAdapter);
         }
        varientdialog.show();


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(minustatus.equals("false")){
                    minus.setColorFilter(Color.parseColor("#AA4098"), PorterDuff.Mode.SRC_IN);
                    minustatus="true";
                }else{
                    minus.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
                    minustatus="false";
                }

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String oldval = edittext.getText().toString();
                    oldval = "";
                    edittext.setText(oldval);
                   if(cqtystatus.equals("true")) {
                       if(sizekeylist.size()!=0) {
                           int tqty = 0;
                           for (int a = 0; a < sizekeylist.size(); a++) {
                               String vale = sizekeylist.get(a);
                               if (vale.equals("size_28")) {
                                   String s28 = size28.getText().toString();
                                   if (!s28.equals("")) {
                                       tqty = tqty + Integer.parseInt(s28);
                                       size28.setText("");
                                   }
                               }
                               if (vale.equals("size_30")) {
                                   String s30 = size30.getText().toString();
                                   if (!s30.equals("")) {
                                       tqty = tqty + Integer.parseInt(s30);
                                       size30.setText("");
                                   }
                               }
                               if (vale.equals("size_32")) {
                                   String s32 = size32.getText().toString();
                                   if (!s32.equals("")) {
                                       tqty =tqty+ Integer.parseInt(s32);
                                       size32.setText("");
                                   }
                               }
                               if (vale.equals("size_34")) {
                                   String s34 = size34.getText().toString();
                                   if (!s34.equals("")) {
                                       tqty =tqty + Integer.parseInt(s34);
                                       size32.setText("");
                                   }
                               }
                               if (vale.equals("size_36")) {
                                   String s36 = size36.getText().toString();
                                   if (!s36.equals("")) {
                                       tqty =tqty + Integer.parseInt(s36);
                                       size36.setText("");
                                   }
                               }
                               if (vale.equals("size_38")) {
                                   String s38 = size38.getText().toString();
                                   if (!s38.equals("")) {
                                       tqty =tqty + Integer.parseInt(s38);
                                       size38.setText("");
                                   }
                               }
                               if (vale.equals("size_40")) {
                                   String s40 = size40.getText().toString();
                                   if (!s40.equals("")) {
                                       tqty =tqty + Integer.parseInt(s40);
                                       size40.setText("");
                                   }
                               }
                               if (vale.equals("size_42")) {
                                   String s42 = size42.getText().toString();
                                   if (!s42.equals("")) {
                                       tqty =tqty + Integer.parseInt(s42);
                                       size42.setText("");
                                   }
                               }
                               if (vale.equals("size_44")) {
                                   String s44 = size44.getText().toString();
                                   if (!s44.equals("")) {
                                       tqty =tqty + Integer.parseInt(s44);
                                       size44.setText("");
                                   }
                               }
                               if (vale.equals("size_46")) {
                                   String s46 = size46.getText().toString();
                                   if (!s46.equals("")) {
                                       tqty =tqty + Integer.parseInt(s46);
                                       size46.setText("");
                                   }
                               }
                               if (vale.equals("size_48")) {
                                   String s48 = size48.getText().toString();
                                   if (!s48.equals("")) {
                                       tqty =tqty + Integer.parseInt(s48);
                                       size48.setText("");
                                   }
                               }
                               if (vale.equals("size_00")) {
                                   String s00 = qty.getText().toString();
                                   if (!s00.equals("")) {
                                       tqty =tqty + Integer.parseInt(s00);
                                       qty.setText("");
                                   }
                               }
                           }
                           if(!cqtystatus.equals("true")) {
                               cqty.setText(String.valueOf(tqty));
                           }

                       }
                   }else{
                       if(sizekeylist.size()!=0) {
                           int tqty = 0;
                           for (int a = 0; a < sizekeylist.size(); a++) {
                               String vale = sizekeylist.get(a);
                               if (vale.equals("size_28")) {
                                   String s28 = size28.getText().toString();
                                   if (!s28.equals("")) {
                                       tqty = tqty + Integer.parseInt(s28);
                                   }
                               }
                               if (vale.equals("size_30")) {
                                   String s30 = size30.getText().toString();
                                   if (!s30.equals("")) {
                                       tqty = tqty + Integer.parseInt(s30);
                                   }
                               }
                               if (vale.equals("size_32")) {
                                   String s32 = size32.getText().toString();
                                   if (!s32.equals("")) {
                                       tqty =tqty+ Integer.parseInt(s32);
                                   }
                               }
                               if (vale.equals("size_34")) {
                                   String s34 = size34.getText().toString();
                                   if (!s34.equals("")) {
                                       tqty =tqty + Integer.parseInt(s34);
                                   }
                               }
                               if (vale.equals("size_36")) {
                                   String s36 = size36.getText().toString();
                                   if (!s36.equals("")) {
                                       tqty =tqty + Integer.parseInt(s36);
                                   }
                               }
                               if (vale.equals("size_38")) {
                                   String s38 = size38.getText().toString();
                                   if (!s38.equals("")) {
                                       tqty =tqty + Integer.parseInt(s38);
                                   }
                               }
                               if (vale.equals("size_40")) {
                                   String s40 = size40.getText().toString();
                                   if (!s40.equals("")) {
                                       tqty =tqty + Integer.parseInt(s40);
                                   }
                               }
                               if (vale.equals("size_42")) {
                                   String s42 = size42.getText().toString();
                                   if (!s42.equals("")) {
                                       tqty =tqty + Integer.parseInt(s42);
                                   }
                               }
                               if (vale.equals("size_44")) {
                                   String s44 = size44.getText().toString();
                                   if (!s44.equals("")) {
                                       tqty =tqty + Integer.parseInt(s44);
                                   }
                               }
                               if (vale.equals("size_46")) {
                                   String s46 = size46.getText().toString();
                                   if (!s46.equals("")) {
                                       tqty =tqty + Integer.parseInt(s46);
                                   }
                               }
                               if (vale.equals("size_48")) {
                                   String s48 = size48.getText().toString();
                                   if (!s48.equals("")) {
                                       tqty =tqty + Integer.parseInt(s48);
                                   }
                               }
                               if (vale.equals("size_00")) {
                                   String s00 = qty.getText().toString();
                                   if (!s00.equals("")) {
                                       tqty =tqty + Integer.parseInt(s00);
                                   }
                               }
                           }
                           if(!cqtystatus.equals("true")) {
                               cqty.setText(String.valueOf(tqty));
                           }

                       }
                   }

                }catch (Exception e){

                }
            }
        });

        fit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = fit1.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    fitaddlist.add(fit1.getText().toString());
                    fit1.setTextColor(getResources().getColor(R.color.colorWhite));
                    fit1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<fitaddlist.size();i++){
                        if(fitaddlist.get(i).equals(fit1.getText().toString())){
                            fitaddlist.remove(i);
                            fit1.setTextColor(getResources().getColor(R.color.colorAccent));
                            fit1.setBackgroundResource(R.drawable.buttonstyle_background);
                        }
                    }

                }

            }
        });
        fit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = fit2.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    fitaddlist.add(fit2.getText().toString());
                    fit2.setTextColor(getResources().getColor(R.color.colorWhite));
                    fit2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<fitaddlist.size();i++){
                        if(fitaddlist.get(i).equals(fit2.getText().toString())){
                            fitaddlist.remove(i);
                            fit2.setTextColor(getResources().getColor(R.color.colorAccent));
                            fit2.setBackgroundResource(R.drawable.buttonstyle_background);
                        }
                    }

                }
;
            }
        });
        fit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = fit3.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    fitaddlist.add(fit3.getText().toString());
                    fit3.setTextColor(getResources().getColor(R.color.colorWhite));
                    fit3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<fitaddlist.size();i++){
                        if(fitaddlist.get(i).equals(fit3.getText().toString())){
                            fitaddlist.remove(i);
                            fit3.setTextColor(getResources().getColor(R.color.colorAccent));
                            fit3.setBackgroundResource(R.drawable.buttonstyle_background);
                        }
                    }

                }

            }
        });
        fit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = fit4.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    fitaddlist.add(fit4.getText().toString());
                    fit4.setTextColor(getResources().getColor(R.color.colorWhite));
                    fit4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<fitaddlist.size();i++){
                        if(fitaddlist.get(i).equals(fit4.getText().toString())){
                            fitaddlist.remove(i);
                            fit4.setTextColor(getResources().getColor(R.color.colorAccent));
                            fit4.setBackgroundResource(R.drawable.buttonstyle_background);
                        }
                    }

                }

            }
        });
        fit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = fit5.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    fitaddlist.add(fit5.getText().toString());
                    fit5.setTextColor(getResources().getColor(R.color.colorWhite));
                    fit5.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<fitaddlist.size();i++){
                        if(fitaddlist.get(i).equals(fit5.getText().toString())){
                            fitaddlist.remove(i);
                            fit5.setTextColor(getResources().getColor(R.color.colorAccent));
                            fit5.setBackgroundResource(R.drawable.buttonstyle_background);
                        }
                    }

                }

            }
        });
        slev1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = slev1.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    sleeveaddlist.add(slev1.getText().toString());
                    slev1.setTextColor(getResources().getColor(R.color.colorWhite));
                    slev1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    String svalue = slev1.getText().toString();
                    if(pricelist.length()!=0) {
                        for (int i = 0; i < pricelist.length(); i++) {
                            try {
                                JSONObject list = pricelist.getJSONObject(i);
                                String slkey = list.get("sl_key").toString();
                                String skey = list.get("size_key").toString();
                                String wsp = list.get("wsp").toString();
                                String mrp = list.get("mrp").toString();
                                if(slkey.equals(svalue)) {
                                    if (skey.equals("size_28")) {
                                        wsp28.setText(wsp);
                                        mrp28.setText(mrp);
                                    } else if (skey.equals("size_30")) {
                                        wsp30.setText(wsp);
                                        mrp30.setText(mrp);
                                    } else if (skey.equals("size_32")) {
                                        wsp32.setText(wsp);
                                        mrp32.setText(mrp);
                                    } else if (skey.equals("size_36")) {
                                        wsp36.setText(wsp);
                                        mrp36.setText(mrp);
                                    } else if (skey.equals("size_38")) {
                                        wsp38.setText(wsp);
                                        mrp38.setText(mrp);
                                    } else if (skey.equals("size_40")) {
                                        wsp40.setText(wsp);
                                        mrp40.setText(mrp);
                                    } else if (skey.equals("size_42")) {
                                        wsp42.setText(wsp);
                                        mrp42.setText(mrp);
                                    } else if (skey.equals("size_44")) {
                                        wsp44.setText(wsp);
                                        mrp44.setText(mrp);
                                    } else if (skey.equals("size_46")) {
                                        wsp46.setText(wsp);
                                        mrp46.setText(mrp);
                                    } else if (skey.equals("size_48")) {
                                        wsp48.setText(wsp);
                                        mrp48.setText(mrp);
                                    } else if (skey.equals("size_00")) {
                                        wqty.setText(wsp);
                                        mqty.setText(mrp);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<sleeveaddlist.size();i++){
                        if(sleeveaddlist.get(i).equals(slev1.getText().toString())){
                            sleeveaddlist.remove(i);
                            slev1.setTextColor(getResources().getColor(R.color.colorAccent));
                            slev1.setBackgroundResource(R.drawable.buttonstyle_background);
                            if(sleeveaddlist.size()!=0){
                                String svalue = sleeveaddlist.get(sleeveaddlist.size()-1);
                                if(pricelist.length()!=0) {
                                    for (int a = 0; a < pricelist.length(); a++) {
                                        try {
                                            JSONObject list = pricelist.getJSONObject(a);
                                            String slkey = list.get("sl_key").toString();
                                            String skey = list.get("size_key").toString();
                                            String wsp = list.get("wsp").toString();
                                            String mrp = list.get("mrp").toString();
                                            if(slkey.equals(svalue)) {
                                                if (skey.equals("size_28")) {
                                                    wsp28.setText(wsp);
                                                    mrp28.setText(mrp);
                                                } else if (skey.equals("size_30")) {
                                                    wsp30.setText(wsp);
                                                    mrp30.setText(mrp);
                                                } else if (skey.equals("size_32")) {
                                                    wsp32.setText(wsp);
                                                    mrp32.setText(mrp);
                                                } else if (skey.equals("size_36")) {
                                                    wsp36.setText(wsp);
                                                    mrp36.setText(mrp);
                                                } else if (skey.equals("size_38")) {
                                                    wsp38.setText(wsp);
                                                    mrp38.setText(mrp);
                                                } else if (skey.equals("size_40")) {
                                                    wsp40.setText(wsp);
                                                    mrp40.setText(mrp);
                                                } else if (skey.equals("size_42")) {
                                                    wsp42.setText(wsp);
                                                    mrp42.setText(mrp);
                                                } else if (skey.equals("size_44")) {
                                                    wsp44.setText(wsp);
                                                    mrp44.setText(mrp);
                                                } else if (skey.equals("size_46")) {
                                                    wsp46.setText(wsp);
                                                    mrp46.setText(mrp);
                                                } else if (skey.equals("size_48")) {
                                                    wsp48.setText(wsp);
                                                    mrp48.setText(mrp);
                                                } else if (skey.equals("size_00")) {
                                                    wqty.setText(wsp);
                                                    mqty.setText(mrp);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }


            }
        });
        slev2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = slev2.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    sleeveaddlist.add(slev2.getText().toString());
                    slev2.setTextColor(getResources().getColor(R.color.colorWhite));
                    slev2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    String svalue = slev2.getText().toString();
                    if(pricelist.length()!=0) {
                        for (int i = 0; i < pricelist.length(); i++) {
                            try {
                                JSONObject list = pricelist.getJSONObject(i);
                                String slkey = list.get("sl_key").toString();
                                String skey = list.get("size_key").toString();
                                String wsp = list.get("wsp").toString();
                                String mrp = list.get("mrp").toString();
                                if(slkey.equals(svalue)) {
                                    if (skey.equals("size_28")) {
                                        wsp28.setText(wsp);
                                        mrp28.setText(mrp);
                                    } else if (skey.equals("size_30")) {
                                        wsp30.setText(wsp);
                                        mrp30.setText(mrp);
                                    } else if (skey.equals("size_32")) {
                                        wsp32.setText(wsp);
                                        mrp32.setText(mrp);
                                    } else if (skey.equals("size_36")) {
                                        wsp36.setText(wsp);
                                        mrp36.setText(mrp);
                                    } else if (skey.equals("size_38")) {
                                        wsp38.setText(wsp);
                                        mrp38.setText(mrp);
                                    } else if (skey.equals("size_40")) {
                                        wsp40.setText(wsp);
                                        mrp40.setText(mrp);
                                    } else if (skey.equals("size_42")) {
                                        wsp42.setText(wsp);
                                        mrp42.setText(mrp);
                                    } else if (skey.equals("size_44")) {
                                        wsp44.setText(wsp);
                                        mrp44.setText(mrp);
                                    } else if (skey.equals("size_46")) {
                                        wsp46.setText(wsp);
                                        mrp46.setText(mrp);
                                    } else if (skey.equals("size_48")) {
                                        wsp48.setText(wsp);
                                        mrp48.setText(mrp);
                                    } else if (skey.equals("size_00")) {
                                        wqty.setText(wsp);
                                        mqty.setText(mrp);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<sleeveaddlist.size();i++){
                        if(sleeveaddlist.get(i).equals(slev2.getText().toString())){
                            sleeveaddlist.remove(i);
                            slev2.setTextColor(getResources().getColor(R.color.colorAccent));
                            slev2.setBackgroundResource(R.drawable.buttonstyle_background);
                            if(sleeveaddlist.size()!=0){
                                String svalue = sleeveaddlist.get(sleeveaddlist.size()-1);
                                if(pricelist.length()!=0) {
                                    for (int a = 0; a < pricelist.length(); a++) {
                                        try {
                                            JSONObject list = pricelist.getJSONObject(a);
                                            String slkey = list.get("sl_key").toString();
                                            String skey = list.get("size_key").toString();
                                            String wsp = list.get("wsp").toString();
                                            String mrp = list.get("mrp").toString();
                                            if(slkey.equals(svalue)) {
                                                if (skey.equals("size_28")) {
                                                    wsp28.setText(wsp);
                                                    mrp28.setText(mrp);
                                                } else if (skey.equals("size_30")) {
                                                    wsp30.setText(wsp);
                                                    mrp30.setText(mrp);
                                                } else if (skey.equals("size_32")) {
                                                    wsp32.setText(wsp);
                                                    mrp32.setText(mrp);
                                                } else if (skey.equals("size_36")) {
                                                    wsp36.setText(wsp);
                                                    mrp36.setText(mrp);
                                                } else if (skey.equals("size_38")) {
                                                    wsp38.setText(wsp);
                                                    mrp38.setText(mrp);
                                                } else if (skey.equals("size_40")) {
                                                    wsp40.setText(wsp);
                                                    mrp40.setText(mrp);
                                                } else if (skey.equals("size_42")) {
                                                    wsp42.setText(wsp);
                                                    mrp42.setText(mrp);
                                                } else if (skey.equals("size_44")) {
                                                    wsp44.setText(wsp);
                                                    mrp44.setText(mrp);
                                                } else if (skey.equals("size_46")) {
                                                    wsp46.setText(wsp);
                                                    mrp46.setText(mrp);
                                                } else if (skey.equals("size_48")) {
                                                    wsp48.setText(wsp);
                                                    mrp48.setText(mrp);
                                                } else if (skey.equals("size_00")) {
                                                    wqty.setText(wsp);
                                                    mqty.setText(mrp);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        });
        slev3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = slev3.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    sleeveaddlist.add(slev3.getText().toString());
                    slev3.setTextColor(getResources().getColor(R.color.colorWhite));
                    slev3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    String svalue = slev3.getText().toString();
                    if(pricelist.length()!=0) {
                        for (int i = 0; i < pricelist.length(); i++) {
                            try {
                                JSONObject list = pricelist.getJSONObject(i);
                                String slkey = list.get("sl_key").toString();
                                String skey = list.get("size_key").toString();
                                String wsp = list.get("wsp").toString();
                                String mrp = list.get("mrp").toString();
                                if(slkey.equals(svalue)) {
                                    if (skey.equals("size_28")) {
                                        wsp28.setText(wsp);
                                        mrp28.setText(mrp);
                                    } else if (skey.equals("size_30")) {
                                        wsp30.setText(wsp);
                                        mrp30.setText(mrp);
                                    } else if (skey.equals("size_32")) {
                                        wsp32.setText(wsp);
                                        mrp32.setText(mrp);
                                    } else if (skey.equals("size_36")) {
                                        wsp36.setText(wsp);
                                        mrp36.setText(mrp);
                                    } else if (skey.equals("size_38")) {
                                        wsp38.setText(wsp);
                                        mrp38.setText(mrp);
                                    } else if (skey.equals("size_40")) {
                                        wsp40.setText(wsp);
                                        mrp40.setText(mrp);
                                    } else if (skey.equals("size_42")) {
                                        wsp42.setText(wsp);
                                        mrp42.setText(mrp);
                                    } else if (skey.equals("size_44")) {
                                        wsp44.setText(wsp);
                                        mrp44.setText(mrp);
                                    } else if (skey.equals("size_46")) {
                                        wsp46.setText(wsp);
                                        mrp46.setText(mrp);
                                    } else if (skey.equals("size_48")) {
                                        wsp48.setText(wsp);
                                        mrp48.setText(mrp);
                                    } else if (skey.equals("size_00")) {
                                        wqty.setText(wsp);
                                        mqty.setText(mrp);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<sleeveaddlist.size();i++){
                        if(sleeveaddlist.get(i).equals(slev3.getText().toString())){
                            sleeveaddlist.remove(i);
                            slev3.setTextColor(getResources().getColor(R.color.colorAccent));
                            slev3.setBackgroundResource(R.drawable.buttonstyle_background);
                            if(sleeveaddlist.size()!=0){
                                String svalue = sleeveaddlist.get(sleeveaddlist.size()-1);
                                if(pricelist.length()!=0) {
                                    for (int a = 0; a < pricelist.length(); a++) {
                                        try {
                                            JSONObject list = pricelist.getJSONObject(a);
                                            String slkey = list.get("sl_key").toString();
                                            String skey = list.get("size_key").toString();
                                            String wsp = list.get("wsp").toString();
                                            String mrp = list.get("mrp").toString();
                                            if(slkey.equals(svalue)) {
                                                if (skey.equals("size_28")) {
                                                    wsp28.setText(wsp);
                                                    mrp28.setText(mrp);
                                                } else if (skey.equals("size_30")) {
                                                    wsp30.setText(wsp);
                                                    mrp30.setText(mrp);
                                                } else if (skey.equals("size_32")) {
                                                    wsp32.setText(wsp);
                                                    mrp32.setText(mrp);
                                                } else if (skey.equals("size_36")) {
                                                    wsp36.setText(wsp);
                                                    mrp36.setText(mrp);
                                                } else if (skey.equals("size_38")) {
                                                    wsp38.setText(wsp);
                                                    mrp38.setText(mrp);
                                                } else if (skey.equals("size_40")) {
                                                    wsp40.setText(wsp);
                                                    mrp40.setText(mrp);
                                                } else if (skey.equals("size_42")) {
                                                    wsp42.setText(wsp);
                                                    mrp42.setText(mrp);
                                                } else if (skey.equals("size_44")) {
                                                    wsp44.setText(wsp);
                                                    mrp44.setText(mrp);
                                                } else if (skey.equals("size_46")) {
                                                    wsp46.setText(wsp);
                                                    mrp46.setText(mrp);
                                                } else if (skey.equals("size_48")) {
                                                    wsp48.setText(wsp);
                                                    mrp48.setText(mrp);
                                                } else if (skey.equals("size_00")) {
                                                    wqty.setText(wsp);
                                                    mqty.setText(mrp);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        });
        slev4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = slev4.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    sleeveaddlist.add(slev4.getText().toString());
                    slev4.setTextColor(getResources().getColor(R.color.colorWhite));
                    slev4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    String svalue = slev4.getText().toString();
                    if(pricelist.length()!=0) {
                        for (int i = 0; i < pricelist.length(); i++) {
                            try {
                                JSONObject list = pricelist.getJSONObject(i);
                                String slkey = list.get("sl_key").toString();
                                String skey = list.get("size_key").toString();
                                String wsp = list.get("wsp").toString();
                                String mrp = list.get("mrp").toString();
                                if(slkey.equals(svalue)) {
                                    if (skey.equals("size_28")) {
                                        wsp28.setText(wsp);
                                        mrp28.setText(mrp);
                                    } else if (skey.equals("size_30")) {
                                        wsp30.setText(wsp);
                                        mrp30.setText(mrp);
                                    } else if (skey.equals("size_32")) {
                                        wsp32.setText(wsp);
                                        mrp32.setText(mrp);
                                    } else if (skey.equals("size_36")) {
                                        wsp36.setText(wsp);
                                        mrp36.setText(mrp);
                                    } else if (skey.equals("size_38")) {
                                        wsp38.setText(wsp);
                                        mrp38.setText(mrp);
                                    } else if (skey.equals("size_40")) {
                                        wsp40.setText(wsp);
                                        mrp40.setText(mrp);
                                    } else if (skey.equals("size_42")) {
                                        wsp42.setText(wsp);
                                        mrp42.setText(mrp);
                                    } else if (skey.equals("size_44")) {
                                        wsp44.setText(wsp);
                                        mrp44.setText(mrp);
                                    } else if (skey.equals("size_46")) {
                                        wsp46.setText(wsp);
                                        mrp46.setText(mrp);
                                    } else if (skey.equals("size_48")) {
                                        wsp48.setText(wsp);
                                        mrp48.setText(mrp);
                                    } else if (skey.equals("size_00")) {
                                        wqty.setText(wsp);
                                        mqty.setText(mrp);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<sleeveaddlist.size();i++){
                        if(sleeveaddlist.get(i).equals(slev4.getText().toString())){
                            sleeveaddlist.remove(i);
                            slev4.setTextColor(getResources().getColor(R.color.colorAccent));
                            slev4.setBackgroundResource(R.drawable.buttonstyle_background);
                            if(sleeveaddlist.size()!=0){
                                String svalue = sleeveaddlist.get(sleeveaddlist.size()-1);
                                if(pricelist.length()!=0) {
                                    for (int a = 0; a < pricelist.length(); a++) {
                                        try {
                                            JSONObject list = pricelist.getJSONObject(a);
                                            String slkey = list.get("sl_key").toString();
                                            String skey = list.get("size_key").toString();
                                            String wsp = list.get("wsp").toString();
                                            String mrp = list.get("mrp").toString();
                                            if(slkey.equals(svalue)) {
                                                if (skey.equals("size_28")) {
                                                    wsp28.setText(wsp);
                                                    mrp28.setText(mrp);
                                                } else if (skey.equals("size_30")) {
                                                    wsp30.setText(wsp);
                                                    mrp30.setText(mrp);
                                                } else if (skey.equals("size_32")) {
                                                    wsp32.setText(wsp);
                                                    mrp32.setText(mrp);
                                                } else if (skey.equals("size_36")) {
                                                    wsp36.setText(wsp);
                                                    mrp36.setText(mrp);
                                                } else if (skey.equals("size_38")) {
                                                    wsp38.setText(wsp);
                                                    mrp38.setText(mrp);
                                                } else if (skey.equals("size_40")) {
                                                    wsp40.setText(wsp);
                                                    mrp40.setText(mrp);
                                                } else if (skey.equals("size_42")) {
                                                    wsp42.setText(wsp);
                                                    mrp42.setText(mrp);
                                                } else if (skey.equals("size_44")) {
                                                    wsp44.setText(wsp);
                                                    mrp44.setText(mrp);
                                                } else if (skey.equals("size_46")) {
                                                    wsp46.setText(wsp);
                                                    mrp46.setText(mrp);
                                                } else if (skey.equals("size_48")) {
                                                    wsp48.setText(wsp);
                                                    mrp48.setText(mrp);
                                                } else if (skey.equals("size_00")) {
                                                    wqty.setText(wsp);
                                                    mqty.setText(mrp);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        });
        slev5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = slev5.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    sleeveaddlist.add(slev5.getText().toString());
                    slev5.setTextColor(getResources().getColor(R.color.colorWhite));
                    slev5.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    String svalue = slev5.getText().toString();
                    if(pricelist.length()!=0) {
                        for (int i = 0; i < pricelist.length(); i++) {
                            try {
                                JSONObject list = pricelist.getJSONObject(i);
                                String slkey = list.get("sl_key").toString();
                                String skey = list.get("size_key").toString();
                                String wsp = list.get("wsp").toString();
                                String mrp = list.get("mrp").toString();
                                if(slkey.equals(svalue)) {
                                    if (skey.equals("size_28")) {
                                        wsp28.setText(wsp);
                                        mrp28.setText(mrp);
                                    } else if (skey.equals("size_30")) {
                                        wsp30.setText(wsp);
                                        mrp30.setText(mrp);
                                    } else if (skey.equals("size_32")) {
                                        wsp32.setText(wsp);
                                        mrp32.setText(mrp);
                                    } else if (skey.equals("size_36")) {
                                        wsp36.setText(wsp);
                                        mrp36.setText(mrp);
                                    } else if (skey.equals("size_38")) {
                                        wsp38.setText(wsp);
                                        mrp38.setText(mrp);
                                    } else if (skey.equals("size_40")) {
                                        wsp40.setText(wsp);
                                        mrp40.setText(mrp);
                                    } else if (skey.equals("size_42")) {
                                        wsp42.setText(wsp);
                                        mrp42.setText(mrp);
                                    } else if (skey.equals("size_44")) {
                                        wsp44.setText(wsp);
                                        mrp44.setText(mrp);
                                    } else if (skey.equals("size_46")) {
                                        wsp46.setText(wsp);
                                        mrp46.setText(mrp);
                                    } else if (skey.equals("size_48")) {
                                        wsp48.setText(wsp);
                                        mrp48.setText(mrp);
                                    } else if (skey.equals("size_00")) {
                                        wqty.setText(wsp);
                                        mqty.setText(mrp);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<sleeveaddlist.size();i++){
                        if(sleeveaddlist.get(i).equals(slev5.getText().toString())){
                            sleeveaddlist.remove(i);
                            slev5.setTextColor(getResources().getColor(R.color.colorAccent));
                            slev5.setBackgroundResource(R.drawable.buttonstyle_background);
                            if(sleeveaddlist.size()!=0){
                                String svalue = sleeveaddlist.get(sleeveaddlist.size()-1);
                                if(pricelist.length()!=0) {
                                    for (int a = 0; a < pricelist.length(); a++) {
                                        try {
                                            JSONObject list = pricelist.getJSONObject(a);
                                            String slkey = list.get("sl_key").toString();
                                            String skey = list.get("size_key").toString();
                                            String wsp = list.get("wsp").toString();
                                            String mrp = list.get("mrp").toString();
                                            if(slkey.equals(svalue)) {
                                                if (skey.equals("size_28")) {
                                                    wsp28.setText(wsp);
                                                    mrp28.setText(mrp);
                                                } else if (skey.equals("size_30")) {
                                                    wsp30.setText(wsp);
                                                    mrp30.setText(mrp);
                                                } else if (skey.equals("size_32")) {
                                                    wsp32.setText(wsp);
                                                    mrp32.setText(mrp);
                                                } else if (skey.equals("size_36")) {
                                                    wsp36.setText(wsp);
                                                    mrp36.setText(mrp);
                                                } else if (skey.equals("size_38")) {
                                                    wsp38.setText(wsp);
                                                    mrp38.setText(mrp);
                                                } else if (skey.equals("size_40")) {
                                                    wsp40.setText(wsp);
                                                    mrp40.setText(mrp);
                                                } else if (skey.equals("size_42")) {
                                                    wsp42.setText(wsp);
                                                    mrp42.setText(mrp);
                                                } else if (skey.equals("size_44")) {
                                                    wsp44.setText(wsp);
                                                    mrp44.setText(mrp);
                                                } else if (skey.equals("size_46")) {
                                                    wsp46.setText(wsp);
                                                    mrp46.setText(mrp);
                                                } else if (skey.equals("size_48")) {
                                                    wsp48.setText(wsp);
                                                    mrp48.setText(mrp);
                                                } else if (skey.equals("size_00")) {
                                                    wqty.setText(wsp);
                                                    mqty.setText(mrp);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        });
        leng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = leng1.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    lengthaddlist.add(leng1.getText().toString());
                    leng1.setTextColor(getResources().getColor(R.color.colorWhite));
                    leng1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    String lvalue = leng1.getText().toString();
                    if(pricelist.length()!=0) {
                        for (int i = 0; i < pricelist.length(); i++) {
                            try {
                                JSONObject list = pricelist.getJSONObject(i);
                                String slkey = list.get("sl_key").toString();
                                String skey = list.get("size_key").toString();
                                String wsp = list.get("wsp").toString();
                                String mrp = list.get("mrp").toString();
                                if(slkey.equals(lvalue)) {
                                    if (skey.equals("size_28")) {
                                        wsp28.setText(wsp);
                                        mrp28.setText(mrp);
                                    } else if (skey.equals("size_30")) {
                                        wsp30.setText(wsp);
                                        mrp30.setText(mrp);
                                    } else if (skey.equals("size_32")) {
                                        wsp32.setText(wsp);
                                        mrp32.setText(mrp);
                                    } else if (skey.equals("size_36")) {
                                        wsp36.setText(wsp);
                                        mrp36.setText(mrp);
                                    } else if (skey.equals("size_38")) {
                                        wsp38.setText(wsp);
                                        mrp38.setText(mrp);
                                    } else if (skey.equals("size_40")) {
                                        wsp40.setText(wsp);
                                        mrp40.setText(mrp);
                                    } else if (skey.equals("size_42")) {
                                        wsp42.setText(wsp);
                                        mrp42.setText(mrp);
                                    } else if (skey.equals("size_44")) {
                                        wsp44.setText(wsp);
                                        mrp44.setText(mrp);
                                    } else if (skey.equals("size_46")) {
                                        wsp46.setText(wsp);
                                        mrp46.setText(mrp);
                                    } else if (skey.equals("size_48")) {
                                        wsp48.setText(wsp);
                                        mrp48.setText(mrp);
                                    } else if (skey.equals("size_00")) {
                                        wqty.setText(wsp);
                                        mqty.setText(mrp);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<lengthaddlist.size();i++){
                        if(lengthaddlist.get(i).equals(leng1.getText().toString())){
                            lengthaddlist.remove(i);
                            leng1.setTextColor(getResources().getColor(R.color.colorAccent));
                            leng1.setBackgroundResource(R.drawable.buttonstyle_background);
                            if(lengthaddlist.size()!=0){
                                String lvalue = lengthaddlist.get(lengthaddlist.size()-1);
                                if(pricelist.length()!=0) {
                                    for (int a = 0; a < pricelist.length(); a++) {
                                        try {
                                            JSONObject list = pricelist.getJSONObject(a);
                                            String slkey = list.get("sl_key").toString();
                                            String skey = list.get("size_key").toString();
                                            String wsp = list.get("wsp").toString();
                                            String mrp = list.get("mrp").toString();
                                            if(slkey.equals(lvalue)) {
                                                if (skey.equals("size_28")) {
                                                    wsp28.setText(wsp);
                                                    mrp28.setText(mrp);
                                                } else if (skey.equals("size_30")) {
                                                    wsp30.setText(wsp);
                                                    mrp30.setText(mrp);
                                                } else if (skey.equals("size_32")) {
                                                    wsp32.setText(wsp);
                                                    mrp32.setText(mrp);
                                                } else if (skey.equals("size_36")) {
                                                    wsp36.setText(wsp);
                                                    mrp36.setText(mrp);
                                                } else if (skey.equals("size_38")) {
                                                    wsp38.setText(wsp);
                                                    mrp38.setText(mrp);
                                                } else if (skey.equals("size_40")) {
                                                    wsp40.setText(wsp);
                                                    mrp40.setText(mrp);
                                                } else if (skey.equals("size_42")) {
                                                    wsp42.setText(wsp);
                                                    mrp42.setText(mrp);
                                                } else if (skey.equals("size_44")) {
                                                    wsp44.setText(wsp);
                                                    mrp44.setText(mrp);
                                                } else if (skey.equals("size_46")) {
                                                    wsp46.setText(wsp);
                                                    mrp46.setText(mrp);
                                                } else if (skey.equals("size_48")) {
                                                    wsp48.setText(wsp);
                                                    mrp48.setText(mrp);
                                                } else if (skey.equals("size_00")) {
                                                    wqty.setText(wsp);
                                                    mqty.setText(mrp);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        });
        leng2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = leng2.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    lengthaddlist.add(leng2.getText().toString());
                    leng2.setTextColor(getResources().getColor(R.color.colorWhite));
                    leng2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    String lvalue = leng2.getText().toString();
                    if(pricelist.length()!=0) {
                        for (int i = 0; i < pricelist.length(); i++) {
                            try {
                                JSONObject list = pricelist.getJSONObject(i);
                                String slkey = list.get("sl_key").toString();
                                String skey = list.get("size_key").toString();
                                String wsp = list.get("wsp").toString();
                                String mrp = list.get("mrp").toString();
                                if(slkey.equals(lvalue)) {
                                    if (skey.equals("size_28")) {
                                        wsp28.setText(wsp);
                                        mrp28.setText(mrp);
                                    } else if (skey.equals("size_30")) {
                                        wsp30.setText(wsp);
                                        mrp30.setText(mrp);
                                    } else if (skey.equals("size_32")) {
                                        wsp32.setText(wsp);
                                        mrp32.setText(mrp);
                                    } else if (skey.equals("size_36")) {
                                        wsp36.setText(wsp);
                                        mrp36.setText(mrp);
                                    } else if (skey.equals("size_38")) {
                                        wsp38.setText(wsp);
                                        mrp38.setText(mrp);
                                    } else if (skey.equals("size_40")) {
                                        wsp40.setText(wsp);
                                        mrp40.setText(mrp);
                                    } else if (skey.equals("size_42")) {
                                        wsp42.setText(wsp);
                                        mrp42.setText(mrp);
                                    } else if (skey.equals("size_44")) {
                                        wsp44.setText(wsp);
                                        mrp44.setText(mrp);
                                    } else if (skey.equals("size_46")) {
                                        wsp46.setText(wsp);
                                        mrp46.setText(mrp);
                                    } else if (skey.equals("size_48")) {
                                        wsp48.setText(wsp);
                                        mrp48.setText(mrp);
                                    } else if (skey.equals("size_00")) {
                                        wqty.setText(wsp);
                                        mqty.setText(mrp);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<lengthaddlist.size();i++){
                        if(lengthaddlist.get(i).equals(leng2.getText().toString())){
                            lengthaddlist.remove(i);
                            leng2.setTextColor(getResources().getColor(R.color.colorAccent));
                            leng2.setBackgroundResource(R.drawable.buttonstyle_background);
                            if(lengthaddlist.size()!=0){
                                String lvalue = lengthaddlist.get(lengthaddlist.size()-1);
                                if(pricelist.length()!=0) {
                                    for (int a = 0; a < pricelist.length(); a++) {
                                        try {
                                            JSONObject list = pricelist.getJSONObject(a);
                                            String slkey = list.get("sl_key").toString();
                                            String skey = list.get("size_key").toString();
                                            String wsp = list.get("wsp").toString();
                                            String mrp = list.get("mrp").toString();
                                            if(slkey.equals(lvalue)) {
                                                if (skey.equals("size_28")) {
                                                    wsp28.setText(wsp);
                                                    mrp28.setText(mrp);
                                                } else if (skey.equals("size_30")) {
                                                    wsp30.setText(wsp);
                                                    mrp30.setText(mrp);
                                                } else if (skey.equals("size_32")) {
                                                    wsp32.setText(wsp);
                                                    mrp32.setText(mrp);
                                                } else if (skey.equals("size_36")) {
                                                    wsp36.setText(wsp);
                                                    mrp36.setText(mrp);
                                                } else if (skey.equals("size_38")) {
                                                    wsp38.setText(wsp);
                                                    mrp38.setText(mrp);
                                                } else if (skey.equals("size_40")) {
                                                    wsp40.setText(wsp);
                                                    mrp40.setText(mrp);
                                                } else if (skey.equals("size_42")) {
                                                    wsp42.setText(wsp);
                                                    mrp42.setText(mrp);
                                                } else if (skey.equals("size_44")) {
                                                    wsp44.setText(wsp);
                                                    mrp44.setText(mrp);
                                                } else if (skey.equals("size_46")) {
                                                    wsp46.setText(wsp);
                                                    mrp46.setText(mrp);
                                                } else if (skey.equals("size_48")) {
                                                    wsp48.setText(wsp);
                                                    mrp48.setText(mrp);
                                                } else if (skey.equals("size_00")) {
                                                    wqty.setText(wsp);
                                                    mqty.setText(mrp);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        });
        leng3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = leng3.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    lengthaddlist.add(leng3.getText().toString());
                    leng3.setTextColor(getResources().getColor(R.color.colorWhite));
                    leng3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    String lvalue = leng3.getText().toString();
                    if(pricelist.length()!=0) {
                        for (int i = 0; i < pricelist.length(); i++) {
                            try {
                                JSONObject list = pricelist.getJSONObject(i);
                                String slkey = list.get("sl_key").toString();
                                String skey = list.get("size_key").toString();
                                String wsp = list.get("wsp").toString();
                                String mrp = list.get("mrp").toString();
                                if(slkey.equals(lvalue)) {
                                    if (skey.equals("size_28")) {
                                        wsp28.setText(wsp);
                                        mrp28.setText(mrp);
                                    } else if (skey.equals("size_30")) {
                                        wsp30.setText(wsp);
                                        mrp30.setText(mrp);
                                    } else if (skey.equals("size_32")) {
                                        wsp32.setText(wsp);
                                        mrp32.setText(mrp);
                                    } else if (skey.equals("size_36")) {
                                        wsp36.setText(wsp);
                                        mrp36.setText(mrp);
                                    } else if (skey.equals("size_38")) {
                                        wsp38.setText(wsp);
                                        mrp38.setText(mrp);
                                    } else if (skey.equals("size_40")) {
                                        wsp40.setText(wsp);
                                        mrp40.setText(mrp);
                                    } else if (skey.equals("size_42")) {
                                        wsp42.setText(wsp);
                                        mrp42.setText(mrp);
                                    } else if (skey.equals("size_44")) {
                                        wsp44.setText(wsp);
                                        mrp44.setText(mrp);
                                    } else if (skey.equals("size_46")) {
                                        wsp46.setText(wsp);
                                        mrp46.setText(mrp);
                                    } else if (skey.equals("size_48")) {
                                        wsp48.setText(wsp);
                                        mrp48.setText(mrp);
                                    } else if (skey.equals("size_00")) {
                                        wqty.setText(wsp);
                                        mqty.setText(mrp);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<lengthaddlist.size();i++){
                        if(lengthaddlist.get(i).equals(leng3.getText().toString())){
                            lengthaddlist.remove(i);
                            leng3.setTextColor(getResources().getColor(R.color.colorAccent));
                            leng3.setBackgroundResource(R.drawable.buttonstyle_background);
                            if(lengthaddlist.size()!=0){
                                String lvalue = lengthaddlist.get(lengthaddlist.size()-1);
                                if(pricelist.length()!=0) {
                                    for (int a = 0; a < pricelist.length(); a++) {
                                        try {
                                            JSONObject list = pricelist.getJSONObject(a);
                                            String slkey = list.get("sl_key").toString();
                                            String skey = list.get("size_key").toString();
                                            String wsp = list.get("wsp").toString();
                                            String mrp = list.get("mrp").toString();
                                            if(slkey.equals(lvalue)) {
                                                if (skey.equals("size_28")) {
                                                    wsp28.setText(wsp);
                                                    mrp28.setText(mrp);
                                                } else if (skey.equals("size_30")) {
                                                    wsp30.setText(wsp);
                                                    mrp30.setText(mrp);
                                                } else if (skey.equals("size_32")) {
                                                    wsp32.setText(wsp);
                                                    mrp32.setText(mrp);
                                                } else if (skey.equals("size_36")) {
                                                    wsp36.setText(wsp);
                                                    mrp36.setText(mrp);
                                                } else if (skey.equals("size_38")) {
                                                    wsp38.setText(wsp);
                                                    mrp38.setText(mrp);
                                                } else if (skey.equals("size_40")) {
                                                    wsp40.setText(wsp);
                                                    mrp40.setText(mrp);
                                                } else if (skey.equals("size_42")) {
                                                    wsp42.setText(wsp);
                                                    mrp42.setText(mrp);
                                                } else if (skey.equals("size_44")) {
                                                    wsp44.setText(wsp);
                                                    mrp44.setText(mrp);
                                                } else if (skey.equals("size_46")) {
                                                    wsp46.setText(wsp);
                                                    mrp46.setText(mrp);
                                                } else if (skey.equals("size_48")) {
                                                    wsp48.setText(wsp);
                                                    mrp48.setText(mrp);
                                                } else if (skey.equals("size_00")) {
                                                    wqty.setText(wsp);
                                                    mqty.setText(mrp);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        });
        leng4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = leng4.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    lengthaddlist.add(leng4.getText().toString());
                    leng4.setTextColor(getResources().getColor(R.color.colorWhite));
                    leng4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    String lvalue = leng4.getText().toString();
                    if(pricelist.length()!=0) {
                        for (int i = 0; i < pricelist.length(); i++) {
                            try {
                                JSONObject list = pricelist.getJSONObject(i);
                                String slkey = list.get("sl_key").toString();
                                String skey = list.get("size_key").toString();
                                String wsp = list.get("wsp").toString();
                                String mrp = list.get("mrp").toString();
                                if(slkey.equals(lvalue)) {
                                    if (skey.equals("size_28")) {
                                        wsp28.setText(wsp);
                                        mrp28.setText(mrp);
                                    } else if (skey.equals("size_30")) {
                                        wsp30.setText(wsp);
                                        mrp30.setText(mrp);
                                    } else if (skey.equals("size_32")) {
                                        wsp32.setText(wsp);
                                        mrp32.setText(mrp);
                                    } else if (skey.equals("size_36")) {
                                        wsp36.setText(wsp);
                                        mrp36.setText(mrp);
                                    } else if (skey.equals("size_38")) {
                                        wsp38.setText(wsp);
                                        mrp38.setText(mrp);
                                    } else if (skey.equals("size_40")) {
                                        wsp40.setText(wsp);
                                        mrp40.setText(mrp);
                                    } else if (skey.equals("size_42")) {
                                        wsp42.setText(wsp);
                                        mrp42.setText(mrp);
                                    } else if (skey.equals("size_44")) {
                                        wsp44.setText(wsp);
                                        mrp44.setText(mrp);
                                    } else if (skey.equals("size_46")) {
                                        wsp46.setText(wsp);
                                        mrp46.setText(mrp);
                                    } else if (skey.equals("size_48")) {
                                        wsp48.setText(wsp);
                                        mrp48.setText(mrp);
                                    } else if (skey.equals("size_00")) {
                                        wqty.setText(wsp);
                                        mqty.setText(mrp);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<lengthaddlist.size();i++){
                        if(lengthaddlist.get(i).equals(leng4.getText().toString())){
                            lengthaddlist.remove(i);
                            leng4.setTextColor(getResources().getColor(R.color.colorAccent));
                            leng4.setBackgroundResource(R.drawable.buttonstyle_background);
                            if(lengthaddlist.size()!=0){
                                String lvalue = lengthaddlist.get(lengthaddlist.size()-1);
                                if(pricelist.length()!=0) {
                                    for (int a = 0; a < pricelist.length(); a++) {
                                        try {
                                            JSONObject list = pricelist.getJSONObject(a);
                                            String slkey = list.get("sl_key").toString();
                                            String skey = list.get("size_key").toString();
                                            String wsp = list.get("wsp").toString();
                                            String mrp = list.get("mrp").toString();
                                            if(slkey.equals(lvalue)) {
                                                if (skey.equals("size_28")) {
                                                    wsp28.setText(wsp);
                                                    mrp28.setText(mrp);
                                                } else if (skey.equals("size_30")) {
                                                    wsp30.setText(wsp);
                                                    mrp30.setText(mrp);
                                                } else if (skey.equals("size_32")) {
                                                    wsp32.setText(wsp);
                                                    mrp32.setText(mrp);
                                                } else if (skey.equals("size_36")) {
                                                    wsp36.setText(wsp);
                                                    mrp36.setText(mrp);
                                                } else if (skey.equals("size_38")) {
                                                    wsp38.setText(wsp);
                                                    mrp38.setText(mrp);
                                                } else if (skey.equals("size_40")) {
                                                    wsp40.setText(wsp);
                                                    mrp40.setText(mrp);
                                                } else if (skey.equals("size_42")) {
                                                    wsp42.setText(wsp);
                                                    mrp42.setText(mrp);
                                                } else if (skey.equals("size_44")) {
                                                    wsp44.setText(wsp);
                                                    mrp44.setText(mrp);
                                                } else if (skey.equals("size_46")) {
                                                    wsp46.setText(wsp);
                                                    mrp46.setText(mrp);
                                                } else if (skey.equals("size_48")) {
                                                    wsp48.setText(wsp);
                                                    mrp48.setText(mrp);
                                                } else if (skey.equals("size_00")) {
                                                    wqty.setText(wsp);
                                                    mqty.setText(mrp);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        });
        leng5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList mList = leng5.getTextColors();
                String hexColor = String.format("#%06X", (0xFFFFFF & mList.getDefaultColor()));
                if(hexColor.equals("#AA4098")) {
                    lengthaddlist.add(leng5.getText().toString());
                    leng5.setTextColor(getResources().getColor(R.color.colorWhite));
                    leng5.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    String lvalue = leng5.getText().toString();
                    if(pricelist.length()!=0) {
                        for (int i = 0; i < pricelist.length(); i++) {
                            try {
                                JSONObject list = pricelist.getJSONObject(i);
                                String slkey = list.get("sl_key").toString();
                                String skey = list.get("size_key").toString();
                                String wsp = list.get("wsp").toString();
                                String mrp = list.get("mrp").toString();
                                if(slkey.equals(lvalue)) {
                                    if (skey.equals("size_28")) {
                                        wsp28.setText(wsp);
                                        mrp28.setText(mrp);
                                    } else if (skey.equals("size_30")) {
                                        wsp30.setText(wsp);
                                        mrp30.setText(mrp);
                                    } else if (skey.equals("size_32")) {
                                        wsp32.setText(wsp);
                                        mrp32.setText(mrp);
                                    } else if (skey.equals("size_36")) {
                                        wsp36.setText(wsp);
                                        mrp36.setText(mrp);
                                    } else if (skey.equals("size_38")) {
                                        wsp38.setText(wsp);
                                        mrp38.setText(mrp);
                                    } else if (skey.equals("size_40")) {
                                        wsp40.setText(wsp);
                                        mrp40.setText(mrp);
                                    } else if (skey.equals("size_42")) {
                                        wsp42.setText(wsp);
                                        mrp42.setText(mrp);
                                    } else if (skey.equals("size_44")) {
                                        wsp44.setText(wsp);
                                        mrp44.setText(mrp);
                                    } else if (skey.equals("size_46")) {
                                        wsp46.setText(wsp);
                                        mrp46.setText(mrp);
                                    } else if (skey.equals("size_48")) {
                                        wsp48.setText(wsp);
                                        mrp48.setText(mrp);
                                    } else if (skey.equals("size_00")) {
                                        wqty.setText(wsp);
                                        mqty.setText(mrp);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(hexColor.equals("#FFFFFF")){
                    for(int i=0;i<lengthaddlist.size();i++){
                        if(lengthaddlist.get(i).equals(leng5.getText().toString())){
                            lengthaddlist.remove(i);
                            leng5.setTextColor(getResources().getColor(R.color.colorAccent));
                            leng5.setBackgroundResource(R.drawable.buttonstyle_background);
                            if(lengthaddlist.size()!=0){
                                String lvalue = lengthaddlist.get(lengthaddlist.size()-1);
                                if(pricelist.length()!=0) {
                                    for (int a = 0; a < pricelist.length(); a++) {
                                        try {
                                            JSONObject list = pricelist.getJSONObject(a);
                                            String slkey = list.get("sl_key").toString();
                                            String skey = list.get("size_key").toString();
                                            String wsp = list.get("wsp").toString();
                                            String mrp = list.get("mrp").toString();
                                            if(slkey.equals(lvalue)) {
                                                if (skey.equals("size_28")) {
                                                    wsp28.setText(wsp);
                                                    mrp28.setText(mrp);
                                                } else if (skey.equals("size_30")) {
                                                    wsp30.setText(wsp);
                                                    mrp30.setText(mrp);
                                                } else if (skey.equals("size_32")) {
                                                    wsp32.setText(wsp);
                                                    mrp32.setText(mrp);
                                                } else if (skey.equals("size_36")) {
                                                    wsp36.setText(wsp);
                                                    mrp36.setText(mrp);
                                                } else if (skey.equals("size_38")) {
                                                    wsp38.setText(wsp);
                                                    mrp38.setText(mrp);
                                                } else if (skey.equals("size_40")) {
                                                    wsp40.setText(wsp);
                                                    mrp40.setText(mrp);
                                                } else if (skey.equals("size_42")) {
                                                    wsp42.setText(wsp);
                                                    mrp42.setText(mrp);
                                                } else if (skey.equals("size_44")) {
                                                    wsp44.setText(wsp);
                                                    mrp44.setText(mrp);
                                                } else if (skey.equals("size_46")) {
                                                    wsp46.setText(wsp);
                                                    mrp46.setText(mrp);
                                                } else if (skey.equals("size_48")) {
                                                    wsp48.setText(wsp);
                                                    mrp48.setText(mrp);
                                                } else if (skey.equals("size_00")) {
                                                    wqty.setText(wsp);
                                                    mqty.setText(mrp);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        });

        size28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size28);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                    cqtystatus="false";
                }else {
                    cqtystatus="false";
                    String val = size28.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size28.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size28.setText(String.valueOf(data));
                        }

                    } else {
                        size28.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }

            }
        });
        size30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size30);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = size30.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size30.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size30.setText(String.valueOf(data));
                        }

                    } else {
                        size30.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }
            }
        });
        size32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size32);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = size32.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size32.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size32.setText(String.valueOf(data));
                        }

                    } else {
                        size32.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }
            }
        });
        size34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size34);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = size34.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size34.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size34.setText(String.valueOf(data));
                        }

                    } else {
                        size34.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }

            }
        });
        size36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size36);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = size36.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size36.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size36.setText(String.valueOf(data));
                        }

                    } else {
                        size36.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }
            }
        });
        size38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size38);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = size38.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size38.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size38.setText(String.valueOf(data));
                        }

                    } else {
                        size38.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }
            }
        });
        size40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size40);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = size40.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size40.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size40.setText(String.valueOf(data));
                        }

                    } else {
                        size40.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }
            }
        });
        size42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size42);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = size42.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size42.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size42.setText(String.valueOf(data));
                        }

                    } else {
                        size42.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }
            }
        });
        size44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size44);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = size44.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size44.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size44.setText(String.valueOf(data));
                        }

                    } else {
                        size44.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }
            }
        });
        size46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size46);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = size46.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size46.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size46.setText(String.valueOf(data));
                        }

                    } else {
                        size46.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }
            }
        });
        size48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size48);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = size48.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                size48.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            size48.setText(String.valueOf(data));
                        }

                    } else {
                        size48.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }
            }
        });
        qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.qty);
                emptystatus ="false";
                size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
                        if (vale.equals("size_28")) {
                            String s28 = size28.getText().toString();
                            if (!s28.equals("")) {
                                tqty = tqty + Integer.parseInt(s28);
                            }
                        }
                        if (vale.equals("size_30")) {
                            String s30 = size30.getText().toString();
                            if (!s30.equals("")) {
                                tqty = tqty + Integer.parseInt(s30);
                            }
                        }
                        if (vale.equals("size_32")) {
                            String s32 = size32.getText().toString();
                            if (!s32.equals("")) {
                                tqty =tqty+ Integer.parseInt(s32);
                            }
                        }
                        if (vale.equals("size_34")) {
                            String s34 = size34.getText().toString();
                            if (!s34.equals("")) {
                                tqty =tqty + Integer.parseInt(s34);
                            }
                        }
                        if (vale.equals("size_36")) {
                            String s36 = size36.getText().toString();
                            if (!s36.equals("")) {
                                tqty =tqty + Integer.parseInt(s36);
                            }
                        }
                        if (vale.equals("size_38")) {
                            String s38 = size38.getText().toString();
                            if (!s38.equals("")) {
                                tqty =tqty + Integer.parseInt(s38);
                            }
                        }
                        if (vale.equals("size_40")) {
                            String s40 = size40.getText().toString();
                            if (!s40.equals("")) {
                                tqty =tqty + Integer.parseInt(s40);
                            }
                        }
                        if (vale.equals("size_42")) {
                            String s42 = size42.getText().toString();
                            if (!s42.equals("")) {
                                tqty =tqty + Integer.parseInt(s42);
                            }
                        }
                        if (vale.equals("size_44")) {
                            String s44 = size44.getText().toString();
                            if (!s44.equals("")) {
                                tqty =tqty + Integer.parseInt(s44);
                            }
                        }
                        if (vale.equals("size_46")) {
                            String s46 = size46.getText().toString();
                            if (!s46.equals("")) {
                                tqty =tqty + Integer.parseInt(s46);
                            }
                        }
                        if (vale.equals("size_48")) {
                            String s48 = size48.getText().toString();
                            if (!s48.equals("")) {
                                tqty =tqty + Integer.parseInt(s48);
                            }
                        }
                        if (vale.equals("size_00")) {
                            String s00 = qty.getText().toString();
                            if (!s00.equals("")) {
                                tqty =tqty + Integer.parseInt(s00);
                            }
                        }
                    }
                    cqty.setText(String.valueOf(tqty));
                    cqtystatus="false";
                }else {
                    cqtystatus = "false";
                    String val = qty.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                qty.setText(String.valueOf(data));
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            qty.setText(String.valueOf(data));
                        }

                    } else {
                        qty.setText(String.valueOf(1));
                    }
                    if(sizekeylist.size()!=0) {
                        int tqty = 0;
                        for (int a = 0; a < sizekeylist.size(); a++) {
                            String vale = sizekeylist.get(a);
                            if (vale.equals("size_28")) {
                                String s28 = size28.getText().toString();
                                if (!s28.equals("")) {
                                    tqty = tqty + Integer.parseInt(s28);
                                }
                            }
                            if (vale.equals("size_30")) {
                                String s30 = size30.getText().toString();
                                if (!s30.equals("")) {
                                    tqty = tqty + Integer.parseInt(s30);
                                }
                            }
                            if (vale.equals("size_32")) {
                                String s32 = size32.getText().toString();
                                if (!s32.equals("")) {
                                    tqty =tqty+ Integer.parseInt(s32);
                                }
                            }
                            if (vale.equals("size_34")) {
                                String s34 = size34.getText().toString();
                                if (!s34.equals("")) {
                                    tqty =tqty + Integer.parseInt(s34);
                                }
                            }
                            if (vale.equals("size_36")) {
                                String s36 = size36.getText().toString();
                                if (!s36.equals("")) {
                                    tqty =tqty + Integer.parseInt(s36);
                                }
                            }
                            if (vale.equals("size_38")) {
                                String s38 = size38.getText().toString();
                                if (!s38.equals("")) {
                                    tqty =tqty + Integer.parseInt(s38);
                                }
                            }
                            if (vale.equals("size_40")) {
                                String s40 = size40.getText().toString();
                                if (!s40.equals("")) {
                                    tqty =tqty + Integer.parseInt(s40);
                                }
                            }
                            if (vale.equals("size_42")) {
                                String s42 = size42.getText().toString();
                                if (!s42.equals("")) {
                                    tqty =tqty + Integer.parseInt(s42);
                                }
                            }
                            if (vale.equals("size_44")) {
                                String s44 = size44.getText().toString();
                                if (!s44.equals("")) {
                                    tqty =tqty + Integer.parseInt(s44);
                                }
                            }
                            if (vale.equals("size_46")) {
                                String s46 = size46.getText().toString();
                                if (!s46.equals("")) {
                                    tqty =tqty + Integer.parseInt(s46);
                                }
                            }
                            if (vale.equals("size_48")) {
                                String s48 = size48.getText().toString();
                                if (!s48.equals("")) {
                                    tqty =tqty + Integer.parseInt(s48);
                                }
                            }
                            if (vale.equals("size_00")) {
                                String s00 = qty.getText().toString();
                                if (!s00.equals("")) {
                                    tqty =tqty + Integer.parseInt(s00);
                                }
                            }
                        }
                        cqty.setText(String.valueOf(tqty));

                    }
                }
            }
        });
        cqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cqtystatus.equals("")||cqtystatus.equals("true")) {
                    emptystatus ="false";
                    registerEditText(R.id.cqty);
                    cqtystatus = "true";
                    size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    cqty.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                    String val = cqty.getText().toString();
                    if (!val.equals("")) {
                        if (minustatus.equals("true")) {
                            int data = Integer.parseInt(val) - 1;
                            if (data > 0) {
                                cqty.setText(String.valueOf(data));
                                if (sizekeylist.size() != 0) {
                                    for (int a = 0; a < sizekeylist.size(); a++) {
                                        String vale = sizekeylist.get(a);
                                        if (vale.equals("size_28")) {
                                            size28.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_30")) {
                                            size30.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_32")) {
                                            size32.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_34")) {
                                            size34.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_36")) {
                                            size36.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_38")) {
                                            size38.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_40")) {
                                            size40.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_42")) {
                                            size42.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_44")) {
                                            size44.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_46")) {
                                            size46.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_48")) {
                                            size48.setText(String.valueOf(data));
                                        }
                                        if (vale.equals("size_00")) {
                                            qty.setText(String.valueOf(data));
                                        }
                                    }
                                }
                            }
                        } else {
                            int data = Integer.parseInt(val) + 1;
                            cqty.setText(String.valueOf(data));
                            if (sizekeylist.size() != 0) {
                                for (int a = 0; a < sizekeylist.size(); a++) {
                                    String vale = sizekeylist.get(a);
                                    if (vale.equals("size_28")) {
                                        size28.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_30")) {
                                        size30.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_32")) {
                                        size32.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_34")) {
                                        size34.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_36")) {
                                        size36.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_38")) {
                                        size38.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_40")) {
                                        size40.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_42")) {
                                        size42.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_44")) {
                                        size44.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_46")) {
                                        size46.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_48")) {
                                        size48.setText(String.valueOf(data));
                                    }
                                    if (vale.equals("size_00")) {
                                        qty.setText(String.valueOf(data));
                                    }
                                }
                            }
                        }
                    } else {
                        cqty.setText(String.valueOf(1));
                        if (sizekeylist.size() != 0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    size28.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_30")) {
                                    size30.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_32")) {
                                    size32.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_34")) {
                                    size34.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_36")) {
                                    size36.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_38")) {
                                    size38.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_40")) {
                                    size40.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_42")) {
                                    size42.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_44")) {
                                    size44.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_46")) {
                                    size46.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_48")) {
                                    size48.setText(String.valueOf(1));
                                }
                                if (vale.equals("size_00")) {
                                    qty.setText(String.valueOf(1));
                                }
                            }
                        }
                    }
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = b1.getText().toString();
                try {

                     if(cqtystatus.equals("true")){
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            oldval = val;
                            edittext.setText(oldval);
                            emptystatus ="true";
                        }else {
                             oldval = edittext.getText().toString();
                             oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale= sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                        size28.setText(oldval);
                                }
                                if (vale.equals("size_30")) {
                                        size30.setText(oldval);
                                }
                                if (vale.equals("size_32")) {
                                        size32.setText(oldval);
                                }
                                if (vale.equals("size_34")) {
                                        size34.setText(oldval);
                                }
                                if (vale.equals("size_36")) {
                                        size36.setText(oldval);
                                }
                                if (vale.equals("size_38")) {
                                        size38.setText(oldval);
                                }
                                if (vale.equals("size_40")) {
                                        size40.setText(oldval);
                                }
                                if (vale.equals("size_42")) {
                                        size42.setText(oldval);
                                }
                                if (vale.equals("size_44")) {
                                        size44.setText(oldval);
                                }
                                if (vale.equals("size_46")) {
                                        size46.setText(oldval);
                                }
                                if (vale.equals("size_48")) {
                                        size48.setText(oldval);
                                }
                                if (vale.equals("size_00")) {

                                        qty.setText(oldval);
                                }
                            }
                        }
                    }else{
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            edittext.setText(val);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    String s28 = size28.getText().toString();
                                    if (!s28.equals("")) {
                                        tqty = tqty + Integer.parseInt(s28);
                                    }
                                }
                                if (vale.equals("size_30")) {
                                    String s30 = size30.getText().toString();
                                    if (!s30.equals("")) {
                                        tqty = tqty + Integer.parseInt(s30);
                                    }
                                }
                                if (vale.equals("size_32")) {
                                    String s32 = size32.getText().toString();
                                    if (!s32.equals("")) {
                                        tqty =tqty+ Integer.parseInt(s32);
                                    }
                                }
                                if (vale.equals("size_34")) {
                                    String s34 = size34.getText().toString();
                                    if (!s34.equals("")) {
                                        tqty =tqty + Integer.parseInt(s34);
                                    }
                                }
                                if (vale.equals("size_36")) {
                                    String s36 = size36.getText().toString();
                                    if (!s36.equals("")) {
                                        tqty =tqty + Integer.parseInt(s36);
                                    }
                                }
                                if (vale.equals("size_38")) {
                                    String s38 = size38.getText().toString();
                                    if (!s38.equals("")) {
                                        tqty =tqty + Integer.parseInt(s38);
                                    }
                                }
                                if (vale.equals("size_40")) {
                                    String s40 = size40.getText().toString();
                                    if (!s40.equals("")) {
                                        tqty =tqty + Integer.parseInt(s40);
                                    }
                                }
                                if (vale.equals("size_42")) {
                                    String s42 = size42.getText().toString();
                                    if (!s42.equals("")) {
                                        tqty =tqty + Integer.parseInt(s42);
                                    }
                                }
                                if (vale.equals("size_44")) {
                                    String s44 = size44.getText().toString();
                                    if (!s44.equals("")) {
                                        tqty =tqty + Integer.parseInt(s44);
                                    }
                                }
                                if (vale.equals("size_46")) {
                                    String s46 = size46.getText().toString();
                                    if (!s46.equals("")) {
                                        tqty =tqty + Integer.parseInt(s46);
                                    }
                                }
                                if (vale.equals("size_48")) {
                                    String s48 = size48.getText().toString();
                                    if (!s48.equals("")) {
                                        tqty =tqty + Integer.parseInt(s48);
                                    }
                                }
                                if (vale.equals("size_00")) {
                                    String s00 = qty.getText().toString();
                                    if (!s00.equals("")) {
                                        tqty =tqty + Integer.parseInt(s00);
                                    }
                                }
                            }
                            cqty.setText(String.valueOf(tqty));

                        }

                    }
                }catch (Exception e){

                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = b2.getText().toString();
                try {

                    if(cqtystatus.equals("true")){
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            oldval = val;
                            edittext.setText(oldval);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale= sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    size28.setText(oldval);
                                }
                                if (vale.equals("size_30")) {
                                    size30.setText(oldval);
                                }
                                if (vale.equals("size_32")) {
                                    size32.setText(oldval);
                                }
                                if (vale.equals("size_34")) {
                                    size34.setText(oldval);
                                }
                                if (vale.equals("size_36")) {
                                    size36.setText(oldval);
                                }
                                if (vale.equals("size_38")) {
                                    size38.setText(oldval);
                                }
                                if (vale.equals("size_40")) {
                                    size40.setText(oldval);
                                }
                                if (vale.equals("size_42")) {
                                    size42.setText(oldval);
                                }
                                if (vale.equals("size_44")) {
                                    size44.setText(oldval);
                                }
                                if (vale.equals("size_46")) {
                                    size46.setText(oldval);
                                }
                                if (vale.equals("size_48")) {
                                    size48.setText(oldval);
                                }
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            edittext.setText(val);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    String s28 = size28.getText().toString();
                                    if (!s28.equals("")) {
                                        tqty = tqty + Integer.parseInt(s28);
                                    }
                                }
                                if (vale.equals("size_30")) {
                                    String s30 = size30.getText().toString();
                                    if (!s30.equals("")) {
                                        tqty = tqty + Integer.parseInt(s30);
                                    }
                                }
                                if (vale.equals("size_32")) {
                                    String s32 = size32.getText().toString();
                                    if (!s32.equals("")) {
                                        tqty =tqty+ Integer.parseInt(s32);
                                    }
                                }
                                if (vale.equals("size_34")) {
                                    String s34 = size34.getText().toString();
                                    if (!s34.equals("")) {
                                        tqty =tqty + Integer.parseInt(s34);
                                    }
                                }
                                if (vale.equals("size_36")) {
                                    String s36 = size36.getText().toString();
                                    if (!s36.equals("")) {
                                        tqty =tqty + Integer.parseInt(s36);
                                    }
                                }
                                if (vale.equals("size_38")) {
                                    String s38 = size38.getText().toString();
                                    if (!s38.equals("")) {
                                        tqty =tqty + Integer.parseInt(s38);
                                    }
                                }
                                if (vale.equals("size_40")) {
                                    String s40 = size40.getText().toString();
                                    if (!s40.equals("")) {
                                        tqty =tqty + Integer.parseInt(s40);
                                    }
                                }
                                if (vale.equals("size_42")) {
                                    String s42 = size42.getText().toString();
                                    if (!s42.equals("")) {
                                        tqty =tqty + Integer.parseInt(s42);
                                    }
                                }
                                if (vale.equals("size_44")) {
                                    String s44 = size44.getText().toString();
                                    if (!s44.equals("")) {
                                        tqty =tqty + Integer.parseInt(s44);
                                    }
                                }
                                if (vale.equals("size_46")) {
                                    String s46 = size46.getText().toString();
                                    if (!s46.equals("")) {
                                        tqty =tqty + Integer.parseInt(s46);
                                    }
                                }
                                if (vale.equals("size_48")) {
                                    String s48 = size48.getText().toString();
                                    if (!s48.equals("")) {
                                        tqty =tqty + Integer.parseInt(s48);
                                    }
                                }
                                if (vale.equals("size_00")) {
                                    String s00 = qty.getText().toString();
                                    if (!s00.equals("")) {
                                        tqty =tqty + Integer.parseInt(s00);
                                    }
                                }
                            }
                            cqty.setText(String.valueOf(tqty));

                        }
                    }
                }catch (Exception e){

                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = b3.getText().toString();
                try {

                    if(cqtystatus.equals("true")){
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            oldval = val;
                            edittext.setText(oldval);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale= sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    size28.setText(oldval);
                                }
                                if (vale.equals("size_30")) {
                                    size30.setText(oldval);
                                }
                                if (vale.equals("size_32")) {
                                    size32.setText(oldval);
                                }
                                if (vale.equals("size_34")) {
                                    size34.setText(oldval);
                                }
                                if (vale.equals("size_36")) {
                                    size36.setText(oldval);
                                }
                                if (vale.equals("size_38")) {
                                    size38.setText(oldval);
                                }
                                if (vale.equals("size_40")) {
                                    size40.setText(oldval);
                                }
                                if (vale.equals("size_42")) {
                                    size42.setText(oldval);
                                }
                                if (vale.equals("size_44")) {
                                    size44.setText(oldval);
                                }
                                if (vale.equals("size_46")) {
                                    size46.setText(oldval);
                                }
                                if (vale.equals("size_48")) {
                                    size48.setText(oldval);
                                }
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            edittext.setText(val);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    String s28 = size28.getText().toString();
                                    if (!s28.equals("")) {
                                        tqty = tqty + Integer.parseInt(s28);
                                    }
                                }
                                if (vale.equals("size_30")) {
                                    String s30 = size30.getText().toString();
                                    if (!s30.equals("")) {
                                        tqty = tqty + Integer.parseInt(s30);
                                    }
                                }
                                if (vale.equals("size_32")) {
                                    String s32 = size32.getText().toString();
                                    if (!s32.equals("")) {
                                        tqty =tqty+ Integer.parseInt(s32);
                                    }
                                }
                                if (vale.equals("size_34")) {
                                    String s34 = size34.getText().toString();
                                    if (!s34.equals("")) {
                                        tqty =tqty + Integer.parseInt(s34);
                                    }
                                }
                                if (vale.equals("size_36")) {
                                    String s36 = size36.getText().toString();
                                    if (!s36.equals("")) {
                                        tqty =tqty + Integer.parseInt(s36);
                                    }
                                }
                                if (vale.equals("size_38")) {
                                    String s38 = size38.getText().toString();
                                    if (!s38.equals("")) {
                                        tqty =tqty + Integer.parseInt(s38);
                                    }
                                }
                                if (vale.equals("size_40")) {
                                    String s40 = size40.getText().toString();
                                    if (!s40.equals("")) {
                                        tqty =tqty + Integer.parseInt(s40);
                                    }
                                }
                                if (vale.equals("size_42")) {
                                    String s42 = size42.getText().toString();
                                    if (!s42.equals("")) {
                                        tqty =tqty + Integer.parseInt(s42);
                                    }
                                }
                                if (vale.equals("size_44")) {
                                    String s44 = size44.getText().toString();
                                    if (!s44.equals("")) {
                                        tqty =tqty + Integer.parseInt(s44);
                                    }
                                }
                                if (vale.equals("size_46")) {
                                    String s46 = size46.getText().toString();
                                    if (!s46.equals("")) {
                                        tqty =tqty + Integer.parseInt(s46);
                                    }
                                }
                                if (vale.equals("size_48")) {
                                    String s48 = size48.getText().toString();
                                    if (!s48.equals("")) {
                                        tqty =tqty + Integer.parseInt(s48);
                                    }
                                }
                                if (vale.equals("size_00")) {
                                    String s00 = qty.getText().toString();
                                    if (!s00.equals("")) {
                                        tqty =tqty + Integer.parseInt(s00);
                                    }
                                }
                            }
                            cqty.setText(String.valueOf(tqty));

                        }
                    }
                }catch (Exception e){

                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = b4.getText().toString();
                try {
                    if(cqtystatus.equals("true")){
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            oldval = val;
                            edittext.setText(oldval);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale= sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    size28.setText(oldval);
                                }
                                if (vale.equals("size_30")) {
                                    size30.setText(oldval);
                                }
                                if (vale.equals("size_32")) {
                                    size32.setText(oldval);
                                }
                                if (vale.equals("size_34")) {
                                    size34.setText(oldval);
                                }
                                if (vale.equals("size_36")) {
                                    size36.setText(oldval);
                                }
                                if (vale.equals("size_38")) {
                                    size38.setText(oldval);
                                }
                                if (vale.equals("size_40")) {
                                    size40.setText(oldval);
                                }
                                if (vale.equals("size_42")) {
                                    size42.setText(oldval);
                                }
                                if (vale.equals("size_44")) {
                                    size44.setText(oldval);
                                }
                                if (vale.equals("size_46")) {
                                    size46.setText(oldval);
                                }
                                if (vale.equals("size_48")) {
                                    size48.setText(oldval);
                                }
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            edittext.setText(val);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    String s28 = size28.getText().toString();
                                    if (!s28.equals("")) {
                                        tqty = tqty + Integer.parseInt(s28);
                                    }
                                }
                                if (vale.equals("size_30")) {
                                    String s30 = size30.getText().toString();
                                    if (!s30.equals("")) {
                                        tqty = tqty + Integer.parseInt(s30);
                                    }
                                }
                                if (vale.equals("size_32")) {
                                    String s32 = size32.getText().toString();
                                    if (!s32.equals("")) {
                                        tqty =tqty+ Integer.parseInt(s32);
                                    }
                                }
                                if (vale.equals("size_34")) {
                                    String s34 = size34.getText().toString();
                                    if (!s34.equals("")) {
                                        tqty =tqty + Integer.parseInt(s34);
                                    }
                                }
                                if (vale.equals("size_36")) {
                                    String s36 = size36.getText().toString();
                                    if (!s36.equals("")) {
                                        tqty =tqty + Integer.parseInt(s36);
                                    }
                                }
                                if (vale.equals("size_38")) {
                                    String s38 = size38.getText().toString();
                                    if (!s38.equals("")) {
                                        tqty =tqty + Integer.parseInt(s38);
                                    }
                                }
                                if (vale.equals("size_40")) {
                                    String s40 = size40.getText().toString();
                                    if (!s40.equals("")) {
                                        tqty =tqty + Integer.parseInt(s40);
                                    }
                                }
                                if (vale.equals("size_42")) {
                                    String s42 = size42.getText().toString();
                                    if (!s42.equals("")) {
                                        tqty =tqty + Integer.parseInt(s42);
                                    }
                                }
                                if (vale.equals("size_44")) {
                                    String s44 = size44.getText().toString();
                                    if (!s44.equals("")) {
                                        tqty =tqty + Integer.parseInt(s44);
                                    }
                                }
                                if (vale.equals("size_46")) {
                                    String s46 = size46.getText().toString();
                                    if (!s46.equals("")) {
                                        tqty =tqty + Integer.parseInt(s46);
                                    }
                                }
                                if (vale.equals("size_48")) {
                                    String s48 = size48.getText().toString();
                                    if (!s48.equals("")) {
                                        tqty =tqty + Integer.parseInt(s48);
                                    }
                                }
                                if (vale.equals("size_00")) {
                                    String s00 = qty.getText().toString();
                                    if (!s00.equals("")) {
                                        tqty =tqty + Integer.parseInt(s00);
                                    }
                                }
                            }
                            cqty.setText(String.valueOf(tqty));

                        }
                    }
                }catch (Exception e){

                }
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = b5.getText().toString();
                try {

                    if(cqtystatus.equals("true")){
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            oldval = val;
                            edittext.setText(oldval);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale= sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    size28.setText(oldval);
                                }
                                if (vale.equals("size_30")) {
                                    size30.setText(oldval);
                                }
                                if (vale.equals("size_32")) {
                                    size32.setText(oldval);
                                }
                                if (vale.equals("size_34")) {
                                    size34.setText(oldval);
                                }
                                if (vale.equals("size_36")) {
                                    size36.setText(oldval);
                                }
                                if (vale.equals("size_38")) {
                                    size38.setText(oldval);
                                }
                                if (vale.equals("size_40")) {
                                    size40.setText(oldval);
                                }
                                if (vale.equals("size_42")) {
                                    size42.setText(oldval);
                                }
                                if (vale.equals("size_44")) {
                                    size44.setText(oldval);
                                }
                                if (vale.equals("size_46")) {
                                    size46.setText(oldval);
                                }
                                if (vale.equals("size_48")) {
                                    size48.setText(oldval);
                                }
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            edittext.setText(val);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    String s28 = size28.getText().toString();
                                    if (!s28.equals("")) {
                                        tqty = tqty + Integer.parseInt(s28);
                                    }
                                }
                                if (vale.equals("size_30")) {
                                    String s30 = size30.getText().toString();
                                    if (!s30.equals("")) {
                                        tqty = tqty + Integer.parseInt(s30);
                                    }
                                }
                                if (vale.equals("size_32")) {
                                    String s32 = size32.getText().toString();
                                    if (!s32.equals("")) {
                                        tqty =tqty+ Integer.parseInt(s32);
                                    }
                                }
                                if (vale.equals("size_34")) {
                                    String s34 = size34.getText().toString();
                                    if (!s34.equals("")) {
                                        tqty =tqty + Integer.parseInt(s34);
                                    }
                                }
                                if (vale.equals("size_36")) {
                                    String s36 = size36.getText().toString();
                                    if (!s36.equals("")) {
                                        tqty =tqty + Integer.parseInt(s36);
                                    }
                                }
                                if (vale.equals("size_38")) {
                                    String s38 = size38.getText().toString();
                                    if (!s38.equals("")) {
                                        tqty =tqty + Integer.parseInt(s38);
                                    }
                                }
                                if (vale.equals("size_40")) {
                                    String s40 = size40.getText().toString();
                                    if (!s40.equals("")) {
                                        tqty =tqty + Integer.parseInt(s40);
                                    }
                                }
                                if (vale.equals("size_42")) {
                                    String s42 = size42.getText().toString();
                                    if (!s42.equals("")) {
                                        tqty =tqty + Integer.parseInt(s42);
                                    }
                                }
                                if (vale.equals("size_44")) {
                                    String s44 = size44.getText().toString();
                                    if (!s44.equals("")) {
                                        tqty =tqty + Integer.parseInt(s44);
                                    }
                                }
                                if (vale.equals("size_46")) {
                                    String s46 = size46.getText().toString();
                                    if (!s46.equals("")) {
                                        tqty =tqty + Integer.parseInt(s46);
                                    }
                                }
                                if (vale.equals("size_48")) {
                                    String s48 = size48.getText().toString();
                                    if (!s48.equals("")) {
                                        tqty =tqty + Integer.parseInt(s48);
                                    }
                                }
                                if (vale.equals("size_00")) {
                                    String s00 = qty.getText().toString();
                                    if (!s00.equals("")) {
                                        tqty =tqty + Integer.parseInt(s00);
                                    }
                                }
                            }
                            cqty.setText(String.valueOf(tqty));

                        }
                    }
                }catch (Exception e){

                }
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = b6.getText().toString();
                try {

                    if(cqtystatus.equals("true")){
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            oldval = val;
                            edittext.setText(oldval);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale= sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    size28.setText(oldval);
                                }
                                if (vale.equals("size_30")) {
                                    size30.setText(oldval);
                                }
                                if (vale.equals("size_32")) {
                                    size32.setText(oldval);
                                }
                                if (vale.equals("size_34")) {
                                    size34.setText(oldval);
                                }
                                if (vale.equals("size_36")) {
                                    size36.setText(oldval);
                                }
                                if (vale.equals("size_38")) {
                                    size38.setText(oldval);
                                }
                                if (vale.equals("size_40")) {
                                    size40.setText(oldval);
                                }
                                if (vale.equals("size_42")) {
                                    size42.setText(oldval);
                                }
                                if (vale.equals("size_44")) {
                                    size44.setText(oldval);
                                }
                                if (vale.equals("size_46")) {
                                    size46.setText(oldval);
                                }
                                if (vale.equals("size_48")) {
                                    size48.setText(oldval);
                                }
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            edittext.setText(val);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    String s28 = size28.getText().toString();
                                    if (!s28.equals("")) {
                                        tqty = tqty + Integer.parseInt(s28);
                                    }
                                }
                                if (vale.equals("size_30")) {
                                    String s30 = size30.getText().toString();
                                    if (!s30.equals("")) {
                                        tqty = tqty + Integer.parseInt(s30);
                                    }
                                }
                                if (vale.equals("size_32")) {
                                    String s32 = size32.getText().toString();
                                    if (!s32.equals("")) {
                                        tqty =tqty+ Integer.parseInt(s32);
                                    }
                                }
                                if (vale.equals("size_34")) {
                                    String s34 = size34.getText().toString();
                                    if (!s34.equals("")) {
                                        tqty =tqty + Integer.parseInt(s34);
                                    }
                                }
                                if (vale.equals("size_36")) {
                                    String s36 = size36.getText().toString();
                                    if (!s36.equals("")) {
                                        tqty =tqty + Integer.parseInt(s36);
                                    }
                                }
                                if (vale.equals("size_38")) {
                                    String s38 = size38.getText().toString();
                                    if (!s38.equals("")) {
                                        tqty =tqty + Integer.parseInt(s38);
                                    }
                                }
                                if (vale.equals("size_40")) {
                                    String s40 = size40.getText().toString();
                                    if (!s40.equals("")) {
                                        tqty =tqty + Integer.parseInt(s40);
                                    }
                                }
                                if (vale.equals("size_42")) {
                                    String s42 = size42.getText().toString();
                                    if (!s42.equals("")) {
                                        tqty =tqty + Integer.parseInt(s42);
                                    }
                                }
                                if (vale.equals("size_44")) {
                                    String s44 = size44.getText().toString();
                                    if (!s44.equals("")) {
                                        tqty =tqty + Integer.parseInt(s44);
                                    }
                                }
                                if (vale.equals("size_46")) {
                                    String s46 = size46.getText().toString();
                                    if (!s46.equals("")) {
                                        tqty =tqty + Integer.parseInt(s46);
                                    }
                                }
                                if (vale.equals("size_48")) {
                                    String s48 = size48.getText().toString();
                                    if (!s48.equals("")) {
                                        tqty =tqty + Integer.parseInt(s48);
                                    }
                                }
                                if (vale.equals("size_00")) {
                                    String s00 = qty.getText().toString();
                                    if (!s00.equals("")) {
                                        tqty =tqty + Integer.parseInt(s00);
                                    }
                                }
                            }
                            cqty.setText(String.valueOf(tqty));

                        }
                    }
                }catch (Exception e){

                }
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = b7.getText().toString();
                try {

                    if(cqtystatus.equals("true")){
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            oldval = val;
                            edittext.setText(oldval);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale= sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    size28.setText(oldval);
                                }
                                if (vale.equals("size_30")) {
                                    size30.setText(oldval);
                                }
                                if (vale.equals("size_32")) {
                                    size32.setText(oldval);
                                }
                                if (vale.equals("size_34")) {
                                    size34.setText(oldval);
                                }
                                if (vale.equals("size_36")) {
                                    size36.setText(oldval);
                                }
                                if (vale.equals("size_38")) {
                                    size38.setText(oldval);
                                }
                                if (vale.equals("size_40")) {
                                    size40.setText(oldval);
                                }
                                if (vale.equals("size_42")) {
                                    size42.setText(oldval);
                                }
                                if (vale.equals("size_44")) {
                                    size44.setText(oldval);
                                }
                                if (vale.equals("size_46")) {
                                    size46.setText(oldval);
                                }
                                if (vale.equals("size_48")) {
                                    size48.setText(oldval);
                                }
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            edittext.setText(val);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    String s28 = size28.getText().toString();
                                    if (!s28.equals("")) {
                                        tqty = tqty + Integer.parseInt(s28);
                                    }
                                }
                                if (vale.equals("size_30")) {
                                    String s30 = size30.getText().toString();
                                    if (!s30.equals("")) {
                                        tqty = tqty + Integer.parseInt(s30);
                                    }
                                }
                                if (vale.equals("size_32")) {
                                    String s32 = size32.getText().toString();
                                    if (!s32.equals("")) {
                                        tqty =tqty+ Integer.parseInt(s32);
                                    }
                                }
                                if (vale.equals("size_34")) {
                                    String s34 = size34.getText().toString();
                                    if (!s34.equals("")) {
                                        tqty =tqty + Integer.parseInt(s34);
                                    }
                                }
                                if (vale.equals("size_36")) {
                                    String s36 = size36.getText().toString();
                                    if (!s36.equals("")) {
                                        tqty =tqty + Integer.parseInt(s36);
                                    }
                                }
                                if (vale.equals("size_38")) {
                                    String s38 = size38.getText().toString();
                                    if (!s38.equals("")) {
                                        tqty =tqty + Integer.parseInt(s38);
                                    }
                                }
                                if (vale.equals("size_40")) {
                                    String s40 = size40.getText().toString();
                                    if (!s40.equals("")) {
                                        tqty =tqty + Integer.parseInt(s40);
                                    }
                                }
                                if (vale.equals("size_42")) {
                                    String s42 = size42.getText().toString();
                                    if (!s42.equals("")) {
                                        tqty =tqty + Integer.parseInt(s42);
                                    }
                                }
                                if (vale.equals("size_44")) {
                                    String s44 = size44.getText().toString();
                                    if (!s44.equals("")) {
                                        tqty =tqty + Integer.parseInt(s44);
                                    }
                                }
                                if (vale.equals("size_46")) {
                                    String s46 = size46.getText().toString();
                                    if (!s46.equals("")) {
                                        tqty =tqty + Integer.parseInt(s46);
                                    }
                                }
                                if (vale.equals("size_48")) {
                                    String s48 = size48.getText().toString();
                                    if (!s48.equals("")) {
                                        tqty =tqty + Integer.parseInt(s48);
                                    }
                                }
                                if (vale.equals("size_00")) {
                                    String s00 = qty.getText().toString();
                                    if (!s00.equals("")) {
                                        tqty =tqty + Integer.parseInt(s00);
                                    }
                                }
                            }
                            cqty.setText(String.valueOf(tqty));

                        }
                    }
                }catch (Exception e){

                }
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = b8.getText().toString();
                try {

                    if(cqtystatus.equals("true")){
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            oldval = val;
                            edittext.setText(oldval);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale= sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    size28.setText(oldval);
                                }
                                if (vale.equals("size_30")) {
                                    size30.setText(oldval);
                                }
                                if (vale.equals("size_32")) {
                                    size32.setText(oldval);
                                }
                                if (vale.equals("size_34")) {
                                    size34.setText(oldval);
                                }
                                if (vale.equals("size_36")) {
                                    size36.setText(oldval);
                                }
                                if (vale.equals("size_38")) {
                                    size38.setText(oldval);
                                }
                                if (vale.equals("size_40")) {
                                    size40.setText(oldval);
                                }
                                if (vale.equals("size_42")) {
                                    size42.setText(oldval);
                                }
                                if (vale.equals("size_44")) {
                                    size44.setText(oldval);
                                }
                                if (vale.equals("size_46")) {
                                    size46.setText(oldval);
                                }
                                if (vale.equals("size_48")) {
                                    size48.setText(oldval);
                                }
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            edittext.setText(val);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    String s28 = size28.getText().toString();
                                    if (!s28.equals("")) {
                                        tqty = tqty + Integer.parseInt(s28);
                                    }
                                }
                                if (vale.equals("size_30")) {
                                    String s30 = size30.getText().toString();
                                    if (!s30.equals("")) {
                                        tqty = tqty + Integer.parseInt(s30);
                                    }
                                }
                                if (vale.equals("size_32")) {
                                    String s32 = size32.getText().toString();
                                    if (!s32.equals("")) {
                                        tqty =tqty+ Integer.parseInt(s32);
                                    }
                                }
                                if (vale.equals("size_34")) {
                                    String s34 = size34.getText().toString();
                                    if (!s34.equals("")) {
                                        tqty =tqty + Integer.parseInt(s34);
                                    }
                                }
                                if (vale.equals("size_36")) {
                                    String s36 = size36.getText().toString();
                                    if (!s36.equals("")) {
                                        tqty =tqty + Integer.parseInt(s36);
                                    }
                                }
                                if (vale.equals("size_38")) {
                                    String s38 = size38.getText().toString();
                                    if (!s38.equals("")) {
                                        tqty =tqty + Integer.parseInt(s38);
                                    }
                                }
                                if (vale.equals("size_40")) {
                                    String s40 = size40.getText().toString();
                                    if (!s40.equals("")) {
                                        tqty =tqty + Integer.parseInt(s40);
                                    }
                                }
                                if (vale.equals("size_42")) {
                                    String s42 = size42.getText().toString();
                                    if (!s42.equals("")) {
                                        tqty =tqty + Integer.parseInt(s42);
                                    }
                                }
                                if (vale.equals("size_44")) {
                                    String s44 = size44.getText().toString();
                                    if (!s44.equals("")) {
                                        tqty =tqty + Integer.parseInt(s44);
                                    }
                                }
                                if (vale.equals("size_46")) {
                                    String s46 = size46.getText().toString();
                                    if (!s46.equals("")) {
                                        tqty =tqty + Integer.parseInt(s46);
                                    }
                                }
                                if (vale.equals("size_48")) {
                                    String s48 = size48.getText().toString();
                                    if (!s48.equals("")) {
                                        tqty =tqty + Integer.parseInt(s48);
                                    }
                                }
                                if (vale.equals("size_00")) {
                                    String s00 = qty.getText().toString();
                                    if (!s00.equals("")) {
                                        tqty =tqty + Integer.parseInt(s00);
                                    }
                                }
                            }
                            cqty.setText(String.valueOf(tqty));

                        }
                    }
                }catch (Exception e){

                }
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = b9.getText().toString();
                try {

                    if(cqtystatus.equals("true")){
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            oldval = val;
                            edittext.setText(oldval);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale= sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    size28.setText(oldval);
                                }
                                if (vale.equals("size_30")) {
                                    size30.setText(oldval);
                                }
                                if (vale.equals("size_32")) {
                                    size32.setText(oldval);
                                }
                                if (vale.equals("size_34")) {
                                    size34.setText(oldval);
                                }
                                if (vale.equals("size_36")) {
                                    size36.setText(oldval);
                                }
                                if (vale.equals("size_38")) {
                                    size38.setText(oldval);
                                }
                                if (vale.equals("size_40")) {
                                    size40.setText(oldval);
                                }
                                if (vale.equals("size_42")) {
                                    size42.setText(oldval);
                                }
                                if (vale.equals("size_44")) {
                                    size44.setText(oldval);
                                }
                                if (vale.equals("size_46")) {
                                    size46.setText(oldval);
                                }
                                if (vale.equals("size_48")) {
                                    size48.setText(oldval);
                                }
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            edittext.setText(val);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    String s28 = size28.getText().toString();
                                    if (!s28.equals("")) {
                                        tqty = tqty + Integer.parseInt(s28);
                                    }
                                }
                                if (vale.equals("size_30")) {
                                    String s30 = size30.getText().toString();
                                    if (!s30.equals("")) {
                                        tqty = tqty + Integer.parseInt(s30);
                                    }
                                }
                                if (vale.equals("size_32")) {
                                    String s32 = size32.getText().toString();
                                    if (!s32.equals("")) {
                                        tqty =tqty+ Integer.parseInt(s32);
                                    }
                                }
                                if (vale.equals("size_34")) {
                                    String s34 = size34.getText().toString();
                                    if (!s34.equals("")) {
                                        tqty =tqty + Integer.parseInt(s34);
                                    }
                                }
                                if (vale.equals("size_36")) {
                                    String s36 = size36.getText().toString();
                                    if (!s36.equals("")) {
                                        tqty =tqty + Integer.parseInt(s36);
                                    }
                                }
                                if (vale.equals("size_38")) {
                                    String s38 = size38.getText().toString();
                                    if (!s38.equals("")) {
                                        tqty =tqty + Integer.parseInt(s38);
                                    }
                                }
                                if (vale.equals("size_40")) {
                                    String s40 = size40.getText().toString();
                                    if (!s40.equals("")) {
                                        tqty =tqty + Integer.parseInt(s40);
                                    }
                                }
                                if (vale.equals("size_42")) {
                                    String s42 = size42.getText().toString();
                                    if (!s42.equals("")) {
                                        tqty =tqty + Integer.parseInt(s42);
                                    }
                                }
                                if (vale.equals("size_44")) {
                                    String s44 = size44.getText().toString();
                                    if (!s44.equals("")) {
                                        tqty =tqty + Integer.parseInt(s44);
                                    }
                                }
                                if (vale.equals("size_46")) {
                                    String s46 = size46.getText().toString();
                                    if (!s46.equals("")) {
                                        tqty =tqty + Integer.parseInt(s46);
                                    }
                                }
                                if (vale.equals("size_48")) {
                                    String s48 = size48.getText().toString();
                                    if (!s48.equals("")) {
                                        tqty =tqty + Integer.parseInt(s48);
                                    }
                                }
                                if (vale.equals("size_00")) {
                                    String s00 = qty.getText().toString();
                                    if (!s00.equals("")) {
                                        tqty =tqty + Integer.parseInt(s00);
                                    }
                                }
                            }
                            cqty.setText(String.valueOf(tqty));

                        }
                    }
                }catch (Exception e){

                }
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = b0.getText().toString();
                try {

                    if(cqtystatus.equals("true")){
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            oldval = val;
                            edittext.setText(oldval);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale= sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    size28.setText(oldval);
                                }
                                if (vale.equals("size_30")) {
                                    size30.setText(oldval);
                                }
                                if (vale.equals("size_32")) {
                                    size32.setText(oldval);
                                }
                                if (vale.equals("size_34")) {
                                    size34.setText(oldval);
                                }
                                if (vale.equals("size_36")) {
                                    size36.setText(oldval);
                                }
                                if (vale.equals("size_38")) {
                                    size38.setText(oldval);
                                }
                                if (vale.equals("size_40")) {
                                    size40.setText(oldval);
                                }
                                if (vale.equals("size_42")) {
                                    size42.setText(oldval);
                                }
                                if (vale.equals("size_44")) {
                                    size44.setText(oldval);
                                }
                                if (vale.equals("size_46")) {
                                    size46.setText(oldval);
                                }
                                if (vale.equals("size_48")) {
                                    size48.setText(oldval);
                                }
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
                        String oldval ="";
                        if(emptystatus.equals("false")){
                            edittext.setText("");
                            edittext.setText(val);
                            emptystatus ="true";
                        }else {
                            oldval = edittext.getText().toString();
                            oldval = oldval + val;
                            edittext.setText(oldval);
                        }
                        if(sizekeylist.size()!=0) {
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
                                if (vale.equals("size_28")) {
                                    String s28 = size28.getText().toString();
                                    if (!s28.equals("")) {
                                        tqty = tqty + Integer.parseInt(s28);
                                    }
                                }
                                if (vale.equals("size_30")) {
                                    String s30 = size30.getText().toString();
                                    if (!s30.equals("")) {
                                        tqty = tqty + Integer.parseInt(s30);
                                    }
                                }
                                if (vale.equals("size_32")) {
                                    String s32 = size32.getText().toString();
                                    if (!s32.equals("")) {
                                        tqty =tqty+ Integer.parseInt(s32);
                                    }
                                }
                                if (vale.equals("size_34")) {
                                    String s34 = size34.getText().toString();
                                    if (!s34.equals("")) {
                                        tqty =tqty + Integer.parseInt(s34);
                                    }
                                }
                                if (vale.equals("size_36")) {
                                    String s36 = size36.getText().toString();
                                    if (!s36.equals("")) {
                                        tqty =tqty + Integer.parseInt(s36);
                                    }
                                }
                                if (vale.equals("size_38")) {
                                    String s38 = size38.getText().toString();
                                    if (!s38.equals("")) {
                                        tqty =tqty + Integer.parseInt(s38);
                                    }
                                }
                                if (vale.equals("size_40")) {
                                    String s40 = size40.getText().toString();
                                    if (!s40.equals("")) {
                                        tqty =tqty + Integer.parseInt(s40);
                                    }
                                }
                                if (vale.equals("size_42")) {
                                    String s42 = size42.getText().toString();
                                    if (!s42.equals("")) {
                                        tqty =tqty + Integer.parseInt(s42);
                                    }
                                }
                                if (vale.equals("size_44")) {
                                    String s44 = size44.getText().toString();
                                    if (!s44.equals("")) {
                                        tqty =tqty + Integer.parseInt(s44);
                                    }
                                }
                                if (vale.equals("size_46")) {
                                    String s46 = size46.getText().toString();
                                    if (!s46.equals("")) {
                                        tqty =tqty + Integer.parseInt(s46);
                                    }
                                }
                                if (vale.equals("size_48")) {
                                    String s48 = size48.getText().toString();
                                    if (!s48.equals("")) {
                                        tqty =tqty + Integer.parseInt(s48);
                                    }
                                }
                                if (vale.equals("size_00")) {
                                    String s00 = qty.getText().toString();
                                    if (!s00.equals("")) {
                                        tqty =tqty + Integer.parseInt(s00);
                                    }
                                }
                            }
                            cqty.setText(String.valueOf(tqty));

                        }
                    }
                }catch (Exception e){

                }
            }
        });
        varientclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainopenstatus.equals("list")){
                    totalpics = 0;
                    orderfromlistview = (RecyclerView) findViewById(R.id.seleprolist);
                    orderfromlistview.setHasFixedSize(true);
                    layoutManager2 = new LinearLayoutManager(getApplicationContext());
                    orderfromlistview.setLayoutManager(layoutManager2);
                    TrouserAdapter trouserAdapter = new TrouserAdapter();
                    orderfromlistview.setAdapter(trouserAdapter);

                    totalitems =  ordprodidlist.size();
                    pertotitems.setText(String.valueOf(totalitems));
                    for(int i=0;i<ordtqty.size();i++){
                        String val  = ordtqty.get(i);
                        if(!val.equals("")){
                            totalpics = totalpics + Integer.parseInt(val);
                        }
                    }
                    pertotpieces.setText(String.valueOf(totalpics));
                }
                varientdialog.dismiss();
            }
        });
        tempsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String validation = "true";
                if (sleevelist.size() != 0) {
                    if (sleeveaddlist.size()==0) {
                        Toast.makeText(getApplicationContext(), "Select one sleeve", Toast.LENGTH_SHORT).show();
                        validation = "false";
                    }
                }
                if (fitlist.size() != 0) {
                    if (fitaddlist.size()==0) {
                        Toast.makeText(getApplicationContext(), "Select one fit", Toast.LENGTH_SHORT).show();
                        validation = "false";
                    }
                }
                if (lenglist.size() != 0) {
                    if (lengthaddlist.size()==0) {
                        Toast.makeText(getApplicationContext(), "Select one length", Toast.LENGTH_SHORT).show();
                        validation = "false";
                    }
                }
                if (colorcodelist.size() != 0) {
                    if (selectedcolor.size() == 0) {
                        Toast.makeText(getApplicationContext(), "Select one colour", Toast.LENGTH_SHORT).show();
                        validation = "false";
                    }
                }
                if (validation.equals("true")) {
                    if(fitaddlist.size()==0){
                        fitaddlist.add("");
                    }
                    if(sleeveaddlist.size()==0){
                        sleeveaddlist.add("");
                    }
                    if(lengthaddlist.size()==0){
                        lengthaddlist.add("");
                    }
                    if (mainopenstatus.equals("list")) {
                        if (mainsizebasecost.equals("true")) {
                            gridmainprosta = "";
                            if (maincheckingstatus.equals("0")) {
                                ordpronamelist.remove(mainpostions);
                                ordprocatglist.remove(mainpostions);
                                ordpromaincatglist.remove(mainpostions);
                                ordprodidlist.remove(mainpostions);
                                ordsize28.remove(mainpostions);
                                ordsize30.remove(mainpostions);
                                ordsize32.remove(mainpostions);
                                ordsize34.remove(mainpostions);
                                ordsize36.remove(mainpostions);
                                ordsize38.remove(mainpostions);
                                ordsize40.remove(mainpostions);
                                ordsize42.remove(mainpostions);
                                ordsize44.remove(mainpostions);
                                ordsize46.remove(mainpostions);
                                ordsize48.remove(mainpostions);
                                ordsizetype.remove(mainpostions);
                                ordqty.remove(mainpostions);
                                ordtqty.remove(mainpostions);
                                ordsleeve.remove(mainpostions);
                                ordcolor.remove(mainpostions);
                                ordcolimage.remove(mainpostions);
                                ordfit.remove(mainpostions);
                                ordleng.remove(mainpostions);
                                ordvarient.remove(mainpostions);
                                maincheckingstatus = "1";
                            }
                            String s28 = size28.getText().toString();
                            String s30 = size30.getText().toString();
                            String s32 = size32.getText().toString();
                            String s34 = size34.getText().toString();
                            String s36 = size36.getText().toString();
                            String s38 = size38.getText().toString();
                            String s40 = size40.getText().toString();
                            String s42 = size42.getText().toString();
                            String s44 = size44.getText().toString();
                            String s46 = size46.getText().toString();
                            String s48 = size48.getText().toString();
                            String sqty = qty.getText().toString();
                            if (!s28.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize28.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize28.getText().toString());
                                                    ordqty.add(s28);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s28.equals("") ? 0 : Integer.parseInt(s28));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize28.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize28.getText().toString());
                                                        ordqty.add(s28);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s28.equals("") ? 0 : Integer.parseInt(s28));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s30.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize30.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize30.getText().toString());
                                                    ordqty.add(s30);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s30.equals("") ? 0 : Integer.parseInt(s30));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize30.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize30.getText().toString());
                                                        ordqty.add(s30);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s30.equals("") ? 0 : Integer.parseInt(s30));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s32.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize32.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize32.getText().toString());
                                                    ordqty.add(s32);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s32.equals("") ? 0 : Integer.parseInt(s32));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize32.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize32.getText().toString());
                                                        ordqty.add(s32);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s32.equals("") ? 0 : Integer.parseInt(s32));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s34.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize34.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize34.getText().toString());
                                                    ordqty.add(s34);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s34.equals("") ? 0 : Integer.parseInt(s34));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize34.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize34.getText().toString());
                                                        ordqty.add(s34);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s34.equals("") ? 0 : Integer.parseInt(s34));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s36.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize36.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize36.getText().toString());
                                                    ordqty.add(s36);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s36.equals("") ? 0 : Integer.parseInt(s36));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize36.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize36.getText().toString());
                                                        ordqty.add(s36);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s36.equals("") ? 0 : Integer.parseInt(s36));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s38.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize38.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize38.getText().toString());
                                                    ordqty.add(s38);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s38.equals("") ? 0 : Integer.parseInt(s38));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize38.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize38.getText().toString());
                                                        ordqty.add(s38);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s38.equals("") ? 0 : Integer.parseInt(s38));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s40.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize40.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize40.getText().toString());
                                                    ordqty.add(s40);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s40.equals("") ? 0 : Integer.parseInt(s40));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize40.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize40.getText().toString());
                                                        ordqty.add(s40);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s40.equals("") ? 0 : Integer.parseInt(s40));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s42.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize42.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize42.getText().toString());
                                                    ordqty.add(s42);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s42.equals("") ? 0 : Integer.parseInt(s42));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize42.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize42.getText().toString());
                                                        ordqty.add(s42);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s42.equals("") ? 0 : Integer.parseInt(s42));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s44.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize44.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize44.getText().toString());
                                                    ordqty.add(s44);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s44.equals("") ? 0 : Integer.parseInt(s44));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize44.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize44.getText().toString());
                                                        ordqty.add(s44);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s44.equals("") ? 0 : Integer.parseInt(s44));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s46.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize46.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize46.getText().toString());
                                                    ordqty.add(s46);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s46.equals("") ? 0 : Integer.parseInt(s46));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize46.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize46.getText().toString());
                                                        ordqty.add(s46);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s46.equals("") ? 0 : Integer.parseInt(s46));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s48.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize48.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize48.getText().toString());
                                                    ordqty.add(s48);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s48.equals("") ? 0 : Integer.parseInt(s48));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize48.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize48.getText().toString());
                                                        ordqty.add(s48);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s48.equals("") ? 0 : Integer.parseInt(s48));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (maincheckingstatus.equals("0")) {
                                ordpronamelist.remove(mainpostions);
                                ordprocatglist.remove(mainpostions);
                                ordpromaincatglist.remove(mainpostions);
                                ordprodidlist.remove(mainpostions);
                                ordsize28.remove(mainpostions);
                                ordsize30.remove(mainpostions);
                                ordsize32.remove(mainpostions);
                                ordsize34.remove(mainpostions);
                                ordsize36.remove(mainpostions);
                                ordsize38.remove(mainpostions);
                                ordsize40.remove(mainpostions);
                                ordsize42.remove(mainpostions);
                                ordsize44.remove(mainpostions);
                                ordsize46.remove(mainpostions);
                                ordsize48.remove(mainpostions);
                                ordsizetype.remove(mainpostions);
                                ordqty.remove(mainpostions);
                                ordtqty.remove(mainpostions);
                                ordsleeve.remove(mainpostions);
                                ordcolor.remove(mainpostions);
                                ordcolimage.remove(mainpostions);
                                ordfit.remove(mainpostions);
                                ordleng.remove(mainpostions);
                                ordvarient.remove(mainpostions);
                                maincheckingstatus = "1";
                            }
                            String s28 = size28.getText().toString();
                            String s30 = size30.getText().toString();
                            String s32 = size32.getText().toString();
                            String s34 = size34.getText().toString();
                            String s36 = size36.getText().toString();
                            String s38 = size38.getText().toString();
                            String s40 = size40.getText().toString();
                            String s42 = size42.getText().toString();
                            String s44 = size44.getText().toString();
                            String s46 = size46.getText().toString();
                            String s48 = size48.getText().toString();
                            String sqty = qty.getText().toString();
                            if (selectedcolor.size() == 0) {
                                gridmainprosta = "";
                                for(int a=0;a<fitaddlist.size();a++) {
                                    for (int b = 0; b < sleeveaddlist.size(); b++) {
                                        for (int c = 0; c < lengthaddlist.size(); c++) {

                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x))  && lengthaddlist.get(c).equals(ordleng.get(x))) {
                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +" Product is Already added", Toast.LENGTH_LONG).show();
                                            gridmainprosta = "true";
                                            break;
                                        }
                                    }
                                }
                                if (gridmainprosta.equals("")) {
                                            ordpronamelist.add(mainproductname);
                                            ordprocatglist.add(mainproductcatge);
                                            ordpromaincatglist.add(mainproductmaincatge);
                                            ordprodidlist.add(mainproductid);
                                            ordsize28.add(s28);
                                            ordsize30.add(s30);
                                            ordsize32.add(s32);
                                            ordsize34.add(s34);
                                            ordsize36.add(s36);
                                            ordsize38.add(s38);
                                            ordsize40.add(s40);
                                            ordsize42.add(s42);
                                            ordsize44.add(s44);
                                            ordsize46.add(s46);
                                            ordsize48.add(s48);
                                            ordsizetype.add("");
                                            ordqty.add(sqty);
                                            ordsleeve.add(sleeveaddlist.get(b));
                                            ordcolor.add("");
                                            ordcolimage.add("");
                                            ordfit.add(fitaddlist.get(a));
                                            ordleng.add(lengthaddlist.get(c));
                                            ordvarient.add("false");
                                            int value = (s28.equals("") ? 0 : Integer.parseInt(s28)) +
                                                    (s30.equals("") ? 0 : Integer.parseInt(s30)) +
                                                    (s32.equals("") ? 0 : Integer.parseInt(s32)) +
                                                    (s34.equals("") ? 0 : Integer.parseInt(s34)) +
                                                    (s36.equals("") ? 0 : Integer.parseInt(s36)) +
                                                    (s38.equals("") ? 0 : Integer.parseInt(s38)) +
                                                    (s40.equals("") ? 0 : Integer.parseInt(s40)) +
                                                    (s42.equals("") ? 0 : Integer.parseInt(s42)) +
                                                    (s44.equals("") ? 0 : Integer.parseInt(s44)) +
                                                    (s46.equals("") ? 0 : Integer.parseInt(s46)) +
                                                    (s48.equals("") ? 0 : Integer.parseInt(s48)) +
                                                    (sqty.equals("") ? 0 : Integer.parseInt(sqty));
                                            ordtqty.add(String.valueOf(value));
                                             }
                                        }
                                    }
                                }
                            } else {
                                gridmainprosta = "";
                                for(int a=0;a<fitaddlist.size();a++) {
                                    for (int b = 0; b < sleeveaddlist.size(); b++) {
                                        for (int c = 0; c < lengthaddlist.size(); c++) {
                                            for (int i = 0; i < selectedcolor.size(); i++) {

                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x))) {
                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                gridmainprosta = "true";
                                                break;
                                            }
                                        }
                                    }
                                    if (gridmainprosta.equals("")) {
                                                ordpronamelist.add(mainproductname);
                                                ordprocatglist.add(mainproductcatge);
                                                ordpromaincatglist.add(mainproductmaincatge);
                                                ordprodidlist.add(mainproductid);
                                                ordsize28.add(s28);
                                                ordsize30.add(s30);
                                                ordsize32.add(s32);
                                                ordsize34.add(s34);
                                                ordsize36.add(s36);
                                                ordsize38.add(s38);
                                                ordsize40.add(s40);
                                                ordsize42.add(s42);
                                                ordsize44.add(s44);
                                                ordsize46.add(s46);
                                                ordsize48.add(s48);
                                                ordsizetype.add("");
                                                ordqty.add(sqty);
                                                ordsleeve.add(sleeveaddlist.get(b));
                                                ordcolor.add(selectedcolor.get(i));
                                                ordcolimage.add(selectedcolimg.get(i));
                                                ordfit.add(fitaddlist.get(a));
                                                ordleng.add(lengthaddlist.get(c));
                                                ordvarient.add("false");
                                                int value = (s28.equals("") ? 0 : Integer.parseInt(s28)) +
                                                        (s30.equals("") ? 0 : Integer.parseInt(s30)) +
                                                        (s32.equals("") ? 0 : Integer.parseInt(s32)) +
                                                        (s34.equals("") ? 0 : Integer.parseInt(s34)) +
                                                        (s36.equals("") ? 0 : Integer.parseInt(s36)) +
                                                        (s38.equals("") ? 0 : Integer.parseInt(s38)) +
                                                        (s40.equals("") ? 0 : Integer.parseInt(s40)) +
                                                        (s42.equals("") ? 0 : Integer.parseInt(s42)) +
                                                        (s44.equals("") ? 0 : Integer.parseInt(s44)) +
                                                        (s46.equals("") ? 0 : Integer.parseInt(s46)) +
                                                        (s48.equals("") ? 0 : Integer.parseInt(s48)) +
                                                        (sqty.equals("") ? 0 : Integer.parseInt(sqty));
                                                ordtqty.add(String.valueOf(value));
                                              }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (mainopenstatus.equals("grid") || mainopenstatus.equals("tile")) {
                        if (mainsizebasecost.equals("true")) {
                            gridmainprosta = "";
                            String s28 = size28.getText().toString();
                            String s30 = size30.getText().toString();
                            String s32 = size32.getText().toString();
                            String s34 = size34.getText().toString();
                            String s36 = size36.getText().toString();
                            String s38 = size38.getText().toString();
                            String s40 = size40.getText().toString();
                            String s42 = size42.getText().toString();
                            String s44 = size44.getText().toString();
                            String s46 = size46.getText().toString();
                            String s48 = size48.getText().toString();
                            String sqty = qty.getText().toString();
                            if (!s28.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize28.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize28.getText().toString());
                                                    ordqty.add(s28);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s28.equals("") ? 0 : Integer.parseInt(s28));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize28.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize28.getText().toString());
                                                        ordqty.add(s28);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s28.equals("") ? 0 : Integer.parseInt(s28));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s30.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize30.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize30.getText().toString());
                                                    ordqty.add(s30);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s30.equals("") ? 0 : Integer.parseInt(s30));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize30.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize30.getText().toString());
                                                        ordqty.add(s30);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s30.equals("") ? 0 : Integer.parseInt(s30));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s32.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {

                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize32.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize32.getText().toString());
                                                    ordqty.add(s32);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s32.equals("") ? 0 : Integer.parseInt(s32));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize32.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize32.getText().toString());
                                                        ordqty.add(s32);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s32.equals("") ? 0 : Integer.parseInt(s32));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s34.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {

                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize34.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize34.getText().toString());
                                                    ordqty.add(s34);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s34.equals("") ? 0 : Integer.parseInt(s34));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize34.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize34.getText().toString());
                                                        ordqty.add(s34);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s34.equals("") ? 0 : Integer.parseInt(s34));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s36.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize36.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize36.getText().toString());
                                                    ordqty.add(s36);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s36.equals("") ? 0 : Integer.parseInt(s36));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize36.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize36.getText().toString());
                                                        ordqty.add(s36);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s36.equals("") ? 0 : Integer.parseInt(s36));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s38.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize38.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize38.getText().toString());
                                                    ordqty.add(s38);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s38.equals("") ? 0 : Integer.parseInt(s38));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize38.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize38.getText().toString());
                                                        ordqty.add(s38);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s38.equals("") ? 0 : Integer.parseInt(s38));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s40.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize40.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize40.getText().toString());
                                                    ordqty.add(s40);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s40.equals("") ? 0 : Integer.parseInt(s40));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize40.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize40.getText().toString());
                                                        ordqty.add(s40);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s40.equals("") ? 0 : Integer.parseInt(s40));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s42.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize42.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize42.getText().toString());
                                                    ordqty.add(s42);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s42.equals("") ? 0 : Integer.parseInt(s42));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize42.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize42.getText().toString());
                                                        ordqty.add(s42);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s42.equals("") ? 0 : Integer.parseInt(s42));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s44.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize44.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize44.getText().toString());
                                                    ordqty.add(s44);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s44.equals("") ? 0 : Integer.parseInt(s44));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize44.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize44.getText().toString());
                                                        ordqty.add(s44);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s44.equals("") ? 0 : Integer.parseInt(s44));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s46.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize46.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize46.getText().toString());
                                                    ordqty.add(s46);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s46.equals("") ? 0 : Integer.parseInt(s46));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize46.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize46.getText().toString());
                                                        ordqty.add(s46);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s46.equals("") ? 0 : Integer.parseInt(s46));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!s48.equals("")) {
                                if (selectedcolor.size() == 0) {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && tsize48.getText().toString().equals(ordsizetype.get(x))) {
                                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) + ordsizetype.get(x)+" Product is Already added", Toast.LENGTH_LONG).show();
                                                            gridmainprosta = "true";
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (gridmainprosta.equals("")) {
                                                    ordpronamelist.add(mainproductname);
                                                    ordprocatglist.add(mainproductcatge);
                                                    ordpromaincatglist.add(mainproductmaincatge);
                                                    ordprodidlist.add(mainproductid);
                                                    ordsize28.add("");
                                                    ordsize30.add("");
                                                    ordsize32.add("");
                                                    ordsize34.add("");
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsize48.add("");
                                                    ordsizetype.add(tsize48.getText().toString());
                                                    ordqty.add(s48);
                                                    ordsleeve.add(sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c));
                                                    ordvarient.add("false");
                                                    int value = (s48.equals("") ? 0 : Integer.parseInt(s48));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for(int a=0;a<fitaddlist.size();a++) {
                                        for (int b = 0; b < sleeveaddlist.size(); b++) {
                                            for (int c = 0; c < lengthaddlist.size(); c++) {
                                                for (int i = 0; i < selectedcolor.size(); i++) {
                                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                                            if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x)) && tsize48.getText().toString().equals(ordsizetype.get(x))) {
                                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) + ordsizetype.get(x) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                                gridmainprosta = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (gridmainprosta.equals("")) {
                                                        ordpronamelist.add(mainproductname);
                                                        ordprocatglist.add(mainproductcatge);
                                                        ordpromaincatglist.add(mainproductmaincatge);
                                                        ordprodidlist.add(mainproductid);
                                                        ordsize28.add("");
                                                        ordsize30.add("");
                                                        ordsize32.add("");
                                                        ordsize34.add("");
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsize48.add("");
                                                        ordsizetype.add(tsize48.getText().toString());
                                                        ordqty.add(s48);
                                                        ordsleeve.add(sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c));
                                                        ordvarient.add("false");
                                                        int value = (s48.equals("") ? 0 : Integer.parseInt(s48));
                                                        ordtqty.add(String.valueOf(value));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            String s28 = size28.getText().toString();
                            String s30 = size30.getText().toString();
                            String s32 = size32.getText().toString();
                            String s34 = size34.getText().toString();
                            String s36 = size36.getText().toString();
                            String s38 = size38.getText().toString();
                            String s40 = size40.getText().toString();
                            String s42 = size42.getText().toString();
                            String s44 = size44.getText().toString();
                            String s46 = size46.getText().toString();
                            String s48 = size48.getText().toString();
                            String sqty = qty.getText().toString();
                            if (selectedcolor.size() == 0) {
                                gridmainprosta = "";
                                for(int a=0;a<fitaddlist.size();a++) {
                                    for (int b = 0; b < sleeveaddlist.size(); b++) {
                                        for (int c = 0; c < lengthaddlist.size(); c++) {
                                for (int x = 0; x < ordprodidlist.size(); x++) {
                                    if (mainproductid.equals(ordprodidlist.get(x))) {
                                        if (fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x))) {
                                            Toast.makeText(getApplicationContext(), fitaddlist.get(a) +sleeveaddlist.get(b)+lengthaddlist.get(c)+ " Product is Already added", Toast.LENGTH_LONG).show();
                                            gridmainprosta = "true";
                                            break;
                                        }
                                    }
                                }
                                if (gridmainprosta.equals("")) {
                                            ordpronamelist.add(mainproductname);
                                            ordprocatglist.add(mainproductcatge);
                                            ordpromaincatglist.add(mainproductmaincatge);
                                            ordprodidlist.add(mainproductid);
                                            ordsize28.add(s28);
                                            ordsize30.add(s30);
                                            ordsize32.add(s32);
                                            ordsize34.add(s34);
                                            ordsize36.add(s36);
                                            ordsize38.add(s38);
                                            ordsize40.add(s40);
                                            ordsize42.add(s42);
                                            ordsize44.add(s44);
                                            ordsize46.add(s46);
                                            ordsize48.add(s48);
                                            ordsizetype.add("");
                                            ordqty.add(sqty);
                                            ordsleeve.add(sleeveaddlist.get(b));
                                            ordcolor.add("");
                                            ordcolimage.add("");
                                            ordfit.add(fitaddlist.get(a));
                                            ordleng.add(lengthaddlist.get(c));
                                            ordvarient.add("false");
                                            int value = (s28.equals("") ? 0 : Integer.parseInt(s28)) +
                                                    (s30.equals("") ? 0 : Integer.parseInt(s30)) +
                                                    (s32.equals("") ? 0 : Integer.parseInt(s32)) +
                                                    (s34.equals("") ? 0 : Integer.parseInt(s34)) +
                                                    (s36.equals("") ? 0 : Integer.parseInt(s36)) +
                                                    (s38.equals("") ? 0 : Integer.parseInt(s38)) +
                                                    (s40.equals("") ? 0 : Integer.parseInt(s40)) +
                                                    (s42.equals("") ? 0 : Integer.parseInt(s42)) +
                                                    (s44.equals("") ? 0 : Integer.parseInt(s44)) +
                                                    (s46.equals("") ? 0 : Integer.parseInt(s46)) +
                                                    (s48.equals("") ? 0 : Integer.parseInt(s48)) +
                                                    (sqty.equals("") ? 0 : Integer.parseInt(sqty));
                                            ordtqty.add(String.valueOf(value));
                                          }
                                        }
                                    }
                                }
                            } else {
                                gridmainprosta = "";
                                for(int a=0;a<fitaddlist.size();a++) {
                                    for (int b = 0; b < sleeveaddlist.size(); b++) {
                                        for (int c = 0; c < lengthaddlist.size(); c++) {
                                            for (int i = 0; i < selectedcolor.size(); i++) {
                                    for (int x = 0; x < ordprodidlist.size(); x++) {
                                        if (mainproductid.equals(ordprodidlist.get(x))) {
                                            if ( fitaddlist.get(a).equals(ordfit.get(x)) && sleeveaddlist.get(b).equals(ordsleeve.get(x)) && lengthaddlist.get(c).equals(ordleng.get(x)) && selectedcolor.get(i).equals(ordcolor.get(x))) {
                                                Toast.makeText(getApplicationContext(), fitaddlist.get(a) + sleeveaddlist.get(b) + lengthaddlist.get(c) +  selectedcolor.get(i) +" Product is Already added", Toast.LENGTH_LONG).show();
                                                gridmainprosta = "true";
                                                break;
                                            }
                                        }
                                    }
                                    if (gridmainprosta.equals("")) {
                                                ordpronamelist.add(mainproductname);
                                                ordprocatglist.add(mainproductcatge);
                                                ordpromaincatglist.add(mainproductmaincatge);
                                                ordprodidlist.add(mainproductid);
                                                ordsize28.add(s28);
                                                ordsize30.add(s30);
                                                ordsize32.add(s32);
                                                ordsize34.add(s34);
                                                ordsize36.add(s36);
                                                ordsize38.add(s38);
                                                ordsize40.add(s40);
                                                ordsize42.add(s42);
                                                ordsize44.add(s44);
                                                ordsize46.add(s46);
                                                ordsize48.add(s48);
                                                ordsizetype.add("");
                                                ordqty.add(sqty);
                                                ordsleeve.add(sleeveaddlist.get(b));
                                                ordcolor.add(selectedcolor.get(i));
                                                ordcolimage.add(selectedcolimg.get(i));
                                                ordfit.add(fitaddlist.get(a));
                                                ordleng.add(lengthaddlist.get(c));
                                                ordvarient.add("false");
                                                int value = (s28.equals("") ? 0 : Integer.parseInt(s28)) +
                                                        (s30.equals("") ? 0 : Integer.parseInt(s30)) +
                                                        (s32.equals("") ? 0 : Integer.parseInt(s32)) +
                                                        (s34.equals("") ? 0 : Integer.parseInt(s34)) +
                                                        (s36.equals("") ? 0 : Integer.parseInt(s36)) +
                                                        (s38.equals("") ? 0 : Integer.parseInt(s38)) +
                                                        (s40.equals("") ? 0 : Integer.parseInt(s40)) +
                                                        (s42.equals("") ? 0 : Integer.parseInt(s42)) +
                                                        (s44.equals("") ? 0 : Integer.parseInt(s44)) +
                                                        (s46.equals("") ? 0 : Integer.parseInt(s46)) +
                                                        (s48.equals("") ? 0 : Integer.parseInt(s48)) +
                                                        (sqty.equals("") ? 0 : Integer.parseInt(sqty));
                                                ordtqty.add(String.valueOf(value));
                                              }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
      if (gridmainprosta.equals("")) {

                fit1.setTextColor(getResources().getColor(R.color.colorAccent));
                fit2.setTextColor(getResources().getColor(R.color.colorAccent));
                fit3.setTextColor(getResources().getColor(R.color.colorAccent));
                fit4.setTextColor(getResources().getColor(R.color.colorAccent));
                fit5.setTextColor(getResources().getColor(R.color.colorAccent));
                slev1.setTextColor(getResources().getColor(R.color.colorAccent));
                slev2.setTextColor(getResources().getColor(R.color.colorAccent));
                slev3.setTextColor(getResources().getColor(R.color.colorAccent));
                slev4.setTextColor(getResources().getColor(R.color.colorAccent));
                slev5.setTextColor(getResources().getColor(R.color.colorAccent));
                leng1.setTextColor(getResources().getColor(R.color.colorAccent));
                leng2.setTextColor(getResources().getColor(R.color.colorAccent));
                leng3.setTextColor(getResources().getColor(R.color.colorAccent));
                leng4.setTextColor(getResources().getColor(R.color.colorAccent));
                leng5.setTextColor(getResources().getColor(R.color.colorAccent));
                fit1.setBackgroundResource(R.drawable.buttonstyle_background);
          fit2.setBackgroundResource(R.drawable.buttonstyle_background);
          fit3.setBackgroundResource(R.drawable.buttonstyle_background);
          fit4.setBackgroundResource(R.drawable.buttonstyle_background);
          fit5.setBackgroundResource(R.drawable.buttonstyle_background);
          slev1.setBackgroundResource(R.drawable.buttonstyle_background);
          slev2.setBackgroundResource(R.drawable.buttonstyle_background);
          slev3.setBackgroundResource(R.drawable.buttonstyle_background);
          slev4.setBackgroundResource(R.drawable.buttonstyle_background);
          slev5.setBackgroundResource(R.drawable.buttonstyle_background);
          leng1.setBackgroundResource(R.drawable.buttonstyle_background);
          leng2.setBackgroundResource(R.drawable.buttonstyle_background);
          leng3.setBackgroundResource(R.drawable.buttonstyle_background);
          leng4.setBackgroundResource(R.drawable.buttonstyle_background);
          leng5.setBackgroundResource(R.drawable.buttonstyle_background);

                    selectedcolor = new ArrayList<>();
                    selectedcolimg = new ArrayList<>();
                    fitaddlist = new ArrayList<>();
                    sleeveaddlist = new ArrayList<>();
                    lengthaddlist = new ArrayList<>();

                    size28.setText("");
                size30.setText("");
                size32.setText("");
                size34.setText("");
                size36.setText("");
                size38.setText("");
                size40.setText("");
                size42.setText("");
                size44.setText("");
                size46.setText("");
                size48.setText("");
                qty.setText("");
                    cqty.setText("");
                    cqtystatus = "";
                    size28.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size30.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size32.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size34.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size48.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                coloraddlist = new ArrayList<>();
                coloraddlist.addAll(fullcoloraddlist);


                        colorvarientView = (RecyclerView) varientdialog.findViewById(R.id.colorvarientView);
                        colorvarientView.setHasFixedSize(true);
                        layoutManager4 = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.VERTICAL, false);
                        colorvarientView.setLayoutManager(layoutManager4);
                        VarientAdapter varientAdapter = new VarientAdapter();
                        colorvarientView.setAdapter(varientAdapter);

                varientdialog.show();

                    Toast.makeText(getApplicationContext(), "Product add successful", Toast.LENGTH_LONG).show();
                }
            }
            }
        });

    }
    class TileimageAdapter extends RecyclerView.Adapter<TileimageAdapter.ViewHolder>  {

        ImageView varientimage;
        private TileimageAdapter() {

        }

        @Override
        public TileimageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tileimage_dialog, parent, false);

            return new TileimageAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final TileimageAdapter.ViewHolder holder, final int position) {
            holder.setIsRecyclable(false);


            if(!imagevarientlist.get(position).equals("")){


                Glide.with(getApplicationContext())
                        .load(Headers.getUrlWithHeaders(imagevarientlist.get(position)))
                        .into(varientimage);

            }


        }



        @Override
        public int getItemCount() {
            return imagevarientlist.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
                varientimage = (ImageView) itemView.findViewById(R.id.varientimage);


            }
        }
    }

    class VarientAdapter extends RecyclerView.Adapter<VarientAdapter.ViewHolder>  {

        ImageView colorimage;
        private VarientAdapter() {

        }

        @Override
        public VarientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cololistvarient_dialog, parent, false);

            return new VarientAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final VarientAdapter.ViewHolder holder, final int position) {
            holder.setIsRecyclable(false);

            holder.colorname.setText(coloridlist.get(position));
            holder.coloselcimage .setTag(position);
            holder.colorname.setTextColor(Color.parseColor(coloraddlist.get(position)));

            if(!colorcodelist.get(position).equals("")){


                Glide.with(getApplicationContext())
                        .load(Headers.getUrlWithHeaders(colorcodelist.get(position)))
                        .into(colorimage);

            }

            holder.coloselcimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) holder.coloselcimage.getTag();
                    if(coloraddlist.get(pos).equals("#AA4098")){
                        coloraddlist.remove(pos);
                        coloraddlist.add(pos,"#FFFFFF");
                        selectedcolor.add(coloridlist.get(pos));
                        selectedcolimg.add(colorcodelist.get(pos));
                        holder.colorname.setTextColor(Color.parseColor(coloraddlist.get(position)));
                        holder.colorname.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }else if(coloraddlist.get(pos).equals("#FFFFFF")){
                        coloraddlist.remove(pos);
                        coloraddlist.add(pos,"#AA4098");
                        holder.colorname.setTextColor(Color.parseColor(coloraddlist.get(position)));
                        holder.colorname.setBackgroundResource(R.drawable.buttonstyle_background);
                        for(int a=0;a<selectedcolor.size();a++){
                            String val = selectedcolor.get(a);
                            if(coloridlist.get(pos).equals(val)){
                                selectedcolor.remove(a);
                                selectedcolimg.remove(a);
                            }
                        }
                    }
                }
            });

        }



        @Override
        public int getItemCount() {
            return coloridlist.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView colorname;

            CardView coloselcimage;
            public ViewHolder(View itemView) {
                super(itemView);
                colorimage = (ImageView) itemView.findViewById(R.id.colorimage);
                colorname = (TextView) itemView.findViewById(R.id.colorcode);
                coloselcimage = (CardView) itemView.findViewById(R.id.cvPlaces);

            }
        }
    }


    public void registerEditText(int resid) {
        // Find the EditText 'res_id'
        edittext = (TextView) varientdialog.findViewById(resid);

    }
     static class Headers {

          static GlideUrl getUrlWithHeaders(String url){
            return new GlideUrl(url, new LazyHeaders.Builder()
                    .addHeader("Cookie", "session_id=" + Session_ID)
                    .build());
        }
    }
    class productsave extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ApparelsOrderDetails.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonParams = new JSONObject();
            JSONObject params1 = new JSONObject();
            JSONObject params2 = new JSONObject();
            JSONArray params3 = new JSONArray();
            JSONObject finalparams= new JSONObject();

            HttpClient client = new DefaultHttpClient();

            // Prepare a request object
            HttpPost post = new HttpPost(GetURL.getproductlist());
            post.setHeader("Cookie","session_id=" + Session_ID);
            post.setHeader("Content-type", "application/json");
            try {
                params1.put("model", "apparel.order");
                params1.put("method", "create");
                params1.put("kwargs", params2);
                params1.put("args",params3);
                params3.put(finalparams);
                finalparams.put("customer_details",cusID);
                finalparams.put("order_date",odate);
                finalparams.put("top_dispatch_date",ddate);
                finalparams.put("bottom_dispatch_date",ddate);
                finalparams.put("submitted_by",User_ID);
                finalparams.put("top_comments",comment);
                finalparams.put("bottom_comments",comment);
                finalparams.put("agent_lat",latitude);
                finalparams.put("agent_long",longitude);
                finalparams.put("top_lines",toporderlist);
                finalparams.put("bottom_lines",bottomorderlist);


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
                    String finalresult = data.getString("result");
                    if(!finalresult.equals("")){
                        Toast.makeText(getApplicationContext(), "Order Saved Successfully ", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(), ApparelsOrderList.class);
                        startActivity(i);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Order Sent Failed", Toast.LENGTH_LONG).show();
                }

            }

        }

    }
    public void getproductdatafromdb() {

        alldbproduct = databaseHelper.getProductsData();

        for(int i=0; i <  alldbproduct.size();i++) {
            JSONObject list = alldbproduct.get(i);
            try {
                String productname = list.get("name").toString();
                String productid = list.get("id").toString();
                String producatge = list.get("form_types").toString();
                String produsubcatge = list.get("product_categ").toString();
                String prodimage = list.get("image_url").toString();
                String prodwsp = list.get("wsp").toString();
                String prodmrp = list.get("mrp").toString();
                String colorbased = list.get("color_shade").toString();
                String fitbased = list.get("fit_based").toString();
                String sleeveBased = list.get("sleeve_based").toString();
                String lengthBased = list.get("length_based").toString();

                if (producatge.equals("Apparel")) {
                    apparelprolist.add(productname);
                    productnamelist.add(productname);
                    fullproductlist.add(productname);
                    productsubcatge.add(produsubcatge);
                    fullproductsubcatge.add(produsubcatge);
                    productidlist.add(productid);
                    fullproductidlist.add(productid);
                    wsplist.add(prodwsp);
                    mrplist.add(prodmrp);
                    fullwsplist.add(prodwsp);
                    fullmrplist.add(prodmrp);
                    produimagelist.add(GetURL.getServerip() + prodimage);
                    fullproduimagelist.add(GetURL.getServerip() + prodimage);
                    procololist.add("#000000");
                    fullprocololsit.add("#000000");
                    productlist.add(list);
                    if (colorbased.equals("true") || fitbased.equals("true") || sleeveBased.equals("true") || lengthBased.equals("true")) {
                        multicolor.add("true");
                        fullmulticolor.add("true");
                    } else {
                        multicolor.add("false");
                        fullmulticolor.add("false");
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        itemChecked = new boolean[productidlist.size()];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (ApparelsOrderDetails.this, android.R.layout.select_dialog_item, apparelprolist);
        addpro.setThreshold(2);
        addpro.setAdapter(adapter);

        if (status.equals("true")) {
            orderfromlistview = (RecyclerView) findViewById(R.id.seleprolist);
            orderfromlistview.setHasFixedSize(true);
            layoutManager2 = new LinearLayoutManager(getApplicationContext());
            orderfromlistview.setLayoutManager(layoutManager2);
            TrouserAdapter trouserAdapter = new TrouserAdapter();
            orderfromlistview.setAdapter(trouserAdapter);

            totalitems = ordprodidlist.size();
            pertotitems.setText(String.valueOf(totalitems));

            for (int i = 0; i < ordtqty.size(); i++) {
                String val = ordtqty.get(i);
                if (!val.equals("")) {
                    totalpics = totalpics + Integer.parseInt(val);
                }
            }
            pertotpieces.setText(String.valueOf(totalpics));
        }


    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            Double latitude=mylocation.getLatitude();
            Double longitude=mylocation.getLongitude();

            //Or Do whatever you want with your location
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Do whatever you need
        //You can display a message here
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //You can display a message here
    }

    private void getMyLocation(){
        if(googleApiClient!=null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(ApparelsOrderDetails.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation =                     LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(ApparelsOrderDetails.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(ApparelsOrderDetails.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }

    private void checkPermissions(){
        int permissionLocation = ContextCompat.checkSelfPermission(ApparelsOrderDetails.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }else{
            getMyLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(ApparelsOrderDetails.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }
}
