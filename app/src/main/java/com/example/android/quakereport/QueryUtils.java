package com.example.android.quakereport;

/**
 * Created by Rishabh on 16-05-2017.
 */

import android.util.Log;

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
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;


public final class QueryUtils {

    private QueryUtils() {
    }

    public static ArrayList<Earthquake> fetchEarthQuakeData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        ArrayList<Earthquake> earthquake = extractEarthquakes(jsonResponse);
        return earthquake;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL ", e);
        }
        return url;
    }

    public static ArrayList<Earthquake> extractEarthquakes(String jsonResponse) {

        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
        Date dateObj, timeObj;
        SimpleDateFormat dateFormat, timeFormat;
        String dateToDisplay, timeToDisplay, urlString;

        try {
            JSONObject jsonObj = new JSONObject(jsonResponse);
            JSONArray feature = jsonObj.getJSONArray("features");

            for (int i = 0; i < feature.length(); i++) {
                JSONObject jsonObjCurr = feature.getJSONObject(i);
                JSONObject jsonObjProp = jsonObjCurr.getJSONObject("properties");
                Double jsonMag = jsonObjProp.optDouble("mag");
                String jsonLoc = jsonObjProp.optString("place");

                long jsonDate = jsonObjProp.optLong("updated");
                dateObj = new Date(jsonDate);
                dateFormat = new SimpleDateFormat("LLL dd, yyyy");
                dateToDisplay = dateFormat.format(dateObj);

                long time = jsonObjProp.getLong("time");
                timeObj = new Date(time);
                timeFormat = new SimpleDateFormat("h:mm:a");
                timeToDisplay = timeFormat.format(timeObj);

                urlString = jsonObjProp.getString("url");

                earthquakes.add(new Earthquake(jsonMag, jsonLoc, dateToDisplay, timeToDisplay, urlString));
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

}
