package com.sunbi.e_college.Fragment.bim_question;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sunbi.e_college.Activity.BimQuestion.Bim7th;
import com.sunbi.e_college.Activity.BimQuestion.Bim8th;
import com.sunbi.e_college.Activity.BimQuestion.Bim8thQuestionDataList;
import com.sunbi.e_college.AppControllerPackage.AppController;
import com.sunbi.e_college.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class bimquest_eigthsemFragment extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerView.Adapter adapter;

    ArrayList<Bim8thQuestionDataList> arrayList = new ArrayList<>();


    public bimquest_eigthsemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view8 =  inflater.inflate(R.layout.fragment_bimquest_eigthsem, container, false);

        recyclerView = (RecyclerView)view8.findViewById(R.id.bim8thSemQuest);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        getBim8thQuestion();


        return  view8;
    }

    private void getBim8thQuestion(){

        arrayList.clear();

        String url = "http://nepalidolconcert.com/demo/intern/api/subjects/1/13";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String subject = jsonObject.getString("subjects");
                        String id = jsonObject.getString("id");

                        Bim8thQuestionDataList bim8thQuestionDataList = new Bim8thQuestionDataList(subject,id);
                        arrayList.add(bim8thQuestionDataList);

                    }

                    adapter = new MyAdapter(arrayList,getContext());
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

        ArrayList<Bim8thQuestionDataList> newArraylist;
        Context context;

        public MyAdapter(ArrayList<Bim8thQuestionDataList> arrayList, Context context) {

            this.newArraylist = arrayList;
            this.context = context;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.bimeighthquest,parent,false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final Bim8thQuestionDataList bim8thQuestionDataList = newArraylist.get(position);

            holder.subject.setText(bim8thQuestionDataList.getSubject());

            holder.subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Bim8th.class);
                    intent.putExtra("id", bim8thQuestionDataList.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return newArraylist.size();
        }

        public  class ViewHolder extends  RecyclerView.ViewHolder{

            TextView subject;


            public ViewHolder(View itemView) {
                super(itemView);

                subject = (TextView)itemView.findViewById(R.id.bimquestion8thsem);
            }
        }
    }



}
