package com.sunbi.e_college.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.sunbi.e_college.AppControllerPackage.AppController;
import com.sunbi.e_college.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class NoticeActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//            collapsingToolbar =(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
//            collapsingToolbar.setTitle("News and Events");



               ActionBar ab=getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        ImageView imageView =(ImageView)findViewById(R.id.share);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT,"E-College");
                    String string = "Let me recommend you this application";
                    string = string + "https://play.google.com/store/apps/details?id=the.package.id \\n\\n";
                    i.putExtra(Intent.EXTRA_TEXT,string);
                    startActivity(Intent.createChooser(i, "Choose One"));

                }
                catch(Exception e){}
            }
        });



        getContent();


    }

    private void getContent() {

        final SpotsDialog spotsDialog = new SpotsDialog(NoticeActivity.this);
        spotsDialog.show();

        String url ="http://nepalidolconcert.com/demo/intern/api/notices";

        Log.d("url",url);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                spotsDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int n = 0; n < jsonArray.length(); n++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(n);

                        String title = jsonObject.getString("noticetitle");
                        String date = jsonObject.getString("date");
                        String content = jsonObject.getString("noticecontent");
                        String image = jsonObject.getString("image");

                        TextView textnotice = (TextView) findViewById(R.id.txtNotice);
                        TextView textdate = (TextView) findViewById(R.id.txtDate);
                        TextView textcontent = (TextView) findViewById(R.id.txtContent);
                        ImageView imgnotice =(ImageView)findViewById(R.id.imgNotice);

                        textnotice.setText(title);
                        textdate.setText(date);
                        textcontent.setText(content);
                        Glide.with(getApplicationContext()).load(image).into(imgnotice);


                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(getRequest);

    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         if (item.getItemId()==android.R.id.home){

             super.onBackPressed();

         }
        return super.onOptionsItemSelected(item);
    }

}
