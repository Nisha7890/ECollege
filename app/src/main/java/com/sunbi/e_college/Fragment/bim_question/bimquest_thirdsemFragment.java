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
import com.sunbi.e_college.Activity.BimQuestion.Bim3rd;
import com.sunbi.e_college.Activity.BimQuestion.Bim3rdQuestionDataList;
import com.sunbi.e_college.AppControllerPackage.AppController;
import com.sunbi.e_college.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class bimquest_thirdsemFragment extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerView.Adapter adapter;

    ArrayList<Bim3rdQuestionDataList> arrayList = new ArrayList<>();


    public bimquest_thirdsemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view3= inflater.inflate(R.layout.fragment_bimquest_thirdsem, container, false);

        recyclerView = (RecyclerView)view3.findViewById(R.id.bim3rdsemquest);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        getBim3rdQuestion();

      return view3;
    }

    private void getBim3rdQuestion(){

        arrayList.clear();

        String url = "http://nepalidolconcert.com/demo/intern/api/subjects/1/3";

        Log.d("url", url);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i =0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String subject = jsonObject.getString("subjects");
                        String id = jsonObject.getString("id");

                        Log.d("subjectlist",subject);

                        Bim3rdQuestionDataList bim3rdQuestionDataList = new Bim3rdQuestionDataList(subject,id);
                        arrayList.add(bim3rdQuestionDataList);

                    }

                    adapter= new MyAdapter(arrayList,getContext());
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
        ArrayList<Bim3rdQuestionDataList> newArraylist;
        Context context;

        public MyAdapter(ArrayList<Bim3rdQuestionDataList> arrayList, Context context) {

                this.newArraylist = arrayList;
                this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.bimthirdquest,parent,false);

            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final Bim3rdQuestionDataList bim3rdQuestionDataList = newArraylist.get(position);

            holder.subject.setText(bim3rdQuestionDataList.getSubject());

            holder.subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Bim3rd.class);
                    intent.putExtra("id", bim3rdQuestionDataList.getId());
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return newArraylist.size();
        }

        public class ViewHolder extends  RecyclerView.ViewHolder{

            TextView subject;

            public ViewHolder(View itemView) {
                super(itemView);

                subject = (TextView)itemView.findViewById(R.id.bimquestion3rdsem);
            }
        }
    }
}
