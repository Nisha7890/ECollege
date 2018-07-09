package com.sunbi.e_college.Activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

import java.io.File;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class BbaSyllabusActivity2 extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    ArrayList<BbaSyllabusDataList> arrayList = new ArrayList<>();
//    ArrayList<Boolean> isClicked = new ArrayList<>();

    String syllabus;
    String url;
    String url1;
     int row_index = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bba_syllabus2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//         id = getIntent().getStringExtra("id");


        recyclerView = (RecyclerView) findViewById(R.id.bbasyllabusrecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        getBbaSyllabus();


    }

    private void getBbaSyllabus() {

        final SpotsDialog spotsDialog = new SpotsDialog(BbaSyllabusActivity2.this);
        spotsDialog.show();

        String url = "http://nepalidolconcert.com/demo/intern/api/semesters";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                spotsDialog.dismiss();

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String semester = jsonObject.getString("semester");
                        String id = jsonObject.getString("id");

                        BbaSyllabusDataList bbaSyllabusDataList = new BbaSyllabusDataList(semester, id);
                        arrayList.add(bbaSyllabusDataList);

                    }

                    adapter = new MyAdapter(arrayList, getApplicationContext());
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

    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        ArrayList<BbaSyllabusDataList> newArrayList;
        Context context;





        public MyAdapter(ArrayList<BbaSyllabusDataList> arrayList, Context context) {
            this.newArrayList = arrayList;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bbasyllabuslist, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {




            final BbaSyllabusDataList bbaSyllabusDataList = newArrayList.get(position);

            holder.semester.setText(bbaSyllabusDataList.getSemester());




//            holder.semester.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
//                    if ((!holder.linearLayout.isShown())) {
//                        holder.linearLayout.setVisibility(View.VISIBLE);
//                    } else {
//                        holder.linearLayout.setVisibility(View.GONE);
//                    }
//
//                }
//            });

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    holder.semester.setTextColor(Color.RED);

                    arrayList.clear();

                    AlertDialog.Builder builder = new AlertDialog.Builder(BbaSyllabusActivity2.this);

                    final View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupbbasyllabus, null, false);


                    final AlertDialog alertDialog = builder.create();

                    alertDialog.setCancelable(false);

                    alertDialog.setView(view1);



                    String url = "http://nepalidolconcert.com/demo/intern/api/syllabus/" + 2 + "/" + bbaSyllabusDataList.getId();
                    Log.d("url", url);

                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

//                            spotsDialog.dismiss();


                            try {
                                JSONArray jsonArray = response.getJSONArray("data");

                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                syllabus = jsonObject.getString("syllabus");

                                final BbaSyllabusDataList bbaSyllabusDataList = new BbaSyllabusDataList(syllabus);
                                arrayList.add(bbaSyllabusDataList);

                                ImageView imageView = (ImageView) view1.findViewById(R.id.Bbadownload);
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        downloadFile(bbaSyllabusDataList.getSyllabus());
                                        holder.semester.setTextColor(Color.BLACK);
                                        alertDialog.dismiss();
//
                                    }
                                });

                                TextView textView = (TextView)view1.findViewById(R.id.BbaView);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        getSyllabusView(bbaSyllabusDataList.getSyllabus());
                                        holder.semester.setTextColor(Color.BLACK);
                                        alertDialog.dismiss();
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

//                    row_index=position;
//
//                    if(row_index==position ){
//                        holder.semester.setTextColor(Color.RED);
//                    }
//
//
//                    else
//                    {
//                        holder.semester.setBackgroundColor(Color.BLACK);
//
//                    }




        }


        @Override
        public int getItemCount() {
            return newArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView semester;
            CardView cardView;

//            LinearLayout linearLayout;

            public ViewHolder(View itemView) {
                super(itemView);

                semester = (TextView) itemView.findViewById(R.id.bbasemester);
                cardView = (CardView)itemView.findViewById(R.id.cardview);




//               linearLayout =(LinearLayout)itemView.findViewById(R.id.syllabusLinearlayout);
//
//               linearLayout.setVisibility(View.GONE);
//
//               semester.setOnClickListener(new View.OnClickListener() {
//                   @Override
//                   public void onClick(View view) {
//                       linearLayout.setVisibility(View.VISIBLE);
//                   }
//               });

            }
        }
    }

    private void downloadFile(String syllabusurl) {

        url = syllabusurl;

        Log.d("stringurl",url);

        if (ActivityCompat.checkSelfPermission(BbaSyllabusActivity2.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(BbaSyllabusActivity2.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        else{
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Downloading...");
           request.setTitle("syllabus");


           if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
               request.allowScanningByMediaScanner();
               request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
           }

           request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Syllabus/" + "/" + "Sample_" + ".pdf");

           DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

           manager.enqueue(request);

        }
    }

    public  void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){

        if(grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            String url1 = url;

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url1));
            request.setDescription("Downloading...");
            request.setTitle("syllabus");


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Syllabus/" + "/" + "Sample_" + ".pdf");


            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            manager.enqueue(request);


        }
    }

    private void getSyllabusView(String syllabusView){
         url1 = syllabusView;

         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.setData(Uri.parse(url1));
         startActivity(intent);
    }



}
