package com.example.teatrnachaynoy.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teatrnachaynoy.ActorsActivity;
import com.example.teatrnachaynoy.ActorsInfo;
import com.example.teatrnachaynoy.PerformanceDetailActivity;
import com.example.teatrnachaynoy.databinding.TroupeItemBinding;

import java.util.ArrayList;

public class TroupeRecyclerAdapter extends RecyclerView.Adapter<TroupeRecyclerAdapter.ViewHolder> {

    private ArrayList<ActorsInfo> actorsInfoArrayList;

    public TroupeRecyclerAdapter(ArrayList<ActorsInfo> actorsInfoArrayList) {
        this.actorsInfoArrayList = actorsInfoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TroupeItemBinding binding = TroupeItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ActorsInfo actorsInfo = actorsInfoArrayList.get(position);
        holder.binding.setTroupe(actorsInfo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActorsActivity.class);
                intent.putExtra("href", actorsInfo.getLink());
                intent.putExtra("imageUrl", actorsInfo.getImage());
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return actorsInfoArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TroupeItemBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }

    }
}
