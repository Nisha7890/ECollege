package com.sunbi.e_college.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;

public class Testimonials extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    ArrayList<TestimonialsDataList> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimonials);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView)findViewById(R.id.testimonialsRecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getTestimonials();

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

    private void  getTestimonials(){

        String url = "http://nepalidolconcert.com/demo/intern/api/testimonials";

        Log.d("url",url);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("linespacing",response+"");

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String image = jsonObject.getString("timage");
                        String name = jsonObject.getString("tname");
                        String stream = jsonObject.getString("tstream");
                        String batch = jsonObject.getString("tbatch");
                        String message = jsonObject.getString("tmessage");

                        TestimonialsDataList testimonialsDataList = new TestimonialsDataList(image,name,stream,batch,message);
                        arrayList.add(testimonialsDataList);


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

        ArrayList<TestimonialsDataList> newArrayList;
        Context context;

        public MyAdapter(ArrayList<TestimonialsDataList> arrayList, Context applicationContext) {

                this.newArrayList = arrayList;
                this.context = context;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.testimonials,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            TestimonialsDataList testimonialsDataList = newArrayList.get(position);


           holder.name.setText(testimonialsDataList.getName());
           holder.stream.setText(testimonialsDataList.getStream());
           holder.batch.setText(testimonialsDataList.getBatch());

            Glide.with(getApplicationContext()).load(testimonialsDataList.getImage()).into(holder.image);

           holder.messages.setText(testimonialsDataList.getMessage());

        }

        @Override
        public int getItemCount() {
            return newArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            ImageView image;
            TextView name,stream,batch,messages;


            public ViewHolder(View itemView) {
                super(itemView);

                image = (ImageView)itemView.findViewById(R.id.profile_image);
                name = (TextView)itemView.findViewById(R.id.tname);
                stream =(TextView)itemView.findViewById(R.id.tstream);
                batch =(TextView)itemView.findViewById(R.id.tbatch);
                messages=(TextView)itemView.findViewById(R.id.tmessage);
            }
        }
    }
}
