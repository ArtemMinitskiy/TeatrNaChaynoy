package com.example.teatrnachaynoy;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teatrnachaynoy.Adapters.PerformanceActorsAdapter;
import com.example.teatrnachaynoy.Adapters.PerformancePhotosAdapter;
import com.example.teatrnachaynoy.databinding.ActivityPerformanceDetailBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.teatrnachaynoy.Utils.getImageSrc;

public class PerformanceDetailActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ActivityPerformanceDetailBinding binding;
    private String hrefTxt;
    private ArrayList<ActorsInfo> actorsInfoList = new ArrayList<>();
    private ArrayList<Performance> photosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hrefTxt = getIntent().getStringExtra("href");
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
            final StringBuffer perfDescription = new StringBuffer();

            try {
                doc = Jsoup.connect(Utils.THEATER_URL + hrefTxt).get();
                Elements title = doc.select("h2");
                Elements image = doc.select("img.cover");
                Elements p = doc.select("p");
                Elements div = doc.select("div.persons");
                Elements director = div.select("li");
                Elements hrefDirector = director.select("a");
                Elements ul = div.select("ul");

                //Performance Genre and Duration
                perfDescription.append(p.get(2));
                perfDescription.append(System.lineSeparator());
                perfDescription.append(p.get(3));

                //Performance Photos
                Elements a_photos = doc.select("a.fancybox-thumb");
                for (int i = 0; i < a_photos.size(); i++) {
                    performance = new Performance(Utils.THEATER_URL + a_photos.get(i).attr("href"));
                    photosList.add(performance);
                }
                //Performance Description
                Elements divDesc = doc.select("div.desc");
                Elements desc = divDesc.select("p");
                for (int pDesc = 0; pDesc < desc.size(); pDesc++) {
                    perfDescription.append(desc.get(pDesc));
                }

                //Performance Actors
                for (int i = 1; i < ul.size(); i++) {
                    Element ulElem = ul.get(i);
                    Elements li = ulElem.select("li");

                    for (int j = 0; j < li.size(); j++) {
                        Elements link = li.get(j).select("a");
                        String character = li.get(j).text().split("-")[0];
                        String name = li.get(j).text().split("-")[1];

                        actorsInfo = new ActorsInfo(character.trim(), name.trim(), getImageSrc(name.trim()), link.attr("href"));

                        actorsInfoList.add(actorsInfo);

                    }
                }

                performance = new Performance(title.get(0).text(),
                        Utils.THEATER_URL + image.attr("src"),
                        String.valueOf(perfDescription),
                        hrefDirector.get(0).text(),
                        hrefDirector.attr("href"));

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            RecyclerView recyclerActorsView = findViewById(R.id.perf_actors);
            RecyclerView recyclerPhotosView = findViewById(R.id.perf_photos);

            final TextView notExistPhotos = findViewById(R.id.notExistImage);
            if (photosList.isEmpty()) {
                notExistPhotos.post(() -> notExistPhotos.setVisibility(View.VISIBLE));
            }
            final TextView perfDesc = findViewById(R.id.perf_description);
            perfDesc.post(() -> MakeLinksClicable.textEditor(perfDesc, performance.getDescription()));

            LinearLayoutManager actrosLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerActorsView.setHasFixedSize(true);
            recyclerPhotosView.setHasFixedSize(true);
            recyclerActorsView.setLayoutManager(actrosLayoutManager);
            recyclerPhotosView.setLayoutManager(layoutManager);

            RecyclerView.Adapter actorsAdapter = new PerformanceActorsAdapter(actorsInfoList);
            RecyclerView.Adapter photosAdapter = new PerformancePhotosAdapter(photosList);
            actorsAdapter.notifyDataSetChanged();
            photosAdapter.notifyDataSetChanged();
            recyclerActorsView.setAdapter(actorsAdapter);
            recyclerPhotosView.setAdapter(photosAdapter);

            binding.setPerformance(performance);
            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
