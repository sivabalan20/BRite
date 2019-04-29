package com.aspirantlab.salesagent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspirantlab.salesagent.Services.GetURL;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;


public class ProductView  extends MainActivity implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout relativeLayout;
    TextView sleeve1,sleeve2,sleeve3,sleeve4,sleeve5;
    TextView fit1,fit2,fit3,fit4,fit5;
    TextView leng1,leng2,leng3,leng4,leng5;
    TextView s28,s30,s32,s34,s36,s38,s40,s42,s44,s46,s48,sqty;
    ImageView proimage;
    TextView proname,promaincatge,prosubcatge,procatge,probrand,prowsp,promrp;
    static String Session_ID;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.productview, null, false);
        drawer.addView(contentView, 0);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Session_ID = prefs.getString("sessionid", null);

        relativeLayout = (RelativeLayout)findViewById(R.id.mainfile);
        relativeLayout.setVisibility(View.GONE);

        s28 = (TextView)findViewById(R.id.size28);
        s30 = (TextView)findViewById(R.id.size30);
        s32 = (TextView)findViewById(R.id.size32);
        s34 = (TextView)findViewById(R.id.size34);
        s36 = (TextView)findViewById(R.id.size36);
        s38 = (TextView)findViewById(R.id.size38);
        s40 = (TextView)findViewById(R.id.size40);
        s42 = (TextView)findViewById(R.id.size42);
        s44 = (TextView)findViewById(R.id.size44);
        s46 = (TextView)findViewById(R.id.size46);
        s48 = (TextView)findViewById(R.id.size48);
        sqty = (TextView)findViewById(R.id.qty);

        sleeve1 = (TextView)findViewById(R.id.sleeve1);
        sleeve2 = (TextView)findViewById(R.id.sleeve2);
        sleeve3 = (TextView)findViewById(R.id.sleeve3);
        sleeve4 = (TextView)findViewById(R.id.sleeve4);
        sleeve5 = (TextView)findViewById(R.id.sleeve5);

        fit1 = (TextView)findViewById(R.id.fit1);
        fit2 = (TextView)findViewById(R.id.fit2);
        fit3 = (TextView)findViewById(R.id.fit3);
        fit4 = (TextView)findViewById(R.id.fit4);
        fit5 = (TextView)findViewById(R.id.fit5);

        leng1 = (TextView)findViewById(R.id.leng1);
        leng2 = (TextView)findViewById(R.id.leng2);
        leng3 = (TextView)findViewById(R.id.leng3);
        leng4 = (TextView)findViewById(R.id.leng4);
        leng5 = (TextView)findViewById(R.id.leng5);

        proimage = (ImageView)findViewById(R.id.productimage) ;
        proname = (TextView)findViewById(R.id.proname);
        promaincatge = (TextView)findViewById(R.id.maincatge);
        prosubcatge = (TextView)findViewById(R.id.subcatge);
        procatge = (TextView)findViewById(R.id.catge);
        probrand = (TextView)findViewById(R.id.bname);
        prowsp = (TextView)findViewById(R.id.wsp);
        promrp = (TextView)findViewById(R.id.mrp);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String value  = bundle.getString("productdata");
            try{
                JSONObject list = new JSONObject(value);
                String productname = list.get("name").toString();
                String productmaincateg = list.get("form_types").toString();
                String productsubcateg = list.get("categ_types").toString();
                String productcateg = list.get("product_categ").toString();
                String productbrand = list.get("brand_id").toString();
                String productimage= GetURL.getServerip()+list.get("image_url").toString();
                String productwsp = list.get("wsp").toString();
                String productmrp = list.get("mrp").toString();
                proname.setText(productname);
                promaincatge.setText(productmaincateg);
                prosubcatge.setText(productsubcateg);
                procatge.setText(productcateg);
                prowsp.setText(productwsp);
                promrp.setText(productmrp);
                probrand.setText(productbrand);

                if(!productimage.equals("")){

                    RequestOptions options = new RequestOptions()
                            .error(R.drawable.person);

                    Glide.with(getApplicationContext())
                            .load(ApparelsOrderDetails.Headers.getUrlWithHeaders(productimage))
                            .apply(options)
                            .into(proimage);
                }


                String data = list.get("attribute_dict").toString();
                JSONObject jsonObject = new JSONObject(data);
                JSONArray slelist = jsonObject.getJSONArray("sleeve_list");
                if (slelist.length() != 0) {

                    for (int c = 0; c < slelist.length(); c++) {
                        if(c==0){
                            sleeve1.setVisibility(View.VISIBLE);
                            sleeve1.setText(String.valueOf(slelist.get(c)));
                        }else if(c==1){
                            sleeve2.setVisibility(View.VISIBLE);
                            sleeve2.setText(String.valueOf(slelist.get(c)));

                        }else if(c==2){
                            sleeve3.setVisibility(View.VISIBLE);
                            sleeve3.setText(String.valueOf(slelist.get(c)));

                        }else if(c==3){
                            sleeve4.setVisibility(View.VISIBLE);
                            sleeve4.setText(String.valueOf(slelist.get(c)));

                        }else if(c==4){
                            sleeve5.setVisibility(View.VISIBLE);
                            sleeve5.setText(String.valueOf(slelist.get(c)));

                        }

                    }
                }
                JSONArray fitslist = jsonObject.getJSONArray("fit_list");
                if (fitslist.length() != 0) {

                    for (int c = 0; c < fitslist.length(); c++) {
                        if(c==0){
                            fit1.setVisibility(View.VISIBLE);
                            fit1.setText(String.valueOf(fitslist.get(c)));
                        }else if(c==1){
                            fit2.setVisibility(View.VISIBLE);
                            fit2.setText(String.valueOf(fitslist.get(c)));

                        }else if(c==2){
                            fit3.setVisibility(View.VISIBLE);
                            fit3.setText(String.valueOf(fitslist.get(c)));

                        }else if(c==3){
                            fit4.setVisibility(View.VISIBLE);
                            fit4.setText(String.valueOf(fitslist.get(c)));

                        }else if(c==4){
                            fit5.setVisibility(View.VISIBLE);
                            fit5.setText(String.valueOf(fitslist.get(c)));

                        }
                    }
                }
                JSONArray lenlist = jsonObject.getJSONArray("length_list");
                if (lenlist.length() != 0) {

                    for (int c = 0; c < lenlist.length(); c++) {
                        if(c==0){
                            leng1.setVisibility(View.VISIBLE);
                            leng1.setText(String.valueOf(lenlist.get(c)));
                        }else if(c==1){
                            leng2.setVisibility(View.VISIBLE);
                            leng2.setText(String.valueOf(lenlist.get(c)));

                        }else if(c==2){
                            leng3.setVisibility(View.VISIBLE);
                            leng3.setText(String.valueOf(lenlist.get(c)));

                        }else if(c==3){
                            leng4.setVisibility(View.VISIBLE);
                            leng4.setText(String.valueOf(lenlist.get(c)));

                        }else if(c==4){
                            leng5.setVisibility(View.VISIBLE);
                            leng5.setText(String.valueOf(lenlist.get(c)));

                        }
                    }
                }
                JSONArray sizeslist = jsonObject.getJSONArray("size_list");
                if (sizeslist.length() != 0) {


                    for (int c = 0; c < sizeslist.length(); c++) {
                        if(c==0){
                            s28.setVisibility(View.VISIBLE);
                            s28.setText(String.valueOf(sizeslist.get(c)));
                        }else if(c==1){
                            s30.setVisibility(View.VISIBLE);
                            s30.setText(String.valueOf(sizeslist.get(c)));

                        }else if(c==2){
                            s32.setVisibility(View.VISIBLE);
                            s32.setText(String.valueOf(sizeslist.get(c)));

                        }else if(c==3){
                            s34.setVisibility(View.VISIBLE);
                            s34.setText(String.valueOf(sizeslist.get(c)));

                        }else if(c==4){
                            s36.setVisibility(View.VISIBLE);
                            s36.setText(String.valueOf(sizeslist.get(c)));

                        }else if(c==5){
                            s38.setVisibility(View.VISIBLE);
                            s38.setText(String.valueOf(sizeslist.get(c)));

                        }else if(c==6){
                            s40.setVisibility(View.VISIBLE);
                            s40.setText(String.valueOf(sizeslist.get(c)));

                        }else if(c==7){
                            s42.setVisibility(View.VISIBLE);
                            s42.setText(String.valueOf(sizeslist.get(c)));

                        }else if(c==8){
                            s44.setVisibility(View.VISIBLE);
                            s44.setText(String.valueOf(sizeslist.get(c)));

                        }else if(c==9){
                            s46.setVisibility(View.VISIBLE);
                            s46.setText(String.valueOf(sizeslist.get(c)));

                        }else if(c==10){
                            s48.setVisibility(View.VISIBLE);
                            s48.setText(String.valueOf(lenlist.get(c)));

                        }else if(c==11){
                            sqty.setVisibility(View.VISIBLE);
                            sqty.setText(String.valueOf(lenlist.get(c)));

                        }
                    }
                }

            }catch (Exception e){

            }
        }


       }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            Intent intent = new Intent(getBaseContext(), ProductList.class);
            startActivity(intent);
        }
    }
    static class Headers {

        static GlideUrl getUrlWithHeaders(String url){
            return new GlideUrl(url, new LazyHeaders.Builder()
                    .addHeader("Cookie", "session_id=" + Session_ID)
                    .build());
        }
    }

    }
