package com.example.teatrnachaynoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.teatrnachaynoy.Adapters.CustomExpandableListAdapter;
import com.example.teatrnachaynoy.Adapters.PerformanceActorsAdapter;
import com.example.teatrnachaynoy.databinding.ActivityActorsBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ActorsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ActivityActorsBinding binding;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_actors);
        new PerformanceHtmlParserHelper().execute();

    }

    @SuppressLint("StaticFieldLeak")
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
//            StringBuffer actorsPerf = new StringBuffer();
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

                List<String> actorsList = new ArrayList<>();
                Elements h4 = doc.select("h4");
                String actor = h4.get(0).text();
//                actorsPerf.append(actor);
//                actorsPerf.append(System.getProperty("line.separator"));
                Element roles = doc.selectFirst("ul.roles");
                Elements liRoles = roles.select("li");
                for (int i = 0; i < liRoles.size(); i++) {
                    actorsList.add(liRoles.get(i).text());
                    Log.i("Log", "Name: " + liRoles.get(i).select("a").attr("href"));
//                    actorsPerf.append(liRoles.get(i).text());
//                    actorsPerf.append(System.getProperty("line.separator"));
                }

                String director;
                if (h4.size() == 2) {
                    director = h4.get(1).text();
                    List<String> directorsList = new ArrayList<>();
//                    actorsPerf.append(director);
//                    actorsPerf.append(System.getProperty("line.separator"));
                    Element rolesDirector = doc.select("ul.roles").get(1);
                    Elements liDirectorRoles = rolesDirector.select("li");
                    for (int i = 0; i < liDirectorRoles.size(); i++) {
                        directorsList.add(liDirectorRoles.get(i).text());
//                        actorsPerf.append(liDirectorRoles.get(i).text());
//                        actorsPerf.append(System.getProperty("line.separator"));
                    }
                    expandableListDetail.put(director, directorsList);
                }
                expandableListDetail.put(actor, actorsList);
                actorsInfo = new ActorsInfo(actorsName.text(),
                        imageUrl,
                        String.valueOf(perfDescription),
//                        "",
                        "",
//                        String.valueOf(actorsPerf),
                        ""

                );

//                Log.i("Log", "Name: " + actorsName.text());
//                Log.i("Log", "Image: " + imageUrl);
////                Log.i("Log", "Genre: " + p.get(2).text());
////                Log.i("Log", "Duration: " + p.get(3).text());
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

            expandableListView = findViewById(R.id.expandableListView);
            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(), expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);

//            setListViewHeight(expandableListView, 0);

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    String title = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
//                    Log.i("Log", title.substring(0, title.lastIndexOf("\"") + 1));
                    Intent intent = new Intent(getApplicationContext(), PerformanceDetailActivity.class);
                    intent.putExtra("href", getPerformanceHref(title.substring(0, title.lastIndexOf("\"") + 1)));
                    startActivity(intent);
                    return false;
                }
            });
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    setListViewHeight(parent, groupPosition);
                    return false;
                }
            });

            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String getPerformanceHref(String title) {
        String href = null;

        switch (title) {
            case "\"Тайна семейства Рэйвенскрофт\"":
                href = "/show/41";
                break;
            case "\"Ave Мария Ивановна\"":
                href = "/show/6";
                break;
            case "\"В мире животных\"":
                href = "/show/37";
                break;
            case "\"Гармония\"":
                href = "/show/31";
                break;
            case "\"Главное, когда\"":
                href = "/show/22";
                break;
            case "\"Две дамочки в сторону севера\"":
                href = "/show/21";
                break;
            case "\"Если бы акулы стали людьми\"":
                href = "/show/7";
                break;
            case "\"За стеклом\"":
                href = "/show/1";
                break;
            case "\"Иллюзии\"":
                href = "/show/38";
                break;
            case "\"Маленький Донни, победивший мрак\"":
                href = "/show/17";
                break;
            case "\"Натали\"":
                href = "/show/24";
                break;
            case "\"Наш городок\"":
                href = "/show/30";
                break;
            case "\"Осінь\"":
                href = "/show/25";
                break;
            case "ПРЕМЬЕРА! \"ХХ. Семейная хроника\"":
                href = "/show/44";
                break;
            case "\"С Днем Рождения. сынок!\"":
                href = "/show/42";
                break;
            case "\"Смерть Фирса\"":
                href = "/show/28";
                break;
            case "\"Смешная академия\"":
                href = "/show/29";
                break;
            case "\"Старики\"":
                href = "/show/36";
                break;
            case "\"Стоило?!\"":
                href = "/show/43";
                break;
            case "\"Странный спектакль\"":
                href = "/show/39";
                break;
            case "\"Стриптиз\"":
                href = "/show/27";
                break;
            case "\"Чеховские мотивы\"":
                href = "/show/26";
                break;
            default:
                break;
        }

        return href;
    }

    private void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null, listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
                //Add Divider Height
                totalHeight += listView.getDividerHeight() * (listAdapter.getChildrenCount(i) - 1);
            }
        }
        //Add Divider Height
        totalHeight += listView.getDividerHeight() * (listAdapter.getGroupCount() - 1);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
