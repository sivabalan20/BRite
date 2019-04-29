package com.aspirantlab.salesagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.Adapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

public class CustomerList extends MainActivity implements NavigationView.OnNavigationItemSelectedListener {



    RelativeLayout relativeLayout;
    String MY_PREFS_NAME,User_ID,Session_ID;
    ArrayList<String> customeridlist;
    ArrayList<String> customernamelist;
    ArrayList<String> emaillist;
    ArrayList<String> mobilenumberlist;
    ArrayList<String> tinnolist;
    ArrayList<String> gstnolist;
    ArrayList<String> contactpersonlist;
    ArrayList<JSONObject> customerdata;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    PlacesAdapter placesAdapter;
    String cusname,pnum,mail,addr,order,dispatch,paid;
    NetworkInfo netInfo;
    ConnectivityManager connManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.customer_main_list, null, false);
        drawer.addView(contentView, 0);

        relativeLayout = (RelativeLayout)findViewById(R.id.mainfile);
        relativeLayout.setVisibility(View.GONE);

        connManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        netInfo = connManager.getActiveNetworkInfo();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        User_ID = prefs.getString("userid", null);
        Session_ID = prefs.getString("sessionid", null);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        customernamelist = new ArrayList<>();
        emaillist = new ArrayList<>();
        mobilenumberlist = new ArrayList<>();
        tinnolist = new ArrayList<>();
        gstnolist= new ArrayList<>();
        contactpersonlist = new ArrayList<>();
        customerdata = new ArrayList<>();
        customeridlist = new ArrayList<>();


        if(netInfo !=null) {
            new Customerlist().execute();
        }else{
            getcustomerdatafromdb();
        }


        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getBaseContext(),CustomerCreate.class);
                startActivity(i);
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
                ArrayList<String>filidlist = new ArrayList<>();
                ArrayList<String>filnamelist = new ArrayList<>();
                ArrayList<String>filmaillist = new ArrayList<>();
                ArrayList<String>filmoblist = new ArrayList<>();
                ArrayList<String>fitinlist = new ArrayList<>();
                ArrayList<String>figstlist = new ArrayList<>();
                ArrayList<String>filconlist = new ArrayList<>();

                for (int i = 0; i < customernamelist.size(); i++) {

                    final String cusname = customernamelist.get(i).toLowerCase();
                    final String email = emaillist.get(i).toLowerCase();
                    final String mobnum = mobilenumberlist.get(i).toLowerCase();
                    final String tinno = tinnolist.get(i).toLowerCase();
                    final String gstno = gstnolist.get(i).toLowerCase();
                    final String conpers = contactpersonlist.get(i).toLowerCase();
                    if (cusname.contains(keyword)||email.contains(keyword)||mobnum.contains(keyword)||tinno.contains(keyword)||gstno.contains(keyword)||conpers.contains(keyword)) {
                        filidlist.add(customeridlist.get(i));
                        filnamelist.add(customernamelist.get(i));
                        filmaillist.add(emaillist.get(i));
                        filmoblist.add(mobilenumberlist.get(i));
                        fitinlist.add(tinnolist.get(i));
                        figstlist.add(gstnolist.get(i));
                        filconlist.add(contactpersonlist.get(i));
                    }
                }

                layoutManager = new LinearLayoutManager(getApplicationContext());
                placesAdapter = new PlacesAdapter(filidlist,filnamelist,filmaillist,filmoblist,fitinlist,figstlist,filconlist);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(placesAdapter);
                placesAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
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
        ArrayList<String>adcusidlist;
        ArrayList<String>adcusnamelist;
        ArrayList<String>adcusmaillist;
        ArrayList<String>admoblist;
        ArrayList<String>adtinlist;
        ArrayList<String>adgstlist;
        ArrayList<String>adconlist;

        private PlacesAdapter(ArrayList<String>idlist,ArrayList<String>nlist,ArrayList<String>emlist,ArrayList<String>mlist,ArrayList<String>tlist,ArrayList<String>gstlist,ArrayList<String>clist) {
            adcusidlist = idlist;
            adcusnamelist = nlist;
            adcusmaillist = emlist;
            admoblist = mlist;
            adtinlist = tlist;
            adgstlist = gstlist;
            adconlist = clist;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.setIsRecyclable(false);
            holder.customeridname.setText(adcusidlist.get(position));
            holder.customername.setText(adcusnamelist.get(position));
            holder.emailid.setText(adcusmaillist.get(position));
            holder.mobile.setText(admoblist.get(position));
            holder.tinno.setText(adtinlist.get(position));
            holder.gstno.setText(adgstlist.get(position));
            holder.contactperson.setText(adconlist.get(position));
            holder.cardView.setTag(position);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) holder.cardView.getTag();
                    String cusid = adcusidlist.get(pos);
                    for(int i=0;i<customerdata.size();i++){
                        JSONObject data = customerdata.get(i);
                        try {
                            String id = data.get("id").toString();
                            if(id.equals(cusid)){
                                 cusname = data.get("name").toString();
                                 mail = data.get("email").toString();
                                 pnum = data.get("mobile").toString();
                                 addr = data.get("city").toString();
                                 order = data.get("tot_order").toString();
                                 dispatch = data.get("tot_dispatch").toString();
                                 paid = data.get("tot_invoice").toString();
                                break;


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Intent intent=new Intent(getBaseContext(),CustomerData.class);
                    intent.putExtra("CusName", cusname);
                    intent.putExtra("Pnumber", pnum);
                    intent.putExtra("Mailid", mail);
                    intent.putExtra("Address", addr);
                    if(order.equals("null")){
                        intent.putExtra("Orders", "0");
                    }else{
                        intent.putExtra("Orders", order);
                    }
                    if(dispatch.equals("null")){
                        intent.putExtra("Dispatch", "0");
                    }else{
                        intent.putExtra("Dispatch", dispatch);
                    }
                    if(paid.equals("null")){
                        intent.putExtra("Paid", "0");
                    }else{
                        intent.putExtra("Paid", paid);
                    }
                    startActivity(intent);

                }
            });


        }


        @Override
        public int getItemCount() {
            return adcusnamelist.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView customername, emailid,mobile,tinno,gstno,contactperson,customeridname;
            CardView cardView;

            public ViewHolder(View itemView) {
                super(itemView);
                customeridname = (TextView) itemView.findViewById(R.id.cusid);
                customername = (TextView) itemView.findViewById(R.id.cusname);
                emailid = (TextView) itemView.findViewById(R.id.email);
                mobile = (TextView) itemView.findViewById(R.id.mobile);
                tinno = (TextView) itemView.findViewById(R.id.tinno);
                gstno = (TextView) itemView.findViewById(R.id.gstno);
                contactperson = (TextView) itemView.findViewById(R.id.conperson);
                cardView = (CardView) itemView.findViewById(R.id.cardselect);
            }
        }
    }

    class Customerlist extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CustomerList.this);
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
                        String id = list.get("id").toString();
                        String customername = list.get("name").toString();
                        String mailid = list.get("email").toString();
                        String mobile = list.get("mobile").toString();
                        String tinno = list.get("lst_no").toString();
                        String gstno = list.get("gst_no").toString();
                        String agent = list.get("contact_person").toString();
                        customeridlist.add(id);
                        customernamelist.add(customername);
                        emaillist.add(mailid);
                        mobilenumberlist.add(mobile);
                        tinnolist.add(tinno);
                        contactpersonlist.add(agent);
                        gstnolist.add(gstno);
                        customerdata.add(list);
                    }


                    recyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                     placesAdapter = new PlacesAdapter(customeridlist,customernamelist,emaillist,mobilenumberlist,tinnolist,gstnolist,contactpersonlist);
                    recyclerView.setAdapter(placesAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "customer list is empty", Toast.LENGTH_LONG).show();
                }

            }

        }

    }

    public void getcustomerdatafromdb(){
        customerdata =

    }

}
