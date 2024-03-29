package com.example.teatrnachaynoy.Fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teatrnachaynoy.ActorsInfo;
import com.example.teatrnachaynoy.Adapters.TroupeRecyclerAdapter;
import com.example.teatrnachaynoy.R;
import com.example.teatrnachaynoy.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class TroupeFragment extends Fragment {

    private ProgressBar progressBar;
    private View view;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nonswipe_recycler_fragment, null);
        new TroupeHtmlParserHelper().execute();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class TroupeHtmlParserHelper extends AsyncTask<Void, Void, Void> {
        final ArrayList<ActorsInfo> actorsInfoArrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = view.findViewById(R.id.progressBar);
            progressBar.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document doc;
            ActorsInfo actorsInfo;
            try {
                doc = Jsoup.connect(Utils.THEATER_URL + "/person").get();
                Elements pageUrl = doc.select("li.last");
                String hrefLastPage = pageUrl.select("a").attr("href");
                int numberOfPage = Integer.parseInt(hrefLastPage.substring(hrefLastPage.lastIndexOf("=") + 1));
                String url = Utils.THEATER_URL + "/person?page=";
                int i = 0;

                do {
                    i++;
                    Document docPage = Jsoup.connect(url + i).get();

                    Elements divItems = docPage.select("div.items");
                    Elements divItem = divItems.select("div.item");
                    for (int j = 0; j < divItem.size(); j++) {

                        String imageUrl = divItem.select("img").get(j).attr("src");
                        Node description = divItem.select("div.desc").get(j).childNode(2);
                        Element name = divItem.select("h3").get(j);
                        String link = name.select("a").get(0).attr("href");

//                        Log.i("Log", "Image: " + "http://www.tea-atr.com" + imageUrl);
//                        Log.i("Log", "Name: " + name.text());
//                        Log.i("Log", "Description: " + description.replace(name.text(), "").trim());
//                        Log.i("Log", "Link: " + link);

                        actorsInfo = new ActorsInfo(String.valueOf(description),
                                name.text(),
                                Utils.THEATER_URL + imageUrl,
                                link);

                        actorsInfoArrayList.add(actorsInfo);
                    }
                }while (i < numberOfPage);

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

            RecyclerView.Adapter adapter = new TroupeRecyclerAdapter(actorsInfoArrayList);
            recyclerView.setAdapter(adapter);
            Utils.recyclerAnimated(recyclerView, adapter, getContext());
            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }
}
