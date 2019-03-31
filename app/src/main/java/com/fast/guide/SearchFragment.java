package com.fast.guide;

import android.icu.util.Output;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import com.fast.guide.Adapter.SearchRecyclerViewAdapter;
import com.fast.guide.Module.RecyclerViewItem;
import com.fast.guide.Connection.*;
import com.fast.guide.Utilities.settings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class SearchFragment extends Fragment implements SearchRecyclerViewAdapter.ItemClickListener  , AsyncResponse {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    SearchView searchView;

    SearchRecyclerViewAdapter adapter;
    ArrayList<RecyclerViewItem> items;
    ConnectionString con= new ConnectionString();
    settings st;
    ProgressBar prog;
    HttpConnection co;
    RecyclerView recyclerView ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,null);
    }



    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prog=(ProgressBar)getActivity().findViewById(R.id.progressBar3);
        searchView = (SearchView) getActivity().findViewById(R.id.searchView);

        prog.setVisibility(ProgressBar.VISIBLE);
        st = new settings(getView().getContext());
        items = new ArrayList<>();

         co=new HttpConnection(con.URL_GET_Detail,new ArrayList<HttpParamiter>());

        co.delegate = this;
        co.execute();



        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //searchView.requestFocusFromTouch();
                searchView.setIconified(false);
                searchView.setQuery("", false);
                searchView.requestFocus();
                searchView.findFocus();
                searchView.getTouchscreenBlocksFocus();
                searchView.requestFocus();


            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        prog.setVisibility(ProgressBar.INVISIBLE);
    }

    public void Search(String q){
        items.clear();
        items = new ArrayList<>();
        ArrayList<HttpParamiter> par= new ArrayList<HttpParamiter>();
        par.add(new HttpParamiter("title","%"+q+"%") );
        HttpConnection co=new HttpConnection(con.URL_DetailByTitleWithoutCategory,par );
        co.delegate=this;

        co.execute();

    }


    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position).getName() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }


    void LoadData(String input){


        try {
            JSONObject details=new JSONObject(input);
            JSONArray categoriesArray=new JSONArray(details.getString("detail"));
            for (int i=0;i<categoriesArray.length();i++){
                JSONObject categorie=new JSONObject(categoriesArray.get(i).toString());
                items.add(new RecyclerViewItem(categorie.getInt("detail_id") ,categorie.getString( (st.getValue("lang").equals("en")?"":st.getValue("lang")+"_" ) +"title"),con.urlphotoheader+"/"+categorie.getString("header_photo")  ) );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(items.size()>0) {
            recyclerView = getView().findViewById(R.id.search_recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
            adapter = new SearchRecyclerViewAdapter(getContext(), items);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
            prog.setVisibility(ProgressBar.INVISIBLE);
        }

    }





    @Override
    public void OnProcessFinishedListener(String output) {
        LoadData(output);
    }

}
