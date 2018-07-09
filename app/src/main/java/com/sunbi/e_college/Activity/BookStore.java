package com.sunbi.e_college.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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

import dmax.dialog.SpotsDialog;

public class BookStore extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    int numberOfColumns = 2;
    ArrayList<BookStoreDataList> arrayList = new ArrayList<>();

//    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Intent intent = getIntent();
//        status = intent.getStringExtra("status");

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        if(status.equals(true)){
//
//
//                LinearLayout linearLayout =(LinearLayout)findViewById(R.id.loginLinear);
//                linearLayout.setVisibility((linearLayout.getVisibility() == View.VISIBLE)
//                        ? View.GONE : View.VISIBLE);
//        }


        blink();

        LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.sellLinear);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(BookStore.this, LoginActivity.class);
                startActivity(i);
            }
        });


        recyclerView  = (RecyclerView)findViewById(R.id.bookstorerecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);




        getBookInfo();



    }

    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sellLinear);
                        if (linearLayout.getVisibility() == View.VISIBLE) {
                            linearLayout.setVisibility(View.INVISIBLE);
                        } else {
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();

    }



    public  void onBackPressed(){

        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }

    private void getBookInfo(){
        final SpotsDialog spotsDialog = new SpotsDialog(BookStore.this);
        spotsDialog.show();

        String url = "http://nepalidolconcert.com/demo/intern/api/books";
        Log.d("url",url);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                spotsDialog.dismiss();

                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String image = jsonObject.getString("photo");
                        String bookname = jsonObject.getString("bookname");
                        String course = jsonObject.getString("course_name");
                        String semester = jsonObject.getString("semester_name");
                        String yesno = jsonObject.getString("useful");
                        String price = jsonObject.getString("price");
                        String sellerfirstname = jsonObject.getString("seller_firstname");
                        String sellerlastname = jsonObject.getString("seller_lastname");
                        String date = jsonObject.getString("date");
                        String marketprice = jsonObject.getString("book_price");
                        String publication = jsonObject.getString("publication");
                        String bookedition = jsonObject.getString("book_edition");
                        String editionyear = jsonObject.getString("edition_year");
                        String college = jsonObject.getString("college");
                        String email = jsonObject.getString("seller_email");
                        String contact = jsonObject.getString("contact");
                        String address = jsonObject.getString("address");



                        Log.d("publication",publication);
                        Log.d("image1",image);


                        BookStoreDataList bookStoreDataList = new BookStoreDataList(image,bookname,course,semester,yesno,price,marketprice,publication,bookedition,editionyear,sellerfirstname,sellerlastname,college,email,contact,address,date);
                        arrayList.add(bookStoreDataList);


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

//                Log.e("errorrrr",error.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(getRequest);


    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        ArrayList<BookStoreDataList> newArrayList;
        Context context;


        public MyAdapter(ArrayList<BookStoreDataList> arrayList, Context applicationContext) {

        this.newArrayList = arrayList;
        this.context = applicationContext;


        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bookstore,parent,false);

            return  new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final BookStoreDataList bookStoreDataList = newArrayList.get(position);

            Glide.with(getApplicationContext()).load(bookStoreDataList.getBookimg()).into(holder.image);
            holder.bookname.setText(bookStoreDataList.getBookname());
            holder.course.setText(bookStoreDataList.getCourse());
            holder.semester.setText(bookStoreDataList.getSemester());
            holder.yesno.setText(bookStoreDataList.getYesno());
            holder.price.setText(bookStoreDataList.getPrice());
            holder.sellerfirstname.setText(bookStoreDataList.getSellerfirstname());
            holder.sellerlastname.setText(bookStoreDataList.getSellerlastname());
            holder.date.setText(bookStoreDataList.getDate());

            holder.details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BookStore.this);

                    View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupbookstore,null,false);

                    final AlertDialog alertDialog = builder.create();

                    alertDialog.setCancelable(false);

                    alertDialog.setView(view1);


                    ImageView imageView = (ImageView)view1.findViewById(R.id.bookimage);
                    Glide.with(getApplicationContext()).load(bookStoreDataList.getBookimg()).into(imageView);

                    TextView textView1 = (TextView)view1.findViewById(R.id.bname);
                    textView1.setText(bookStoreDataList.bookname);

                    TextView textView2 = (TextView)view1.findViewById(R.id.bcourse);
                    textView2.setText(bookStoreDataList.course);

                    TextView textView3 = (TextView)view1.findViewById(R.id.bsemester);
                    textView3.setText(bookStoreDataList.semester);

                    TextView textView4 = (TextView)view1.findViewById(R.id.byesno);
                    textView4.setText(bookStoreDataList.yesno);

                    TextView textView5 = (TextView)view1.findViewById(R.id.bprice);
                    textView5.setText(bookStoreDataList.price);

                    TextView textView6 = (TextView)view1.findViewById(R.id.bmprice);
                    textView6.setText(bookStoreDataList.marketprice);

                    TextView textView7 = (TextView)view1.findViewById(R.id.bpublication);
                    textView7.setText(bookStoreDataList.publication);

                    TextView textView8 = (TextView)view1.findViewById(R.id.bedition);
                    textView8.setText(bookStoreDataList.bookedition);

                    TextView textView9 = (TextView)view1.findViewById(R.id.editionyear);
                    textView9.setText(bookStoreDataList.editionyear);

                    TextView textView10 = (TextView)view1.findViewById(R.id.bsellerfirstname);
                    textView10.setText(bookStoreDataList.sellerfirstname);

                    TextView textView16 = (TextView)view1.findViewById(R.id.bsellerlastname);
                    textView16.setText(bookStoreDataList.sellerlastname);

                    TextView textView11 = (TextView)view1.findViewById(R.id.bcollege);
                    textView11.setText(bookStoreDataList.college);

                    TextView textView12 = (TextView)view1.findViewById(R.id.bcontact);
                    textView12.setText(bookStoreDataList.contact);

                    TextView textView13 = (TextView)view1.findViewById(R.id.baddress);
                    textView13.setText(bookStoreDataList.address);

                    TextView textView14 = (TextView)view1.findViewById(R.id.bdate);
                    textView14.setText(bookStoreDataList.date);

                    TextView textView15 = (TextView)view1.findViewById(R.id.bemail);
                    textView15.setText(bookStoreDataList.email);


                    ImageView imageView1 = (ImageView)view1.findViewById(R.id.imgexist);
                    imageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            alertDialog.dismiss();
                        }
                    });



                    alertDialog.show();



                }
            });

        }

        @Override
        public int getItemCount() {
            return newArrayList.size();
        }

        public class ViewHolder extends  RecyclerView.ViewHolder{

            ImageView image;
            TextView details,bookname,course,semester,yesno,price,sellerfirstname,sellerlastname,date;

            public ViewHolder(View itemView) {
                super(itemView);

                image = (ImageView)itemView.findViewById(R.id.bookimg);
                bookname = (TextView)itemView.findViewById(R.id.bookname);
                course = (TextView)itemView.findViewById(R.id.course);
                semester = (TextView)itemView.findViewById(R.id.semester);
                yesno = (TextView)itemView.findViewById(R.id.yesno);
                price = (TextView)itemView.findViewById(R.id.price);
                sellerfirstname = (TextView)itemView.findViewById(R.id.sellerfirstname);
                sellerlastname = (TextView)itemView.findViewById(R.id.sellerlastname);
                date = (TextView)itemView.findViewById(R.id.date);

                details = (TextView)itemView.findViewById(R.id.viewdetails);
            }





        }
    }
}
