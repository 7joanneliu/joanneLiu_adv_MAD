package com.example.p2;

import java.util.Arrays;
import android.arch.core.util.Function;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    public WeatherFragment() {
        // Required empty public constructor
    }

    private static final String API_KEY = "37e72b9451eb1e6c8b68e470ed7e618d";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private EditText locationText;
    private TextView locationView;
    private ProgressBar progressBar;
    private TextView weatherView1;
    private TextView highView1;
    private TextView lowView1;
    private ImageView imageView1;
    private TextView weatherView2;
    private TextView highView2;
    private TextView lowView2;
    private ImageView imageView2;
    private TextView weatherView3;
    private TextView highView3;
    private TextView lowView3;
    private ImageView imageView3;
    private TextView weatherView4;
    private TextView highView4;
    private TextView lowView4;
    private ImageView imageView4;
    private TextView weatherView5;
    private TextView highView5;
    private TextView lowView5;
    private ImageView imageView5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
//        locationView = findViewById(R.id.locationView);
        locationText = view.findViewById(R.id.locationText);
        locationView = view.findViewById(R.id.locationView);
        progressBar = view.findViewById(R.id.progressBar);
        lowView1 = view.findViewById(R.id.lowView1);
        highView1 = view.findViewById(R.id.highView1);
        weatherView1 = view.findViewById(R.id.weatherView1);
        imageView1 = view.findViewById(R.id.imageView1);

        lowView2 = view.findViewById(R.id.lowView2);
        highView2 = view.findViewById(R.id.highView2);
        weatherView2 = view.findViewById(R.id.weatherView2);
        imageView2 = view.findViewById(R.id.imageView2);


        lowView3 = view.findViewById(R.id.lowView3);
        highView3 = view.findViewById(R.id.highView3);
        weatherView3 = view.findViewById(R.id.weatherView3);
        imageView3 = view.findViewById(R.id.imageView3);


        lowView4 = view.findViewById(R.id.lowView4);
        highView4 = view.findViewById(R.id.highView4);
        weatherView4 = view.findViewById(R.id.weatherView4);
        imageView4 = view.findViewById(R.id.imageView4);


        lowView5 = view.findViewById(R.id.lowView5);
        highView5 = view.findViewById(R.id.highView5);
        weatherView5 = view.findViewById(R.id.weatherView5);
        imageView5 = view.findViewById(R.id.imageView5);


        Button queryButton = view.findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = locationText.getText().toString();
                new RetrieveFeedTask().execute(location);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        //invoked on the UI thread before the task is executed
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            locationView.setText(" ");
            weatherView1.setText(" ");
            lowView1.setText(" ");
            highView1.setText(" ");
            weatherView2.setText(" ");
            lowView2.setText(" ");
            highView2.setText(" ");
            weatherView3.setText(" ");
            lowView3.setText(" ");
            highView3.setText(" ");
            weatherView4.setText(" ");
            lowView4.setText(" ");
            highView4.setText(" ");
            weatherView5.setText(" ");
            lowView5.setText(" ");
            highView5.setText(" ");

            locationView.setText(" ");
            imageView1.setImageResource(android.R.color.transparent);
            imageView2.setImageResource(android.R.color.transparent);
            imageView3.setImageResource(android.R.color.transparent);
            imageView4.setImageResource(android.R.color.transparent);
            imageView5.setImageResource(android.R.color.transparent);
        }

        @Override
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
                locationView.setText(name);
                JSONArray list = object.getJSONArray("list");

//Day 1 -- day 1 uses the max/min of object/hours 0-7
                int[] d1;
                d1 = new int[8];

                JSONObject day11 = list.getJSONObject(0);
                JSONObject main11 = day11.getJSONObject("main");
                String temp11 = main11.getString("temp");
                Double tempNum11 = Double.parseDouble(temp11);
                int tempInt11 = tempNum11.intValue();
                d1[0] = tempInt11;

                JSONObject day12 = list.getJSONObject(1);
                JSONObject main12 = day12.getJSONObject("main");
                String temp12 = main12.getString("temp");
                Double tempNum12 = Double.parseDouble(temp12);
                int tempInt12 = tempNum12.intValue();
                d1[1] = tempInt12;

                JSONObject day13 = list.getJSONObject(2);
                JSONObject main13 = day13.getJSONObject("main");
                String temp13 = main13.getString("temp");
                Double tempNum13 = Double.parseDouble(temp13);
                int tempInt13 = tempNum13.intValue();
                d1[2] = tempInt13;

                JSONObject day14 = list.getJSONObject(3);
                JSONObject main14 = day14.getJSONObject("main");
                String temp14 = main14.getString("temp");
                Double tempNum14 = Double.parseDouble(temp14);
                int tempInt14 = tempNum14.intValue();
                d1[3] = tempInt14;

                JSONObject day15 = list.getJSONObject(4);
                JSONObject main15 = day15.getJSONObject("main");
                String temp15 = main15.getString("temp");
                Double tempNum15 = Double.parseDouble(temp15);
                int tempInt15 = tempNum15.intValue();
                d1[4] = tempInt15;

                JSONObject day16 = list.getJSONObject(5);
                JSONObject main16 = day16.getJSONObject("main");
                String temp16 = main16.getString("temp");
                Double tempNum16 = Double.parseDouble(temp16);
                int tempInt16 = tempNum16.intValue();
                d1[5] = tempInt16;

                JSONObject day17 = list.getJSONObject(6);
                JSONObject main17 = day17.getJSONObject("main");
                String temp17 = main17.getString("temp");
                Double tempNum17 = Double.parseDouble(temp17);
                int tempInt17 = tempNum17.intValue();
                d1[6] = tempInt17;

                JSONObject day18 = list.getJSONObject(7);
                JSONObject main18 = day18.getJSONObject("main");
                String temp18 = main18.getString("temp");
                Double tempNum18 = Double.parseDouble(temp18);
                int tempInt18 = tempNum18.intValue();
                d1[7] = tempInt18;

                Arrays.sort(d1);
                int maxAF1 = d1[d1.length -1];
                int maxF1 = (maxAF1*9/5)-459;
                int minAF1 = d1[0];
                int minF1 = (minAF1*9/5)-459;
                highView1.setText("H: " + String.valueOf(maxF1) + " °F");
                lowView1.setText("L: " + String.valueOf(minF1) + " °F");

                JSONArray weather1 = day11.getJSONArray("weather");
                JSONObject objw1 = weather1.getJSONObject(0);
                String desc1 = objw1.getString("main");
                weatherView1.setText(desc1);
                String code1 = objw1.getString("id");
                int swatch1 = Integer.parseInt(code1);
                switch (swatch1){
                    case 800:
                        imageView1.setImageResource(R.drawable.weather_sun);
                        break;
                    case 200:
                    case 201:
                    case 202:
                    case 210:
                    case 211:
                    case 212:
                    case 221:
                    case 230:
                    case 231:
                    case 232:
                        imageView1.setImageResource(R.drawable.weather_thund);
                        break;
                    case 300:
                    case 301:
                    case 302:
                    case 310:
                    case 311:
                    case 312:
                    case 313:
                    case 314:
                    case 321:
                    case 500:
                    case 501:
                    case 502:
                    case 503:
                    case 504:
                    case 511:
                    case 520:
                    case 521:
                    case 522:
                    case 531:
                        imageView1.setImageResource(R.drawable.weather_rain);
                        break;
                    case 600:
                    case 601:
                    case 602:
                    case 611:
                    case 612:
                    case 615:
                    case 616:
                    case 620:
                    case 621:
                    case 622:
                        imageView1.setImageResource(R.drawable.weather_snow);
                        break;
                    case 701:
                    case 711:
                    case 721:
                    case 731:
                    case 741:
                    case 751:
                    case 761:
                    case 762:
                    case 771:
                    case 781:
                        imageView1.setImageResource(R.drawable.weather_fog);
                        break;
                    case 801:
                    case 802:
                        imageView1.setImageResource(R.drawable.weather_part);
                        break;
                    case 803:
                    case 804:
                        imageView1.setImageResource(R.drawable.weather_cloud);
                        break;
                }

//Day 2 -- day 2 uses the max/min of object/hours 8-15
                int[] d2;
                d2 = new int[8];

                JSONObject day21 = list.getJSONObject(8);
                JSONObject main21 = day21.getJSONObject("main");
                String temp21 = main21.getString("temp");
                Double tempNum21 = Double.parseDouble(temp21);
                int tempInt21 = tempNum21.intValue();
                d2[0] = tempInt21;

                JSONObject day22 = list.getJSONObject(9);
                JSONObject main22 = day22.getJSONObject("main");
                String temp22 = main22.getString("temp");
                Double tempNum22 = Double.parseDouble(temp22);
                int tempInt22 = tempNum22.intValue();
                d2[1] = tempInt22;

                JSONObject day23 = list.getJSONObject(10);
                JSONObject main23 = day23.getJSONObject("main");
                String temp23 = main23.getString("temp");
                Double tempNum23 = Double.parseDouble(temp23);
                int tempInt23 = tempNum23.intValue();
                d2[2] = tempInt23;

                JSONObject day24 = list.getJSONObject(11);
                JSONObject main24 = day24.getJSONObject("main");
                String temp24 = main24.getString("temp");
                Double tempNum24 = Double.parseDouble(temp24);
                int tempInt24 = tempNum24.intValue();
                d2[3] = tempInt24;

                JSONObject day25 = list.getJSONObject(12);
                JSONObject main25 = day25.getJSONObject("main");
                String temp25 = main25.getString("temp");
                Double tempNum25 = Double.parseDouble(temp25);
                int tempInt25 = tempNum25.intValue();
                d2[4] = tempInt25;

                JSONObject day26 = list.getJSONObject(13);
                JSONObject main26 = day26.getJSONObject("main");
                String temp26 = main26.getString("temp");
                Double tempNum26 = Double.parseDouble(temp26);
                int tempInt26 = tempNum26.intValue();
                d2[5] = tempInt26;

                JSONObject day27 = list.getJSONObject(14);
                JSONObject main27 = day27.getJSONObject("main");
                String temp27 = main27.getString("temp");
                Double tempNum27 = Double.parseDouble(temp27);
                int tempInt27 = tempNum27.intValue();
                d2[6] = tempInt27;

                JSONObject day28 = list.getJSONObject(15);
                JSONObject main28 = day28.getJSONObject("main");
                String temp28 = main28.getString("temp");
                Double tempNum28 = Double.parseDouble(temp28);
                int tempInt28 = tempNum28.intValue();
                d2[7] = tempInt28;

                Arrays.sort(d2);
                int maxAF2 = d2[d2.length -1];
                int maxF2 = (maxAF2*9/5)-459;
                int minAF2 = d2[0];
                int minF2 = (minAF2*9/5)-459;
                highView2.setText("H: " + String.valueOf(maxF2) + " °F");
                lowView2.setText("L: " + String.valueOf(minF2) + " °F");

                JSONArray weather2 = day21.getJSONArray("weather");
                JSONObject objw2 = weather2.getJSONObject(0);
                String desc2 = objw2.getString("main");
                weatherView2.setText(desc2);
                String code2 = objw2.getString("id");
                int swatch2 = Integer.parseInt(code2);
                switch (swatch2){
                    case 800:
                        imageView2.setImageResource(R.drawable.weather_sun);
                        break;
                    case 200:
                    case 201:
                    case 202:
                    case 210:
                    case 211:
                    case 212:
                    case 221:
                    case 230:
                    case 231:
                    case 232:
                        imageView2.setImageResource(R.drawable.weather_thund);
                        break;
                    case 300:
                    case 301:
                    case 302:
                    case 310:
                    case 311:
                    case 312:
                    case 313:
                    case 314:
                    case 321:
                    case 500:
                    case 501:
                    case 502:
                    case 503:
                    case 504:
                    case 511:
                    case 520:
                    case 521:
                    case 522:
                    case 531:
                        imageView2.setImageResource(R.drawable.weather_rain);
                        break;
                    case 600:
                    case 601:
                    case 602:
                    case 611:
                    case 612:
                    case 615:
                    case 616:
                    case 620:
                    case 621:
                    case 622:
                        imageView2.setImageResource(R.drawable.weather_snow);
                        break;
                    case 701:
                    case 711:
                    case 721:
                    case 731:
                    case 741:
                    case 751:
                    case 761:
                    case 762:
                    case 771:
                    case 781:
                        imageView2.setImageResource(R.drawable.weather_fog);
                        break;
                    case 801:
                    case 802:
                        imageView2.setImageResource(R.drawable.weather_part);
                        break;
                    case 803:
                    case 804:
                        imageView2.setImageResource(R.drawable.weather_cloud);
                        break;
                }

//Day 3 -- day 3 uses the max/min of object/hours 16-23
                int[] d3;
                d3 = new int[8];

                JSONObject day31 = list.getJSONObject(16);
                JSONObject main31 = day31.getJSONObject("main");
                String temp31 = main31.getString("temp");
                Double tempNum31 = Double.parseDouble(temp31);
                int tempInt31 = tempNum31.intValue();
                d3[0] = tempInt31;

                JSONObject day32 = list.getJSONObject(17);
                JSONObject main32 = day32.getJSONObject("main");
                String temp32 = main32.getString("temp");
                Double tempNum32 = Double.parseDouble(temp32);
                int tempInt32 = tempNum32.intValue();
                d3[1] = tempInt32;

                JSONObject day33 = list.getJSONObject(18);
                JSONObject main33 = day33.getJSONObject("main");
                String temp33 = main33.getString("temp");
                Double tempNum33 = Double.parseDouble(temp33);
                int tempInt33 = tempNum33.intValue();
                d3[2] = tempInt33;

                JSONObject day34 = list.getJSONObject(19);
                JSONObject main34 = day34.getJSONObject("main");
                String temp34 = main34.getString("temp");
                Double tempNum34 = Double.parseDouble(temp34);
                int tempInt34 = tempNum34.intValue();
                d3[3] = tempInt34;

                JSONObject day35 = list.getJSONObject(20);
                JSONObject main35 = day35.getJSONObject("main");
                String temp35 = main35.getString("temp");
                Double tempNum35 = Double.parseDouble(temp35);
                int tempInt35 = tempNum35.intValue();
                d3[4] = tempInt35;

                JSONObject day36 = list.getJSONObject(21);
                JSONObject main36 = day36.getJSONObject("main");
                String temp36 = main36.getString("temp");
                Double tempNum36 = Double.parseDouble(temp36);
                int tempInt36 = tempNum36.intValue();
                d3[5] = tempInt36;

                JSONObject day37 = list.getJSONObject(22);
                JSONObject main37 = day37.getJSONObject("main");
                String temp37 = main37.getString("temp");
                Double tempNum37 = Double.parseDouble(temp37);
                int tempInt37 = tempNum37.intValue();
                d3[6] = tempInt37;

                JSONObject day38 = list.getJSONObject(23);
                JSONObject main38 = day38.getJSONObject("main");
                String temp38 = main38.getString("temp");
                Double tempNum38 = Double.parseDouble(temp38);
                int tempInt38 = tempNum38.intValue();
                d3[7] = tempInt38;

                Arrays.sort(d3);
                int maxAF3 = d3[d3.length -1];
                int maxF3 = (maxAF3*9/5)-459;
                int minAF3 = d3[0];
                int minF3 = (minAF3*9/5)-459;
                highView3.setText("H: " + String.valueOf(maxF3) + " °F");
                lowView3.setText("L: " + String.valueOf(minF3) + " °F");

                JSONArray weather3 = day31.getJSONArray("weather");
                JSONObject objw3 = weather3.getJSONObject(0);
                String desc3 = objw3.getString("main");
                weatherView3.setText(desc3);
                String code3 = objw3.getString("id");
                int swatch3 = Integer.parseInt(code3);
                switch (swatch3){
                    case 800:
                        imageView3.setImageResource(R.drawable.weather_sun);
                        break;
                    case 200:
                    case 201:
                    case 202:
                    case 210:
                    case 211:
                    case 212:
                    case 221:
                    case 230:
                    case 231:
                    case 232:
                        imageView3.setImageResource(R.drawable.weather_thund);
                        break;
                    case 300:
                    case 301:
                    case 302:
                    case 310:
                    case 311:
                    case 312:
                    case 313:
                    case 314:
                    case 321:
                    case 500:
                    case 501:
                    case 502:
                    case 503:
                    case 504:
                    case 511:
                    case 520:
                    case 521:
                    case 522:
                    case 531:
                        imageView3.setImageResource(R.drawable.weather_rain);
                        break;
                    case 600:
                    case 601:
                    case 602:
                    case 611:
                    case 612:
                    case 615:
                    case 616:
                    case 620:
                    case 621:
                    case 622:
                        imageView3.setImageResource(R.drawable.weather_snow);
                        break;
                    case 701:
                    case 711:
                    case 721:
                    case 731:
                    case 741:
                    case 751:
                    case 761:
                    case 762:
                    case 771:
                    case 781:
                        imageView3.setImageResource(R.drawable.weather_fog);
                        break;
                    case 801:
                    case 802:
                        imageView3.setImageResource(R.drawable.weather_part);
                        break;
                    case 803:
                    case 804:
                        imageView3.setImageResource(R.drawable.weather_cloud);
                        break;
                }

//Day 4 -- day 4 uses the max/min of object/hours 24-31
                int[] d4;
                d4 = new int[8];

                JSONObject day41 = list.getJSONObject(24);
                JSONObject main41 = day41.getJSONObject("main");
                String temp41 = main41.getString("temp");
                Double tempNum41 = Double.parseDouble(temp41);
                int tempInt41 = tempNum41.intValue();
                d4[0] = tempInt41;

                JSONObject day42 = list.getJSONObject(25);
                JSONObject main42 = day42.getJSONObject("main");
                String temp42 = main42.getString("temp");
                Double tempNum42 = Double.parseDouble(temp42);
                int tempInt42 = tempNum42.intValue();
                d4[1] = tempInt34;

                JSONObject day43 = list.getJSONObject(26);
                JSONObject main43 = day43.getJSONObject("main");
                String temp43 = main43.getString("temp");
                Double tempNum43 = Double.parseDouble(temp43);
                int tempInt43 = tempNum43.intValue();
                d4[2] = tempInt43;

                JSONObject day44 = list.getJSONObject(27);
                JSONObject main44 = day44.getJSONObject("main");
                String temp44 = main44.getString("temp");
                Double tempNum44 = Double.parseDouble(temp44);
                int tempInt44 = tempNum44.intValue();
                d4[3] = tempInt44;

                JSONObject day45 = list.getJSONObject(28);
                JSONObject main45 = day45.getJSONObject("main");
                String temp45 = main45.getString("temp");
                Double tempNum45 = Double.parseDouble(temp45);
                int tempInt45 = tempNum45.intValue();
                d4[4] = tempInt45;

                JSONObject day46 = list.getJSONObject(29);
                JSONObject main46 = day46.getJSONObject("main");
                String temp46 = main46.getString("temp");
                Double tempNum46 = Double.parseDouble(temp46);
                int tempInt46 = tempNum46.intValue();
                d4[5] = tempInt46;

                JSONObject day47 = list.getJSONObject(30);
                JSONObject main47 = day47.getJSONObject("main");
                String temp47 = main47.getString("temp");
                Double tempNum47 = Double.parseDouble(temp47);
                int tempInt47 = tempNum47.intValue();
                d4[6] = tempInt47;

                JSONObject day48 = list.getJSONObject(31);
                JSONObject main48 = day48.getJSONObject("main");
                String temp48 = main48.getString("temp");
                Double tempNum48 = Double.parseDouble(temp48);
                int tempInt48 = tempNum48.intValue();
                d4[7] = tempInt48;

                Arrays.sort(d4);
                int maxAF4 = d4[7];
                int maxF4 = (maxAF4*9/5)-459;
                int minAF4 = d4[0];
                int minF4 = (minAF4*9/5)-459;
                highView4.setText("H: " + String.valueOf(maxF4) + " °F");
                lowView4.setText("L: " + String.valueOf(minF4) + " °F");

                JSONArray weather4 = day41.getJSONArray("weather");
                JSONObject objw4 = weather4.getJSONObject(0);
                String desc4 = objw4.getString("main");
                weatherView4.setText(desc4);
                String code4 = objw4.getString("id");
                int swatch4 = Integer.parseInt(code4);
                switch (swatch4){
                    case 800:
                        imageView4.setImageResource(R.drawable.weather_sun);
                        break;
                    case 200:
                    case 201:
                    case 202:
                    case 210:
                    case 211:
                    case 212:
                    case 221:
                    case 230:
                    case 231:
                    case 232:
                        imageView4.setImageResource(R.drawable.weather_thund);
                        break;
                    case 300:
                    case 301:
                    case 302:
                    case 310:
                    case 311:
                    case 312:
                    case 313:
                    case 314:
                    case 321:
                    case 500:
                    case 501:
                    case 502:
                    case 503:
                    case 504:
                    case 511:
                    case 520:
                    case 521:
                    case 522:
                    case 531:
                        imageView4.setImageResource(R.drawable.weather_rain);
                        break;
                    case 600:
                    case 601:
                    case 602:
                    case 611:
                    case 612:
                    case 615:
                    case 616:
                    case 620:
                    case 621:
                    case 622:
                        imageView4.setImageResource(R.drawable.weather_snow);
                        break;
                    case 701:
                    case 711:
                    case 721:
                    case 731:
                    case 741:
                    case 751:
                    case 761:
                    case 762:
                    case 771:
                    case 781:
                        imageView4.setImageResource(R.drawable.weather_fog);
                        break;
                    case 801:
                    case 802:
                        imageView4.setImageResource(R.drawable.weather_part);
                        break;
                    case 803:
                    case 804:
                        imageView4.setImageResource(R.drawable.weather_cloud);
                        break;
                }

//Day 5 -- day 5 uses the max/min of object/hours 32-39
                int[] d5;
                d5 = new int[8];

                JSONObject day51 = list.getJSONObject(32);
                JSONObject main51 = day51.getJSONObject("main");
                String temp51 = main51.getString("temp");
                Double tempNum51 = Double.parseDouble(temp51);
                int tempInt51 = tempNum51.intValue();
                d5[0] = tempInt51;

                JSONObject day52 = list.getJSONObject(33);
                JSONObject main52 = day52.getJSONObject("main");
                String temp52 = main52.getString("temp");
                Double tempNum52 = Double.parseDouble(temp52);
                int tempInt52 = tempNum52.intValue();
                d5[1] = tempInt52;

                JSONObject day53 = list.getJSONObject(34);
                JSONObject main53 = day53.getJSONObject("main");
                String temp53 = main53.getString("temp");
                Double tempNum53 = Double.parseDouble(temp53);
                int tempInt53 = tempNum53.intValue();
                d5[2] = tempInt53;

                JSONObject day54 = list.getJSONObject(35);
                JSONObject main54 = day54.getJSONObject("main");
                String temp54 = main54.getString("temp");
                Double tempNum54 = Double.parseDouble(temp54);
                int tempInt54 = tempNum54.intValue();
                d5[3] = tempInt54;

                JSONObject day55 = list.getJSONObject(36);
                JSONObject main55 = day55.getJSONObject("main");
                String temp55 = main55.getString("temp");
                Double tempNum55 = Double.parseDouble(temp55);
                int tempInt55 = tempNum55.intValue();
                d5[4] = tempInt55;

                JSONObject day56 = list.getJSONObject(37);
                JSONObject main56 = day56.getJSONObject("main");
                String temp56 = main56.getString("temp");
                Double tempNum56 = Double.parseDouble(temp56);
                int tempInt56 = tempNum56.intValue();
                d5[5] = tempInt56;

                JSONObject day57 = list.getJSONObject(38);
                JSONObject main57 = day57.getJSONObject("main");
                String temp57 = main57.getString("temp");
                Double tempNum57 = Double.parseDouble(temp57);
                int tempInt57 = tempNum57.intValue();
                d5[6] = tempInt57;

                JSONObject day58 = list.getJSONObject(39);
                JSONObject main58 = day58.getJSONObject("main");
                String temp58 = main58.getString("temp");
                Double tempNum58 = Double.parseDouble(temp58);
                int tempInt58 = tempNum58.intValue();
                d5[7] = tempInt58;

                Arrays.sort(d5);
                int maxAF5 = d5[7];
                int maxF5 = (maxAF5*9/5)-459;
                int minAF5 = d5[0];
                int minF5 = (minAF5*9/5)-459;
                highView5.setText("H: " + String.valueOf(maxF5) + " °F");
                lowView5.setText("L: " + String.valueOf(minF5) + " °F");

                JSONArray weather5 = day51.getJSONArray("weather");
                JSONObject objw5 = weather5.getJSONObject(0);
                String desc5 = objw5.getString("main");
                weatherView5.setText(desc5);
                String code5 = objw5.getString("id");
                int swatch5 = Integer.parseInt(code5);
                switch (swatch5){
                    case 800:
                        imageView5.setImageResource(R.drawable.weather_sun);
                        break;
                    case 200:
                    case 201:
                    case 202:
                    case 210:
                    case 211:
                    case 212:
                    case 221:
                    case 230:
                    case 231:
                    case 232:
                        imageView5.setImageResource(R.drawable.weather_thund);
                        break;
                    case 300:
                    case 301:
                    case 302:
                    case 310:
                    case 311:
                    case 312:
                    case 313:
                    case 314:
                    case 321:
                    case 500:
                    case 501:
                    case 502:
                    case 503:
                    case 504:
                    case 511:
                    case 520:
                    case 521:
                    case 522:
                    case 531:
                        imageView5.setImageResource(R.drawable.weather_rain);
                        break;
                    case 600:
                    case 601:
                    case 602:
                    case 611:
                    case 612:
                    case 615:
                    case 616:
                    case 620:
                    case 621:
                    case 622:
                        imageView5.setImageResource(R.drawable.weather_snow);
                        break;
                    case 701:
                    case 711:
                    case 721:
                    case 731:
                    case 741:
                    case 751:
                    case 761:
                    case 762:
                    case 771:
                    case 781:
                        imageView5.setImageResource(R.drawable.weather_fog);
                        break;
                    case 801:
                    case 802:
                        imageView5.setImageResource(R.drawable.weather_part);
                        break;
                    case 803:
                    case 804:
                        imageView5.setImageResource(R.drawable.weather_cloud);
                        break;
                }


//attempt at making more elegant -- ran out of time
//                for (int i = 0; list.getJSONObject(i) < 3; i++) {
//                    JSONObject main = list.getJSONObject(i);
//                    String temp = main.getString("temp");
//                    Double tempNum = Double.parseDouble(temp);
//                    int tempInt = tempNum.intValue();
//                    d3[i] = tempInt;
//                    Arrays.sort(d3);
////                    int min = d3[0];
//                    return;
//                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
