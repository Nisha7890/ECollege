package com.sunbi.e_college.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
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
import android.widget.Adapter;
import android.widget.Button;
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
import org.w3c.dom.Text;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class NoticeListActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    ArrayList<DataList> arrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab=getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        recyclerView  = (RecyclerView)findViewById(R.id.noticeList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);




        getNoticeList();


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void getNoticeList() {

        final SpotsDialog spotsDialog = new SpotsDialog(NoticeListActivity.this);
        spotsDialog.show();

        String url = "https://dplofficial.com/dpl/api/get_recent_news";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                spotsDialog.dismiss();


                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String title = jsonObject.getString("title");
                        String author = jsonObject.getString("author");
                        String content = jsonObject.getString("content");

                        Log.d("content",content);


                        DataList dataList = new DataList(title,author,content);
                        arrayList.add(dataList);

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

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        ArrayList<DataList> newArrayList;
        Context context;

        public MyAdapter(ArrayList<DataList> arrayList, Context context) {

            this.newArrayList = arrayList;
            this.context = context;


        }

        @Override
        public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.noticelist,parent,false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final DataList dataList = newArrayList.get(position);

            holder.title.setText(dataList.getTitle());
            holder.author.setText(dataList.getDate());

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeListActivity.this);
//                Toast.makeText(getApplicationContext(),"This is alert Dialog",Toast.LENGTH_SHORT).show();


                    View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popuop,null,false);

                    AlertDialog alertDialog = builder.create();

                    alertDialog.setView(view1);



                    Log.d("popupcontent",dataList.getContent());


                    TextView textView1 =(TextView)view1.findViewById(R.id.title);
                    textView1.setText(dataList.getTitle());

                    TextView textView = (TextView)view1.findViewById(R.id.popupcontent);
                    textView.setText(dataList.getContent());


                    alertDialog.show();

                }
            });



        }

        @Override
        public int getItemCount() {
            return newArrayList.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView title,author;
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);

                title = (TextView)itemView.findViewById(R.id.txtTitle);
                author = (TextView)itemView.findViewById(R.id.txtAuthor);
                imageView = (ImageView) itemView.findViewById(R.id.readmore);

            }
        }
    }
}
