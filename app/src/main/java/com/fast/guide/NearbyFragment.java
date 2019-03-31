package com.fast.guide;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fast.guide.Adapter.RecyclerViewAdapter;
import com.fast.guide.Module.RecyclerViewItem;
import com.fast.guide.Connection.*;
import com.fast.guide.Utilities.settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NearbyFragment extends Fragment  implements RecyclerViewAdapter.ItemClickListener  , AsyncResponse   {

    RecyclerViewAdapter adapter;
    ArrayList<RecyclerViewItem> items;
    ConnectionString con= new ConnectionString();
    settings st;
    ProgressBar prog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_nearby,null);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prog=(ProgressBar)getActivity().findViewById(R.id.progressBar3);
        prog.setVisibility(ProgressBar.VISIBLE);
        st = new settings(getView().getContext());
         items = new ArrayList<>();

         HttpConnection co=new HttpConnection(con.URL_Category,new ArrayList<HttpParamiter>());

        co.delegate = this;
        co.execute();


    }




    void LoadData(String input){

        try {
            JSONObject categories=new JSONObject(input);
            JSONArray categoriesArray=new JSONArray(categories.getString("categories"));
            for (int i=0;i<categoriesArray.length();i++){
                JSONObject categorie=new JSONObject(categoriesArray.get(i).toString());
                items.add(new RecyclerViewItem(categorie.getInt("category_id") ,categorie.getString( (st.getValue("lang").equals("en")?"":st.getValue("lang")+"_" ) +"name"),con.urlphotocategory+"/"+categorie.getString("photo")  ) );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(items.size()>0) {
            RecyclerView recyclerView = getView().findViewById(R.id.nearby_recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            adapter = new RecyclerViewAdapter(getContext(), items);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
            prog.setVisibility(ProgressBar.INVISIBLE);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        prog.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position).getName() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnProcessFinishedListener(String output) {
        LoadData(output);
    }
}
