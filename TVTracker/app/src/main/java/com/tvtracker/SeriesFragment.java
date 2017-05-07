package com.tvtracker;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.tvtracker.seriesDetails.EpisodesListFragment;
import com.tvtracker.seriesDetails.SeriesDetailsFragment;
import com.tvtracker.tools.ImageDownloader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SeriesFragment extends Fragment {
    @BindView(R.id.series_image) ImageView mSeriesImage;
    @BindView(R.id.collapsing) CollapsingToolbarLayout mToolbarLayout;

    private Unbinder unbinder;

    public SeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_series, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.fragment_series);

        mToolbarLayout.setTitle("The Office");

        mSeriesImage.setScaleType(ImageView.ScaleType.CENTER);
        new ImageDownloader(mSeriesImage).execute("https://lh4.googleusercontent.com/-g4OI1bwTNIA/AAAAAAAAAAI/AAAAAAAABt8/aSmWUXBm2bk/s0-c-k-no-ns/photo.jpg");

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.materialup_tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.materialup_viewpager);
        viewPager.setAdapter(new TabsAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

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

    private static class TabsAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 2;

        TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new SeriesDetailsFragment();
                case 1:
                    return new EpisodesListFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Description";
                case 1:
                    return "Episodes";
                default:
                    return null;
            }
        }
    }
}
