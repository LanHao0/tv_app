package club.lanhaoo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class VideoDetails extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);

        Movie video = (Movie) getIntent().getSerializableExtra(DetailsActivity.MOVIE);

        ImageView videoCover=findViewById(R.id.detail_movieCover);
        Glide.with(getApplicationContext()).load(video.getCardImageUrl()).into(videoCover);

        TextView title=findViewById(R.id.detail_movieTitle);
        title.setText(video.getTitle());
        TextView des=findViewById(R.id.detail_movieDes);
        des.setText(video.getDescription());

        Button button_start=findViewById(R.id.detail_btnStart);

        button_start.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(200);
                } else {
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                }
            }
        });

        String videoUrl= video.getVideoUrl();

        ArrayList<Episode> arrayList=new ArrayList<Episode>();
        recyclerView=findViewById(R.id.detail_recycleViewList);


        if (videoUrl.contains("#")){
            button_start.setVisibility(View.INVISIBLE);
            String[] episodeList=video.getVideoUrl().split("#");

            for (int i=0;i<episodeList.length;i++){
                String[] episodeName_url=episodeList[i].split("\\$");
                Episode episode=new Episode(episodeName_url[0],episodeName_url[1]);
                if (episode.getUrl().contains(".m3u8")||episode.getUrl().contains(".mp4")){
                    arrayList.add(episode);
                }
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            mlayoutManager = linearLayoutManager;


            madapter = new EpisodeAdaper(arrayList,VideoDetails.this);
            recyclerView.setLayoutManager(mlayoutManager);
            recyclerView.setAdapter(madapter);


        }else{
            TextView tv_list=findViewById(R.id.detail_textViewLIST);
            tv_list.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            button_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(VideoDetails.this, FullscreenActivity.class);
                    intent.putExtra(DetailsActivity.MOVIE, video.getVideoUrl().split("\\$\\$\\$")[0].split("\\$")[1]);
                    startActivity(intent);
                }
            });

        }






    }
}