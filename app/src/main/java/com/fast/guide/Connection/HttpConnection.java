package com.fast.guide.Connection;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class HttpConnection extends AsyncTask <Void, Void, String> {

    public AsyncResponse delegate = null;

    private String URL;
    private ArrayList<HttpParamiter> httpParamiters;

    public HttpConnection( String URL,ArrayList<HttpParamiter> httpParamiters ){
        this.URL=URL;
        this.httpParamiters=httpParamiters;
    }


    @Override
    protected String doInBackground(Void... voids) {
        // return null;
        return Connect( this.URL,this.httpParamiters );
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.OnProcessFinishedListener(result);
    }


    public String Connect(String URL,ArrayList<HttpParamiter> httpParamiters ){

        try {

            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            List<Pair> paramiters=new ArrayList<>();

            for (int i=0;i<httpParamiters.size();i++){
                paramiters.add(new Pair( httpParamiters.get(i).getKey(),httpParamiters.get(i).getValue()));
            }

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(paramiters));
            writer.flush();
            writer.close();
            os.close();
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response= Buffre2String(br);

            return response;



        }catch (Exception e){
            Log.v("main2.java",e.toString());
            Log.v("main2.java",e.getMessage());
            //e.printStackTrace();
            return "";
        }
    }

    private  String Buffre2String( BufferedReader br ){
        try {
            String line;
            String response = "";
            while ((line=br.readLine()) != null) {
                response+=line;
            }
            return response;
        }catch (Exception e){
            return  "";
        }
    }


    private String getQuery(List<Pair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Pair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(pair.first.toString(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second.toString(), "UTF-8"));
        }

        return result.toString();
    }


}
