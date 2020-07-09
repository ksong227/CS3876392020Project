package com.example.gardenmate;

import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class parseJSON extends AsyncTask<String, Void, String> {
    String data = "";
    String name;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseRef = database.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid;


    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //read
            String line = "";
            while(line != null)
            {
                line = bufferedReader.readLine();
                data += line;
            }

            //parse
            JSONObject jo = new JSONObject(data);
            JSONArray resultsArr = jo.getJSONArray("results");
            JSONObject result0 = (JSONObject) resultsArr.get(0);
            JSONObject species = result0.getJSONObject("species");
            name = species.getString("scientificNameWithoutAuthor");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return name;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        //getUId and key
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                uid = profile.getUid().replace("@","").replace(".","");
            }
        }
        String key = ItemActivity.item.getKey();

        //set
        ItemActivity.name = string;
        ItemActivity.title.setText(name);

        //update db
        mDatabaseRef.child(uid).child(key).child("name").setValue(name);
    }
}
