package com.sunbi.e_college.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sunbi.e_college.R;

public class ContactActivity extends AppCompatActivity  {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.call);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9849858485"));
                startActivity(intent);

            }
        });



        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.email);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] recipients = new String[]{"nishabhattarai7890@gmail.com", ""};
                Intent testIntent = new Intent(android.content.Intent.ACTION_SEND);
                testIntent.setType("text/plain");
                testIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                testIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Message Body");
                testIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
                startActivity(testIntent);
            }
        });

        LinearLayout linearLayout2 =(LinearLayout) findViewById(R.id.message);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "9849858485";  // The number on which you want to send SMS
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
            }
        });

        LinearLayout linearLayout3=(LinearLayout) findViewById(R.id.linearlocation);
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent imap = new Intent(ContactActivity.this,MapsActivity.class);
               startActivity(imap);

            }
        });

        TextView textView =(TextView)findViewById(R.id.click);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ic = new Intent(ContactActivity.this,FeedbackActivity.class);
                startActivity(ic);
            }
        });

    }


    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }




}
