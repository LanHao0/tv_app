package club.lanhaoo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private ArrayList<JSONObject> arrayList;

    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        keyword = getIntent().getExtras().getString("search_keyWord");

        TextView textView = findViewById(R.id.search_Result);
        textView.setText("正在显示 “"+keyword+"” 的搜索结果...");

        arrayList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyecler);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,6);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        mlayoutManager = gridLayoutManager;

        madapter = new VideoAdapter(arrayList, SearchResult.this);
        recyclerView.setLayoutManager(mlayoutManager);

        MovieList movieList = new MovieList(getApplicationContext());

        movieList.getVideoBySearch(keyword, new VolleyCallBackArray() {
            @Override
            public void onSuccessListMovie(JSONArray jsonArray) throws JSONException {
                for (int i = 0; i < jsonArray.length(); i++) {
                    arrayList.add(jsonArray.getJSONObject(i));
                }
                madapter.notifyItemInserted(arrayList.size());
            }
        });

        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(madapter);


    }


}