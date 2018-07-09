package com.sunbi.e_college.Activity;

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
import android.widget.Toast;

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

import dmax.dialog.SpotsDialog;

public class BimSyllabusActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    ArrayList<BimSyllabusDataList> arrayList = new ArrayList<>();

    String syllabus;
    String url;
    String url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bim_syllabus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView =(RecyclerView)findViewById(R.id.bimsyllabusrecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getBimSyllabus();
    }

    private void getBimSyllabus(){

        final SpotsDialog spotsDialog = new SpotsDialog(BimSyllabusActivity.this);
        spotsDialog.show();

        String url=" http://nepalidolconcert.com/demo/intern/api/semesters";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                spotsDialog.dismiss();

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                            for(int i =0; i<jsonArray.length();i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String semester = jsonObject.getString("semester");
                                String id = jsonObject.getString("id");

                                Log.d("semester", semester);

                                BimSyllabusDataList bimSyllabusDataList = new BimSyllabusDataList(semester,id);
                                arrayList.add(bimSyllabusDataList);
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

    public  void onBackPressed(){
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        ArrayList<BimSyllabusDataList> newArrayList;
        Context context;

        public MyAdapter(ArrayList<BimSyllabusDataList> arrayList, Context context) {

            this.newArrayList = arrayList;
            this.context = context;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bimsyllabuslist,parent,false);
                return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final BimSyllabusDataList bimSyllabusDataList = newArrayList.get(position);

            holder.semester.setText(bimSyllabusDataList.getSemester());

            holder.semester.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BimSyllabusActivity.this);

                   final View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupbimsyllabus,null,false);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.setView(v);

                    String url = "http://nepalidolconcert.com/demo/intern/api/syllabus/" + 1 + "/" + bimSyllabusDataList.getId();

                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonArray = response.getJSONArray("data");

                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                syllabus = jsonObject.getString("syllabus");
                                final BimSyllabusDataList bimSyllabusDataList = new BimSyllabusDataList(syllabus);
                                arrayList.add(bimSyllabusDataList);

                                ImageView download =(ImageView) v.findViewById(R.id.Bimdownload);

                                download.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                      Toast.makeText(BimSyllabusActivity.this,"Clicked",Toast.LENGTH_LONG).show();
                                        downloadFile(bimSyllabusDataList.getSyllabus());

                                    }
                                });

                                TextView textView =(TextView)v.findViewById(R.id.bimView);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        getBimSyllabusView(bimSyllabusDataList.getSyllabus());

                                    }
                                });




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


                    alertDialog.show();


                }
            });






        }

        @Override
        public int getItemCount() {
            return newArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView semester;

            public ViewHolder(View itemView) {
                super(itemView);

                semester =(TextView)itemView.findViewById(R.id.bimsemester);
            }
        }
    }

    private void downloadFile(String syllabusurl){

        url = syllabusurl;

        if(ActivityCompat.checkSelfPermission(BimSyllabusActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

           ActivityCompat.requestPermissions(BimSyllabusActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else{

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Downloading...");
            request.setTitle("Syllabus Download !!");

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            }

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"/Syllabus/" + "/" +"Sample_" + ".pdf");


            DownloadManager manager =(DownloadManager) getSystemService(DOWNLOAD_SERVICE);

            manager.enqueue(request);


        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(grantResults.length>0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            String url1 = url;

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url1));
            request.setDescription("Downloading...");
            request.setTitle("Syllabus");

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Syllabus" + "/" +"Sample_" + ".pdf");

            DownloadManager manager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);

            manager.enqueue(request);

        }
    }


    private void getBimSyllabusView(String bimsyllabusView){

        url2 = bimsyllabusView;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url2));
        startActivity(intent);
    }
}
