package com.aspirantlab.salesagent;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class ApparelsOrderList extends MainActivity implements NavigationView.OnNavigationItemSelectedListener  {
      RelativeLayout relativeLayout;
    String MY_PREFS_NAME,User_ID,Session_ID;
    ArrayList<String> orderdatelist;
    ArrayList<String> ordernumberlist;
    ArrayList<String> agentnamelist;
    ArrayList<String> customernamelist;
    ArrayList<String> totalpislist,orderidlist,ordstatus;
    private RecyclerView place;
    private RecyclerView.LayoutManager layoutManager;
    PlacesAdapter placesAdapter;
    AlertDialog.Builder builder;
    String Order_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.shirt_list);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.shirt_main_list, null, false);
        drawer.addView(contentView, 0);


        relativeLayout = (RelativeLayout)findViewById(R.id.mainfile);
        relativeLayout.setVisibility(View.GONE);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        User_ID = prefs.getString("userid", null);
        Session_ID = prefs.getString("sessionid", null);

        place = (RecyclerView) findViewById(R.id.listView);

        builder = new AlertDialog.Builder(this);

        orderdatelist = new ArrayList<>();
        ordernumberlist = new ArrayList<>();
        agentnamelist = new ArrayList<>();
        customernamelist = new ArrayList<>();
        totalpislist = new ArrayList<>();
        orderidlist = new ArrayList<>();
        ordstatus = new ArrayList<>();

        new ShirtList().execute();

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getBaseContext(),ApparelsCustomerDetails.class);
                startActivity(i);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        MenuItem search_item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {

                //clear the previous data in search arraylist if exist
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String  keyword = s.toLowerCase();

                ArrayList<String>filodatelist = new ArrayList<>();
                ArrayList<String>filonumlist = new ArrayList<>();
                ArrayList<String>filagntlist = new ArrayList<>();
                ArrayList<String>filcnlist = new ArrayList<>();
                ArrayList<String>filtpiclist = new ArrayList<>();
                ArrayList<String>filtidlist = new ArrayList<>();
                ArrayList<String>filtstatlist = new ArrayList<>();


                for (int i = 0; i < customernamelist.size(); i++) {

                    final String cusname = customernamelist.get(i).toLowerCase();
                    final String orddate = orderdatelist.get(i).toLowerCase();
                    final String ordnum = ordernumberlist.get(i).toLowerCase();
                    final String agename = agentnamelist.get(i).toLowerCase();
                    final String totpis = totalpislist.get(i).toLowerCase();
                    final String ordid = orderidlist.get(i).toLowerCase();
                    final String ordsta = ordstatus.get(i).toLowerCase();
                    if (cusname.contains(keyword)||orddate.contains(keyword)||ordnum.contains(keyword)||agename.contains(keyword)||totpis.contains(keyword)||ordid.contains(keyword)||ordsta.contains(keyword)) {
                        filodatelist.add(orderdatelist.get(i));
                        filonumlist.add(ordernumberlist.get(i));
                        filagntlist.add(agentnamelist.get(i));
                        filcnlist.add(customernamelist.get(i));
                        filtpiclist.add(totalpislist.get(i));
                        filtidlist.add(orderidlist.get(i));
                        filtstatlist.add(ordstatus.get(i));
                    }
                }

                layoutManager = new LinearLayoutManager(getApplicationContext());
                placesAdapter = new PlacesAdapter(filodatelist,filonumlist,filagntlist,filcnlist,filtpiclist,filtidlist,filtstatlist);
                place.setLayoutManager(layoutManager);
                place.setAdapter(placesAdapter);
                placesAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
    class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder>  {
        ArrayList<String>adodatelist;
        ArrayList<String>adonumlist;
        ArrayList<String>adagntlist;
        ArrayList<String>adcnlist;
        ArrayList<String>adtpiclist;
        ArrayList<String>adoid;
        ArrayList<String>adstatus;



        private PlacesAdapter(ArrayList<String>odatelist,ArrayList<String>onumlist,ArrayList<String>agntlist,ArrayList<String>cnlist,ArrayList<String>tpiclist,ArrayList<String>oid,ArrayList<String>osta) {
            adodatelist = odatelist;
            adonumlist = onumlist;
            adagntlist = agntlist;
            adcnlist = cnlist;
            adtpiclist = tpiclist;
            adoid = oid;
            adstatus =osta;
        }

        @Override
        public PlacesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shirt_list, parent, false);

            return new PlacesAdapter.ViewHolder(v);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.setIsRecyclable(false);
            holder.orderdate.setText(adodatelist.get(position));
            holder.ordernumber.setText(adonumlist.get(position));
            holder.agentname.setText(adagntlist.get(position));
            holder.customername.setText(adcnlist.get(position));
            holder.totalpics.setText(adtpiclist.get(position));

            holder.selecteditem.setTag(position);
            holder.deleteorder.setTag(position);
            holder.orderid.setTag(position);
            holder.status.setTag(position);
            holder.statusimage.setTag(position);
            holder.image.setTag(position);


            String status = adstatus.get(position);
            if(status.equals("cancel")) {
                holder.status.setText("Cancel");
                holder.image.setImageDrawable(getResources().getDrawable(R.drawable.error));
                holder.statusimage.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                holder.status.setBackgroundColor(getResources().getColor(R.color.colorBlue));

            }else if(status.equals("draft")){
                holder.status.setText("Draft");
                holder.image.setImageDrawable(getResources().getDrawable(R.drawable.save));
                holder.statusimage.setBackgroundColor(getResources().getColor(R.color.colorgray));
                holder.status.setBackgroundColor(getResources().getColor(R.color.colorgray));
                holder.deleteorder.setVisibility(View.VISIBLE);
            }else if(status.equals("wait")){
                holder.status.setText("Wait");
                holder.image.setImageDrawable(getResources().getDrawable(R.drawable.wait));
                holder.statusimage.setBackgroundColor(getResources().getColor(R.color.colorpink));
                holder.status.setBackgroundColor(getResources().getColor(R.color.colorpink));
                holder.deleteorder.setVisibility(View.VISIBLE);
            }
            else if(status.equals("agent_confirmed")){
                holder.status.setText("Confirmed");
                holder.image.setImageDrawable(getResources().getDrawable(R.drawable.verified));
                holder.statusimage.setBackgroundColor(getResources().getColor(R.color.colorgreen));
                holder.status.setBackgroundColor(getResources().getColor(R.color.colorgreen));
            }
            holder.selecteditem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) holder.selecteditem.getTag();
                    String status = adstatus.get(pos);
                    if(!status.equals("cancel")) {
                        String pid = adoid.get(pos);
                        Bundle bundle = new Bundle();
                        bundle.putString("shirtoid", pid);
                        bundle.putString("status", status);
                        Intent i = new Intent(getBaseContext(), ApparelsEditOrderDetails.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                }
            });
            holder.deleteorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    builder.setMessage("Do you want to cancel this order ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    int pos = (int) holder.deleteorder.getTag();
                                    Order_ID = adoid.get(pos);
                                    new Agnetcancel().execute();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();

                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.show();

                }
            });
        }



        @Override
        public int getItemCount(){
            return adcnlist.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView orderdate, orderid,ordernumber,agentname,customername,totalpics,status;
            CardView selecteditem;
            ImageView deleteorder,image;
            RelativeLayout statusimage;
            public ViewHolder(View itemView) {
                super(itemView);
                selecteditem = (CardView) itemView.findViewById(R.id.selecteditem);
                orderdate = (TextView) itemView.findViewById(R.id.odate);
                orderid = (TextView) itemView.findViewById(R.id.orderid);
                ordernumber = (TextView) itemView.findViewById(R.id.onumber);
                agentname = (TextView) itemView.findViewById(R.id.aname);
                customername = (TextView) itemView.findViewById(R.id.cname);
                totalpics = (TextView) itemView.findViewById(R.id.tpics);
                status = (TextView) itemView.findViewById(R.id.status);
                deleteorder = (ImageView) itemView.findViewById(R.id.prodelete);
                image = (ImageView) itemView.findViewById(R.id.image);
                statusimage = (RelativeLayout) itemView.findViewById(R.id.statusimage);
            }
        }
    }
    class ShirtList extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ApparelsOrderList.this);
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
                params1.put("model", "apparel.order");
                params1.put("method", "get_apparel_order_data");
                params1.put("kwargs", params2);
                params1.put("args",array);
                array.put(params3);
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
                    JSONArray cuslist = data.getJSONArray("result");
                    for(int i=0; i <  cuslist.length();i++){
                        JSONObject list = cuslist.getJSONObject(i);
                        String ordernumber = list.get("name").toString();
                        String date = list.get("order_date").toString();
                        String agent_name = list.get("agent_name").toString();
                        String customername = list.get("customer_name").toString();
                        String totalpics = list.get("no_pieces").toString();
                        String id = list.get("id").toString();
                        String status = list.get("state").toString();
                        orderdatelist.add(date);
                        ordernumberlist.add(ordernumber);
                        agentnamelist.add(agent_name);
                        customernamelist.add(customername);
                        totalpislist.add(totalpics);
                        orderidlist.add(id);
                        ordstatus.add(status);
                    }


                    place.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(ApparelsOrderList.this);
                    place.setLayoutManager(layoutManager);
                    placesAdapter = new PlacesAdapter(orderdatelist,ordernumberlist,agentnamelist,customernamelist,totalpislist,orderidlist,ordstatus);
                    place.setAdapter(placesAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Apparel list is empty", Toast.LENGTH_LONG).show();
                }

            }


        }

    }
    class Agnetcancel extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ApparelsOrderList.this);
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
                params1.put("model", "apparel.order");
                params1.put("method", "action_cancel_call");
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
                    if(!finalresult.equals("")&&finalresult.equals("cancel")){
                        orderdatelist = new ArrayList<>();
                        ordernumberlist = new ArrayList<>();
                        agentnamelist = new ArrayList<>();
                        customernamelist = new ArrayList<>();
                        totalpislist = new ArrayList<>();
                        orderidlist = new ArrayList<>();
                        ordstatus = new ArrayList<>();

                        new ShirtList().execute();
                        Toast.makeText(getApplicationContext(), "Order cancelled Successfully", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), errormsg, Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Order cancelled Failed", Toast.LENGTH_LONG).show();
                }
            }

        }

    }
}
