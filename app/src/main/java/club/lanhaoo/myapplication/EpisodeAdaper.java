package club.lanhaoo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EpisodeAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Episode> arrayList;
    private Context context;

    public EpisodeAdaper(ArrayList<Episode> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_title;
        private LinearLayout linearLayout;
        private LinearLayout linearLayout2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_title = itemView.findViewById(R.id.episodeName);
            linearLayout=itemView.findViewById(R.id.episodeHolder);
            linearLayout2=itemView.findViewById(R.id.episocard_background);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.episode_card, parent, false);
        return new EpisodeAdaper.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EpisodeAdaper.ViewHolder viewHolder = ((EpisodeAdaper.ViewHolder) holder);
        viewHolder.textView_title.setText(arrayList.get(position).getName());

        viewHolder.linearLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    viewHolder.linearLayout2.setBackgroundColor(context.getResources().getColor(R.color.light_blue_900));
                    view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(200);
                } else {
                    viewHolder.linearLayout2.setBackgroundColor(Color.TRANSPARENT);
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                }
            }
        });

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullscreenActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, arrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
