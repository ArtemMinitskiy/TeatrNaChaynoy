package com.example.teatrnachaynoy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teatrnachaynoy.Performance;
import com.example.teatrnachaynoy.R;
import com.example.teatrnachaynoy.databinding.PerfPhotosItemBinding;
import com.squareup.picasso.Picasso;
import com.stfalcon.imageviewer.StfalconImageViewer;

import java.util.ArrayList;

public class PerformancePhotosAdapter extends RecyclerView.Adapter<PerformancePhotosAdapter.ViewHolder> {

    private ArrayList<Performance> photosList;

    public PerformancePhotosAdapter(ArrayList<Performance> photosList) {
        this.photosList = photosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PerfPhotosItemBinding binding = PerfPhotosItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Performance photos = photosList.get(position);
        holder.binding.setPerformance(photos);
        holder.itemView.setOnClickListener(view ->
                new StfalconImageViewer.Builder<>(view.getContext(), photosList, (imageView, image) ->
                        Picasso.get()
                                .load(image.getImage_url())
                                .placeholder(R.drawable.ic_theater_logo)
                                .into(imageView))
                        .withStartPosition(position)
                        .show());

    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        PerfPhotosItemBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }

    }
}
