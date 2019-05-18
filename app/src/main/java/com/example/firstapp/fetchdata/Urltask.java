package com.example.firstapp.fetchdata;


import android.os.AsyncTask;

import com.example.firstapp.models.BestItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Urltask extends AsyncTask<String, String, List<BestItemModel>> {


    @Override
    protected List<BestItemModel> doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("offers");

            List<BestItemModel> articleModelList = new ArrayList<>();


            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject finalObject = parentArray.getJSONObject(i);
                BestItemModel articleModel = new BestItemModel();
                articleModel.setTitle(finalObject.getString("title"));
                articleModel.setCategory(finalObject.getString("category"));
                articleModel.setThumbnail(finalObject.getString("thumbnail"));
                articleModel.setUrl(finalObject.getString("url"));
                // adding the final object in the list
                articleModelList.add(articleModel);
            }
            return articleModelList;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}