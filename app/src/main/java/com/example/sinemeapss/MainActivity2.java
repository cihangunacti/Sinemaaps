package com.example.sinemeapss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    TextView genre,years,actors,director,plot;
    ImageView movieImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        genre=findViewById(R.id.genre);
        years=findViewById(R.id.Year);
        actors=findViewById(R.id.actors);
        director=findViewById(R.id.Director);
        plot=findViewById(R.id.plot);
        movieImage=findViewById(R.id.imageView2);
        Intent get=getIntent();
        String geturl=get.getStringExtra("url");

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =new StringRequest(Request.Method.GET, geturl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)  {
                        try {
                            JSONObject movie =new JSONObject(response);
                            String result = movie.getString("Response");
                            if (result.equals("True")){
                                Toast.makeText(MainActivity2.this, "Found", Toast.LENGTH_SHORT).show();

                                String title = movie.getString("Title");
                                setTitle(""+title);
                                String get_genre=movie.getString("Genre");
                                genre.setText(""+get_genre);

                                String getyear = movie.getString("Year");
                                years.setText(""+getyear);
                                String getdirector = movie.getString("Director");
                                director.setText(""+getdirector);

                                String getactors = movie.getString("Actors");
                                actors.setText(""+getactors);
                                String get_plot=movie.getString("Plot");
                                plot.setText(""+get_plot);

                                String posterUrl = movie.getString("Poster");
                                if(posterUrl.equals("N/A")){
                                }
                                else
                                {
                                    Picasso.get().load(posterUrl).into(movieImage);
                                }
                            }
                            else
                            {
                                Toast.makeText(MainActivity2.this, "Movie not found", Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e ){
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this, "Movie not found:", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        queue.add(request);
    }
}