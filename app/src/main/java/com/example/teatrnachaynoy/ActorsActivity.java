package com.example.teatrnachaynoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.teatrnachaynoy.databinding.ActivityActorsBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActorsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView actorsDesc, director;
    private ActivityActorsBinding binding;
    private List<String> listActorsLinks, listDirectorLinks;
    private ListView listViewActors, listViewDirector;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_actors);
        new PerformanceHtmlParserHelper().execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class PerformanceHtmlParserHelper extends AsyncTask<Void, Void, Void> {
        ActorsInfo actorsInfo;
        private ArrayList<String> listActors, listDirector;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            actorsDesc = findViewById(R.id.actorsDescription);
            listViewActors = findViewById(R.id.listActors);
            listViewDirector = findViewById(R.id.listDirector);
            director = findViewById(R.id.directorTitle);
            scrollView = findViewById(R.id.scrollView);
            scrollView.setFocusableInTouchMode(true);
            scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            progressBar.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document doc;
            final StringBuffer actorsDescription = new StringBuffer();
            Intent intent = getIntent();
            String hrefTxt = intent.getStringExtra("href");
            String imageUrl = intent.getStringExtra("imageUrl");
            try {
                doc = Jsoup.connect(Utils.THEATER_URL + hrefTxt).get();
                Elements actorsName = doc.select("h2");

                //actors description
                Elements divDesc = doc.select("div.desc");
                Elements desc = divDesc.select("p");
                for (int pDesc = 0; pDesc < desc.size(); pDesc++) {
                    actorsDescription.append(desc.get(pDesc));
                }
                actorsDesc.post(() -> MakeLinksClicable.textEditor(actorsDesc, String.valueOf(actorsDescription)));

                listActorsLinks = new ArrayList<>();
                listActors = new ArrayList<>();

//                Actors Roles
                Elements h4 = doc.select("h4");
                Element roles = doc.selectFirst("ul.roles");
                Elements liRoles = roles.select("li");
                for (int i = 0; i < liRoles.size(); i++) {
                    listActors.add(liRoles.get(i).text());
                    listActorsLinks.add(liRoles.get(i).select("a").attr("href"));
                }

//                Director;
                if (h4.size() == 2) {
                    listDirector = new ArrayList<>();
                    listDirectorLinks = new ArrayList<>();
                    Element rolesDirector = doc.select("ul.roles").get(1);
                    Elements liDirectorRoles = rolesDirector.select("li");
                    for (int i = 0; i < liDirectorRoles.size(); i++) {
                        listDirector.add(liDirectorRoles.get(i).text());
                        listDirectorLinks.add(liDirectorRoles.get(i).select("a").attr("href"));
                    }
                } else {
                    director.post(() -> director.setVisibility(View.INVISIBLE));
                }
                actorsInfo = new ActorsInfo(actorsName.text(), imageUrl);

//                Log.i("Log", "Name: " + actorsName.text());
//                Log.i("Log", "Image: " + imageUrl);
//                Log.i("Log", "Genre: " + p.get(2).text());
//                Log.i("Log", "Duration: " + p.get(3).text());
//                Log.i("Log", "Actors: " + actorsPerf + " Director: " + director);
//                Log.i("Log", "Description: " + perfDescription);

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

            binding.setActors(actorsInfo);

            listViewActors.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, listActors));
            listViewActors.setOnItemClickListener((adapterView, view, position, l) -> transition(listActorsLinks, position));
            Utils.setDynamicHeight(listViewActors);

            if (listDirector != null) {
                listViewDirector.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, listDirector));
                listViewDirector.setOnItemClickListener((adapterView, view, position, l) -> transition(listDirectorLinks, position));
                Utils.setDynamicHeight(listViewDirector);
            }

//            scrollView.fullScroll(ScrollView.FOCUS_UP);

            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void transition(List<String> listLinks, int position){
        Intent intent = new Intent(this, PerformanceDetailActivity.class);
        intent.putExtra("href", listLinks.get(position));
        startActivity(intent);
        finish();
    }
}
