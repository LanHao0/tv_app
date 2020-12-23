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

public class TypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<MovieType> arrayList;
    private Context context;
    private OnTypeFocusChange mCallback;


    public int getLastChildFocused() {
        return lastChildFocused;
    }

    private int lastChildFocused;



    public TypeAdapter(ArrayList<MovieType> arrayList, Context context, BrowseActivity onFocusChange) {
        this.arrayList = arrayList;
        this.context = context;
        lastChildFocused= 0;
        mCallback=onFocusChange;
        mCallback.onChange(arrayList.get(0));
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
        return new TypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TypeAdapter.ViewHolder viewHolder = ((TypeAdapter.ViewHolder) holder);

        viewHolder.textView_title.setText(arrayList.get(position).getName());

        viewHolder.linearLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    viewHolder.linearLayout2.setBackgroundColor(context.getResources().getColor(R.color.light_blue_600));
                    view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(200);
                    //todo change Result
                    mCallback.onChange(arrayList.get(position));

                } else {
                    viewHolder.linearLayout2.setBackgroundColor(Color.TRANSPARENT);
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                }


            }
        });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
