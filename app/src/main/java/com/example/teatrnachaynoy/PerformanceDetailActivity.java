package com.example.teatrnachaynoy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teatrnachaynoy.Adapters.PerformanceActorsAdapter;
import com.example.teatrnachaynoy.databinding.ActivityPerformanceDetailBinding;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class PerformanceDetailActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ActivityPerformanceDetailBinding binding;
    private ArrayList<ActorsInfo> actorsInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_performance_detail);
        new PerformanceHtmlParserHelper().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class PerformanceHtmlParserHelper extends AsyncTask<Void, Void, Void> {
        Performance performance;
        ActorsInfo actorsInfo;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progrss_bar);
            progressBar.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document doc;
            Intent intent = getIntent();
            String hrefTxt = intent.getStringExtra("href");
            try {
                doc = Jsoup.connect("http://tea-atr.com" + hrefTxt).get();
                Elements title = doc.select("h2");
                Elements image = doc.select("img.cover");
                Elements p = doc.select("p");
                Elements div = doc.select("div.persons");
                Elements director = div.select("li");
                Elements ul = div.select("ul");

                for (int i = 1; i < ul.size(); i++){
                    Element ulElem = ul.get(i);
                    Elements li = ulElem.select("li");

                    for (int j = 0; j < li.size(); j++) {
                        Elements a = li.get(j).select("a");
                        String name = li.get(j).text().split("-")[0];
                        String character = li.get(j).text().split("-")[1];

//                        Log.i("Log", "Name: " + name.trim() + " " + "Char: " + character.trim());
//                        Log.i("Log", "Name: " + a.attr("href"));

                        actorsInfo = new ActorsInfo(name.trim(), character.trim(), "", a.attr("href"));

                        actorsInfoList.add(actorsInfo);

                    }
                }

                performance = new Performance(title.get(0).text(),
                        "http://tea-atr.com" + image.attr("src"),
                        p.get(2).text(),
                        p.get(3).text(),
                        "",
                        director.get(0).text()
                        );

//                Log.i("Log", "Title: " + title.get(0).text());
//                Log.i("Log", "Image: " + image.attr("src"));
//                Log.i("Log", "Genre: " + p.get(2).text());
//                Log.i("Log", "Duration: " + p.get(3).text());
//                Log.i("Log", "Director: " + director.get(0).text());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RecyclerView recyclerView = findViewById(R.id.perf_actors);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);

            RecyclerView.Adapter adapter = new PerformanceActorsAdapter(actorsInfoList);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

            binding.setPerformance(performance);
            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView);
    }
}
