package com.tvtracker.seriesDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.tvtracker.interfaces.ISeriesFragment;
import com.tvtracker.models.Show;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SeriesDetailsFragment extends Fragment implements ISeriesFragment {

    @BindView(com.tvtracker.R.id.series_details) TextView mSeriesDescription;
    @BindView(com.tvtracker.R.id.series_title) TextView mSeriesTitle;
    @BindView(com.tvtracker.R.id.series_premiere) TextView mSeriesPremiered;
    @BindView(com.tvtracker.R.id.series_genres) TextView mSeriesGenres;
    @BindView(com.tvtracker.R.id.series_status) TextView mSeriesStatus;
    @BindView(com.tvtracker.R.id.series_network) TextView mSeriesNetwork;
    @BindView(com.tvtracker.R.id.series_runtime) TextView mSeriesRuntime;
    @BindView(com.tvtracker.R.id.series_rating) TextView mSeriesRating;

    private Unbinder unbinder;

    public SeriesDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.tvtracker.R.layout.fragment_series_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void update(Show show) {
        mSeriesTitle.setText(show.name);
        mSeriesPremiered.setText(show.premiered);
        mSeriesGenres.setText(show.genres);
        mSeriesStatus.setText(show.status);
        mSeriesNetwork.setText(show.network);
        mSeriesRuntime.setText(String.valueOf(show.runtime));
        mSeriesRating.setText(String.valueOf(show.rating));
        mSeriesDescription.setText(show.summary != null ? Html.fromHtml(show.summary) : "");
    }
}
