package com.fast.guide;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fast.guide.Connection.AsyncResponse;
import com.fast.guide.Connection.ConnectionString;
import com.fast.guide.Connection.HttpConnection;
import com.fast.guide.Connection.HttpParamiter;
import com.fast.guide.R;
import com.fast.guide.Utilities.settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AboutFragment extends Fragment implements AsyncResponse {

    ConnectionString con= new ConnectionString();
    settings st;
    ProgressBar prog;

    TextView about_detail;
    TextView about_title;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about,null);
    }

    @Override
    public void OnProcessFinishedListener(String output) {
        LoadData(output);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        about_detail=(TextView) getActivity().findViewById(R.id.about_detail );
        about_title=(TextView) getActivity().findViewById(R.id.about_title );


        prog=(ProgressBar)getActivity().findViewById(R.id.progressBar3);
        prog.setVisibility(ProgressBar.VISIBLE);
        st = new settings(getView().getContext());

        HttpConnection co=new HttpConnection(con.URL_About,new ArrayList<HttpParamiter>());
        co.delegate = this;
        co.execute();
    }


    void LoadData(String input){
        try {
            JSONObject About=new JSONObject(input);
            JSONArray AboutArray=new JSONArray(About.getString("abouts"));

            JSONObject value=new JSONObject(AboutArray.get(0).toString() );

            String title=value.getString(st.getValue("lang")+"_title");
            String description=value.getString(st.getValue("lang")+"_description");

            about_title.setText(title);
            about_detail.setText(description);
            prog.setVisibility(ProgressBar.INVISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        prog.setVisibility(ProgressBar.INVISIBLE);
    }
}
