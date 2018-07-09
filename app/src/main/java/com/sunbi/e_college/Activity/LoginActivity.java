package com.sunbi.e_college.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
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

public class LoginActivity extends AppCompatActivity {

    String email,password,token;
    TextView txtlogin,txtcreatenewaccount;
    EditText editEmail, editPassword;

    private AppCompatAutoCompleteTextView autoTextView;
    private AppCompatAutoCompleteTextView autoTextViewCustom;
    private AppCompatCheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        txtcreatenewaccount = (TextView)findViewById(R.id.createnewaccount);
        txtcreatenewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        checkbox = (AppCompatCheckBox)findViewById(R.id.checkbox);

        editEmail = (EditText)findViewById(R.id.loginemail);
        editPassword =(EditText)findViewById(R.id.loginpassword);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(!isChecked){
                    editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                else{

                    editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        txtlogin = (TextView)findViewById(R.id.txtlogin);
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginForm();

            }
        });

    }

    private void loginForm(){

        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

        if(email.length() == 0){
            editEmail.setError("Please enter email!");
        }

//        else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
//            editEmail.setError("Please enter valid email address.");
//        }

        else if(password.length() == 0){
            editPassword.setError("Please enter password");
        }

        else if(password.length() < 8){
            editPassword.setError("Password should be atleast 8 characters.");
        }

        else{

            final SpotsDialog spotsDialog = new SpotsDialog(LoginActivity.this);
            spotsDialog.show();

            String url = "http://nepalidolconcert.com/demo/intern/api/userlogin";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("jsonresponse",response);

                    spotsDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");



                        JSONObject jsonObject1 = jsonObject.getJSONObject("user");

                         token = jsonObject1.getString("api_token");


                        if(status.equals("true")){

                            Toast.makeText(getApplicationContext(),"Login Successfully.",Toast.LENGTH_LONG).show();


                            SharedPreferences loginPref = getSharedPreferences("loginPred",MODE_PRIVATE);

                            SharedPreferences.Editor editor = loginPref.edit();

                            editor.putBoolean("IsLoggedIn",true);

                            editor.putString("api_token",token);

                            editor.putString("name",jsonObject1.getString("firstname"));

                            editor.apply();
                            finish();


                         }

                        else if (status.equals("false")){

                            Toast.makeText(getApplicationContext(),"failure", Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

//                    Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_LONG).show();


                }
            }){
                protected Map<String, String> getParams(){

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email",email);
                    params.put("password",password);

                    return params;

            }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();

                    headers.put("Accept","application/json");
                    //headers.put("Authorization","Bearer "+ getSharedPreferences("loginPred",MODE_PRIVATE).getString("api_token",null));

                    return headers;
                }


            };

            AppController.getInstance().addToRequestQueue(postRequest);
        }


    }

    public  void onBackPressed(){

        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }

        return  super.onOptionsItemSelected(item);

    }

}
