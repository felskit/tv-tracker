package android.tvtracker;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.tvtracker.favourites.FavouriteItem;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener,
        CalendarFragment.OnFragmentInteractionListener, FragmentManager.OnBackStackChangedListener,
        FavouritesFragment.OnListFragmentInteractionListener {

    private FragmentManager mFragmentManager;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.content_main, new HomeFragment()).commit();
        mActionBar = getSupportActionBar();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            mFragmentManager.beginTransaction().replace(R.id.content_main, new SettingsFragment()).addToBackStack(null).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                mFragmentManager.beginTransaction().replace(R.id.content_main, new HomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_calendar:
                mFragmentManager.beginTransaction().replace(R.id.content_main, new CalendarFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_fav:
                mFragmentManager.beginTransaction().replace(R.id.content_main, new FavouritesFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_search:
                mFragmentManager.beginTransaction().replace(R.id.content_main, new SearchFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_preferences:
                mFragmentManager.beginTransaction().replace(R.id.content_main, new UserPreferenceFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_logout:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        mFragmentManager.popBackStack();
        return true;
    }

    @Override
    public void onListFragmentInteraction(FavouriteItem item) {
        mFragmentManager.beginTransaction().replace(R.id.content_main, new SeriesFragment()).addToBackStack(null).commit();
    }

    public void setActionBarTitle(String title) {
        mActionBar.setTitle(title);
    }
}
