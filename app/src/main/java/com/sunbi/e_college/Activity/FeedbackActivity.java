package com.sunbi.e_college.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sunbi.e_college.AppControllerPackage.AppController;
import com.sunbi.e_college.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class FeedbackActivity extends AppCompatActivity {


    String name, email,message;

      TextView txtSubmit;
      EditText editName,editEmail,editMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);


        editName=(EditText)findViewById(R.id.your_name);
        editEmail=(EditText)findViewById(R.id.your_email);
        editMessage=(EditText)findViewById(R.id.your_message);

        txtSubmit = (TextView)findViewById(R.id.txtSubmit);
        txtSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               validateFeedback();

//               String Name=editName.getText().toString();
//               String Email=editEmail.getText().toString();
//              String Message=editMessage.getText().toString();
//
//               if(Name.length()==0){
//                   editName.setError("Please enter your name");
//               }
//
//               else if(!Name.matches("[a-zA-Z ]+")){
//
//                   editName.setError("Enter only Alphabet");
//               }
//
//               else if(Email.length()==0){
//                   editEmail.setError("Please enter your  email address");
//               }
//
//               else if(!Email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
//                   editEmail.setError("Please enter your valid email address");
//
//               }
//               else if(Message.length()==0){
//                   editMessage.setError("Please enter message");
//               }
//
//               else{
//                   Toast.makeText(FeedbackActivity.this,"Successfully Submit",Toast.LENGTH_LONG).show();
//               }

           }
       });



    }

    private void validateFeedback(){

        name = editName.getText().toString();
        email = editEmail.getText().toString();
        message = editMessage.getText().toString();

        if(name.length() == 0){

            editName.setError("Please enter full name");
        }
        else if(!name.matches("[a-zA-Z ]+")){

                   editName.setError("Enter only Alphabet");
               }

         else if(email.length()==0){
                   editEmail.setError("Please enter email ");
               }

               else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                   editEmail.setError("Please enter valid email address");

               }
//
//               else if(password.length()==0){
//                   editpassword.setError("Please enter password");
//        }
//
//        else if(cpassword.length()==0){
//                   editrepassword.setError("Please enter confirmation password");
//        }
//
//        else if(!password.equals(cpassword)){
//            Toast.makeText(getApplicationContext(),"Your password and confirmation password mismatched !!",Toast.LENGTH_LONG).show();
//        }

        else if(message.length()==0){
            editMessage.setError("Please enter message");
        }

        else {
            final SpotsDialog spotsDialog = new SpotsDialog(FeedbackActivity.this);
            spotsDialog.show();

            String url = "http://nepalidolconcert.com/demo/intern/api/feedbacks";

             Log.d("urlfeedback",url);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("jsonpostresponse",response);

                    spotsDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");

                        if(status.equals("true")){

                            Toast.makeText(getApplicationContext(),"Submitted Successfully",Toast.LENGTH_LONG).show();

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
                protected Map<String,  String> getParams(){

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("sender_name", name);
                    params.put("sender_email",email);
                    params.put("sender_message",message);

                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//
//                    headers.put("Accept","application/json");
//
////                        headers.put("Authorization","Bearer "+ getSharedPreferences("loginPred",MODE_PRIVATE).getString("api_token",null));
//
//                    return headers;
//
//                }

            };

            AppController.getInstance().addToRequestQueue(postRequest);
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
