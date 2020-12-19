package club.lanhaoo.myapplication;

import android.annotation.SuppressLint;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends Activity {
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        Intent intent = getIntent();
        Movie movie =
                (Movie) intent.getSerializableExtra(DetailsActivity.MOVIE);
        System.out.println("进入2");
        videoView = (VideoView)findViewById(R.id.video_view);
        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                videoView.start();

            }
        });

        //For now we just picked an arbitrary item to play
        String videoUrl=movie.getVideoUrl();
        System.out.println(videoUrl.split("\\$\\$\\$")[0]);
        videoView.setVideoURI(Uri.parse(videoUrl.split("\\$\\$\\$")[0].split("\\$")[1]));

    }

}