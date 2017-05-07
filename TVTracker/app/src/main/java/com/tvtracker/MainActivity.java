package com.tvtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tvtracker.controllers.ControllerConfig;
import com.tvtracker.favourites.FavouriteItem;
import com.tvtracker.home.SeriesCardItem;
import com.tvtracker.models.ListShow;
import com.tvtracker.seriesDetails.EpisodesListFragment;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnHomeFragmentInteractionListener,
        EpisodesListFragment.OnEpisodeInteractionListener, FragmentManager.OnBackStackChangedListener,
        FavouritesFragment.OnListFragmentInteractionListener, SearchFragment.OnSearchFragmentInteractionListener {

    private FragmentManager mFragmentManager;
    private ActionBar mActionBar;
    @BindView(R.id.nav_view) NavigationView mNavigationView;
    private Boolean isLoggedIn = false;

    private ProfilePictureView mPictureView;
    private TextView mUsername;
    private TextView mEmail;
    private String mFbUserId;
  //private String mGoogleUserId;
    private int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

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
            Fragment current = mFragmentManager.findFragmentById(R.id.content_main);
            if (current instanceof HomeFragment) {
                mNavigationView.setCheckedItem(R.id.nav_home);
            } else if (current instanceof CalendarFragment) {
                mNavigationView.setCheckedItem(R.id.nav_calendar);
            } else if (current instanceof FavouritesFragment) {
                if (((FavouritesFragment) current).IsSuggestedFragment()) {
                    mNavigationView.setCheckedItem(R.id.nav_suggested);
                } else {
                    mNavigationView.setCheckedItem(R.id.nav_fav);
                }
            } else if (current instanceof SearchFragment) {
                mNavigationView.setCheckedItem(R.id.nav_search);
            } else if (current instanceof PreferenceFragment) {
                mNavigationView.setCheckedItem(R.id.nav_preferences);
            } else {
                mNavigationView.setCheckedItem(R.id.nav_empty);
            }
        }
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
                FavouritesFragment favFragment = new FavouritesFragment();
                Bundle favBundle = new Bundle();
                favBundle.putBoolean("isSuggested", false);
                favFragment.setArguments(favBundle);
                mFragmentManager.beginTransaction().replace(R.id.content_main, favFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_search:
                mFragmentManager.beginTransaction().replace(R.id.content_main, new SearchFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_preferences:
                mFragmentManager.beginTransaction().replace(R.id.content_main, new PreferenceFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_suggested:
                FavouritesFragment suggestedFragment = new FavouritesFragment();
                Bundle suggestedBundle = new Bundle();
                suggestedBundle.putBoolean("isSuggested", true);
                suggestedFragment.setArguments(suggestedBundle);
                mFragmentManager.beginTransaction().replace(R.id.content_main, suggestedFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_logout:
                isLoggedIn = false;
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(SeriesCardItem item) {
        //TODO change 1 to item.getId when it will be valid id
        onSearchFragmentInteraction(1);
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
    public void onListFragmentInteraction(ListShow item) {
        onSearchFragmentInteraction(item.id);
    }

    public void setActionBarTitle(String title) {
        mActionBar.setTitle(title);
    }

    @Override
    public void onFragmentInteraction(int id) {
        mNavigationView.setCheckedItem(R.id.nav_empty);
        Bundle bundle = new Bundle();
        bundle.putInt("episodeId", id);
        EpisodeDetailsFragment fragment = new EpisodeDetailsFragment();
        fragment.setArguments(bundle);
        mFragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                isLoggedIn = true;

                mUsername = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.username);
                mEmail = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.email);
                mFbUserId = data.getStringExtra("fbUserId");
                mUserId = data.getIntExtra("userId",0);
                ControllerConfig.userId = mUserId;

                mPictureView = (ProfilePictureView) mNavigationView.getHeaderView(0).findViewById(R.id.profilePicture);
                mPictureView.setProfileId(mFbUserId);

                mUsername.setText(data.getStringExtra("userName"));
                mEmail.setText(data.getStringExtra("email"));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onSearchFragmentInteraction(int seriesId) {
        mNavigationView.setCheckedItem(R.id.nav_empty);
        Bundle bundle = new Bundle();
        bundle.putInt("seriesId", seriesId);
        SeriesFragment fragment = new SeriesFragment();
        fragment.setArguments(bundle);
        mFragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
    }
}
