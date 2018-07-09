package com.sunbi.e_college.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sunbi.e_college.R;

public class AboutUsActivity extends AppCompatActivity {

//    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);


//        collapsingToolbarLayout.setTitleEnabled(false);
//        toolbar.setTitle("About Us");



//        ImageView imageview =(ImageView)findViewById(R.id.facebook);
//        imageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent facebookIntent = getOpenFacebookIntent(getApplicationContext());
//                startActivity(facebookIntent);
//            }
//        });

//        ImageView imageview1 =(ImageView)findViewById(R.id.insta);
//        imageview1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent instaintent = getApplication().getPackageManager().getLaunchIntentForPackage("com.instagram.android");
//             instaintent.setComponent(new ComponentName("com.instagram.android","com.instagram.android.activity.UrlHandlerActivity"));
//                instaintent.setData(Uri.parse("https://www.instagram.com/nishaabhattarai"));
//
//                startActivity(instaintent);
//
//            }
//        });

        ImageView imgView = (ImageView)findViewById(R.id.back);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iback = new Intent(AboutUsActivity.this, MainActivity.class);
                startActivity(iback);
            }
        });

        ImageView imageView = (ImageView)findViewById(R.id.aboutusarrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//                LinearLayout linearLayout =(LinearLayout)findViewById(R.id.aboutuslinearlayout);
//                linearLayout.setVisibility((linearLayout.getVisibility() == View.VISIBLE)
//                        ? View.GONE : View.VISIBLE);

                AlertDialog.Builder builder  = new AlertDialog.Builder(AboutUsActivity.this);

                View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupaboutus_team,null,false);

                final AlertDialog alertDialog = builder.create();

                alertDialog.setCancelable(false);

                alertDialog.setView(view1);

                ImageView imageView1 = (ImageView)view1.findViewById(R.id.exist);
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });





    }


    public void onBackPressed(){
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    public static Intent getOpenFacebookIntent(Context context) {
//
//        try {
//            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
//            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/1921894204790204"));
//        } catch (Exception e) {
//            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/arkverse"));
//
//        }
//    }



}
