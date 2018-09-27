package id.web.wfebriadi.submission5;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import id.web.wfebriadi.submission5.database.FavoriteHelper;
import id.web.wfebriadi.submission5.fragment.FavoriteFragment;
import id.web.wfebriadi.submission5.fragment.HomeFragment;
import id.web.wfebriadi.submission5.fragment.NowPlayingFragment;
import id.web.wfebriadi.submission5.fragment.SearchFragment;
import id.web.wfebriadi.submission5.fragment.UpcommingFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int count = 1;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FavoriteHelper favoriteHelper = new FavoriteHelper(MainActivity.this);
        favoriteHelper.open();
        favoriteHelper.close();

        if (savedInstanceState == null) {
            Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, currentFragment)
                    .commit();
        }
        navigationView.setCheckedItem(R.id.nav_now_plaing);
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
    protected void onPause() {
        super.onPause();
        drawerLayout.removeDrawerListener(toggle);
    }
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString(SearchFragment.EXTRAS, query);
                Fragment fragment = new SearchFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .commit();
                getSupportActionBar().setTitle(getResources().getString(R.string.search));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Bundle bundle = new Bundle();
        Fragment fragment = null;

        String title = "";
        if (id == R.id.nav_home) {
            title = getResources().getString(R.string.home);
            fragment = new HomeFragment();
            count = 1;
        } else if (id == R.id.nav_now_plaing) {
            title = getResources().getString(R.string.now_plying);
            fragment = new NowPlayingFragment();
            count = 1;
        } else if (id == R.id.nav_upcomming) {
            title = getResources().getString(R.string.upcomming);
            fragment = new UpcommingFragment();
            bundle.putString(UpcommingFragment.EXTRAS, "Upcoming");
            fragment.setArguments(bundle);
            count = 1;
        } else if (id == R.id.nav_search) {
            title = getResources().getString(R.string.search);
            fragment = new SearchFragment();
            fragment.setArguments(bundle);
        }else if (id == R.id.nav_favorite) {
            title = getResources().getString(R.string.favorite);
            fragment = new FavoriteFragment();
            bundle.putString(UpcommingFragment.EXTRAS, "Favorite");
            fragment.setArguments(bundle);
            count = 1;
        }
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();

        }
        getSupportActionBar().setTitle(title);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
