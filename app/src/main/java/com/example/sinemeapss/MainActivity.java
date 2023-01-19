package com.example.sinemeapss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView tvYear,tvActors,tvDirector,tvCountry,tvLaunguage,tvPlot,tvGenre,tvTitle;
    ProgressBar progressBar;
    ImageView ivPoster;
    EditText edmName;
    int count =0;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edmName =findViewById(R.id.edMName);
        tvYear=findViewById(R.id.tvYear);
        tvActors=findViewById(R.id.tvActors);
        tvDirector=findViewById(R.id.tvDrector);
        tvCountry=findViewById(R.id.tvCountry);
        tvLaunguage=findViewById(R.id.tvLanguage);
        ivPoster = findViewById(R.id.imageView);
        tvPlot = findViewById(R.id.tvP);
        tvGenre=findViewById(R.id.tvGenre);
        tvTitle=findViewById(R.id.tvTitle);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
        tvGenre.setVisibility(View.GONE);
        tvPlot.setVisibility(View.GONE);
        ivPoster.setVisibility(View.GONE);

    }

    public void search(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String mName = edmName.getText().toString();
        if(mName.isEmpty()){
            edmName.setError("PLEASE PROVİDE MOVİE NAME");
            return;
        }
        url ="https://www.omdbapi.com/?t="+mName+"&plot=full&apikey=e2db11fe";
        RequestQueue queue =Volley.newRequestQueue(this);
        StringRequest request =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)  {
                        tvTitle.setVisibility(View.VISIBLE);
                        tvGenre.setVisibility(View.VISIBLE);
                        tvPlot.setVisibility(View.VISIBLE);
                        ivPoster.setVisibility(View.VISIBLE);
                        Handler handler =new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);


                            }
                        },1000);
                        try {
                            JSONObject movie =new JSONObject(response);
                            String result = movie.getString("Response");
                            if (result.equals("True")){
                                Toast.makeText(MainActivity.this, "Found", Toast.LENGTH_SHORT).show();
                                String title = movie.getString("Title");
                                tvTitle.setText(""+title);
                                String genre=movie.getString("Genre");
                                tvGenre.setText(""+genre);
                                /*String year = movie.getString("Year");
                                tvYear.setText("Year: "+year);
                                String director = movie.getString("Director");
                                tvDirector.setText("Director: "+director);

                                String actors = movie.getString("Actors");
                                tvActors.setText("Actor: "+actors);
                                String country = movie.getString("Country");
                                tvCountry.setText("Country: "+country);
                                String laungage= movie.getString("Language");
                                tvLaunguage.setText("Language: "+laungage);*/
                                String plot=movie.getString("Plot");
                                tvPlot.setText("Plot: "+plot);

                                String posterUrl = movie.getString("Poster");
                                if(posterUrl.equals("N/A")){
                                }
                                else
                                {
                                    Picasso.get().load(posterUrl).into(ivPoster);
                                }
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Movie not found", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }catch (JSONException e ){
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Movie not found:", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    }
                }
        );
        queue.add(request);
    }

    public void movieDetails(View view) {
        Intent intent =new Intent(MainActivity.this,MainActivity2.class);
        intent.putExtra("url",url);
        startActivity(intent);

    }
}