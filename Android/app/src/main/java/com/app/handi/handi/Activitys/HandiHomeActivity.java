package com.app.handi.handi.Activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.handi.handi.Fragments.HandiHomeFragment;
import com.app.handi.handi.Fragments.HandiSettingsFragment;
import com.app.handi.handi.Fragments.HandiViewProfileFragment;
import com.app.handi.handi.R;
import com.google.firebase.auth.FirebaseAuth;

public class HandiHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HandiHomeFragment.OnFragmentInteractionListener, HandiSettingsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handi_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HandiHomeFragment handiHomeFragment = HandiHomeFragment.newInstance("hello", "it's me");
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_handi_home,
                handiHomeFragment,
                handiHomeFragment.getTag()
        ).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.handi_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.activity_handi_home_drawer_item_home) {
            HandiHomeFragment handiHomeFragment = HandiHomeFragment.newInstance("somebody", "once told me");
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.content_handi_home,
                    handiHomeFragment,
                    handiHomeFragment.getTag()
            ).commit();
        }
        else if (id == R.id.activity_handi_home_drawer_item_profile) {
            HandiViewProfileFragment handiViewProfileFragment = new HandiViewProfileFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(
                    R.id.content_handi_home,
                    handiViewProfileFragment,
                    handiViewProfileFragment.getTag()
            ).commit();
        }
        else if (id == R.id.activity_handi_home_drawer_item_settings) {
            HandiSettingsFragment handiSettingsFragment = HandiSettingsFragment.newInstance("I ain't", "The sharpest tool");
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.content_handi_home,
                    handiSettingsFragment,
                    handiSettingsFragment.getTag()
            ).commit();
        }
        else if (id == R.id.activity_handi_home_drawer_item_logout) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            startActivity(new Intent(HandiHomeActivity.this, LoginOrSignupActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}