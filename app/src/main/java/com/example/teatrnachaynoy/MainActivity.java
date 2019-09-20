package com.example.teatrnachaynoy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.teatrnachaynoy.Fragments.AboutFragment;
import com.example.teatrnachaynoy.Fragments.ContactsFragment;
import com.example.teatrnachaynoy.Fragments.NewsFragment;
import com.example.teatrnachaynoy.Fragments.RepertoireFragment;
import com.example.teatrnachaynoy.Fragments.ScheduleFragment;
import com.example.teatrnachaynoy.Fragments.TroupeFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private ArrayList<Schedule> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Intent intent = getIntent();
        scheduleList = intent.getParcelableArrayListExtra("Schedule");

        isOnline(this);
        setView(scheduleList);

    }

    private void setView(ArrayList<Schedule> scheduleList) {
        fragmentTransaction.replace(R.id.containerView, new ScheduleFragment(scheduleList)).commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorAccent));

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        fragmentTransaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.schedule:
                fragmentTransaction.replace(R.id.containerView, new ScheduleFragment(scheduleList)).addToBackStack(null).commit();
                break;
            case R.id.repertoire:
                fragmentTransaction.replace(R.id.containerView, new RepertoireFragment()).addToBackStack(null).commit();
                break;
            case R.id.troupe:
                fragmentTransaction.replace(R.id.containerView, new TroupeFragment()).addToBackStack(null).commit();
                break;
            case R.id.news:
                fragmentTransaction.replace(R.id.containerView, new NewsFragment()).addToBackStack(null).commit();
                break;
            case R.id.сontacts:
                fragmentTransaction.replace(R.id.containerView, new ContactsFragment()).addToBackStack(null).commit();
                break;
            case R.id.about_the_theater:
                fragmentTransaction.replace(R.id.containerView, new AboutFragment()).addToBackStack(null).commit();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        Toast.makeText(this, "Отсутствует подключние к интернету", Toast.LENGTH_LONG).show();
        return false;
    }
}
