package com.sunbi.e_college.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunbi.e_college.Activity.BimCollegeInfoActivity;
import com.sunbi.e_college.Activity.BimQuestionActivity;
import com.sunbi.e_college.Activity.BimSyllabusActivity;
import com.sunbi.e_college.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BimFragment extends Fragment {


    public BimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v1 = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bim,null,false);

        ImageView imageView = (ImageView) v1.findViewById(R.id.imgQuestionBim);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getContext(), BimQuestionActivity.class);
                startActivity(in);
            }
        });

        ImageView imageView1 = (ImageView) v1.findViewById(R.id.imgSyllabusBim);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent is = new Intent(getContext(), BimSyllabusActivity.class);
                startActivity(is);
            }
        });


        ImageView imageView2 =(ImageView) v1.findViewById(R.id.imgCollegeinfoBim);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent ibim = new Intent(getContext(), BimCollegeInfoActivity.class);
                 startActivity(ibim);
            }
        });

        return v1;
    }

}
