package com.aspirantlab.salesagent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspirantlab.salesagent.Database.DatabaseHelper;
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

public class ProductList extends MainActivity implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout relativeLayout;
    String MY_PREFS_NAME,User_ID,Session_ID;
    ArrayList<String> productnamelist;
    ArrayList<String> productcateglist;
    ArrayList<String> productidlist;
    ArrayList<String> wsplist;
    ArrayList<String> mrplist;
    ArrayList<JSONObject> productlist;
    private RecyclerView place;
    private RecyclerView.LayoutManager layoutManager;
    PlacesAdapter placesAdapter;
    NetworkInfo netInfo;
    ConnectivityManager connManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.shirt_list);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.product_main_list, null, false);
        drawer.addView(contentView, 0);

        relativeLayout = (RelativeLayout)findViewById(R.id.mainfile);
        relativeLayout.setVisibility(View.GONE);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Session_ID = prefs.getString("sessionid", null);

        connManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        netInfo = connManager.getActiveNetworkInfo();

        place = (RecyclerView) findViewById(R.id.listView);

        productnamelist = new ArrayList<>();
        productcateglist = new ArrayList<>();
        productidlist = new ArrayList<>();
        wsplist = new ArrayList<>();
        mrplist = new ArrayList<>();
        productlist  = new ArrayList<JSONObject>();

       if(netInfo !=null) {
           new productlist().execute();
       }else{
           getproductdatafromdb();
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

                ArrayList<String>filnamelist = new ArrayList<>();
                ArrayList<String>filcatgelist = new ArrayList<>();
                ArrayList<String>filpidlist = new ArrayList<>();
                ArrayList<String>filwsplist = new ArrayList<>();
                ArrayList<String>filmrplist = new ArrayList<>();

                for (int i = 0; i < productnamelist.size(); i++) {

                    final String pname = productnamelist.get(i).toLowerCase();
                    final String pcatge = productcateglist.get(i).toLowerCase();
                    final String pid = productidlist.get(i).toLowerCase();
                    final String pwsp = wsplist.get(i).toLowerCase();
                    final String pmrp = mrplist.get(i).toLowerCase();
                    if (pname.contains(keyword)||pcatge.contains(keyword)||pid.contains(keyword)||pwsp.contains(keyword)||pmrp.contains(keyword)) {

                        filnamelist.add(productnamelist.get(i));
                        filcatgelist.add(productcateglist.get(i));
                        filpidlist.add(productidlist.get(i));
                        filwsplist.add(wsplist.get(i));
                        filmrplist.add(mrplist.get(i));
                    }
                }

                layoutManager = new LinearLayoutManager(getApplicationContext());
                placesAdapter = new PlacesAdapter(filnamelist,filcatgelist,filpidlist,filwsplist,filmrplist);
                place.setLayoutManager(layoutManager);
                place.setAdapter(placesAdapter);
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

        ArrayList<String>adpronamelist;
        ArrayList<String>adcatgelist;
        ArrayList<String>adpidlist;
        ArrayList<String>adwsplist;
        ArrayList<String>admrplist;
        private PlacesAdapter(ArrayList<String>nlist,ArrayList<String>catlist,ArrayList<String>pidlist,ArrayList<String>wsplist,ArrayList<String>mrplist) {
            adpronamelist = nlist;
            adcatgelist = catlist;
            adpidlist = pidlist;
            adwsplist = wsplist;
            admrplist = mrplist;
        }

        @Override
        public PlacesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.setIsRecyclable(false);
            holder.productname.setText(adpronamelist.get(position));
            holder.productcatge.setText(adcatgelist.get(position));
            holder.productid.setText(adpidlist.get(position));
            holder.wsp.setText(adwsplist.get(position));
            holder.mrp.setText(admrplist.get(position));
            holder.cardView.setTag(position);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) holder.cardView.getTag();
                    String pid = adpidlist.get(pos);
                    for(int i=0;i<productlist.size();i++){
                        JSONObject list = productlist.get(i);
                        try {
                            String productid = list.get("id").toString();
                            if (productid.equals(pid)) {
                                Intent intent=new Intent(getBaseContext(),ProductView.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("productdata", list.toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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

            private TextView productname, productcatge,productid,wsp,mrp;
            CardView cardView;
            public ViewHolder(View itemView) {
                super(itemView);
                productname = (TextView) itemView.findViewById(R.id.pname);
                productcatge = (TextView) itemView.findViewById(R.id.pcatge);
                productid = (TextView) itemView.findViewById(R.id.pid);
                wsp = (TextView) itemView.findViewById(R.id.pwsp);
                mrp = (TextView) itemView.findViewById(R.id.pmrp);
                cardView = (CardView) itemView.findViewById(R.id.selectcardview);


            }
        }
    }

    class productlist extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProductList.this);
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
                        String productcateg = list.get("product_categ").toString();
                        String productid = list.get("id").toString();
                        String wsp = list.get("wsp").toString();
                        String mrp = list.get("mrp").toString();
                        productnamelist.add(productname);
                        productcateglist.add(productcateg);
                        productidlist.add(productid);
                        wsplist.add(wsp);
                        mrplist.add(mrp);
                        productlist.add(list);
                    }


                    place.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(ProductList.this);
                    place.setLayoutManager(layoutManager);
                    placesAdapter = new PlacesAdapter(productnamelist,productcateglist,productidlist,wsplist,mrplist);
                    place.setAdapter(placesAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Product list is empty", Toast.LENGTH_LONG).show();
                }

            }

        }

    }
    public void getproductdatafromdb(){
        productlist = databaseHelper.getProductsData();
        for (int i=0;i<productlist.size();i++){
            JSONObject list = productlist.get(i);
            try {
                String productname = list.get("name").toString();
                String productcateg = list.get("product_categ").toString();
                String productid = list.get("id").toString();
                String wsp = list.get("wsp").toString();
                String mrp = list.get("mrp").toString();
                productnamelist.add(productname);
                productcateglist.add(productcateg);
                productidlist.add(productid);
                wsplist.add(wsp);
                mrplist.add(mrp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        place.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ProductList.this);
        place.setLayoutManager(layoutManager);
        placesAdapter = new PlacesAdapter(productnamelist,productcateglist,productidlist,wsplist,mrplist);
        place.setAdapter(placesAdapter);
    }

}

