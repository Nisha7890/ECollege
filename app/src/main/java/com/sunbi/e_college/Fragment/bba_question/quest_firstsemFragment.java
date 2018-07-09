package com.sunbi.e_college.Fragment.bba_question;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.sunbi.e_college.Activity.BbaQuestion.Bba1st;
import com.sunbi.e_college.Activity.BbaQuestion.Bba1stQuestionDataList;
import com.sunbi.e_college.AppControllerPackage.AppController;
import com.sunbi.e_college.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class quest_firstsemFragment extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    ArrayList<Bba1stQuestionDataList> arrayList = new ArrayList<>();


    public quest_firstsemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_quest_firstsem, container, false);

        recyclerView = (RecyclerView)view1.findViewById(R.id.bba1stsemquest);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        getBba1stQuestion();

        return view1;

    }

    private void getBba1stQuestion(){

        arrayList.clear();

        String url = "http://nepalidolconcert.com/demo/intern/api/subjects/2/1";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String subject = jsonObject.getString("subjects");
                        String id = jsonObject.getString("id");

                        Bba1stQuestionDataList bba1stQuestionDataList = new Bba1stQuestionDataList(subject,id);
                        arrayList.add(bba1stQuestionDataList);
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

        ArrayList<Bba1stQuestionDataList> newArraylist;
        Context context;

        public MyAdapter(ArrayList<Bba1stQuestionDataList> arrayList, Context context) {

            this.newArraylist = arrayList;
            this.context = context;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.bbafirstquest,parent,false);
            return  new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

                final Bba1stQuestionDataList bba1stQuestionDataList = newArraylist.get(position);

                holder.subject.setText(bba1stQuestionDataList.getSubject());
                holder.subject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), Bba1st.class);
                        intent.putExtra("id", bba1stQuestionDataList.getId());
                        startActivity(intent);
                    }
                });


        }

        @Override
        public int getItemCount() {
            return newArraylist.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder{

            TextView subject;

            public ViewHolder(View itemView) {
                super(itemView);

                subject = (TextView)itemView.findViewById(R.id.bbaquestion1stsem);

            }
        }
    }
}
