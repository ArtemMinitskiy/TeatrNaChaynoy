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
import com.example.teatrnachaynoy.databinding.ActorsViewItemBinding;

import java.util.ArrayList;

public class PerformanceActorsAdapter extends RecyclerView.Adapter<PerformanceActorsAdapter.ViewHolder> {

    private ArrayList<ActorsInfo> actorsInfoList;

    public PerformanceActorsAdapter(ArrayList<ActorsInfo> actorsInfoList) {
        this.actorsInfoList = actorsInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ActorsViewItemBinding binding = ActorsViewItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ActorsInfo actorsInfo = actorsInfoList.get(position);
        holder.binding.setActorsInfo(actorsInfo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActorsActivity.class);
                intent.putExtra("href", actorsInfo.getLink());
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return actorsInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ActorsViewItemBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), PerformanceDetailActivity.class);
//                    intent.putExtra("href", binding.getSchedule().getLink());
//
////                Log.i("Log", binding.getSchedule().getLink());
//                }
//            });
        }

    }
}
