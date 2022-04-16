package com.dhruv.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dhruv.myapplication.adapter.DataAdapter;
import com.dhruv.myapplication.databinding.FragmentDataBinding;
import com.dhruv.myapplication.helper.PojoMore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataFragment extends Fragment {
    MainActivity mainActivity;
    private FragmentDataBinding binding;
    private List<PojoMore> lstAnime;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;

    public static DataFragment newInstance() {
        DataFragment fragment = new DataFragment();
        return fragment;
    }

    public void onAttach(Context activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDataBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mainActivity.onBackPressed();
            }
        });



        lstAnime = new ArrayList<>();

        jsonrequest();

        return view;
    }

    private void jsonrequest() {
        request = new JsonArrayRequest(Config.JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        PojoMore anime = new PojoMore();
                        anime.setName(jsonObject.getString("name"));
                        anime.setId(jsonObject.getString("id"));

                        JSONArray subjects = jsonObject.getJSONArray("subjects");
                        for (int i1 = 0; i1 < subjects.length(); i1++) {
                            anime.setSubject(subjects.getString(i1));
                        }
                        JSONArray qualification = jsonObject.getJSONArray("qualification");
                        for (int i2 = 0; i2 < qualification.length(); i2++) {
                            anime.setQualification(qualification.getString(i2));
                        }
                        anime.setImage_url(jsonObject.getString("profileImage"));
                        lstAnime.add(anime);

                        binding.circularIndicator.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                initComponent(lstAnime);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    private void initComponent(List<PojoMore> lstAnime) {
        DataAdapter myadapter = new DataAdapter(getContext(), lstAnime);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(myadapter);
        binding.recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
    }



}