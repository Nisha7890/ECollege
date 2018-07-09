package com.sunbi.e_college.Fragment.bim_question;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sunbi.e_college.Activity.BimQuestion.Bim2nd;
import com.sunbi.e_college.Activity.BimQuestion.Bim2ndQuestionDataList;
import com.sunbi.e_college.Activity.BimQuestion.BimQuestionDataList;
import com.sunbi.e_college.AppControllerPackage.AppController;
import com.sunbi.e_college.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class bimquest_secondsemFragment extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    ArrayList<Bim2ndQuestionDataList> arrayList = new ArrayList<>();

    public bimquest_secondsemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view2 =  inflater.inflate(R.layout.fragment_bimquest_secondsem, container, false);

        recyclerView =(RecyclerView)view2.findViewById(R.id.bim2ndsemquest);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        getBim2ndQuestion();

        return  view2;

    }

    private void getBim2ndQuestion(){

        arrayList.clear();
        String url = "http://nepalidolconcert.com/demo/intern/api/subjects/1/2";

        Log.d("url",url);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String subject = jsonObject.getString("subjects");
                        String id = jsonObject.getString("id");

                        Log.d("subjects",subject);

                        Bim2ndQuestionDataList bim2ndQuestionDataList = new  Bim2ndQuestionDataList(subject,id);
                        arrayList.add(bim2ndQuestionDataList);
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

        ArrayList<Bim2ndQuestionDataList> newArraylist;
        Context context;

        public MyAdapter(ArrayList<Bim2ndQuestionDataList> arrayList, Context context) {

            this.newArraylist = arrayList;
            this.context = context;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view = LayoutInflater.from(getContext()).inflate(R.layout.bimsecondquest,parent,false);

           return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final Bim2ndQuestionDataList bim2ndQuestionDataList = newArraylist.get(position);

            holder.subject.setText(bim2ndQuestionDataList.getSubject());

            holder.subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),Bim2nd.class);
                    intent.putExtra("id",bim2ndQuestionDataList.getId() );
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return newArraylist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView subject;


            public ViewHolder(View itemView) {
                super(itemView);

                subject = (TextView)itemView.findViewById(R.id.bimquestion2ndsem);
            }
        }


    }
}
