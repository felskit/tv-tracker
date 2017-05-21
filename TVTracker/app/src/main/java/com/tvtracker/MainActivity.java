package com.tvtracker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tvtracker.models.HomeEpisode;
import com.tvtracker.models.ListShow;
import com.tvtracker.seriesDetails.EpisodesListFragment;
import com.tvtracker.tools.ImageDownloader;
import com.tvtracker.tools.NetworkStateReceiver;
import com.tvtracker.tools.TVTrackerFirebaseInstanceIDService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnHomeFragmentInteractionListener,
        EpisodesListFragment.OnEpisodeInteractionListener,
        FavouritesFragment.OnListFragmentInteractionListener, SearchFragment.OnSearchFragmentInteractionListener {

    private FragmentManager mFragmentManager;
    private ActionBar mActionBar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    private Boolean isLoggedIn = false;

    private ImageView mImageView;
    private TextView mUsername;
    private TextView mEmail;

    private int LOGIN_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseInstanceIdService service = new TVTrackerFirebaseInstanceIDService();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        ImageDownloader.init((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE),
                PreferenceManager.getDefaultSharedPreferences(this));

        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        if (!networkStateReceiver.isConnected()) {
            Toast.makeText(this, getString(R.string.network_dialog_message), Toast.LENGTH_SHORT).show();
            finish();
        }

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
            updateSelected(current);
        }
    }

    private void updateSelected(Fragment fragment) {
        if (fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).populate();
            mNavigationView.setCheckedItem(R.id.nav_home);
        } else if (fragment instanceof CalendarFragment) {
            mNavigationView.setCheckedItem(R.id.nav_calendar);
        } else if (fragment instanceof FavouritesFragment) {
            if (((FavouritesFragment) fragment).IsSuggestedFragment()) {
                mNavigationView.setCheckedItem(R.id.nav_suggested);
            } else {
                mNavigationView.setCheckedItem(R.id.nav_fav);
            }
        } else if (fragment instanceof SearchFragment) {
            mNavigationView.setCheckedItem(R.id.nav_search);
        } else if (fragment instanceof AboutFragment) {
            mNavigationView.setCheckedItem(R.id.nav_about);
        } else if (fragment instanceof PreferenceFragment) {
            mNavigationView.setCheckedItem(R.id.nav_preferences);
        } else {
            mNavigationView.setCheckedItem(R.id.nav_empty);
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
            case R.id.nav_about:
                mFragmentManager.beginTransaction().replace(R.id.content_main, new AboutFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_logout:
                //isLoggedIn = false;
                //ControllerConfig.userId = -1;
                //mImageView.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                //mUsername.setText("");
                //mEmail.setText("");

                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(HomeEpisode item) {
        //TODO change 1 to item.getId when it will be valid id
        onSearchFragmentInteraction(item.showId);
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
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                isLoggedIn = true;
                TVTrackerFirebaseInstanceIDService.updateToken(this);

                mImageView = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.profilePicture);
                mUsername = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.username);
                mEmail = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.email);

                String url = data.getStringExtra("imageUrl");
                if (url != null) {
                    ImageDownloader.execute(this, url, false, mImageView);
                }
                mUsername.setText(data.getStringExtra("userName"));
                mEmail.setText(data.getStringExtra("email"));

                Fragment current = mFragmentManager.findFragmentById(R.id.content_main);
                updateSelected(current);

                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String episodeId = extras.getString("episodeId", null);
                    if (episodeId != null) {
                        onFragmentInteraction(Integer.parseInt(episodeId));
                        extras.remove("episodeId");
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
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
