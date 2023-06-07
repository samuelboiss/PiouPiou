package com.example.pioupioy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TwitterActivity extends AppCompatActivity {
    TextView tweetTextView;
    ArrayAdapter<String> tweetAdapter;

    RecyclerView RecyclerTweetView;

    ListView tweetListView;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        tweetListView = findViewById(R.id.tweetListView);


        new Thread(new Runnable() {
            @SuppressLint("LogConditional")
            @Override
            public void run() {
                try {
                    Log.d("AA", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    URL url = new URL("https://twitter135.p.rapidapi.com/v2/UserTweets/?id=1666449333519020036&count=5");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // Définir les en-têtes de la requête
                    connection.setRequestProperty("X-Rapidapi-Key", "b849a33655mshfbce557eb2ff8e0p1bdc50jsnc3b6913d7740");
                    connection.setRequestProperty("X-Rapidapi-Host", "twitter135.p.rapidapi.com");

                    // Envoyer la requête GET
                    connection.setRequestMethod("GET");


                    int responseCode = connection.getResponseCode();
                    Log.d("Response Code", String.valueOf(responseCode));
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuilder response = new StringBuilder();

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        reader.close();

                        // Afficher la réponse dans les logs
                        Log.d("HTTP Response", response.toString());
                        ArrayList<String> tweets = workJsonData(response.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayTweets(tweets);
                            }
                        });
                    } else {
                        Log.e("HTTP Error", "Response Code: " + responseCode);
                    }

                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @NonNull
    public ArrayList<String> workJsonData(@NonNull String response){
        ArrayList<String> tweets = new ArrayList<>();

        String regex = "\"full_text\":\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);

        while (matcher.find()) {
            String match = matcher.group();
            match = match.replace("\"full_text\":\"", "");
            match = match.replace("\",\"is_quote", "");
            match = match.replace("\\\\n", "");

            Pattern unicodePattern = Pattern.compile("\\\\u[0-9a-fA-F]{4}");
            Matcher unicodeMatcher = unicodePattern.matcher(match);
            match = unicodeMatcher.replaceAll("");

            Log.d("Test", match);
            tweets.add(match);
        }
        return tweets;
        /*
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray tweetsArray = jsonResponse.getJSONArray("tweets");
            String total = "";

            for (int i = 0; i < tweetsArray.length(); i++) {
                JSONObject tweetObject = tweetsArray.getJSONObject(i);
                String tweetText = tweetObject.getString("text");
                total += tweetText + "\n";
                Log.d("Tweet", tweetText);

                String[] tweets = total.split("\n");
                tweetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tweets);
                tweetListView.setAdapter(tweetAdapter);
            }
            tweetTextView.setText(total);
        } catch (JSONException e) {
            e.printStackTrace();
        }
         */
    }

    public void displayTweets (@NonNull ArrayList<String> tweets) {
        ListView tweetListView = findViewById(R.id.tweetListView);
        tweetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tweets);
        tweetListView.setAdapter(tweetAdapter);
    }
}