package com.example.gardenmate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;

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

class IdentifyTask extends AsyncTask<String, Void, String> {
    String data = "";
    String name;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseRef = database.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid;

    Context context;
    protected IdentifyTask(Context context) {
        this.context = context.getApplicationContext();
    }

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
        String key = Tab1Fragment.item.getKey();

        //set
        Tab1Fragment.name = string;
        Tab1Fragment.title.setText(name);

        //update db
        mDatabaseRef.child(uid).child(key).child("name").setValue(name);
        mDatabaseRef.child(uid).child(key).child("identified").setValue(true);

        //enable tab2
        Tab2Fragment.textView.setVisibility(View.GONE);
        Tab2Fragment.webView.setVisibility(View.VISIBLE);
        Tab2Fragment.fabSearch.setVisibility(View.VISIBLE);
        String url1 = "https://en.wikipedia.org/wiki/Special:Search?search="+name;
        final String url2 = "https://garden.org/plants/search/text/?q="+name;

        Tab2Fragment.webView.setWebViewClient(new Tab2Fragment.Browser());
        Tab2Fragment.webView.loadUrl(url1);

        Tab2Fragment.fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
}
