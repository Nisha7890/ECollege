package com.sunbi.e_college.Activity.BbaQuestion;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sunbi.e_college.AppControllerPackage.AppController;
import com.sunbi.e_college.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Bba5th extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;


    ArrayList<Bba5thDataList> arrayList = new ArrayList<>();

    String question;
    String url;
    String url1;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bba5th);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Log.d("intentid",id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.bba5th);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getBba5thyear();

    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){

            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getBba5thyear(){

        String url = "http://nepalidolconcert.com/demo/intern/api/years";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0; i< jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String year = jsonObject.getString("year");
                        String id = jsonObject.getString("id");

                        Bba5thDataList bba5thDataList = new Bba5thDataList(year,id);
                        arrayList.add(bba5thDataList);
                    }

                    adapter = new MyAdapter(arrayList,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
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

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        ArrayList<Bba5thDataList> newArrayList;
        Context context;

        public MyAdapter(ArrayList<Bba5thDataList> arrayList, Context applicationContext) {

            this.newArrayList = arrayList;
            this.context = applicationContext;


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bba5thyear,parent,false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final Bba5thDataList bba5thDataList = newArrayList.get(position);
            holder.year.setText(bba5thDataList.getYear());

            holder.year.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Bba5th.this);
                    final View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupbba5thsem,null,false);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.setView(view1);

                    final String url = "http://nepalidolconcert.com/demo/intern/api/questions" + "/" + 2 + "/" + 10 + "/" + bba5thDataList.getId() + "/" + id;

                    Log.d("url",url);

                    JsonObjectRequest getReQuest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonArray = response.getJSONArray("data");

                                if(jsonArray !=null && jsonArray.length() >0) {

                                    CardView cardView = (CardView)view1.findViewById(R.id.cardview);
                                    cardView.setVisibility(View.VISIBLE);

                                final JSONObject jsonObject = jsonArray.getJSONObject(0);

                                question = jsonObject.getString("questions");

                                final Bba5thDataList bba5thDataList = new Bba5thDataList(question);
                                arrayList.add(bba5thDataList);

                                ImageView imageView = (ImageView) view1.findViewById(R.id.Bba5thdownload);

                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        downloadFile(bba5thDataList.getQuestion());


                                    }
                                });

                                TextView textView =(TextView)view1.findViewById(R.id.bba5thView);

                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getQuestionView(bba5thDataList.getQuestion());
                                    }
                                });

                                }

                                else {
                                    TextView textView = (TextView)view1.findViewById(R.id.text);
                                    textView.setVisibility(View.VISIBLE);

                                }



                            } catch (JSONException e) {


                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    AppController.getInstance().addToRequestQueue(getReQuest);

                    alertDialog.show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return newArrayList.size();
        }

        public class  ViewHolder extends RecyclerView.ViewHolder{

            TextView year;


            public ViewHolder(View itemView) {
                super(itemView);

                year = (TextView)itemView.findViewById(R.id.bba5thyear);
            }
        }
    }


    private void downloadFile(String questionsurl) {

        url = questionsurl;
        Log.d("url", url);

        if (ActivityCompat.checkSelfPermission(Bba5th.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Bba5th.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {




            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Downloading...");
            request.setTitle("Question Download !!");


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Question/" + "/" + "Sample_" + ".pdf");


            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            manager.enqueue(request);

        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            String url1 = url;
            Log.d("questionsurl",url1);

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url1));
            request.setDescription("Downloading...");
            request.setTitle("Question Download !!");


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Question/" + "/" + "Sample_" + ".pdf");


            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            manager.enqueue(request);



        }
    }

    private void getQuestionView(String questionView){

        url1 = questionView;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url1));
        startActivity(intent);

    }



}
