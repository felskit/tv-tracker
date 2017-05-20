package com.tvtracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.tvtracker.controllers.FavouritesPostController;
import com.tvtracker.controllers.SeriesController;
import com.tvtracker.interfaces.IPostFragment;
import com.tvtracker.interfaces.ISeriesFragment;
import com.tvtracker.models.Show;
import com.tvtracker.seriesDetails.EpisodesListFragment;
import com.tvtracker.seriesDetails.SeriesDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SeriesFragment extends Fragment implements ISeriesFragment, IPostFragment {
    @BindView(R.id.series_image) ImageView mSeriesImage;
    @BindView(R.id.collapsing) CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.series_title) TextView seriesTitle;

    private SeriesDetailsFragment mDetailsFragment;
    private EpisodesListFragment mListFragment;
    private TabsAdapter mAdapter;

    private Unbinder mUnbinder;

    private int mSeriesId;
    private SeriesController mSeriesController;
    private FavouritesPostController mFavouritesController;

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailsFragment = new SeriesDetailsFragment();
        mListFragment = new EpisodesListFragment();
        mFavouritesController = new FavouritesPostController(this, getContext());
        mFavouritesController.start();
        mSeriesController = new SeriesController(this, getContext());
        mSeriesController.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_series, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        mSeriesId = arguments.getInt("seriesId");
        mSeriesController.getSeries(mSeriesId);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.fragment_series);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.materialup_tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.materialup_viewpager);
        mAdapter = new TabsAdapter(getChildFragmentManager(), getActivity(), mDetailsFragment, mListFragment);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

        mSeriesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSeriesImage != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SeriesFragment.this.getContext());
                    final AlertDialog dialog = builder.create();
                    ImageView imageView = new ImageView(SeriesFragment.this.getContext());
                    BitmapDrawable drawable = ((BitmapDrawable) mSeriesImage.getDrawable());
                    if(drawable != null) {
                        Bitmap bitmap = drawable.getBitmap();
                        imageView.setImageBitmap(bitmap);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.hide();
                            }
                        });

                        dialog.setView(imageView);
                        dialog.show();
                    }
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.series_subscribe);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFavouritesController.addFavourite(mSeriesId);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BitmapDrawable drawable = (BitmapDrawable)mSeriesImage.getDrawable();
        if(drawable != null) {
            drawable.getBitmap().recycle();
        }
        mUnbinder.unbind();
    }

    @Override
    public void update(Show show) {
        Picasso.with(getContext()).load(show.image).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(mSeriesImage);
        seriesTitle.setText(show.name);
        mDetailsFragment.update(show);
        mListFragment.update(show);
    }

    @Override
    public void notify(String message) {
        Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout), message, Snackbar.LENGTH_LONG).show();
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
