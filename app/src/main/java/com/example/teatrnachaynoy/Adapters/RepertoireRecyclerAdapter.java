package com.example.teatrnachaynoy.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teatrnachaynoy.PerformanceDetailActivity;
import com.example.teatrnachaynoy.Repertoire;
import com.example.teatrnachaynoy.databinding.RepertoireItemBinding;

import java.util.ArrayList;

public class RepertoireRecyclerAdapter extends RecyclerView.Adapter<RepertoireRecyclerAdapter.ViewHolder> {

    private ArrayList<Repertoire> repertoireArrayList;

    public RepertoireRecyclerAdapter(ArrayList<Repertoire> repertoireArrayList) {
        this.repertoireArrayList = repertoireArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RepertoireItemBinding binding = RepertoireItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Repertoire repertoire = repertoireArrayList.get(position);
        holder.binding.setRepertoire(repertoire);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PerformanceDetailActivity.class);
            intent.putExtra("href", repertoire.getLink());
            view.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return repertoireArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RepertoireItemBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }

    }
}
