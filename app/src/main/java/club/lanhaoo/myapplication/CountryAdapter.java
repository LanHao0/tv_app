package club.lanhaoo.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<MovieCountry> arrayList;
    private Context context;
    private View lastViewFocused;
    private OnCountryFocusChange mCallBACK;

    public View getLastViewFocused() {
        return lastViewFocused;
    }


    public CountryAdapter(ArrayList<MovieCountry> arrayList, Context context,OnCountryFocusChange onCountryFocusChange) {
        this.arrayList = arrayList;
        this.context = context;
        this.mCallBACK=onCountryFocusChange;
        mCallBACK.onChange(arrayList.get(0));
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
        return new CountryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CountryAdapter.ViewHolder viewHolder = ((CountryAdapter.ViewHolder) holder);

        viewHolder.textView_title.setText(arrayList.get(position).getName());

        viewHolder.linearLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    viewHolder.linearLayout2.setBackgroundColor(context.getResources().getColor(R.color.light_blue_600));
                    view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(200);

                    lastViewFocused = viewHolder.linearLayout2;
                    mCallBACK.onChange(arrayList.get(position));
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
