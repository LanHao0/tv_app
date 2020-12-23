package club.lanhaoo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;




public class BrowseActivity extends Activity implements OnTypeFocusChange, OnCountryFocusChange {
    private RecyclerView recyclerCountryChoose;
    private RecyclerView recyclerTypeChoose;
    private RecyclerView recyclerChooseResult;

    private TypeAdapter adapterTypeAdapter;
    private CountryAdapter adapterCountryAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    private RequestQueue requestQueue;
    private MovieType selectedMovieType;
    private MovieCountry selectedMovieCountry;
    private TextView tv_type;
    private TextView tv_country;


    private ArrayList<JSONObject> arrayList_video;
    private int nowPage = 1;
    private VideoAdapter videoAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        selectedMovieType = null;
        selectedMovieCountry = null;

        recyclerChooseResult = findViewById(R.id.recyclerChooseResult);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BrowseActivity.this, 6);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);


        recyclerChooseResult.setLayoutManager(gridLayoutManager);

        recyclerChooseResult.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ConstraintLayout constraintLayout = findViewById(R.id.choose_selectionLayout);
                if (b) {
                    constraintLayout.animate()
                            .y(20f)
                            .setDuration(1000);
                } else {
                    constraintLayout.animate()
                            .y(-20f)
                            .setDuration(1000);
                }
            }
        });

        tv_type = findViewById(R.id.textView_typeSelection);
        tv_country = findViewById(R.id.textView_countrySelection);

        ArrayList<MovieType> arrayList_movieType = new ArrayList<MovieType>();

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String API_getCategories = new ServerUrl().getServerUrl("getCategories.php");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_getCategories, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        MovieType movieType = new MovieType(response.getJSONObject(i).getString("t_name"), response.getJSONObject(i).getString("t_id"));
                        arrayList_movieType.add(movieType);

                    }
                    LoadType(arrayList_movieType);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);


        ArrayList<MovieCountry> arrayList_movieCountry = new ArrayList<MovieCountry>();
        String API_Country = new ServerUrl().getServerUrl("getCountries.php");
        JsonArrayRequest jsonArrayCountry = new JsonArrayRequest(Request.Method.GET, API_Country, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        MovieCountry movieCountry = new MovieCountry(response.getJSONObject(i).getString("country"));
                        arrayList_movieCountry.add(movieCountry);

                    }
                    LoadCountry(arrayList_movieCountry);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayCountry);


    }


    private void LoadType(ArrayList<MovieType> arrayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mlayoutManager = linearLayoutManager;

        recyclerTypeChoose = findViewById(R.id.recyclerTypeChoose);

        adapterTypeAdapter = new TypeAdapter(arrayList, getApplicationContext(), this);
        recyclerTypeChoose.setLayoutManager(mlayoutManager);
        recyclerTypeChoose.setAdapter(adapterTypeAdapter);


    }

    private void LoadCountry(ArrayList<MovieCountry> arrayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);


        recyclerCountryChoose = findViewById(R.id.recyclerCountryChoose);
        recyclerCountryChoose.setLayoutManager(linearLayoutManager);
        adapterCountryAdapter = new CountryAdapter(arrayList, getApplicationContext(), this);
        recyclerCountryChoose.setAdapter(adapterCountryAdapter);

    }

    @Override
    public void onChange(MovieType movieType) {
        selectedMovieType = movieType;
        tv_type.setText(movieType.getName());
        changeVideoList();
    }

    @Override
    public void onChange(MovieCountry movieCountry) {
        selectedMovieCountry = movieCountry;
        tv_country.setText(movieCountry.getName());
        changeVideoList();
    }

    public void changeVideoList() {
        arrayList_video = new ArrayList<JSONObject>();

        if (selectedMovieCountry != null && selectedMovieType != null) {

            String API_getMovies = new ServerUrl().getServerUrl("get10newByTypeAndCountry.php?type=" + selectedMovieType.getId() + "&country=" + selectedMovieCountry.getName() + "&page=" + nowPage);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_getMovies, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            arrayList_video.add(response.getJSONObject(i));
                        }

                        LoadVideo(arrayList_video);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonArrayRequest);
        }

    }

    private void LoadVideo(ArrayList<JSONObject> arrayList) {
        videoAdapter = new VideoAdapter(arrayList, BrowseActivity.this);
        recyclerChooseResult.setAdapter(videoAdapter);

    }


}