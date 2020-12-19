package club.lanhaoo.myapplication;

import android.content.Context;

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
import java.util.List;

public final class MovieList {
    private List<Movie> list;
    private static long count = 0;
    private RequestQueue requestQueue;

//    public static List<Movie> getList(int Type) {
//        list = setupMovies(Type);
//        return list;
//    }

    public MovieList(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }


    public List<Movie> get10NewVideoByType(int Type, final VolleyCallBack callBack) {
        String API_getCategories = new ServerUrl().getServerUrl("get10newByType.php?type=" + Type + "&page=1");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_getCategories, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    list = new ArrayList<>();
                    for (int index = 0; index < response.length(); index++) {
                        JSONObject video = response.getJSONObject(index);
                        list.add(
                                buildMovieInfo(
                                        video.getString("d_name"),
                                        video.getString("d_content"),
                                        " ",
                                        video.getString("d_playurl"),
                                        video.getString("d_pic"),
                                        video.getString("d_pic")
                                ));
                    }
                    callBack.onSuccessListMovie(list);
                } catch (JSONException ignored) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);


        return null;
    }

    private static Movie buildMovieInfo(
            String title,
            String description,
            String studio,
            String videoUrl,
            String cardImageUrl,
            String backgroundImageUrl) {
        Movie movie = new Movie();
        movie.setId(count++);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setStudio(studio);
        movie.setCardImageUrl(cardImageUrl);
        movie.setBackgroundImageUrl(backgroundImageUrl);
        movie.setVideoUrl(videoUrl);
        return movie;
    }
}