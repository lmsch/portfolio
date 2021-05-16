package com.hfad.appgodsproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.fragments.AnalysisFragment;
import com.hfad.appgodsproject.fragments.CategoryFragment;
import com.hfad.appgodsproject.fragments.ReceiptHistoryFragment;
import com.hfad.appgodsproject.fragments.StoreFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        doContentFrameReplace(new AnalysisFragment());
    }

    private void doContentFrameReplace(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, frag);
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = null;
        switch (id) {
            case R.id.nav_add_receipt:
                intent = new Intent(this, AddReceiptOuterActivity.class);
                break;
            case R.id.nav_receipt_history:
                fragment = new ReceiptHistoryFragment();
                break;
            case R.id.nav_categories:
                fragment = new CategoryFragment();
                break;
            case R.id.nav_stores:
                fragment = new StoreFragment();
                break;
            case R.id.nav_analysis:
                fragment = new AnalysisFragment();
                break;
            default:
                fragment = null;
                intent = null;
        }
        if (intent != null) {
            startActivity(intent);
        } else if (fragment != null)
            doContentFrameReplace(fragment);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
