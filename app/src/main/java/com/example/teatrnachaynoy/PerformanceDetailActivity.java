package com.example.teatrnachaynoy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.example.teatrnachaynoy.databinding.ActivityPerformanceDetailBinding;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PerformanceDetailActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ActivityPerformanceDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_performance_detail);
        new PerformanceHtmlParserHelper().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class PerformanceHtmlParserHelper extends AsyncTask<Void, Void, Void> {
        Performance performance;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progrss_bar);
            progressBar.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document doc;
            StringBuffer actors = new StringBuffer();
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
                        actors.append(li.get(j).text());
                        actors.append(System.getProperty("line.separator"));
//                        Log.i("Log", "Person: " + actors);

                    }
                }

                performance = new Performance(title.get(0).text(),
                        "http://tea-atr.com" + image.attr("src"),
                        p.get(2).text(),
                        p.get(3).text(),
                        "",
                        director.get(0).text(),
                        actors);

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

            binding.setPerformance(performance);
            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView);
    }
}
