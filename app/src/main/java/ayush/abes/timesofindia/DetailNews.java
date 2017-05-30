package ayush.abes.timesofindia;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

public class DetailNews extends AppCompatActivity {
private TextView desc_Title,desc_Content,desc_link,desc_date,desc_time;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
setUpUIViews();
        Bundle bundle=getIntent().getExtras();
        if(bundle != null){
            String json = bundle.getString("newsmodel"); // getting the model from MainActivity send via extras
            NewsModel newsModel = new Gson().fromJson(json, NewsModel.class);
            desc_Title.setText(newsModel.getTitle());
            desc_time.setText(newsModel.getTime());
            desc_date.setText( newsModel.getDate());
            desc_Content.setText(newsModel.getContent());
            desc_link.setText(newsModel.getLink());

    }}
        private void setUpUIViews() {
            //ivMovieIcon = (ImageView)findViewById(R.id.ivIcon);
            desc_Title = (TextView)findViewById(R.id.text_title);
            desc_time = (TextView)findViewById(R.id.text_time);
            desc_date = (TextView)findViewById(R.id.text_date);
            desc_Content = (TextView)findViewById(R.id.text_content);
            desc_link = (TextView)findViewById(R.id.text_link);

        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
