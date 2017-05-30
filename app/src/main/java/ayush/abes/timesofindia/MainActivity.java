package ayush.abes.timesofindia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textresult;
    Button hitbutton;
    private String TAG = "Main";
    String image;
    private ListView newsList;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsList = (ListView) findViewById(R.id.movie_list);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...Please Wait");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        new JSONTask().execute("http://codedamn.com/filesCodedamn/news.php/");
    }

    public class JSONTask extends AsyncTask<String, String, List<NewsModel>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        @Override
        protected List<NewsModel> doInBackground(String... params) {
            Log.d("result", "donInBackground called");
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                List<NewsModel> newsModelList = new ArrayList<>();
                String finalJson = buffer.toString();
                //  NewsModel newsModel = new NewsModel();
                JSONObject parentObject = new JSONObject(finalJson);
                Gson gson = new Gson();
                for (int i = 0; i <=24; i++) {
                    JSONObject jsonObject = parentObject.getJSONObject(i + "");

                    NewsModel newsModel = gson.fromJson(jsonObject.toString(), NewsModel.class);

                    Log.d(TAG, "doInBackground: " + newsModel);
                    newsModelList.add(newsModel);

                }
                return newsModelList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<NewsModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            // textresult.setText(result);
            if (result != null) {
                NewsAdapter adapter = new NewsAdapter(getApplicationContext(), R.layout.news_layout, result);
                newsList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
               /* newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        NewsModel newsModel = result.get(position);
                        Intent intent = new Intent(MainActivity.this, DetailNews.class);
                        intent.putExtra("newsmodel", new Gson().toJson(newsModel));
                        startActivity(intent);
                    }
                });
*/

                Log.d("result", result.get(0).getTitle() + "ans");
            } else {
                Toast.makeText(MainActivity.this, "Not able to fetch", Toast.LENGTH_SHORT).show();
            }
        }

        public class NewsAdapter extends ArrayAdapter {
            private List<NewsModel> newsModelsList;
            private int resource;
            private LayoutInflater inflater;

            public NewsAdapter(Context context, int resource, List<NewsModel> objects) {
                super(context, resource, objects);

                Log.d("result", "title is " + objects.get(3).getTitle());
                Log.d("result", "title is " + objects.get(4).getTitle());
                Log.d("result", "title is " + objects.get(5).getTitle());

                this.newsModelsList = objects;

                Log.d("result", "title is " + newsModelsList.get(3).getTitle());

                this.resource = resource;
                this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;

                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = inflater.inflate(resource, null);
                    holder.NewsTitle = (TextView) convertView.findViewById(R.id.text_title);
                    holder.NewsDate = (TextView) convertView.findViewById(R.id.text_date);
                    holder.NewsContent = (TextView) convertView.findViewById(R.id.text_desc);
                    holder.NewsTime = (TextView) convertView.findViewById(R.id.text_time);
                    holder.NewsTab=(TextView) convertView.findViewById(R.id.text_click);
                    //     holder.NewsLink = (TextView) convertView.findViewById(R.id.text_link);
                    //  holder.NewsImage = (ImageView) convertView.findViewById(R.id.news_image);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                Log.d("result", "get View called");

//                ImageLoader.getInstance().displayImage(newsModelsList.get(position).getImage(), holder.NewsImage);
                Log.d("result", "title is this`" + newsModelsList.get(position).getTitle());

                holder.NewsTitle.setText(newsModelsList.get(position).getTitle());
                holder.NewsDate.setText(newsModelsList.get(position).getDate());
                holder.NewsContent.setText(newsModelsList.get(position).getContent());
                holder.NewsTime.setText(newsModelsList.get(position).getTime());
                // holder.NewsLink.setText(newsModelsList.get(position).getLink());
                final View finalConvertView = convertView;
                holder.NewsTab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent webintent = new Intent(finalConvertView.getContext(), WebActivity.class);
                        webintent.putExtra("url", newsModelsList.get(position).getLink()).
                                putExtra("title", newsModelsList.get(position).getTitle());
                        startActivity(webintent);
                    }
                });
                return convertView;
            }

            class ViewHolder {
                private TextView NewsTitle, NewsDate, NewsTime, NewsContent, NewsLink,NewsTab;
                ImageView NewsImage;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            new JSONTask().execute("http://codedamn.com/filesCodedamn/news.php/");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
