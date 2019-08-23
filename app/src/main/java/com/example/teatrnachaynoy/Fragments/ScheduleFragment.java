package com.example.teatrnachaynoy.Fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teatrnachaynoy.Adapters.ScheduleRecyclerAdapter;
import com.example.teatrnachaynoy.R;
import com.example.teatrnachaynoy.Schedule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ScheduleFragment extends Fragment {

    private ProgressBar progressBar;
    private View view;

    public ScheduleFragment() {
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recycler_fragment,null);
        new ScheduleHtmlParserHelper().execute();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class ScheduleHtmlParserHelper extends AsyncTask<Void, Void, Void>{
        final ArrayList<Schedule> schedulesList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = view.findViewById(R.id.progressBar);
            progressBar.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document doc;
            Schedule schedule;
            try {
                doc = Jsoup.connect("http://tea-atr.com/timetable").get();
                Elements table = doc.select("table");
                Elements rows = table.select("tr");

                for (int i = 0; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    Elements date = cols.select("span.wrap");
                    Elements title = date.select("a.url");
                    Elements timeLenght = date.select("span.time-lenght");

//                    Log.i("Log", "Title: " + title.get(0).text());
//                    Log.i("Log", "Date: " + date.get(0).text().substring(0, cols.get(0).text().indexOf(",")));
//                    Log.i("Log", "Time and price: " + cols.get(2).text());
//                    Log.i("Log", "Time-lenght: " + timeLenght.get(0).text());
//                    Log.i("Log", "Href: " + title.attr("href"));

                    Document docInsideImage = Jsoup.connect("http://www.tea-atr.com" + title.attr("href")).get();
                    Elements image = docInsideImage.select("img.cover");
//                    Log.i("Log", "Image: " + "http://www.tea-atr.com" + image.attr("src"));

                    schedule = new Schedule(title.get(0).text(),
                            date.get(0).text().substring(0, cols.get(0).text().indexOf(",")),
                            cols.get(2).text(),
                            timeLenght.get(0).text(),
                            title.attr("href"),
                            "http://www.tea-atr.com" + image.attr("src"));

                    schedulesList.add(schedule);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);

            RecyclerView.Adapter adapter = new ScheduleRecyclerAdapter(schedulesList);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }

}
