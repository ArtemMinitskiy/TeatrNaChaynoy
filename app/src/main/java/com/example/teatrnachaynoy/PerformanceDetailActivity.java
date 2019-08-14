package com.example.teatrnachaynoy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.widget.Toolbar;

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
import java.util.Objects;

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
            StringBuffer perfDescription = new StringBuffer();
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

                Elements divDesc = doc.select("div.desc");
                Elements desc = divDesc.select("p");
                for (int pDesc = 0; pDesc < desc.size(); pDesc++){
                    perfDescription.append(desc.get(pDesc).text());
                    perfDescription.append(System.getProperty("line.separator"));

                }

                for (int i = 1; i < ul.size(); i++) {
                    Element ulElem = ul.get(i);
                    Elements li = ulElem.select("li");

                    for (int j = 0; j < li.size(); j++) {
                        Elements a = li.get(j).select("a");
                        String character = li.get(j).text().split("-")[0];
                        String name = li.get(j).text().split("-")[1];

                        actorsInfo = new ActorsInfo(character.trim(), name.trim(), getImageSrc(name.trim()), a.attr("href"));

                        actorsInfoList.add(actorsInfo);

                    }
                }

                performance = new Performance(title.get(0).text(),
                        "http://tea-atr.com" + image.attr("src"),
                        p.get(2).text(),
                        p.get(3).text(),
                        String.valueOf(perfDescription),
                        director.get(0).text()
                );

//                Log.i("Log", "Title: " + title.get(0).text());
//                Log.i("Log", "Image: " + image.attr("src"));
//                Log.i("Log", "Genre: " + p.get(2).text());
//                Log.i("Log", "Duration: " + p.get(3).text());
//                Log.i("Log", "Director: " + director.get(0).text());
//                Log.i("Log", "Description: " + divDesc.text());

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public String getImageSrc(String name) {
        String src = null;

        switch (name) {
            case "Александр Бойко":
                src = "http://tea-atr.com/images/u/person/4.jpg?v=1404166837";
                break;
            case "Александр Онищенко":
                src = "http://tea-atr.com/images/u/person/1.jpg?v=1383865774";
                break;
            case "Ангелина Смиян":
                src = "http://tea-atr.com/images/u/person/46.jpg?v=1494348313";
                break;
            case "Анна Милешина":
                src = "http://tea-atr.com/images/u/person/49.jpg?v=1553116373";
                break;
            case "Валерия Задумкина":
                src = "http://tea-atr.com/images/u/person/9.jpg?v=1502195911";
                break;
            case "Владимир Ангел":
                src = "http://tea-atr.com/images/u/person/42.jpg?v=1489614336";
                break;
            case "Владислав Костыка":
                src = "http://tea-atr.com/images/u/person/10.jpg?v=1540412793";
                break;
            case "Денис Фалюта":
                src = "http://tea-atr.com/images/u/person/32.jpg?v=1411661405";
                break;
            case "Егор Карельский":
                src = "http://tea-atr.com/images/u/person/47.jpg?v=1494347263";
                break;
            case "Елена Юзвак":
                src = "http://tea-atr.com/images/u/person/5.jpg?v=1380663572";
                break;
            case "Ирина Костырко":
                src = "http://tea-atr.com/images/u/person/7.jpg?v=1380745782";
                break;
            case "Милена Компаниец":
                src = "http://tea-atr.com/images/u/person/50.jpg?v=1547163941";
                break;
            case "Олег Симоненко":
                src = "http://tea-atr.com/images/u/person/40.jpg?v=1467660103";
                break;
            case "Олег Фендюра":
                src = "http://tea-atr.com/images/u/person/41.jpg?v=1467144625";
                break;
            case "Олег Шевчук":
                src = "http://tea-atr.com/images/u/person/13.jpg?v=1383679196";
                break;
            case "Ольга Белоконь":
                src = "http://tea-atr.com/images/u/person/51.jpg?v=1556533542";
                break;
            case "Ольга Салтыкова":
                src = "http://tea-atr.com/images/u/person/12.jpg?v=1378967052";
                break;
            case "Руслана Рудая":
                src = "http://tea-atr.com/images/u/person/3.jpg?v=1395664011";
                break;
            case "Сергей Деньга":
                src = "http://tea-atr.com/images/u/person/43.jpg?v=1554714954";
                break;
            case "Татьяна Параскева":
                src = "http://tea-atr.com/images/u/person/2.jpg?v=1379877683";
                break;
            case "Филипп Азаренко":
                src = "http://tea-atr.com/images/u/person/14.jpg?v=1379878307";
                break;
            case "Юлия Амелькина":
                src = "http://tea-atr.com/images/u/person/39.jpg?v=1447941593";
                break;
            case "Юрий Невгамонный":
                src = "http://tea-atr.com/images/u/person/44.jpg?v=1502391146";
                break;
            default:
                break;
        }

        return src;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView);
    }
}
