package com.example.teatrnachaynoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ActorsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ActivityActorsBinding binding;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

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

                List<String> actorsList = new ArrayList<String>();
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

                String director = "";
                if (h4.size() == 2) {
                    director = h4.get(1).text();
                    List<String> directorsList = new ArrayList<String>();
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
                } else {
                    director = "";
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
            binding.setActors(actorsInfo);

            expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
            expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
            expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(), expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);

//            setListViewHeight(expandableListView, 0);

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Toast.makeText(getApplicationContext(), expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
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

    private void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
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
