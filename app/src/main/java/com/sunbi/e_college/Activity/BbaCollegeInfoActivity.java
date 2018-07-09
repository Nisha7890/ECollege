package com.sunbi.e_college.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class BbaCollegeInfoActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    ArrayList<BbaDataList> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bba_college_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView  = (RecyclerView)findViewById(R.id.bbarecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getBbaList();
    }


    private void getBbaList(){

        String url = "http://nepalidolconcert.com/demo/intern/api/colleges/2";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject =jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("college_name");
                        String contact = jsonObject.getString("contact");
                        String address = jsonObject.getString("address");
                        String website = jsonObject.getString("website_link");
                        String email = jsonObject.getString("email");

                        BbaDataList bbadataList = new BbaDataList(name,contact,address,website,email);
                        arrayList.add(bbadataList);

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

    private class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ViewHolder>{

        ArrayList<BbaDataList> newArrayList;
        Context context;

        public MyAdapter(ArrayList<BbaDataList> arrayList, Context ApplicationContext) {
            this.newArrayList = arrayList;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bbacollegelist,parent,false);

            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final BbaDataList bbadataList = newArrayList.get(position);

            holder.name.setText(bbadataList.getCollegename());
            holder.contact.setText(bbadataList.getCollegecontact());
            holder.address.setText(bbadataList.getCollegeaddress());
            holder.website.setText(bbadataList.getCollegewebsite());
            holder.email.setText(bbadataList.getCollegeemail());

            holder.website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bbadataList.getCollegewebsite()));
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return newArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView name,contact,address,website,email;


            public ViewHolder(View itemView) {
                super(itemView);

                name = (TextView)itemView.findViewById(R.id.collegename);
                contact = (TextView)itemView.findViewById(R.id.collegecontact);
                address = (TextView)itemView.findViewById(R.id.collegeaddress);
                website = (TextView)itemView.findViewById(R.id.collegewebsite);
                email = (TextView)itemView.findViewById(R.id.collegeemail);
            }
        }
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

}
