package com.example.teatrnachaynoy;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.teatrnachaynoy.Fragments.About;
import com.example.teatrnachaynoy.Fragments.Contacts;
import com.example.teatrnachaynoy.Fragments.News;
import com.example.teatrnachaynoy.Fragments.Repertoire;
import com.example.teatrnachaynoy.Fragments.Schedule;
import com.example.teatrnachaynoy.Fragments.Troupe;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        setView();

    }

    private void setView() {
        fragmentTransaction.replace(R.id.containerView, new Schedule()).commit();

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
                fragmentTransaction.replace(R.id.containerView, new Schedule()).addToBackStack(null).commit();
                break;
            case R.id.repertoire:
                fragmentTransaction.replace(R.id.containerView, new Repertoire()).addToBackStack(null).commit();
                break;
            case R.id.troupe:
                fragmentTransaction.replace(R.id.containerView, new Troupe()).addToBackStack(null).commit();
                break;
            case R.id.news:
                fragmentTransaction.replace(R.id.containerView, new News()).addToBackStack(null).commit();
                break;
            case R.id.сontacts:
                fragmentTransaction.replace(R.id.containerView, new Contacts()).addToBackStack(null).commit();
                break;
            case R.id.about_the_theater:
                fragmentTransaction.replace(R.id.containerView, new About()).addToBackStack(null).commit();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
