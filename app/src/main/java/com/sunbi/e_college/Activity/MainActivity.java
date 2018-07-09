package com.sunbi.e_college.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sunbi.e_college.AppControllerPackage.AppController;
import com.sunbi.e_college.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        TextView contact_us;
//        ImageView back;
        DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    String name, email, course,question;
    EditText editName, editEmail, editQuestion;
    TextView txtSend;
    Spinner dropdown;
    AlertDialog alertDialog;
//   Typewriter textView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       getSupportActionBar().setDisplayShowTitleEnabled(false);





        SharedPreferences loginPrefl = getSharedPreferences("loginPred",MODE_PRIVATE);
        String name1 = "Welcome" + " " + loginPrefl.getString("name","");



//        String name1 = loginPref.getString("name","");

        Log.d("preferencename",name1);

//        TextView textView = (TextView)findViewById(R.id.txtwelcome);
//        textView.setText("Welcome" + loginPref.getString("name",""));


        ImageView imageView = (ImageView)findViewById(R.id.navigationicon);
       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

//               final LinearLayout mDrawer = (LinearLayout)findViewById(R.id.nav_view);
               DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
               mDrawerLayout.openDrawer(GravityCompat.START);

           }
       });


       getNotice();

//       ImageView imageView =(ImageView)findViewById(R.id.imageheader);
//        Display display = getWindowManager().getDefaultDisplay();
//        float width = display.getWidth();
//        TranslateAnimation animation = new TranslateAnimation(0,width -50,0,0);
//        animation.setDuration(30000);
//        animation.setRepeatCount(1);
//        animation.setRepeatMode(1);
////        animation.setFillAfter(true);
//
//        imageView.startAnimation(animation);


        contact_us   = (TextView)findViewById(R.id.contact_us);
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);

                View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.contact_us_layout,null,false);

                final AlertDialog alertDialog = builder.create();

                alertDialog.setView(view1);

                editName=(EditText)view1.findViewById(R.id.txtName);
                editEmail=(EditText)view1.findViewById(R.id.txtEmail);
//                editCourse=(EditText)view1.findViewById(R.id.txtCourse);
                 dropdown =(Spinner)view1.findViewById(R.id.spinner1);

                String[] items = new String[]{"BIM", "BBA"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_item,items);
               adapter.setDropDownViewResource(R.layout.spinner_item);
                dropdown.setAdapter(adapter);

                editQuestion =(EditText)view1.findViewById(R.id.txtQuestion);


                txtSend = (TextView)view1.findViewById(R.id.txtSend);

                txtSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                            name = editName.getText().toString();
                            email = editEmail.getText().toString();
                            course = dropdown.getSelectedItem().toString();
                            question = editQuestion.getText().toString();


                            if(name.length()==0){

                                editName.setError("Please enter full name.");

                            }

                            else if(!name.matches("[a-zA-Z]+")){

                                editName.setError("Enter only Alphabet.");
                            }

                            else if(email.length()==0){
                                editEmail.setError("Please enter email.");
                            }

//                            else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
//
//                                editEmail.setError("Please enter valid email address.");
//                            }


                            else if(question.length()==0){

                                editQuestion.setError("Please enter your question.");
                            }

                            else {
                                final SpotsDialog spotsDialog = new SpotsDialog(MainActivity.this);
                                spotsDialog.show();

                                String url = "http://nepalidolconcert.com/demo/intern/api/enquiries";

                                Log.d("url1",url);

                                StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.d("jsonpostresponse",response);

                                        spotsDialog.dismiss();

                                        try {
                                            JSONObject jsonObject = new JSONObject(response);

                                            String status = jsonObject.getString("status");

                                            if(status.equals("true")){

                                                Toast.makeText(getApplicationContext(),"Submitted Successfully", Toast.LENGTH_LONG).show();
                                                    alertDialog.dismiss();
                                            }

                                            else{
                                                Toast.makeText(getApplicationContext(),"Something went wrong !!", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    protected Map<String, String> getParams(){
                                        Map<String, String>params = new HashMap<String, String>();
                                        params.put("sender_name", name);
                                        params.put("sender_email",email);
                                        params.put("subject",course);
                                        params.put("enquiry",question);

                                        return params;

                                    }

                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        HashMap<String, String> headers = new HashMap<String, String>();

                                        headers.put("Accept","application/json");
//                    headers.put("Authorization","Bearer "+ getSharedPreferences("loginPref",MODE_PRIVATE).getString("gmailaccesstoken",null));
                                        return headers;
                                    }

                                };

                                AppController.getInstance().addToRequestQueue(postRequest);

                            }

                        }

                });


                alertDialog.show();


            }
        });




        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.noticeLinearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(i);
            }
        });

        LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.aboutuslinearlayout);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ia = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(ia);
            }
        });

        LinearLayout linearLayout2 =(LinearLayout)findViewById(R.id.linearlayoutourcourses);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ic = new Intent(MainActivity.this,CourseActivity1.class);
                startActivity(ic);

            }
        });

        LinearLayout linearLayout3 = (LinearLayout)findViewById(R.id.linearlayoutnewsandevents);
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ie = new Intent(MainActivity.this, NoticeListActivity.class);
                startActivity(ie);
            }
        });

        ImageView imageView1 =(ImageView)findViewById(R.id.calendar);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//              Toast.makeText(getApplicationContext(),"This is alert Dialog",Toast.LENGTH_SHORT).show();
//
//
//                View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_calendar2,null,false);
//
//                AlertDialog alertDialog = builder.create();
//
//                alertDialog.setView(view1);
//
//
//                alertDialog.show();

                Intent intent = new Intent(MainActivity.this,CalendarActivity.class);
                startActivity(intent);


//                Calendar cal = Calendar.getInstance();
//                Intent intent = new Intent(Intent.ACTION_EDIT);
//                intent.setType("vnd.android.cursor.item/event");
//                intent.putExtra("beginTime", cal.getTimeInMillis());
//                intent.putExtra("allDay", true);
//                intent.putExtra("rrule", "FREQ=YEARLY");
//                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
//                intent.putExtra("title", "A Test Event from android app");
//                startActivity(intent);
            }
        });





      drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);

        TextView navUsername = (TextView) headerView.findViewById(R.id.txtwelcome);
        navUsername.setText(name1);


    }

    private void getNotice() {


        final SpotsDialog spotsDialog = new SpotsDialog(MainActivity.this);
        spotsDialog.show();

        String url = "http://nepalidolconcert.com/demo/intern/api/notices";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                spotsDialog.dismiss();

                Log.d("jsonresponse",response+"");


                try {
                    JSONArray jsonArray = response.getJSONArray("data");

//                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//                     String url = jsonObject.getString("syllabus");

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String title = jsonObject.getString("noticetitle");
                        String date = jsonObject.getString("date");

                        TextView textnotice = (TextView)findViewById(R.id.txtNotice);
                        TextView textdate = (TextView)findViewById(R.id.txtDate);

                        textnotice.setText(title);
                        textdate.setText(date);



//                        DataList dataList = new DataList(title,date);



                    }




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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rateus) {

            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + this.getPackageName())));
            } catch (android.content.ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }
        }
//        else if (id == R.id.nav_notice) {
//            Intent in = new Intent(MainActivity.this, NoticeListActivity.class);
//            startActivity(in);
//
//        }

        else if (id == R.id.nav_contact) {
            Intent ic = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(ic);

        }



//        else if (id == R.id.nav_logout) {
//
//            AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);
//
//            View view2 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.logout,null,false);
//
//            final AlertDialog alertDialog = builder.create();
//
//            alertDialog.setView(view2);
//
//            TextView textView = (TextView)view2.findViewById(R.id.yes);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    SharedPreferences preferences =getSharedPreferences("loginPred",Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.clear().apply();
//
//
//                }
//            });
//
//            TextView textView1 = (TextView)view2.findViewById(R.id.no);
//            textView1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                   alertDialog.dismiss();
//                }
//            });
//            alertDialog.show();
//
//        }


        else  if(id == R.id.nav_testimonials){
            Intent it = new Intent(MainActivity.this,Testimonials.class);
            startActivity(it);
        }

        else  if(id == R.id.nav_bookstore){
            Intent it = new Intent(MainActivity.this,BookStore.class);
            startActivity(it);
        }

        else  if(id == R.id.nav_youraccount){
            final SharedPreferences loginPref = getSharedPreferences("loginPred",MODE_PRIVATE);

            SharedPreferences.Editor editor = loginPref.edit();



            if(loginPref.getBoolean("IsLoggedIn",false))
            {

                AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);

                View view3 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.useracoount_layout,null,false);

                final AlertDialog alertDialog = builder.create();

                alertDialog.setView(view3);


                            SharedPreferences loginPrefq = getSharedPreferences("loginPred",MODE_PRIVATE);
                          String name =   loginPrefq.getString("name","");


                          TextView txtview = (TextView)view3.findViewById(R.id.username);
                        txtview.setText(loginPrefq.getString("name",""));

                TextView textView2 = (TextView)view3.findViewById(R.id.sellBooks);
                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intentsell = new Intent(MainActivity.this,SellBookActivity.class);

                        startActivity(intentsell);
                    }
                });

                TextView textView4 = (TextView)view3.findViewById(R.id.myprofile);
              textView4.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);

                      final View view6 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.myprofile,null,false);

                      final AlertDialog alertDialog8 = builder.create();

                      alertDialog8.setView(view6);


                      final SpotsDialog spotsDialog = new SpotsDialog(MainActivity.this);
                      spotsDialog.show();

                      String url ="http://nepalidolconcert.com/demo/intern/api/myProfile";

                      Log.d("url",url);

                      JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


                          @Override
                          public void onResponse(JSONObject response) {

                              spotsDialog.dismiss();
                              try {

                                  JSONObject jsonObject = response.getJSONObject("data");

                                  String firstname = jsonObject.getString("firstname");
                                  String lastname = jsonObject.getString("lastname");
                                  String email = jsonObject.getString("email");
                                  String postedbooks = jsonObject.getString("postedbooks");

                                  TextView txtfirstname = (TextView)view6.findViewById(R.id.firstname);
                                  TextView txtlastname = (TextView)view6.findViewById(R.id.lastname);
                                  TextView txtemail = (TextView)view6.findViewById(R.id.email);
                                  TextView txtpostedbooks = (TextView)view6.findViewById(R.id.postedbooks);

                                  txtfirstname.setText(firstname);
                                  txtlastname.setText(lastname);
                                  txtemail.setText(email);
                                  txtpostedbooks.setText(postedbooks);


                                  Log.d("firstname",firstname);
                                  Log.d("email",email);


                              }catch (JSONException e) {
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


                              headers.put("Authorization","Bearer "+getSharedPreferences("loginPred",MODE_PRIVATE).getString("api_token",null));
                              return headers;
                          }



                      };

                      AppController.getInstance().addToRequestQueue(getRequest);

                      ImageView imageView = (ImageView)view6.findViewById(R.id.profileexist);
                      imageView.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {

                              alertDialog8.dismiss();
                          }
                      });


                      alertDialog8.show();


                  }
              });





                TextView txtview1 = (TextView)view3.findViewById(R.id.postedBooks);
                txtview1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intentpostedbooks = new Intent(MainActivity.this, PostedbookActivity.class);

                        startActivity(intentpostedbooks);
                    }
                });

                ImageView imgaccount =(ImageView)view3.findViewById(R.id.accountexist);
                imgaccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();

                    }
                });

                TextView textView1 = (TextView)view3.findViewById(R.id.logout);
                textView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);

                        View view5 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.logout,null,false);

                        final AlertDialog alertDialog1 = builder.create();

                        alertDialog1.setCancelable(false);

                        alertDialog1.setView(view5);

                        TextView textView = (TextView)view5.findViewById(R.id.yes);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Toast.makeText(getApplicationContext(),"Successfully Logged Out !!", Toast.LENGTH_SHORT).show();

                                SharedPreferences preferences =getSharedPreferences("loginPred",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear().apply();

                                alertDialog1.dismiss();

//                                Toast.makeText(getApplicationContext(),"Successfully Logged Out !!", Toast.LENGTH_LONG);

                            }
                        });

                        TextView textView3 = (TextView)view5.findViewById(R.id.no);
                        textView3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                alertDialog1.dismiss();
                            }
                        });
                        alertDialog1.show();

                        ImageView imgexist = (ImageView)view5.findViewById(R.id.exist);
                        imgexist.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog1.dismiss();
                            }
                        });

                    }
                });

                alertDialog.show();

            }

            else
            {
               Intent intent1 = new Intent(MainActivity.this,LoginActivity.class);
               startActivity(intent1);
            }
        }

//        else  if(id == R.id.nav_login){
//            Intent il = new Intent(MainActivity.this,LoginActivity.class);
//            startActivity(il);
//        }

        else  if(id == R.id.nav_signup){
            Intent il = new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(il);
        }
//
//        else if (id== R.id.nav_sellbook){
//
//            Intent isb = new Intent(MainActivity.this, SellBookActivity.class);
//            startActivity(isb);
//        }


        else if (id == R.id.nav_bimquestion) {

            Intent ifd = new Intent (MainActivity.this, BimQuestionActivity.class);
            startActivity(ifd);

        }

        else if (id == R.id.nav_bimsyllabus) {

            Intent ifd = new Intent (MainActivity.this, BimSyllabusActivity.class);
            startActivity(ifd);

        }

        else if (id == R.id.nav_bbaquestion) {

            Intent ifd = new Intent (MainActivity.this, QuestionActivity.class);
            startActivity(ifd);

        }

        else if (id == R.id.nav_bbasyllabus) {

            Intent ifd = new Intent (MainActivity.this, BbaSyllabusActivity2.class);
            startActivity(ifd);

        }
//
//        else if (id == R.id.nav_collegeinfo) {
//
//            Intent ifd = new Intent (MainActivity.this, FeedbackActivity.class);
//            startActivity(ifd);
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
