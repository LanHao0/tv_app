package club.lanhaoo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Search extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TextView textView=findViewById(R.id.search_KeyWord);
        Button btn_Search = (Button) findViewById(R.id.button_Search);
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this, SearchResult.class);
                intent.putExtra("search_keyWord", textView.getText());
                startActivity(intent);
            }
        });

        btn_Search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(200);
                }else{
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);

                }

            }
        });

    }
}