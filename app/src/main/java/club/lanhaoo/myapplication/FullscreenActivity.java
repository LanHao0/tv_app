package club.lanhaoo.myapplication;

import android.annotation.SuppressLint;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
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
    private boolean pause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        Intent intent = getIntent();
        String videoUrl =
                (String) intent.getSerializableExtra(DetailsActivity.MOVIE);


        videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                videoView.start();

            }
        });

        videoView.setVideoURI(Uri.parse(videoUrl));
        videoView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                System.out.println("keyCode: " + keyCode);
                VideoView view = (VideoView) v;
                if (keyCode == 23) {
                    //center
                    if (!pause) {
                        view.pause();
                        pause = true;
                    } else {
                        view.start();
                        pause = false;
                    }
                } else if (keyCode == 22) {
                    //right
                    if (view.getDuration() > view.getCurrentPosition() + 60000) {
                        view.seekTo(view.getCurrentPosition() + 60000);
                    } else {
                        view.seekTo(view.getDuration() - 30000);
                    }

                } else if (keyCode == 21) {
                    //left
                    if (view.getCurrentPosition() - 60000 > 0) {
                        view.seekTo(view.getCurrentPosition() - 60000);
                    } else {
                        view.seekTo(0);
                    }
                }
                System.out.println("event: " + event.toString());
                return false;
            }
        });
    }

}