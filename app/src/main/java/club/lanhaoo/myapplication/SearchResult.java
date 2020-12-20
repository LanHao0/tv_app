package club.lanhaoo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchResult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        TextView search_keyWord=findViewById(R.id.textView_searchKeyWord);
        String keyWord =(String) getIntent().getSerializableExtra("search_keyWord");
        search_keyWord.setText("显示 “"+keyWord+"” 的搜索结果");


    }
}