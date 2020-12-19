package club.lanhaoo.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface VolleyCallBack {
    void onSuccessListMovie(List<Movie> result) throws JSONException;
}
