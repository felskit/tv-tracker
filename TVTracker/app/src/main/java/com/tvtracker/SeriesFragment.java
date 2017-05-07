package com.tvtracker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tvtracker.controllers.SeriesController;
import com.tvtracker.interfaces.ISeriesFragment;
import com.tvtracker.models.Show;
import com.tvtracker.seriesDetails.EpisodesListFragment;
import com.tvtracker.seriesDetails.SeriesDetailsFragment;
import com.tvtracker.tools.ImageDownloader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SeriesFragment extends Fragment implements ISeriesFragment {
    @BindView(R.id.series_image) ImageView mSeriesImage;
    @BindView(R.id.collapsing) CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.series_title) TextView seriesTitle;

    private SeriesDetailsFragment detailsFragment;
    private EpisodesListFragment listFragment;
    private TabsAdapter adapter;

    private Unbinder unbinder;

    private int seriesId;
    private SeriesController controller;

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsFragment = new SeriesDetailsFragment();
        listFragment = new EpisodesListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_series, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        seriesId = arguments.getInt("seriesId");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.fragment_series);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.materialup_tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.materialup_viewpager);
        adapter = new TabsAdapter(getChildFragmentManager(), getActivity(), detailsFragment, listFragment);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        controller = new SeriesController(this);
        controller.start();
        controller.getSeries(seriesId);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.series_subscribe);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Series added to favourites", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void update(Show show) {
        new ImageDownloader(mSeriesImage).execute(show.image);
        seriesTitle.setText(show.name);
        detailsFragment.update(show);
        listFragment.update(show);
    }

    private static class TabsAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 2;
        private Context context;
        private SeriesDetailsFragment detailsFragment;
        private EpisodesListFragment listFragment;

        TabsAdapter(FragmentManager fm, Context context, SeriesDetailsFragment detailsFragment,
                    EpisodesListFragment listFragment) {
            super(fm);
            this.context = context;
            this.detailsFragment = detailsFragment;
            this.listFragment = listFragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return detailsFragment;
                case 1:
                    return listFragment;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return context.getString(R.string.fragment_series_details_description);
                case 1:
                    return context.getString(R.string.fragment_series_details_episodes);
                default:
                    return null;
            }
        }
    }
}
