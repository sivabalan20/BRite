package com.aspirantlab.salesagent;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;

public class CustomerCreate extends MainActivity implements NavigationView.OnNavigationItemSelectedListener {
    AutoCompleteTextView autocomplete;
    RelativeLayout relativeLayout;
    String[] arr = { "siva", "raja","mani",
            "kumar", "athavan,CA,karthi"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.customer_create, null, false);
        drawer.addView(contentView, 0);

        relativeLayout = (RelativeLayout)findViewById(R.id.mainfile);
        relativeLayout.setVisibility(View.GONE);

        autocomplete = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arr);

        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);
    }
}
