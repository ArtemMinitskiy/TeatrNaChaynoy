package com.example.teatrnachaynoy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ScheduleHtmlParserHelper().execute();

    }

    private void goToMainActivity(ArrayList<Schedule> scheduleList) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putParcelableArrayListExtra("Schedule", scheduleList);
        startActivity(intent);
        finish();
    }

    @SuppressLint("StaticFieldLeak")
    public class ScheduleHtmlParserHelper extends AsyncTask<Void, Void, Void> {
        final ArrayList<Schedule> schedulesList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document doc;
            Schedule schedule;
            try {
                doc = Jsoup.connect(Utils.THEATER_URL + "/timetable").get();
                Elements table = doc.select("table");
                Elements rows = table.select("tr");

                for (int i = 0; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    Elements date = cols.select("span.wrap");
                    Elements title = date.select("a.url");
                    Elements timeLenght = date.select("span.time-lenght");

                    Document docInsideImage = Jsoup.connect(Utils.THEATER_URL + title.attr("href")).get();
                    Elements image = docInsideImage.select("img.cover");

                    schedule = new Schedule(title.get(0).text(),
                            date.get(0).text().substring(0, cols.get(0).text().indexOf(",")),
                            cols.get(2).text(),
                            timeLenght.get(0).text(),
                            title.attr("href"),
                            Utils.THEATER_URL + image.attr("src"));

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

            goToMainActivity(schedulesList);
        }
    }
}
