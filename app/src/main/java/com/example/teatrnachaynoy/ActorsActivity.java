package com.example.teatrnachaynoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.teatrnachaynoy.Adapters.PerformanceActorsAdapter;
import com.example.teatrnachaynoy.databinding.ActivityActorsBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ActorsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ActivityActorsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_actors);
        new PerformanceHtmlParserHelper().execute();

    }

    private class PerformanceHtmlParserHelper extends AsyncTask<Void, Void, Void> {
        ActorsInfo actorsInfo;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document doc;
            StringBuffer perfDescription = new StringBuffer();
            StringBuffer actorsPerf = new StringBuffer();
            Intent intent = getIntent();
            String hrefTxt = intent.getStringExtra("href");
            String imageUrl = intent.getStringExtra("imageUrl");
            try {
                doc = Jsoup.connect("http://tea-atr.com" + hrefTxt).get();
                Elements actorsName = doc.select("h2");

                Elements divDesc = doc.select("div.desc");
                Elements desc = divDesc.select("p");
                for (int pDesc = 0; pDesc < desc.size(); pDesc++) {
                    perfDescription.append(desc.get(pDesc).text());
                    perfDescription.append(System.getProperty("line.separator"));

                }

                Elements h4 = doc.select("h4");
                String actor = h4.get(0).text();
                actorsPerf.append(actor);
                actorsPerf.append(System.getProperty("line.separator"));
                Element roles = doc.selectFirst("ul.roles");
                Elements liRoles = roles.select("li");
                for (int i = 0; i < liRoles.size(); i++){
                    actorsPerf.append(liRoles.get(i).text());
                    actorsPerf.append(System.getProperty("line.separator"));
                }
                String director = "";
                if (h4.size() == 2) {
                    director = h4.get(1).text();
                    actorsPerf.append(director);
                    actorsPerf.append(System.getProperty("line.separator"));
                    Element rolesDirector = doc.select("ul.roles").get(1);
                    Elements liDirectorRoles = rolesDirector.select("li");
                    for (int i = 0; i < liDirectorRoles.size(); i++){
                        actorsPerf.append(liDirectorRoles.get(i).text());
                        actorsPerf.append(System.getProperty("line.separator"));
                    }

                }else {
                    director = "";
                }

                actorsInfo = new ActorsInfo(actorsName.text(),
                        imageUrl,
                        String.valueOf(perfDescription),
                        String.valueOf(actorsPerf),
                        ""

                );

                Log.i("Log", "Name: " + actorsName.text());
                Log.i("Log", "Image: " + imageUrl);
//                Log.i("Log", "Genre: " + p.get(2).text());
//                Log.i("Log", "Duration: " + p.get(3).text());
                Log.i("Log", "Actors: " + actorsPerf + " Director: " + director);
                Log.i("Log", "Description: " + perfDescription);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            binding.setActors(actorsInfo);
            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }
}
