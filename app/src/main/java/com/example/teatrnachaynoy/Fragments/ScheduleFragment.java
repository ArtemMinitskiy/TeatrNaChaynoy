package com.example.teatrnachaynoy.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teatrnachaynoy.Adapters.ScheduleRecyclerAdapter;
import com.example.teatrnachaynoy.R;

public class ScheduleFragment extends Fragment {

    public ScheduleFragment() {
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_fragment,null);
        setView(view);

        return view;
    }

    private void setView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.scheduleRecycler);
        RecyclerView.Adapter adapter;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ScheduleRecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

}
