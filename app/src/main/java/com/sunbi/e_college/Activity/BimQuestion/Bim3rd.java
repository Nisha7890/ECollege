package com.sunbi.e_college.Activity.BimQuestion;

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

public class Bim3rd extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;


    ArrayList<Bim3rdDataList> arrayList = new ArrayList<>();

    String question;
    String url;
    String url1;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bim3rd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        recyclerView = (RecyclerView) findViewById(R.id.bim3rd);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getBim3rdyear();



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


    private void getBim3rdyear(){

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

                        Bim3rdDataList bim3rdDataList = new Bim3rdDataList(year,id);
                        arrayList.add(bim3rdDataList);
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

        ArrayList<Bim3rdDataList> newArrayList;
        Context context;

        public MyAdapter(ArrayList<Bim3rdDataList> arrayList, Context applicationContext) {

            this.newArrayList = arrayList;
            this.context = applicationContext;



        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupbim3rdyear,parent,false);
            return new  ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final Bim3rdDataList bim3rdDataList = newArrayList.get(position);
            holder.year.setText(bim3rdDataList.getYear());
            holder.year.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Bim3rd.this);
                    final View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupbim3rdsem,null,false);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.setView(view1);

                    final String url = "http://nepalidolconcert.com/demo/intern/api/questions" + "/" + 1 + "/" + 3 + "/" + bim3rdDataList.getId() + "/" + id ;

                    Log.d("url",url);

                    JsonObjectRequest getReQuest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonArray = response.getJSONArray("data");

                                if(jsonArray !=null && jsonArray.length() >0) {

                                    CardView cardView = (CardView) view1.findViewById(R.id.cardview);
                                    cardView.setVisibility(View.VISIBLE);


                                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                                    question = jsonObject.getString("questions");

                                    final Bim3rdDataList bim3rdDataList = new Bim3rdDataList(question);
                                    arrayList.add(bim3rdDataList);

                                    ImageView imageView = (ImageView) view1.findViewById(R.id.Bim3rddownload);

                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            downloadFile(bim3rdDataList.getQuestion());
                                        }
                                    });

                                    TextView textView = (TextView) view1.findViewById(R.id.bim3rdView);

                                    textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getQuestionView(bim3rdDataList.getQuestion());
                                        }
                                    });

                                }

                                else {
                                    TextView textView = (TextView) view1.findViewById(R.id.text);
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


        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView year;


            public ViewHolder(View itemView) {
                super(itemView);

                year = (TextView) itemView.findViewById(R.id.bim3rdyear);
            }
        }
    }
    private void downloadFile(String questionsurl) {

        url = questionsurl;
        Log.d("url", url);

        if (ActivityCompat.checkSelfPermission(Bim3rd.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Bim3rd.this,
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
