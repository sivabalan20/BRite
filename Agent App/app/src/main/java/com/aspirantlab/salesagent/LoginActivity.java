package com.aspirantlab.salesagent;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class LoginActivity extends AppCompatActivity {
       Button button;
       EditText username,password;
       String MY_PREFS_NAME ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.login_screen);

         button = (Button)findViewById(R.id.btn_login);
         username = (EditText)findViewById(R.id.input_username);
         password = (EditText)findViewById(R.id.input_password);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(username.getText().toString().equals("") && password.getText().toString().equals("")){
                     Toast.makeText(getApplicationContext(), "Enter the Username & Password", Toast.LENGTH_LONG).show();
                 }else if(username.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter the Username", Toast.LENGTH_LONG).show();
                 }else if(password.getText().toString().equals("")){
                     Toast.makeText(getApplicationContext(), "Enter the Password", Toast.LENGTH_LONG).show();
                 }else{
                     new Login().execute();
                 }
             }
         });
    }
    @Override
    public void onBackPressed() {
        // do nothing.
    }
    class Login extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonParams = new JSONObject();
            JSONObject params1 = new JSONObject();


            HttpClient client = new DefaultHttpClient();

            // Prepare a request object
            HttpPost post = new HttpPost(GetURL.getloginserverip());
            post.setHeader("Content-type", "application/json");
            try {
                params1.put("db", GetURL.getdb());
                params1.put("login", username.getText().toString());
                params1.put("password", password.getText().toString());

                jsonParams.put("id", "ID");
                jsonParams.put("method", "call");
                jsonParams.put("params", params1);
                jsonParams.put("jsonrpc", "2.0");

                StringEntity se = new StringEntity(jsonParams.toString());
                 //post.setHeader("Content-length", "" + se.getContentLength()); //header has to be set after jsonparams are complete

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
                    JSONObject data1 = new JSONObject(String.valueOf(data.getJSONObject("result")));
                    String UID = data1.getString("uid");
                    String SESSIONID = data1.getString("session_id");

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("userid",UID);
                    editor.putString("sessionid",SESSIONID);
                    editor.putBoolean("loginstatus",true);
                    editor.commit();
                    editor.apply();

                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Invalid login", Toast.LENGTH_LONG).show();
                }

            }

        }

    }

}
