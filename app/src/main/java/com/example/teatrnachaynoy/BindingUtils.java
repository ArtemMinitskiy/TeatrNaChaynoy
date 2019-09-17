package com.example.teatrnachaynoy;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import static com.example.teatrnachaynoy.Utils.getImageSrc;

public class BindingUtils {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).placeholder(R.drawable.ic_theater_logo).into(imageView);
    }

    @BindingAdapter({"imageActorsUrl"})
    public static void loadActorsImage(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView);
    }

    @BindingAdapter({"bind:director", "bind:link"})
    public static void directorTransition(TextView textView, String director, String link) {
        textView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ActorsActivity.class);
            intent.putExtra("href", link);
            intent.putExtra("imageUrl", getImageSrc(director.trim()));
            view.getContext().startActivity(intent);
        });
    }
}
