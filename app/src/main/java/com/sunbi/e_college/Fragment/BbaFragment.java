package com.sunbi.e_college.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunbi.e_college.Activity.BbaCollegeInfoActivity;
import com.sunbi.e_college.Activity.BbaSyllabusActivity2;
import com.sunbi.e_college.Activity.QuestionActivity;
import com.sunbi.e_college.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BbaFragment extends Fragment {


    public BbaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bba,null,false);


        ImageView imageView = (ImageView) v.findViewById(R.id.imgQuestionBba);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),QuestionActivity.class);
                startActivity(intent);

            }
        });

        ImageView imageView1 =(ImageView) v.findViewById(R.id.imgSyllabusBba);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent is = new Intent(getContext(), BbaSyllabusActivity2.class);
                startActivity(is);

            }
        });

        ImageView imageView2 = (ImageView) v.findViewById(R.id.imgCollegeinfoBba);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentbba = new Intent(getContext(), BbaCollegeInfoActivity.class);
                startActivity(intentbba);
            }
        });

        return v;


    }

}
