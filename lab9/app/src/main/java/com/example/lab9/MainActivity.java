package com.example.lab9;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText locationText;
    private TextView weatherView;
    private TextView highView;
    private TextView lowView;
    private TextView populationView;
    private TextView locationView;
    private ProgressBar progressBar;
//    private ImageView photoImageView;
    private static final String API_KEY = "37e72b9451eb1e6c8b68e470ed7e618d";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
//    http://api.openweathermap.org/data/2.5/forecast?q=München,DE&apiKey=37e72b9451eb1e6c8b68e470ed7e618d

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        lowView = findViewById(R.id.lowView);
//        highView = findViewById(R.id.highView);
        locationView = findViewById(R.id.locationView);
//        weatherView = findViewById(R.id.weatherView);
        locationText = findViewById(R.id.locationText);
        progressBar = findViewById(R.id.progressBar);
        populationView = findViewById(R.id.populationView);
//        photoImageView = findViewById(R.id.imageView);

        Button queryButton = findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = locationText.getText().toString();
                new RetrieveFeedTask().execute(location);
            }
        });

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        //invoked on the UI thread before the task is executed
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
//            weatherView.setText(" ");
//            lowView.setText(" ");
//            highView.setText(" ");
            locationView.setText(" ");
            populationView.setText(" ");
//            photoImageView.setImageResource(android.R.color.transparent);
        }

        //invoked on the background thread immediately after onPreExecute() finishes executing
        protected String doInBackground(String... args) {
            String location = args[0]; //input
            try {
                //create full URL for search
                URL url = new URL(API_URL + location + "&apiKey=" + API_KEY);
                //create open connection to URL
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    //create an InputStreamReader with the JSON stream
                    InputStreamReader is = new InputStreamReader(urlConnection.getInputStream());
                    //convert the byte stream to a character stream using BufferedReader
                    BufferedReader bufferedReader = new BufferedReader(is);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    //loop through the character stream and build a sequence of characters using StringBuilder
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    //convert character sequence to a String
                    return stringBuilder.toString();
                } finally {
                    //disconnect the http connection
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        //invoked on the UI thread after the background computation finishes
        //response is the result of doInBackground
        protected void onPostExecute(String response) {
            //response should be a String with JSON objects
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);

            //parse JSON object
            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                JSONObject city = object.getJSONObject("city");
                String name = city.getString("name");
                locationView.setText(name); //location name

                JSONObject cityPop = object.getJSONObject("city");
                String pop = cityPop.getString("population");
                populationView.setText("Population: " +pop); //weather name
//                JSONArray jArr = object.getJSONArray("list");
//                for (int i=0; i < jArr.length(); i++) {
//                    JSONObject jDayForecast = jArr.getJSONObject(i);
//                }
//                ArrayList arrayList = new ArrayList();
//                JSONArray list=object.getJSONArray("list");

//                JSONArray photoarray = object.getJSONArray("photos");
//                JSONObject photos = photoarray.getJSONObject(0);
//                String photo = photos.getString("url");
//                new DownloadImageTask(photoImageView).execute(photo);

//                JSONArray socialprofiles = object.getJSONArray("socialProfiles");
//                for (int i = 0; i < socialprofiles.length(); i++) {
//                    JSONObject socialprofile = socialprofiles.getJSONObject(i);
//                    String social = socialprofile.getString("type");
//                    String url = socialprofile.getString("url");
//                    responseView.append(social + " \t" + url + "\n");
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
