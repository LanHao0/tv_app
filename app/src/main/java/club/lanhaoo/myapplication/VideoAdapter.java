package club.lanhaoo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.leanback.widget.ImageCardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public VideoAdapter(ArrayList<JSONObject> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    private ArrayList<JSONObject> arrayList;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_title;
        private TextView textView_des;
        private ImageView imageView;
        private CardView cardView;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_title = itemView.findViewById(R.id.title);
            textView_des = itemView.findViewById(R.id.des);
            imageView = itemView.findViewById(R.id.videoCover);
            cardView = itemView.findViewById(R.id.videoCard);
            linearLayout = itemView.findViewById(R.id.cardholder);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.video_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = ((ViewHolder) holder);
        try {
            viewHolder.textView_title.setText(arrayList.get(position).getString("d_name"));
            viewHolder.textView_des.setText(arrayList.get(position).getString("d_content"));
            Glide.with(context).load(arrayList.get(position).getString("d_pic")).into(viewHolder.imageView);

            viewHolder.linearLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) {
                        view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(200);
                    } else {
                        view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                    }
                }
            });
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        JSONObject jsonObject=arrayList.get(position);
                        Movie movie=new Movie(jsonObject.getString("d_name"),jsonObject.getString("d_content"),jsonObject.getString("d_pic"),jsonObject.getString("d_playurl"));

                        Intent intent = new Intent(context, VideoDetails.class);
                        intent.putExtra(DetailsActivity.MOVIE, movie);

                        Activity activity =(Activity)context;
                        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                viewHolder.imageView,
                                DetailsActivity.SHARED_ELEMENT_NAME)
                                .toBundle();

                        context.startActivity(intent,bundle);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
