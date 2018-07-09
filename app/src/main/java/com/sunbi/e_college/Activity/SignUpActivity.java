package com.sunbi.e_college.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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

public class SignUpActivity extends AppCompatActivity {


//    ImageView imgShow,imgShow1;
    String name,username, email, password, cpassword;
    EditText editName,editUserName, editEmail, editPassword, editCpassword;
    TextView txtSignUp;

    private AppCompatAutoCompleteTextView autoTextView;
    private AppCompatAutoCompleteTextView autoTextViewCustom;
    private AppCompatCheckBox checkbox1;
    private AppCompatCheckBox checkbox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        editName = (EditText)findViewById(R.id.signupname);
        editUserName = (EditText)findViewById(R.id.signupusername);
        editEmail = (EditText)findViewById(R.id.signupemail);
        editPassword = (EditText)findViewById(R.id.signuppassword);
        editCpassword = (EditText)findViewById(R.id.signupconfirmpassword);

        checkbox1 = (AppCompatCheckBox)findViewById(R.id.checkbox1);

        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        checkbox2 = (AppCompatCheckBox)findViewById(R.id.checkbox2);

        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(!isChecked){
                    editCpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                else{

                    editCpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        txtSignUp = (TextView)findViewById(R.id.txtSignup);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signUp();

            }
        });




    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){

            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }

    private void signUp(){

        name = editName.getText().toString();
        username = editUserName.getText().toString();
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        cpassword = editCpassword.getText().toString();

        if(name.length()==0){
            editName.setError("Please enter full name");
        }

        else if(!name.matches("[a-zA-Z]+")){

            editName.setError("Enter only Aplhabets");
        }

       else if(username.length()==0){
            editUserName.setError("Please enter user name");
        }

//        else if(!username.matches("[a-zA-Z]+")){
//
//            editUserName.setError("Enter only Aplhabets");
//        }

        else if(email.length()==0){

            editEmail.setError("Please enter email");
        }

//        else if (!isemailValid(email)) {
//
//
//            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
//
//
//        }


//
//        else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
//            editEmail.setError("Please enter valid email address");
//        }


        else if(password.length()==0){
            editPassword.setError("Please enter password");
        }

        else if(password.length() < 8){
            editPassword.setError("Password should be atleast 8 characters.");
        }

        else if(cpassword.length()==0){
            editCpassword.setError("Please enter confirmation password");
        }

        else if(!password.equals(cpassword)){

            Toast.makeText(getApplicationContext(),"Password mismatched",Toast.LENGTH_LONG).show();
        }

        else{
            final SpotsDialog spotsDialog = new SpotsDialog(SignUpActivity.this);
            spotsDialog.show();

            String url = "http://nepalidolconcert.com/demo/intern/api/userregistration";

            Log.d("url",url);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("jsonpostresponse",response);

                    spotsDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");

//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//
//                        String access_token = jsonObject.getString("access_token");

                        if(status.equals("true")){

                            Toast.makeText(getApplicationContext(),"Thank you, To verify your account please check your mail",Toast.LENGTH_LONG).show();


//                            SharedPreferences loginPref = getSharedPreferences("loginPred",MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = loginPref.edit();
//
//                            editor.putString("name",name);
//
//                            editor.apply();
//
//
//                            SharedPreferences loginPrefq = getSharedPreferences("loginPred",MODE_PRIVATE);
//                          String name =   loginPrefq.getString("username","");
//                          String email =   loginPrefq.getString("email","");
//
//                          TextView textView = (TextView)findViewById(R.id.txtSignup);
//                          textView.setText(loginPrefq.getString("name",""));


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


                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();



                    params.put("firstname", name);
                    params.put("lastname", username);
                    params.put("email", email);
                    params.put("password", password);

//                    params.put("password_confirmation", cpassword);


                    return params;
                }



                @Override
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

}
