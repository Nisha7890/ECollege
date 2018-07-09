package com.sunbi.e_college.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class SellBookActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 555;
    String mCurrentPhotoPath;
    private Uri mImageCaptureUri;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 5;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 400;

    private static final int PICK_FROM_CAMERA = 500;
    ImageView imageView;

    private static final int PICK_FROM_GALLERY = 4;
    ArrayList<String> imageEncodedStr = new ArrayList<>();

    MarshMallowPermission marshMallowPermission;


//    private static int RESULT_LOAD_IMAGE = 1;

    String bookname,publication,editionyear,bookprice,marketprice,date,contact,address,college,course,semester,edition,checked,photo;
    Spinner spinnerCourse,spinnerSemester,spinnerEdition;
    TextView txtPost;
    Button browse_button;
    CheckBox checkYes, checkNo;
    EditText editbookname,editPublication,editEditionyear,editBookprice,editMarketprice,editDate,editContact,editAddress,editCollege;
    Calendar myCalendar;
    String yes;
    CardView cardviewdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        SharedPreferences loginPrefq = getSharedPreferences("loginPred",MODE_PRIVATE);

          imageView = (ImageView) findViewById(R.id.bookimg);


        marshMallowPermission = new MarshMallowPermission(this);
        browse_button = (Button)findViewById(R.id.browse_button);

        browse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showOptions();

            }
        });

        editbookname=(EditText)findViewById(R.id.bookname);
        editPublication=(EditText)findViewById(R.id.publicationname);
        editEditionyear=(EditText)findViewById(R.id.editionyear);
        editBookprice=(EditText)findViewById(R.id.bookprice);
        editMarketprice=(EditText)findViewById(R.id.bookmarketprice);
//        editDate=(EditText)findViewById(R.id.editionyear);
        editContact=(EditText)findViewById(R.id.contact);
        editAddress=(EditText)findViewById(R.id.address);
        editCollege=(EditText)findViewById(R.id.college);

         myCalendar = Calendar.getInstance();

        editDate=(EditText)findViewById(R.id.date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(SellBookActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        spinnerCourse =(Spinner)findViewById(R.id.spinnerCourse);
        String[] items3 = new String[]{"BIM", "BBA"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(SellBookActivity.this,android.R.layout.simple_spinner_item,items3);
        adapter3.setDropDownViewResource(R.layout.spinner_item);
        spinnerCourse.setAdapter(adapter3);


        spinnerSemester =(Spinner)findViewById(R.id.spinnerSemester);
        String[] item1 = new String[]{"1 Semester", "2 Semester","3 Semester","4 Semester","4 Semester","5 Semester","6 Semester","7 Semester","8 Semester"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(SellBookActivity.this,android.R.layout.simple_spinner_item,item1);
        adapter1.setDropDownViewResource(R.layout.spinner_item);
        spinnerSemester.setAdapter(adapter1);

        spinnerEdition =(Spinner)findViewById(R.id.spinnerBookedition);
        String[] item2 = new String[]{"1st Edition", "2nd Edition","3rd Edition","4th Edition","5th Edition","6th Edition"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(SellBookActivity.this,android.R.layout.simple_spinner_item,item2);
        adapter2.setDropDownViewResource(R.layout.spinner_item);
        spinnerEdition.setAdapter(adapter2);


        checkYes = (CheckBox) findViewById(R.id.checkYes);
        checkNo = (CheckBox) findViewById(R.id.checkNo);

        checkYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (checkYes.isChecked()){

                    yes = "yes";

                    Log.d("checkedstate",yes);


                    checkNo.setChecked(false);
                }

            }
        });
        checkNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (checkNo.isChecked()){

                    yes = "no";
                    checkYes.setChecked(false);
                    Log.d("checkedstate",yes);

                }


            }
        });



        txtPost = (TextView)findViewById(R.id.postForsell);
        txtPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postForSale();

            }
        });




    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    private  void postForSale(){





        bookname = editbookname.getText().toString();
        publication = editPublication.getText().toString();
        editionyear = editEditionyear.getText().toString();
        bookprice = editBookprice.getText().toString();
        marketprice = editMarketprice.getText().toString();
        date = editDate.getText().toString();
        contact = editContact.getText().toString();
        address = editAddress.getText().toString();
        college = editCollege.getText().toString();



//        photo = browse_button.getText().toString();

        course = spinnerCourse.getSelectedItem().toString();

        semester = spinnerSemester.getSelectedItem().toString();

        edition = spinnerEdition.getSelectedItem().toString();

        Log.d("bookname",bookname);
        Log.d("publication",publication);
        Log.d("editionyear",editionyear);
        Log.d("bookprice",bookprice);
        Log.d("marketprice",marketprice);
        Log.d("date",date);
        Log.d("contact",contact);
        Log.d("address",address);
        Log.d("college",college);
        Log.d("course",course);
        Log.d("semester",semester);
        Log.d("edition",edition);
//        Log.d("photo",photo);
//        if(checkYes.isChecked())
//        {
//            checked=checkYes.getText().toString();
//        }
//
//       else if(checkNo.isChecked())
//        {
//            checked=checkNo.getText().toString();
//        }




        if(bookname.length() == 0){

            editbookname.setError("Please enter Book Name");
        }
        else if(publication.length() == 0){

            editPublication.setError("Please enter Publication Name");
        }

        else if(editionyear.length() == 0){

            editEditionyear.setError("Please enter Edition Year");
        }

        else if(bookprice.length() == 0){

            editBookprice.setError("Please enter Book Price");
        }

        else if(marketprice.length() == 0){

            editMarketprice.setError("Please enter Market Price of Book");
        }

        else if(date.length() == 0){

            editDate.setError("Please enter Date");
        }

        else if(contact.length() == 0){

            editContact.setError("Please enter Contact Number");
        }

        else if(address.length() == 0){

            editAddress.setError("Please enter Address");
        }

        else if(college.length() == 0){

            editCollege.setError("Please enter Collge Name");

        }else if (imageEncodedStr.size()==0){

            browse_button.setError("Please select image.");

       }

        else {
            final SpotsDialog spotsDialog = new SpotsDialog(SellBookActivity.this);
            spotsDialog.show();

            String url = "http://nepalidolconcert.com/demo/intern/api/sellbooks";

            Log.d("url",url);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("jsonpostresponse",response);

                    spotsDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");

                        if(status.equals("true")){

                            Toast.makeText(getApplicationContext(),"Posted Successfully",Toast.LENGTH_LONG).show();
                            finish();
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
                    params.put("bookname", bookname);
                    params.put("publication",publication);
                    params.put("edition_year",editionyear);
                    params.put("book_price", bookprice);
                    params.put("price",marketprice);
                    params.put("date",date);
                    params.put("contact", contact);
                    params.put("address",address);
                    params.put("college",college);
                    params.put("course", course);
                    params.put("semester",semester);
                    params.put("book_edition",edition);
                    params.put("useful",yes);

                    for (int i = 0; i <imageEncodedStr.size() ; i++) {


                        params.put("photo["+ i+"]", imageEncodedStr.get(i));
                    }


                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();


                    headers.put("Accept","application/json");


                    headers.put("Authorization","Bearer "+ getSharedPreferences("loginPred",MODE_PRIVATE).getString("api_token",null));
                    return headers;
                }


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




    void showOptions(){

        AlertDialog.Builder builder = new AlertDialog.Builder(SellBookActivity.this);
        builder.setTitle("Choose an option");
        String[] options = {"Open Camera", "Gallery", "cancel"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        uploadFromCamera();
                        break;
                    case 1:
                        uploadFromGallery();
                        break;
                    case 2:

                        dialog.cancel();
                        break;

                }
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void uploadFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions()) {
                if (Build.VERSION.SDK_INT >= 24) {
                    File file = null;
                    try {
                        file = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (file != null) {
                        mImageCaptureUri = FileProvider.getUriForFile(SellBookActivity.this, "com.sunbi.nepaleseinoman.provider", file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        startActivityForResult(intent, PICK_FROM_CAMERA);

                    }


                } else {
                    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    try {
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }

        } else {
            mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            try {
                startActivityForResult(intent, PICK_FROM_CAMERA);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void uploadFromGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            } else {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"), PICK_FROM_GALLERY);
            }
        } else {
            Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"), PICK_FROM_GALLERY);
        }
    }




    private boolean checkAndRequestPermissions() {
        int permissionCamera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());

    }


        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        Log.d("enc",encodedImage);

        return encodedImage;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_FROM_GALLERY) {

//                TextView textView = (TextView)findViewById(R.id.synctext);
//                textView.setBackgroundColor(Color.parseColor("#0f8a32"));
//
//                CardView cardView = (CardView)findViewById(R.id.adddocumentbuttonplus);
//                cardView.setVisibility(View.GONE);


                if (data.getData() != null) {
                    try {
                        imageEncodedStr.add(getStringImage(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData())));
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                        imageView.setImageBitmap(bitmap);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    if (data.getClipData() != null) {       // multiplePick
                        ClipData mClipData = data.getClipData();


                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);

                            try {
                                imageEncodedStr.add(getStringImage(MediaStore.Images.Media.getBitmap(getContentResolver(), item.getUri())));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }

            }
            else if(requestCode == PICK_FROM_CAMERA){

//                TextView textView = (TextView)findViewById(R.id.synctext);
//                textView.setBackgroundColor(Color.parseColor("#0f8a32"));
//
//                CardView cardView = (CardView)findViewById(R.id.adddocumentbuttonplus);
//                cardView.setVisibility(View.GONE);

                try {
                    imageEncodedStr.add(getStringImage(MediaStore.Images.Media.getBitmap(getContentResolver(),mImageCaptureUri)));

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mImageCaptureUri);

                    imageView.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }


                Log.i("imagesSizeeee", imageEncodedStr.size()+"");


            }
        }
    }


}


