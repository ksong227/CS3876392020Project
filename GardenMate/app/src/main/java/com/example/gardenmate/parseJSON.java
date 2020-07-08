package com.example.gardenmate;

import android.os.AsyncTask;

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

public class parseJSON extends AsyncTask <Void, Void, Void> {
    String data = "";
    String name = "";

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("https://my-api.plantnet.org/v2/identify/all?api-key=2a10vAmLauo1W9nGfh8EHZbye&images=https%3A%2F%2Ffirebasestorage.googleapis.com%2Fv0%2Fb%2Fgardenmate-b4eb8.appspot.com%2Fo%2Fuploads%252F1593892581561.jpg%3Falt%3Dmedia%26token%3D2e2074c8-9cfc-446d-99b8-ea2ced79b8b9&organs=flower");

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //read file
            String line = "";
            while(line != null)
            {
                line = bufferedReader.readLine();
                data += line;
            }

            //parse and get name
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

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        ItemActivity.jsonText.setText(this.name);
    }
}
