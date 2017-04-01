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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.app.handi.handi.DataTypes.HandimanData;
import com.app.handi.handi.DataTypes.Job;
import com.app.handi.handi.Firebase.HelperHandiMan;
import com.app.handi.handi.Fragments.HandiHomeFragment;
import com.app.handi.handi.Fragments.HandiSettingsFragment;
import com.app.handi.handi.Fragments.HandiViewProfileFragment;
import com.app.handi.handi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HandiHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HandiHomeFragment.OnFragmentInteractionListener, HandiSettingsFragment.OnFragmentInteractionListener {
    DatabaseReference db;
    FirebaseUser user;
    HelperHandiMan helperHandiMan;
    ArrayList<Job> job = new ArrayList<>();
    HandimanData handimanData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        helperHandiMan = new HelperHandiMan(db);
        job = helperHandiMan.retrieveJob(user);
        db.child("HandiMen").child(user.getUid()).child("Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handimanData = dataSnapshot.getValue(HandimanData.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        Log.d("size2",Integer.toString(job.size()));
        HandiHomeFragment handiHomeFragment = HandiHomeFragment.newInstance("hello", "it's me",job,handimanData);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_handi_home,
                handiHomeFragment,
                handiHomeFragment.getTag()
        ).commit();
    }

    @Override
    public void onBackPressed() {
        Log.d("size2",Integer.toString(job.size()));
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
            Log.d("size2",Integer.toString(job.size()));
            HandiHomeFragment handiHomeFragment = HandiHomeFragment.newInstance("somebody", "once told me",job,handimanData);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.content_handi_home,
                    handiHomeFragment,
                    handiHomeFragment.getTag()
            ).commit();
        }
        else if (id == R.id.activity_handi_home_drawer_item_profile) {
            HandiViewProfileFragment handiViewProfileFragment = HandiViewProfileFragment.newInstance("lol",handimanData);
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
