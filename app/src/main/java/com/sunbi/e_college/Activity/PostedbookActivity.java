package com.sunbi.e_college.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class PostedbookActivity extends AppCompatActivity {

    SharedPreferences loginPrefq;

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    ArrayList<PostedBooksDataList> arrayList = new ArrayList<>();
    Integer z;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postedbook);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginPrefq = getSharedPreferences("loginPred",MODE_PRIVATE);


        recyclerView  = (RecyclerView)findViewById(R.id.postedbooksRecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getPostedBook();

    }

    private void getPostedBook(){

        final SpotsDialog spotsDialog = new SpotsDialog(PostedbookActivity.this);

        spotsDialog.show();

        String url = "http://nepalidolconcert.com/demo/intern/api/mypostedbooks";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                spotsDialog.dismiss();

                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String bookname = jsonObject.getString("bookname");
                        String course = jsonObject.getString("course_name");
                        String semester = jsonObject.getString("semester_name");
                        String useful = jsonObject.getString("useful");
                        String date = jsonObject.getString("date");
                        String publication = jsonObject.getString("publication");
                        String book_edition = jsonObject.getString("book_edition");
                        String edition_year = jsonObject.getString("edition_year");
                        String price = jsonObject.getString("price");
                        String book_price = jsonObject.getString("book_price");
                        String image = jsonObject.getString("photo");

                        PostedBooksDataList postedBooksDataList = new PostedBooksDataList(bookname,course,semester,useful,date,publication,book_edition,edition_year,price,book_price,image);
                        arrayList.add(postedBooksDataList);

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
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();


                headers.put("Accept","application/json");


                headers.put("Authorization","Bearer "+loginPrefq.getString("api_token",null));
                return headers;
            }



        };

        AppController.getInstance().addToRequestQueue(getRequest);

    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        ArrayList<PostedBooksDataList> newArrayList;
        Context context;

        public MyAdapter(ArrayList<PostedBooksDataList> arrayList, Context applicationContext) {

            this.newArrayList = arrayList;
            this.context = applicationContext;


        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.postedbookslayout,parent,false);

            return  new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            PostedBooksDataList postedBooksDataList = newArrayList.get(position);

//            for(int n= 1; n<position; n++){
//
//                holder.sn.setText(String.valueOf(n));
//            }
//
//
            holder.bookname.setText(postedBooksDataList.getBookname());
            holder.course.setText(postedBooksDataList.getCourse());
            holder.semester.setText(postedBooksDataList.getSemester());
            holder.useful.setText(postedBooksDataList.getUseful());
            holder.date.setText(postedBooksDataList.getDate());
            holder.publication.setText(postedBooksDataList.getPublication());
            holder.book_edition.setText(postedBooksDataList.getBook_edition());
            holder.edition_year.setText(postedBooksDataList.getEdition_year());
            holder.price.setText(postedBooksDataList.getPrice());
            holder.book_price.setText(postedBooksDataList.getBook_price());

            Glide.with(getApplicationContext()).load(postedBooksDataList.getPhoto()).into(holder.photo);

        }

        @Override
        public int getItemCount() {
            return newArrayList.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder{

            TextView bookname,course,semester,useful,date,publication,book_edition,edition_year,price,book_price;
            ImageView photo;


            public ViewHolder(View itemView) {
                super(itemView);

                bookname = (TextView)itemView.findViewById(R.id.bookname);
                course = (TextView)itemView.findViewById(R.id.course);
                semester = (TextView)itemView.findViewById(R.id.semester);
                useful = (TextView)itemView.findViewById(R.id.yesno);
                date = (TextView)itemView.findViewById(R.id.date);
                publication = (TextView)itemView.findViewById(R.id.publication);
                book_edition = (TextView)itemView.findViewById(R.id.edition);
                edition_year = (TextView)itemView.findViewById(R.id.year);
                price = (TextView)itemView.findViewById(R.id.price);
                book_price = (TextView)itemView.findViewById(R.id.marketprice);

//                sn = (TextView)itemView.findViewById(R.id.sn);

//                for( z=1; z<=10; z++){
//
//                sn.setText(String.valueOf(z));
//                }


                photo = (ImageView)itemView.findViewById(R.id.photo);
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
