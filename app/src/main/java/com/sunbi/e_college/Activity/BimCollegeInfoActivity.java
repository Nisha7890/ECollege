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
import android.text.method.LinkMovementMethod;
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

public class BimCollegeInfoActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    ArrayList<BimDataList> arrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bim_college_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView =(RecyclerView)findViewById(R.id.bimrecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getBimList();


    }

    private void getBimList(){

        String url = "http://nepalidolconcert.com/demo/intern/api/colleges/1";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        String name = jsonObject.getString("college_name");
                        String contact = jsonObject.getString("contact");
                        String address = jsonObject.getString("address");
                        String website = jsonObject.getString("website_link");
                        String email = jsonObject.getString("email");

                        BimDataList bimDataList = new BimDataList(name,contact,address,website,email);
                        arrayList.add(bimDataList);

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

    public void onBackPressed(){
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        ArrayList<BimDataList> newArrayList;
        Context context;


        public MyAdapter(ArrayList<BimDataList> arrayList, Context applicationContext) {

            this.newArrayList = arrayList;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bimcollegelist,parent,false);
              return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final BimDataList bimDataList = newArrayList.get(position);

            holder.name.setText(bimDataList.getCollegename());
            holder.contact.setText(bimDataList.getCollegecontact());
            holder.address.setText(bimDataList.getCollegeaddress());
            holder.website.setText(bimDataList.getCollegewebsite());
            holder.email.setText(bimDataList.getCollegeemail());

           holder.website.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(bimDataList.getCollegewebsite()));
                   startActivity(in);
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
                contact =(TextView)itemView.findViewById(R.id.collegecontact);
                address =(TextView)itemView.findViewById(R.id.collegeaddress);
                website =(TextView)itemView.findViewById(R.id.collegewebsite);
                email =(TextView)itemView.findViewById(R.id.collegeemail);
            }
        }
    }
}
