package com.aspirantlab.salesagent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
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

import com.aspirantlab.salesagent.Services.GetURL;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

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


public class TextilesEditOrderDetails  extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout relativeLayout,saveproduct,tempsave,varientclose,agentconfrim,submit;
    String MY_PREFS_NAME,User_ID,Order_ID;
    static String Session_ID;
    ArrayList <JSONObject>productlist;
    ArrayList<String> ordwsplist;
    ArrayList<String> ordmrplist;
    ArrayList<String> selectedproidlist,ordpronamelist,ordprocatglist,ordpromaincatglist,ordprodidlist,ordvarient,ordsize36,ordsize38,ordsize40,ordsize42,ordsize44,ordsize46,ordsizetype,ordqty,ordsleeve,ordcolor,ordcolimage,ordfit,ordleng,ordkeysize;
    ArrayList<String> colorlist;
    ArrayList<JSONObject> orderfrmainlist;
    ArrayList<String> productnamelist,produimagelist,productidlist,apparelprolist,wsplist,mrplist,multicolor,productsubcatge;
    ArrayList<String> fullproductlist,fullproduimagelist,fullprocololsit,fullproductidlist,fullwsplist,fullmrplist,fullmulticolor,fullproductsubcatge;
    JSONArray toporderlist,bottomorderlist;
    ArrayList<String> ordtqty,procololist;
    ArrayList<JSONObject> hscolor;
    ArrayList<JSONObject> fscolor;
    RecyclerView dialoguelistview,orderfromlistview,colorlistview,colorvarientView,tilevarimageview;
    LinearLayoutManager layoutManager2,layoutManager3;
    GridLayoutManager layoutManager1,layoutManager4,layoutManager5;
    ArrayAdapter adapter;
    Dialog dialog,colordialog,varientdialog;
    Button tops,bottoms,colorclose;
    boolean[] itemChecked,colorChecked,varientchecked;
    String size = "0",savestatus="false",viewstatus="gridview",tileproaddsta="",topbotstatus="",singleproaddsta="",multiproaddsta="",gridproaddsta="",gridmainprosta="", editstatus = "false",status ="",cusID,custname,ordernum,odate,ddate,comment,dproid,dpname,dpcatge,dpwsp,dpmrp,sleevestatus;
    int totalitems = 0,totalpics = 0,colorboxpos;
    ImageView selectproduct,mainviews,gridview,listview,filter,editproduct;
    TableLayout trousertable;
    TextView pertotitems,pertotpieces,hhand,fhand;
    CheckBox hscheckbox,fscheckbox,hsvarientbox,fsvarientbox;
    AutoCompleteTextView addpro;
    ArrayList<String>imagevarientlist,sleevelist,colorcodelist,coloridlist,fitlist,lenglist,sizelist,sizekeylist,coloraddlist,fullcoloraddlist,selectedcolor,selectedcolimg;
    Button fit1,fit2,fit3,fit4,fit5,slev1,slev2,slev3,slev4,slev5,leng1,leng2,leng3,leng4,leng5;
    TextView tsize36,tsize38,tsize40,tsize42,tsize44,tsize46,tqty;
    TextView wsp36,wsp38,wsp40,wsp42,wsp44,wsp46,wqty;
    TextView mrp36,mrp38,mrp40,mrp42,mrp44,mrp46,mqty;
    TextView size36,size38,size40,size42,size44,size46,qty,cqty;
    String acfstatus="false",cqtystatus="",minustatus="false",mainproductid,mainproductname,mainproductcatge,mainproductmaincatge,mainopenstatus,maincheckingstatus,mainsizebasecost;
    int mainpostions;
    ArrayList<String>editpidlist,editidlist,deletidlist,deletpcatglist,fitaddlist,sleeveaddlist,lengthaddlist;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
    TextView edittext,cusname,ordernumber;
    TableLayout addsinglepro;
    int toitem ;
    String bvalue ="";
    ImageView minus,clear;
    ArrayList<String>catgefilterlist,brandfilterlist;
    HashSet<String> hashcatge,hashbrand;
    Spinner selectcatge,selectbrand;
    AlertDialog.Builder builder;
    EditText inputSearch;
    String brandvalue = "";
    String catgevalue = "", emptystatus ="false";
    ActionBar ab;
    JSONArray pricelist;
    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.dhotieditorder_fragment, null, false);
        drawer.addView(contentView, 0);

        relativeLayout = (RelativeLayout)findViewById(R.id.mainfile);
        relativeLayout.setVisibility(View.GONE);
        selectproduct = (ImageView)findViewById(R.id.selectproductimage);
        editproduct = (ImageView)findViewById(R.id.editproduct);
        saveproduct = (RelativeLayout) findViewById(R.id.saveproduct);
        agentconfrim = (RelativeLayout) findViewById(R.id.agentconfrim);
        pertotitems = (TextView)findViewById(R.id.pertotitems);
        pertotpieces = (TextView)findViewById(R.id.pertotpieces);
        addpro = (AutoCompleteTextView)findViewById(R.id.proauto);
        addsinglepro = (TableLayout)findViewById(R.id.table3);
        cusname = (TextView)findViewById(R.id.cusname);
        saveproduct.setEnabled(false);

        ab = getSupportActionBar();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        User_ID = prefs.getString("userid", null);
        Session_ID = prefs.getString("sessionid", null);



            new productlist().execute();


        productlist = new ArrayList<>();
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

        editpidlist = new ArrayList<>();
        editidlist = new ArrayList<>();
        deletidlist = new ArrayList<>();
        deletpcatglist = new ArrayList<>();

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
        ordsize36 = new ArrayList<>();
        ordsize38 = new ArrayList<>();
        ordsize40 = new ArrayList<>();
        ordsize42 = new ArrayList<>();
        ordsize44 = new ArrayList<>();
        ordsize46 = new ArrayList<>();
        ordsizetype =new ArrayList<>();
        ordqty = new ArrayList<>();
        ordtqty = new ArrayList<>();
        ordsleeve = new ArrayList<>();
        ordcolor = new ArrayList<>();
        ordcolimage = new ArrayList<>();
        ordfit = new ArrayList<>();
        ordleng = new ArrayList<>();
        ordkeysize = new ArrayList<>();
        ordvarient = new ArrayList<>();

        selectproduct.setVisibility(View.INVISIBLE);
        addsinglepro.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Order_ID = bundle.getString("dhotioid");
            String status = bundle.getString("status");
            if(status.equals("agent_confirmed")){
                saveproduct.setVisibility(View.GONE);
                editproduct.setVisibility(View.GONE);
                agentconfrim.setVisibility(View.GONE);
            }
        }

        agentconfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bvalue.equals("")){
                    new Agnetconfrim().execute();
                }else{
                    Toast.makeText(getApplicationContext(), "Can't submit this Product", Toast.LENGTH_LONG).show();
                }

            }
        });

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
                                        ordsize36.add("");
                                        ordsize38.add("");
                                        ordsize40.add("");
                                        ordsize42.add("");
                                        ordsize44.add("");
                                        ordsize46.add("");
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
                                ordsize36.add("");
                                ordsize38.add("");
                                ordsize40.add("");
                                ordsize42.add("");
                                ordsize44.add("");
                                ordsize46.add("");
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

        editproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectproduct.setVisibility(View.VISIBLE);
                editproduct.setVisibility(View.GONE);
                addsinglepro.setVisibility(View.VISIBLE);
                saveproduct.setEnabled(true);
                bvalue = "Update";
                orderfromlistview = (RecyclerView) findViewById(R.id.seleprolist);
                orderfromlistview.setHasFixedSize(true);
                layoutManager2 = new LinearLayoutManager(getApplicationContext());
                orderfromlistview.setLayoutManager(layoutManager2);
                TrouserAdapter trouserAdapter = new TrouserAdapter();
                orderfromlistview.setAdapter(trouserAdapter);
                totalitems = ordprodidlist.size();
                pertotitems.setText(String.valueOf(totalitems));
                pertotpieces.setText(String.valueOf(toitem));

            }
        });
        saveproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(ordprodidlist.size()!=0) {
                        if (savestatus.equals("false")) {
                            for (int i = 0; i < ordprodidlist.size(); i++) {
                                editstatus = "false";
                                String id = ordprodidlist.get(i);
                                for (int k = 0; k < editpidlist.size(); k++) {
                                    if (id.equals(editpidlist.get(k))) {
                                        editstatus = "true";
                                        String eid = editidlist.get(k);
                                        String mcatge = ordpromaincatglist.get(i);
                                        String pid = ordprodidlist.get(i);
                                        String size36 = ordsize36.get(i);
                                        String size38 = ordsize38.get(i);
                                        String size40 = ordsize40.get(i);
                                        String size42 = ordsize42.get(i);
                                        String size44 = ordsize44.get(i);
                                        String size46 = ordsize46.get(i);
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
                                                    jsonObject.put("size_36", size36);
                                                    jsonObject.put("size_38", size38);
                                                    jsonObject.put("size_40", size40);
                                                    jsonObject.put("size_42", size42);
                                                    jsonObject.put("size_44", size44);
                                                    jsonObject.put("size_46", size46);
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
                                                jsonArray.put(1);
                                                jsonArray.put(Integer.parseInt(eid));
                                                jsonArray.put(jsonObject);
                                                toporderlist.put(jsonArray);
                                            } else if (mcatge.equals("Bottoms")) {
                                                JSONArray jsonArray = new JSONArray();
                                                JSONObject jsonObject = new JSONObject();
                                                try {
                                                    jsonObject.put("product_id", pid);
                                                    jsonObject.put("sleeve_type", sleeve);
                                                    jsonObject.put("size_38", size38);
                                                    jsonObject.put("size_40", size40);
                                                    jsonObject.put("size_42", size42);
                                                    jsonObject.put("size_44", size44);
                                                    jsonObject.put("size_46", size46);
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
                                                jsonArray.put(1);
                                                jsonArray.put(Integer.parseInt(eid));
                                                jsonArray.put(jsonObject);
                                                bottomorderlist.put(jsonArray);
                                            }
                                            editpidlist.remove(k);
                                            editidlist.remove(k);
                                            break;
                                        }
                                    }
                                }
                                if (editstatus.equals("false")) {
                                    String mcatge = ordpromaincatglist.get(i);
                                    String pid = ordprodidlist.get(i);
                                    String size36 = ordsize36.get(i);
                                    String size38 = ordsize38.get(i);
                                    String size40 = ordsize40.get(i);
                                    String size42 = ordsize42.get(i);
                                    String size44 = ordsize44.get(i);
                                    String size46 = ordsize46.get(i);
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
                                                jsonObject.put("size_36", size36);
                                                jsonObject.put("size_38", size38);
                                                jsonObject.put("size_40", size40);
                                                jsonObject.put("size_42", size42);
                                                jsonObject.put("size_44", size44);
                                                jsonObject.put("size_46", size46);
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
                                                jsonObject.put("size_36", size36);
                                                jsonObject.put("size_38", size38);
                                                jsonObject.put("size_40", size40);
                                                jsonObject.put("size_42", size42);
                                                jsonObject.put("size_44", size44);
                                                jsonObject.put("size_46", size46);
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
                            if (deletidlist.size() != 0) {
                                for (int a = 0; a < deletidlist.size(); a++) {
                                    String did = deletidlist.get(a);
                                    String catg = deletpcatglist.get(a);

                                    if (catg.equals("Tops")) {
                                        JSONArray jsonArray = new JSONArray();
                                        jsonArray.put(2);
                                        jsonArray.put(Integer.parseInt(did));
                                        jsonArray.put("False");
                                        toporderlist.put(jsonArray);
                                    } else if (catg.equals("Bottoms")) {
                                        JSONArray jsonArray = new JSONArray();
                                        jsonArray.put(2);
                                        jsonArray.put(Integer.parseInt(did));
                                        jsonArray.put("False");
                                        bottomorderlist.put(jsonArray);
                                    }
                                    break;
                                }
                            }
                        }
                        if(toporderlist.length()!=0||bottomorderlist.length()!=0){
                            savestatus = "true";
                            new productsave().execute();
                        }else{
                            savestatus = "false";
                            Toast.makeText(getApplicationContext(), "Empty value can't be save", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Empty order can't be update", Toast.LENGTH_LONG).show();
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

                dialog = new Dialog(TextilesEditOrderDetails.this,android.R.style.Theme_Light);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dhotiorderform_dialog);

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
                submit = (RelativeLayout) dialog.findViewById(R.id.submit);
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
                                        String ordwsp = plist.get("wsp").toString();
                                        String ordmrp = plist.get("mrp").toString();
                                        String colorbased= plist.get("color_shade").toString();
                                        String fitbased= plist.get("fit_based").toString();
                                        String sleeveBased= plist.get("sleeve_based").toString();
                                        String lengthBased= plist.get("length_based").toString();
                                        ordpronamelist.add(ordplist);
                                        ordprocatglist.add(ordpclist);
                                        ordpromaincatglist.add(ordmpclist);
                                        ordprodidlist.add(pid);
                                        ordsize36.add("");
                                        ordsize38.add("");
                                        ordsize40.add("");
                                        ordsize42.add("");
                                        ordsize44.add("");
                                        ordsize46.add("");
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
                        orderfromlistview = (RecyclerView) findViewById(R.id.seleprolist);
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
                        PopupMenu popup = new PopupMenu(TextilesEditOrderDetails.this, mainviews);
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
        final Dialog filterdialog = new Dialog(TextilesEditOrderDetails.this);
        filterdialog.setContentView(R.layout.filter_dialog);
        RelativeLayout apply = (RelativeLayout) filterdialog.findViewById(R.id.filterclose);
        ImageView close = (ImageView)filterdialog.findViewById(R.id.close);
        selectcatge = (Spinner)filterdialog.findViewById(R.id.categoryfilter);
        selectbrand = (Spinner)filterdialog.findViewById(R.id.brandfilter);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TextilesEditOrderDetails.this, android.R.layout.simple_spinner_item, catgefilterlist);
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
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(TextilesEditOrderDetails.this, android.R.layout.simple_spinner_item, brandfilterlist);
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

                }   if(viewstatus.equals("tileview")) {
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
                    itemChecked = new boolean[filproidlist.size()];
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
                        .load(ApparelsOrderDetails.Headers.getUrlWithHeaders(adproimagelist.get(position)))
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dhotifragment_list, parent, false);
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
            holder.size36.setText(ordsize36.get(position));
            holder.size38.setText(ordsize38.get(position));
            holder.size40.setText(ordsize40.get(position));
            holder.size42.setText(ordsize42.get(position));
            holder.size44.setText(ordsize44.get(position));
            holder.size46.setText(ordsize46.get(position));
            holder.size36.setTag(position);
            holder.size38.setTag(position);
            holder.size40.setTag(position);
            holder.size42.setTag(position);
            holder.size44.setTag(position);
            holder.size46.setTag(position);
            holder.sizetype.setTag(position);
            holder.qty.setTag(position);
            holder.sleeve.setTag(position);
            holder.prodelete.setTag(position);
            holder.pvariant.setTag(position);

            if(!ordcolimage.get(position).equals("")&&!ordcolimage.get(position).equals("null")){


                Glide.with(getApplicationContext())
                        .load(Headers.getUrlWithHeaders(ordcolimage.get(position)))
                        .into(shade);

            }


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
            // holder.size44.setFocusable(false);
            holder.size44.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            holder.size46.setEnabled(false);
            //holder.size46.setFocusable(false);
            holder.size46.setBackground(getResources().getDrawable(R.drawable.hide_text_style));

            if(ordqty.get(position).equals("")) {
                holder.qty.setEnabled(false);
                // holder.qty.setFocusable(false);
                holder.qty.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
            }else{
                if(bvalue.equals("Update")) {
                    holder.qty.setEnabled(true);
                    // holder.qty.setFocusable(true);
                    holder.qty.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                }else{
                    holder.qty.setEnabled(false);
                    // holder.qty.setFocusable(false);
                    holder.qty.setBackground(getResources().getDrawable(R.drawable.hide_text_style));
                }
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

                                if(getkey.equals("size_36")){
                                    if(bvalue.equals("Update")) {
                                        holder.size36.setEnabled(true);
                                        // holder.size36.setFocusable(true);
                                        holder.size36.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                    }else{

                                    }
                                }
                                else if(getkey.equals("size_38")){
                                    if(bvalue.equals("Update")) {
                                        holder.size38.setEnabled(true);
                                        // holder.size38.setFocusable(true);
                                        holder.size38.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                    }else{

                                    }
                                }
                                else if(getkey.equals("size_40")){
                                    if(bvalue.equals("Update")) {
                                        holder.size40.setEnabled(true);
                                        // holder.size40.setFocusable(true);
                                        holder.size40.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                    }else{

                                    }
                                }
                                else if(getkey.equals("size_42")){
                                    if(bvalue.equals("Update")) {
                                        holder.size42.setEnabled(true);
                                        // holder.size42.setFocusable(true);
                                        holder.size42.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                    }else{

                                    }
                                }
                                else if(getkey.equals("size_44")){
                                    if(bvalue.equals("Update")) {
                                        holder.size44.setEnabled(true);
                                        // holder.size44.setFocusable(true);
                                        holder.size44.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                    }else{

                                    }
                                }
                                else if(getkey.equals("size_46")){
                                    if(bvalue.equals("Update")) {
                                        holder.size46.setEnabled(true);
                                        // holder.size46.setFocusable(true);
                                        holder.size46.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                    }
                                }
                                else if(getkey.equals("size_00")){
                                    if(bvalue.equals("Update")) {
                                        holder.qty.setEnabled(true);
                                        // holder.qty.setFocusable(true);
                                        holder.qty.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                                    }else{

                                    }
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
                    if(bvalue.equals("Update")) {
                    totalpics = 0;

                    int pos = (int) holder.prodelete.getTag();
                    String dpid = ordprodidlist.get(pos);
                    for (int i = 0; i < editpidlist.size(); i++) {
                        if (dpid.equals(editpidlist.get(i))) {
                            deletidlist.add(editidlist.get(pos));
                            deletpcatglist.add(ordpromaincatglist.get(pos));
                            editidlist.remove(pos);
                            editpidlist.remove(pos);
                            break;
                        }
                    }
                    ordpronamelist.remove(pos);
                    ordprocatglist.remove(pos);
                    ordpromaincatglist.remove(pos);
                    ordprodidlist.remove(pos);
                    ordsize36.remove(pos);
                    ordsize38.remove(pos);
                    ordsize40.remove(pos);
                    ordsize42.remove(pos);
                    ordsize44.remove(pos);
                    ordsize46.remove(pos);
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
                    totalitems = ordprodidlist.size();
                    pertotitems.setText(String.valueOf(totalitems));
                    if (ordtqty.size() != 0) {
                        totalpics = 0;
                        for (int i = 0; i < ordtqty.size(); i++) {
                            String val = ordtqty.get(i);
                            if (!val.equals("")) {
                                totalpics = totalpics + Integer.parseInt(val);
                            }
                        }
                        pertotpieces.setText(String.valueOf(totalpics));
                    }
                }
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
                    int value = (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
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
                    int value = (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
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
                    int value = (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
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
                    int value = (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
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
                    int value = (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
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
                    int value = (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
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
                    int value = (ordsize36.get(position1).equals("")? 0: Integer.parseInt(ordsize36.get(position1)))+
                            (ordsize38.get(position1).equals("")? 0: Integer.parseInt(ordsize38.get(position1)))+
                            (ordsize40.get(position1).equals("")? 0: Integer.parseInt(ordsize40.get(position1)))+
                            (ordsize42.get(position1).equals("")? 0: Integer.parseInt(ordsize42.get(position1)))+
                            (ordsize44.get(position1).equals("")? 0: Integer.parseInt(ordsize44.get(position1)))+
                            (ordsize46.get(position1).equals("")? 0: Integer.parseInt(ordsize46.get(position1)))+
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
                size36 = (EditText) itemView.findViewById(R.id.size36);
                size38 = (EditText) itemView.findViewById(R.id.size38);
                size40 = (EditText) itemView.findViewById(R.id.size40);
                size42 = (EditText) itemView.findViewById(R.id.size42);
                size44 = (EditText) itemView.findViewById(R.id.size44);
                size46 = (EditText) itemView.findViewById(R.id.size46);
                qty = (EditText) itemView.findViewById(R.id.qty);
                prodelete = (ImageView) itemView.findViewById(R.id.prodelete);
                pvariant = (ImageView) itemView.findViewById(R.id.pvariant);
                pname = (TextView) itemView.findViewById(R.id.plname);


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

        fitaddlist = new ArrayList<>();
        sleeveaddlist = new ArrayList<>();
        lengthaddlist = new ArrayList<>();
        if(mainopenstatus.equals("tile")){
            varientdialog = new Dialog(TextilesEditOrderDetails.this, android.R.style.Theme_Light);
            varientdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            varientdialog.setContentView(R.layout.tilecolorvarient_dialog);
        }else {
            varientdialog = new Dialog(TextilesEditOrderDetails.this, android.R.style.Theme_Light);
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
        tsize36 = (TextView)varientdialog.findViewById(R.id.tsize36);
        tsize38 = (TextView)varientdialog.findViewById(R.id.tsize38);
        tsize40 = (TextView)varientdialog.findViewById(R.id.tsize40);
        tsize42 = (TextView)varientdialog.findViewById(R.id.tsize42);
        tsize44 = (TextView)varientdialog.findViewById(R.id.tsize44);
        tsize46 = (TextView)varientdialog.findViewById(R.id.tsize46);
        tqty = (TextView)varientdialog.findViewById(R.id.tqty);

        size36 = (TextView)varientdialog.findViewById(R.id.size36);
        size38 = (TextView)varientdialog.findViewById(R.id.size38);
        size40 = (TextView)varientdialog.findViewById(R.id.size40);
        size42 = (TextView)varientdialog.findViewById(R.id.size42);
        size44 = (TextView)varientdialog.findViewById(R.id.size44);
        size46 = (TextView)varientdialog.findViewById(R.id.size46);
        qty = (TextView)varientdialog.findViewById(R.id.qty);
        cqty = (TextView)varientdialog.findViewById(R.id.cqty);



        wsp36 = (TextView)varientdialog.findViewById(R.id.wsp36);
        wsp38 = (TextView)varientdialog.findViewById(R.id.wsp38);
        wsp40 = (TextView)varientdialog.findViewById(R.id.wsp40);
        wsp42 = (TextView)varientdialog.findViewById(R.id.wsp42);
        wsp44 = (TextView)varientdialog.findViewById(R.id.wsp44);
        wsp46 = (TextView)varientdialog.findViewById(R.id.wsp46);
        wqty = (TextView)varientdialog.findViewById(R.id.wqty);

        mrp36 = (TextView)varientdialog.findViewById(R.id.mrp36);
        mrp38 = (TextView)varientdialog.findViewById(R.id.mrp38);
        mrp40 = (TextView)varientdialog.findViewById(R.id.mrp40);
        mrp42 = (TextView)varientdialog.findViewById(R.id.mrp42);
        mrp44 = (TextView)varientdialog.findViewById(R.id.mrp44);
        mrp46 = (TextView)varientdialog.findViewById(R.id.mrp46);
        mqty = (TextView)varientdialog.findViewById(R.id.mqty);

        varientclose = (RelativeLayout) varientdialog.findViewById(R.id.varientclose);
        tempsave = (RelativeLayout) varientdialog.findViewById(R.id.tempsave);


        if(sleevelist.size()!=0) {
            for (int a = 0; a < sleevelist.size(); a++) {
                String val= sleevelist.get(a);
                if(a==0){
                    slev1.setVisibility(View.VISIBLE);
                    slev1.setText(val);
                }
                if(a==1){
                    slev2.setVisibility(View.VISIBLE);
                    slev2.setText(val);
                }
                if(a==2){
                    slev3.setVisibility(View.VISIBLE);
                    slev3.setText(val);
                }
                if(a==3){
                    slev4.setVisibility(View.VISIBLE);
                    slev4.setText(val);
                }
                if(a==4){
                    slev5.setVisibility(View.VISIBLE);
                    slev5.setText(val);
                }
            }
        }
        if(fitlist.size()!=0) {
            for (int a = 0; a < fitlist.size(); a++) {
                String val= fitlist.get(a);
                if(a==0){
                    fit1.setVisibility(View.VISIBLE);
                    fit1.setText(val);
                }
                if(a==1){
                    fit2.setVisibility(View.VISIBLE);
                    fit2.setText(val);
                }
                if(a==2){
                    fit3.setVisibility(View.VISIBLE);
                    fit3.setText(val);
                }
                if(a==3){
                    fit4.setVisibility(View.VISIBLE);
                    fit4.setText(val);
                }
                if(a==4){
                    fit5.setVisibility(View.VISIBLE);
                    fit5.setText(val);
                }
            }
        }
        if(lenglist.size()!=0) {
            for (int a = 0; a < lenglist.size(); a++) {
                String val= lenglist.get(a);
                if(a==0){
                    leng1.setVisibility(View.VISIBLE);
                    leng1.setText(val);
                }
                if(a==1){
                    leng2.setVisibility(View.VISIBLE);
                    leng2.setText(val);
                }
                if(a==2){
                    leng3.setVisibility(View.VISIBLE);
                    leng3.setText(val);
                }
                if(a==3){
                    leng4.setVisibility(View.VISIBLE);
                    leng4.setText(val);
                }
                if(a==4){
                    leng5.setVisibility(View.VISIBLE);
                    leng5.setText(val);
                }
            }
        }
        if(sizekeylist.size()!=0) {
            for (int a = 0; a < sizekeylist.size(); a++) {
                String val= sizekeylist.get(a);
                String key= sizelist.get(a);
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
                            if(skey.equals("size_36")){
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

                            if(skey.equals("size_36")){
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
            layoutManager4 = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.VERTICAL, false);
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
                                    if (skey.equals("size_36")) {
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
                                                if (skey.equals("size_36")) {
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
                                                }else if (skey.equals("size_00")) {
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
                                    if (skey.equals("size_36")) {
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
                                    }else if (skey.equals("size_00")) {
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
                                                if (skey.equals("size_36")) {
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
                                                }else if (skey.equals("size_00")) {
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
                                    if (skey.equals("size_36")) {
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
                                    }else if (skey.equals("size_00")) {
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
                                                if (skey.equals("size_36")) {
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
                                                }else if (skey.equals("size_00")) {
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
                                    if (skey.equals("size_36")) {
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
                                    }else if (skey.equals("size_00")) {
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
                                                if (skey.equals("size_36")) {
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
                                                }else if (skey.equals("size_00")) {
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
                                    if (skey.equals("size_36")) {
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
                                    }else if (skey.equals("size_00")) {
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
                                                if (skey.equals("size_36")) {
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
                                    if (skey.equals("size_36")) {
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
                                                if (skey.equals("size_36")) {
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
                                    if (skey.equals("size_36")) {
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
                                                if (skey.equals("size_36")) {
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
                                    if (skey.equals("size_36")) {
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
                                    }  else if (skey.equals("size_00")) {
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
                                                if (skey.equals("size_36")) {
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
                                    if (skey.equals("size_36")) {
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
                                                if (skey.equals("size_36")) {
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
                                    if (skey.equals("size_36")) {
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
                                                if (skey.equals("size_36")) {
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



        size36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEditText(R.id.size36);
                size36.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);

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
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
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
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
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
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
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
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
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
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
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
                size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                qty.setBackground(getResources().getDrawable(R.drawable.edit1_text_style));
                cqty.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                if(cqtystatus.equals("true")){
                    int tqty = 0;
                    for (int a = 0; a < sizekeylist.size(); a++) {
                        String vale = sizekeylist.get(a);
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
                    size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
                    size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
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

                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
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
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);

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
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
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
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);

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
                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
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
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);
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

                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
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
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);

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

                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
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
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);

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

                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
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
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);

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

                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
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
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);

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

                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
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
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);

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

                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
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
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);

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

                                if (vale.equals("size_00")) {

                                    qty.setText(oldval);
                                }
                            }
                        }
                    }else{
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
                            int tqty = 0;
                            for (int a = 0; a < sizekeylist.size(); a++) {
                                String vale = sizekeylist.get(a);

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
                                ordsize36.remove(mainpostions);
                                ordsize38.remove(mainpostions);
                                ordsize40.remove(mainpostions);
                                ordsize42.remove(mainpostions);
                                ordsize44.remove(mainpostions);
                                ordsize46.remove(mainpostions);
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

                            String s36 = size36.getText().toString();
                            String s38 = size38.getText().toString();
                            String s40 = size40.getText().toString();
                            String s42 = size42.getText().toString();
                            String s44 = size44.getText().toString();
                            String s46 = size46.getText().toString();
                            String sqty = qty.getText().toString();
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize36.getText().toString());
                                                    ordqty.add(s36);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize36.getText().toString());
                                                        ordqty.add(s36);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize38.getText().toString());
                                                    ordqty.add(s38);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize38.getText().toString());
                                                        ordqty.add(s38);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize40.getText().toString());
                                                    ordqty.add(s40);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize40.getText().toString());
                                                        ordqty.add(s40);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add("");
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize42.getText().toString());
                                                    ordqty.add(s42);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize42.getText().toString());
                                                        ordqty.add(s42);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add("");
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize44.getText().toString());
                                                    ordqty.add(s44);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
                                                    ordvarient.add("false");

                                                    int value = (s44.equals("") ? 0 : Integer.parseInt(s44));
                                                    ordtqty.add(String.valueOf(value));
                                                }
                                            }
                                        }
                                    }
                                }else{
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize44.getText().toString());
                                                        ordqty.add(s44);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize46.getText().toString());
                                                    ordqty.add(s46);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize46.getText().toString());
                                                        ordqty.add(s46);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add("");
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                        } else {
                            if (maincheckingstatus.equals("0")) {
                                ordpronamelist.remove(mainpostions);
                                ordprocatglist.remove(mainpostions);
                                ordpromaincatglist.remove(mainpostions);
                                ordsize36.remove(mainpostions);
                                ordsize38.remove(mainpostions);
                                ordsize40.remove(mainpostions);
                                ordsize42.remove(mainpostions);
                                ordsize44.remove(mainpostions);
                                ordsize46.remove(mainpostions);
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

                            String s36 = size36.getText().toString();
                            String s38 = size38.getText().toString();
                            String s40 = size40.getText().toString();
                            String s42 = size42.getText().toString();
                            String s44 = size44.getText().toString();
                            String s46 = size46.getText().toString();
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
                                                ordsize36.add(s36);
                                                ordsize38.add(s38);
                                                ordsize40.add(s40);
                                                ordsize42.add(s42);
                                                ordsize44.add(s44);
                                                ordsize46.add(s46);
                                                ordsizetype.add("");
                                                ordqty.add(sqty);
                                                ordsleeve.add(sleeveaddlist.get(b).equals("") ?"" : sleeveaddlist.get(b));
                                                ordcolor.add("");
                                                ordcolimage.add("");
                                                ordfit.add(fitaddlist.get(a).equals("") ?"" : fitaddlist.get(a));
                                                ordleng.add(lengthaddlist.get(c).equals("") ?"" : lengthaddlist.get(c));
                                                ordvarient.add("false");

                                                int value = (s36.equals("") ? 0 : Integer.parseInt(s36)) +
                                                        (s38.equals("") ? 0 : Integer.parseInt(s38)) +
                                                        (s40.equals("") ? 0 : Integer.parseInt(s40)) +
                                                        (s42.equals("") ? 0 : Integer.parseInt(s42)) +
                                                        (s44.equals("") ? 0 : Integer.parseInt(s44)) +
                                                        (s46.equals("") ? 0 : Integer.parseInt(s46)) +
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
                                                    ordsize36.add(s36);
                                                    ordsize38.add(s38);
                                                    ordsize40.add(s40);
                                                    ordsize42.add(s42);
                                                    ordsize44.add(s44);
                                                    ordsize46.add(s46);
                                                    ordsizetype.add("");
                                                    ordqty.add(sqty);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ?"" : sleeveaddlist.get(b));
                                                    ordcolor.add(selectedcolor.get(i));
                                                    ordcolimage.add(selectedcolimg.get(i));
                                                    ordfit.add(fitaddlist.get(a).equals("") ?"" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ?"" : lengthaddlist.get(c));
                                                    ordvarient.add("false");

                                                    int value = (s36.equals("") ? 0 : Integer.parseInt(s36)) +
                                                            (s38.equals("") ? 0 : Integer.parseInt(s38)) +
                                                            (s40.equals("") ? 0 : Integer.parseInt(s40)) +
                                                            (s42.equals("") ? 0 : Integer.parseInt(s42)) +
                                                            (s44.equals("") ? 0 : Integer.parseInt(s44)) +
                                                            (s46.equals("") ? 0 : Integer.parseInt(s46)) +
                                                            (sqty.equals("") ? 0 : Integer.parseInt(sqty));
                                                    ordtqty.add(String.valueOf(value));
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (mainopenstatus.equals("grid")||mainopenstatus.equals("tile")) {
                        if (mainsizebasecost.equals("true")) {
                            gridmainprosta = "";
                            String s36 = size36.getText().toString();
                            String s38 = size38.getText().toString();
                            String s40 = size40.getText().toString();
                            String s42 = size42.getText().toString();
                            String s44 = size44.getText().toString();
                            String s46 = size46.getText().toString();
                            String sqty = qty.getText().toString();

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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize36.getText().toString());
                                                    ordqty.add(s36);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize36.getText().toString());
                                                        ordqty.add(s36);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize38.getText().toString());
                                                    ordqty.add(s38);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize38.getText().toString());
                                                        ordqty.add(s38);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize40.getText().toString());
                                                    ordqty.add(s40);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize40.getText().toString());
                                                        ordqty.add(s40);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize42.getText().toString());
                                                    ordqty.add(s42);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize42.getText().toString());
                                                        ordqty.add(s42);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize44.getText().toString());
                                                    ordqty.add(s44);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize44.getText().toString());
                                                        ordqty.add(s44);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add(selectedcolimg.get(i));
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                    ordsize36.add("");
                                                    ordsize38.add("");
                                                    ordsize40.add("");
                                                    ordsize42.add("");
                                                    ordsize44.add("");
                                                    ordsize46.add("");
                                                    ordsizetype.add(tsize46.getText().toString());
                                                    ordqty.add(s46);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                    ordcolor.add("");
                                                    ordcolimage.add("");
                                                    ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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
                                                        ordsize36.add("");
                                                        ordsize38.add("");
                                                        ordsize40.add("");
                                                        ordsize42.add("");
                                                        ordsize44.add("");
                                                        ordsize46.add("");
                                                        ordsizetype.add(tsize46.getText().toString());
                                                        ordqty.add(s46);
                                                        ordsleeve.add(sleeveaddlist.get(b).equals("") ? "" : sleeveaddlist.get(b));
                                                        ordcolor.add(selectedcolor.get(i));
                                                        ordcolimage.add("");
                                                        ordfit.add(fitaddlist.get(a).equals("") ? "" : fitaddlist.get(a));
                                                        ordleng.add(lengthaddlist.get(c).equals("") ? "" : lengthaddlist.get(c));
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


                        } else {
                            String s36 = size36.getText().toString();
                            String s38 = size38.getText().toString();
                            String s40 = size40.getText().toString();
                            String s42 = size42.getText().toString();
                            String s44 = size44.getText().toString();
                            String s46 = size46.getText().toString();
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
                                                ordsize36.add(s36);
                                                ordsize38.add(s38);
                                                ordsize40.add(s40);
                                                ordsize42.add(s42);
                                                ordsize44.add(s44);
                                                ordsize46.add(s46);
                                                ordsizetype.add("");
                                                ordqty.add(sqty);
                                                ordsleeve.add(sleeveaddlist.get(b).equals("") ?"" : sleeveaddlist.get(b));
                                                ordcolor.add("");
                                                ordcolimage.add("");
                                                ordfit.add(fitaddlist.get(a).equals("") ?"" : fitaddlist.get(a));
                                                ordleng.add(lengthaddlist.get(c).equals("") ?"" : lengthaddlist.get(c));
                                                ordvarient.add("false");

                                                int value =
                                                        (s36.equals("") ? 0 : Integer.parseInt(s36)) +
                                                                (s38.equals("") ? 0 : Integer.parseInt(s38)) +
                                                                (s40.equals("") ? 0 : Integer.parseInt(s40)) +
                                                                (s42.equals("") ? 0 : Integer.parseInt(s42)) +
                                                                (s44.equals("") ? 0 : Integer.parseInt(s44)) +
                                                                (s46.equals("") ? 0 : Integer.parseInt(s46)) +
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
                                                    ordsize36.add(s36);
                                                    ordsize38.add(s38);
                                                    ordsize40.add(s40);
                                                    ordsize42.add(s42);
                                                    ordsize44.add(s44);
                                                    ordsize46.add(s46);
                                                    ordsizetype.add("");
                                                    ordqty.add(sqty);
                                                    ordsleeve.add(sleeveaddlist.get(b).equals("") ?"" : sleeveaddlist.get(b));
                                                    ordcolor.add(selectedcolor.get(i));
                                                    ordcolimage.add(selectedcolimg.get(i));
                                                    ordfit.add(fitaddlist.get(a).equals("") ?"" : fitaddlist.get(a));
                                                    ordleng.add(lengthaddlist.get(c).equals("") ?"" : lengthaddlist.get(c));
                                                    ordvarient.add("false");

                                                    int value =
                                                            (s36.equals("") ? 0 : Integer.parseInt(s36)) +
                                                                    (s38.equals("") ? 0 : Integer.parseInt(s38)) +
                                                                    (s40.equals("") ? 0 : Integer.parseInt(s40)) +
                                                                    (s42.equals("") ? 0 : Integer.parseInt(s42)) +
                                                                    (s44.equals("") ? 0 : Integer.parseInt(s44)) +
                                                                    (s46.equals("") ? 0 : Integer.parseInt(s46)) +
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
                    size36.setText("");
                    size38.setText("");
                    size40.setText("");
                    size42.setText("");
                    size44.setText("");
                    size46.setText("");
                    qty.setText("");
                    cqty.setText("");
                    cqtystatus = "";
           size36.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
           size38.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
           size40.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
           size42.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
           size44.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
           size46.setBackground(getResources().getDrawable(R.drawable.edit2_text_style));
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
                        .load(ApparelsOrderDetails.Headers.getUrlWithHeaders(imagevarientlist.get(position)))
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
    class productlist extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TextilesEditOrderDetails.this);
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
                        if(producatge.equals("Textiles")){
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
                            (TextilesEditOrderDetails.this,android.R.layout.select_dialog_item, apparelprolist);

                    addpro.setThreshold(2);
                    addpro.setAdapter(adapter);

                    new orderlist().execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Product list is empty", Toast.LENGTH_LONG).show();
                }

            }

        }

    }
    class orderlist extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TextilesEditOrderDetails.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonParams = new JSONObject();
            JSONObject params1 = new JSONObject();
            JSONObject params2 = new JSONObject();
            JSONObject params4 = new JSONObject();
            JSONArray array = new JSONArray();


            HttpClient client = new DefaultHttpClient();

            // Prepare a request object
            HttpPost post = new HttpPost(GetURL.getcustomerlist());
            post.setHeader("Cookie","session_id=" + Session_ID);
            post.setHeader("Content-type", "application/json");
            try {
                params1.put("model", "textiles.order");
                params1.put("method", "get_textiles_order_data_withlines");
                params1.put("kwargs", params2);
                params1.put("args",array);
                array.put(params4);
                params4.put("uid",Integer.parseInt(User_ID));
                params4.put("order_id",Integer.parseInt(Order_ID));


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
                    JSONArray ordlist = data.getJSONArray("result");
                     toitem = 0;
                    for(int i=0; i <ordlist.length();i++) {
                        JSONObject list = ordlist.getJSONObject(i);
                        odate = list.get("order_date").toString();
                        ddate = list.get("top_dispatch_date").toString();
                        comment = list.get("top_comments").toString();
                        cusID = list.get("customer_details").toString();
                        custname = list.get("customer_name").toString();
                        ordernum = list.get("name").toString();
                        String tops = list.getString("top_lines");
                        if (!tops.equals("null")) {
                            JSONArray rm = list.getJSONArray("top_lines");
                            for (int a = 0; a < rm.length(); a++) {
                                JSONObject value = rm.getJSONObject(a);
                                String pname = value.get("product_name").toString();
                                String pcatge = value.get("product_categ").toString();
                                String pid = value.get("product_id").toString();
                                String id = value.get("id").toString();
                                String sleeve = value.get("sleeve_type").toString();
                                String fit = value.get("fit_type").toString();
                                String leng = value.get("length_type").toString();
                                String color = value.get("color_type").toString();
                                String colimage = value.get("color_image").toString();
                                String sizetype = value.get("size_type").toString();
                                String s36 = value.get("size_36").toString();
                                String s38 = value.get("size_38").toString();
                                String s40 = value.get("size_40").toString();
                                String s42 = value.get("size_42").toString();
                                String s44 = value.get("size_44").toString();
                                String s46 = value.get("size_46").toString();
                                String sqty = value.get("size_00").toString();

                                ordpronamelist.add(pname);
                                ordprodidlist.add(pid);
                                ordprocatglist.add(pcatge);
                                ordpromaincatglist.add("Tops");
                                editpidlist.add(pid);
                                editidlist.add(id);
                                if(s36.equals("0")){
                                    ordsize36.add("");
                                }else{
                                    ordsize36.add(s36);
                                }
                                if(s38.equals("0")){
                                    ordsize38.add("");
                                }else{
                                    ordsize38.add(s38);
                                }
                                if(s40.equals("0")){
                                    ordsize40.add("");
                                }else{
                                    ordsize40.add(s40);
                                }
                                if(s42.equals("0")){
                                    ordsize42.add("");
                                }else{
                                    ordsize42.add(s42);
                                }
                                if(s44.equals("0")){
                                    ordsize44.add("");
                                }else{
                                    ordsize44.add(s44);
                                }
                                if(s46.equals("0")){
                                    ordsize46.add("");
                                }else{
                                    ordsize46.add(s46);
                                }
                                if(sqty.equals("0")){
                                    ordqty.add("");
                                }else {
                                    ordqty.add(sqty);
                                }
                                ordsizetype.add(sizetype);
                                ordsleeve.add(sleeve);
                                ordfit.add(fit);
                                ordleng.add(leng);
                                ordcolor.add(color);
                                ordcolimage.add(colimage);
                                ordvarient.add("false");
                                int val = Integer.parseInt(s36) + Integer.parseInt(s38) + Integer.parseInt(s40) + Integer.parseInt(s42) + Integer.parseInt(s44) + Integer.parseInt(s46)  + Integer.parseInt(sqty);
                                toitem = toitem + val;
                                ordtqty.add(String.valueOf(val));
                            }
                        }
                        String bottoms = list.getString("bottom_lines");
                        if (!bottoms.equals("null")) {
                            JSONArray rm = list.getJSONArray("bottom_lines");
                            for (int a = 0; a < rm.length(); a++) {
                                JSONObject value = rm.getJSONObject(a);
                                String pname = value.get("product_name").toString();
                                String pcatge = value.get("product_categ").toString();
                                String pid = value.get("product_id").toString();
                                String id = value.get("id").toString();
                                String sleeve = value.get("sleeve_type").toString();
                                String fit = value.get("fit_type").toString();
                                String leng = value.get("length_type").toString();
                                String color = value.get("color_type").toString();
                                String colimage = value.get("color_image").toString();
                                String sizetype = value.get("size_type").toString();
                                String s36 = value.get("size_36").toString();
                                String s38 = value.get("size_38").toString();
                                String s40 = value.get("size_40").toString();
                                String s42 = value.get("size_42").toString();
                                String s44 = value.get("size_44").toString();
                                String s46 = value.get("size_46").toString();
                                String sqty = value.get("size_00").toString();

                                ordpronamelist.add(pname);
                                ordprodidlist.add(pid);
                                ordprocatglist.add(pcatge);
                                ordpromaincatglist.add("Bottoms");
                                editpidlist.add(pid);
                                editidlist.add(id);
                                if(s36.equals("0")){
                                    ordsize36.add("");
                                }else{
                                    ordsize36.add(s36);
                                }
                                if(s38.equals("0")){
                                    ordsize38.add("");
                                }else{
                                    ordsize38.add(s38);
                                }
                                if(s40.equals("0")){
                                    ordsize40.add("");
                                }else{
                                    ordsize40.add(s40);
                                }
                                if(s42.equals("0")){
                                    ordsize42.add("");
                                }else{
                                    ordsize42.add(s42);
                                }
                                if(s44.equals("0")){
                                    ordsize44.add("");
                                }else{
                                    ordsize44.add(s44);
                                }
                                if(s46.equals("0")){
                                    ordsize46.add("");
                                }else{
                                    ordsize46.add(s46);
                                }
                                if(sqty.equals("0")){
                                    ordqty.add("");
                                }else {
                                    ordqty.add(sqty);
                                }
                                ordsizetype.add(sizetype);
                                ordsleeve.add(sleeve);
                                ordfit.add(fit);
                                ordleng.add(leng);
                                ordcolor.add(color);
                                ordcolimage.add(colimage);
                                ordvarient.add("false");
                                int val = Integer.parseInt(s36) + Integer.parseInt(s38) + Integer.parseInt(s40) + Integer.parseInt(s42) + Integer.parseInt(s44) + Integer.parseInt(s46) + Integer.parseInt(sqty);
                                toitem = toitem + val;
                                ordtqty.add(String.valueOf(val));
                            }
                        }
                    }
                    cusname.setText(custname);
                    getSupportActionBar().setTitle("Order : " +ordernum);
                    orderfromlistview = (RecyclerView) findViewById(R.id.seleprolist);
                    orderfromlistview.setHasFixedSize(true);
                    layoutManager2 = new LinearLayoutManager(getApplicationContext());
                    orderfromlistview.setLayoutManager(layoutManager2);
                    TrouserAdapter trouserAdapter = new TrouserAdapter();
                    orderfromlistview.setAdapter(trouserAdapter);

                    totalitems =  ordprodidlist.size();
                    pertotitems.setText(String.valueOf(totalitems));
                    pertotpieces.setText(String.valueOf(toitem));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "order list is empty", Toast.LENGTH_LONG).show();
                }

            }

        }

    }


    class productsave extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TextilesEditOrderDetails.this);
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
                params1.put("model", "textiles.order");
                params1.put("method", "write");
                params1.put("kwargs", params2);
                params1.put("args",params3);
                params3.put(Integer.parseInt(Order_ID));
                params3.put(finalparams);
                finalparams.put("customer_details",cusID);
                finalparams.put("order_date",odate);
                finalparams.put("top_dispatch_date",ddate);
                finalparams.put("bottom_dispatch_date",ddate);
                finalparams.put("submitted_by",User_ID);
                finalparams.put("top_comments",comment);
                finalparams.put("bottom_comments",comment);
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
                        Intent i=new Intent(getApplicationContext(), TextilesOrderList.class);
                        startActivity(i);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Order Sent Failed", Toast.LENGTH_LONG).show();
                }

            }

        }

    }
    class Agnetconfrim extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TextilesEditOrderDetails.this);
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
            JSONArray array = new JSONArray();


            HttpClient client = new DefaultHttpClient();

            // Prepare a request object
            HttpPost post = new HttpPost(GetURL.getproductlist());
            post.setHeader("Cookie","session_id=" + Session_ID);
            post.setHeader("Content-type", "application/json");
            try {
                params1.put("model", "textiles.order");
                params1.put("method", "agent_submission_call");
                params1.put("kwargs", params2);
                params1.put("args",array);
                array.put(params3);
                params3.put("order_id", Integer.parseInt(Order_ID));
                params3.put("uid", Integer.parseInt(User_ID));


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
                    JSONObject data1 = data. getJSONObject("result");
                    String finalresult = data1.getString("state");
                    String errormsg = data1.getString("error_msg");
                    if(!finalresult.equals("")&&finalresult.equals("agent_confirmed")){
                        acfstatus="true";
                        Toast.makeText(getApplicationContext(), "Order confirmed Successfully", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(), TextilesOrderList.class);
                        startActivity(i);

                    }else{
                        Toast.makeText(getApplicationContext(), errormsg, Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Order confirmed Failed", Toast.LENGTH_LONG).show();
                }

            }

        }

    }
}
