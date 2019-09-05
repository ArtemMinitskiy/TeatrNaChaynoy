package com.example.teatrnachaynoy.Fragments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.teatrnachaynoy.ActorsActivity;
import com.example.teatrnachaynoy.R;
import com.example.teatrnachaynoy.Utils;

public class ContactsFragment extends Fragment {

    public ContactsFragment() {
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment, null);

        ImageView geoLoc = view.findViewById(R.id.geo);
        geoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse(Utils.GOOGLE_MAPS_URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    view.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView adressTxt = view.findViewById(R.id.adress);
        adressTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse(Utils.GOOGLE_MAPS_URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    view.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView directorTxt = view.findViewById(R.id.director);
        directorTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Log", "Hello");
                Intent intent = new Intent(getActivity(), ActorsActivity.class);
                intent.putExtra("href", "/person/1");
                intent.putExtra("imageUrl", Utils.THEATER_URL + "/images/u/person/1.jpg?v=1383865774");
                startActivity(intent);

            }
        });

        ImageView vkImage = view.findViewById(R.id.vk_logo);
        vkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse(Utils.VK_URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    view.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageView fcImage = view.findViewById(R.id.fc_logo);
        fcImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse(Utils.FACEBOOK_URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    view.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageView instImage = view.findViewById(R.id.inst_logo);
        instImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse(Utils.INSTAGRAM_URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    view.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;

    }
}
