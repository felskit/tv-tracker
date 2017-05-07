package com.tvtracker.seriesDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SeriesDetailsFragment extends Fragment {

    @BindView(com.tvtracker.R.id.series_details) TextView mSeriesDescription;
    @BindView(com.tvtracker.R.id.series_title) TextView mSeriesTitle;
    @BindView(com.tvtracker.R.id.series_premiere) TextView mSeriesPremiered;
    @BindView(com.tvtracker.R.id.series_genres) TextView mSeriesGenres;
    @BindView(com.tvtracker.R.id.series_status) TextView mSeriesStatus;
    @BindView(com.tvtracker.R.id.series_network) TextView mSeriesNetwork;
    @BindView(com.tvtracker.R.id.series_runtime) TextView mSeriesRuntime;
    @BindView(com.tvtracker.R.id.series_rating) TextView mSeriesRating;

    private Unbinder unbinder;

    private String seriesTitle;
    private String premiere;
    private String genres;
    private String status;
    private String network;
    private int runtime;
    private double rating;


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
        seriesTitle = "The Office";
        premiere = "2005-03-24";
        genres = "Comedy";
        status = "Ended";
        network = "NBC";
        runtime = 30;
        rating = 8.8;

        mSeriesDescription.setText(Html.fromHtml(new StringBuilder().append("<p>Steve Carell stars in <strong>The Office</strong>, a fresh and funny mockumentary-style ")
                .append("glimpse into the daily interactions of the eccentric workers at the Dunder Mifflin paper supply company. ")
                .append("Based on the smash-hit British series of the same name and adapted for American Television by Greg Daniels, ")
                .append("this fast-paced comedy parodies contemporary American water-cooler culture. Earnest but clueless regional manager")
                .append(" Michael Scott believes himself to be an exceptional boss and mentor, but actually receives more eye-rolls ")
                .append("than respect from his oddball staff.</p>").toString()));
        mSeriesTitle.setText(seriesTitle);
        mSeriesPremiered.setText(premiere);
        mSeriesGenres.setText(genres);
        mSeriesStatus.setText(status);
        mSeriesNetwork.setText(network);
        mSeriesRuntime.setText(String.valueOf(runtime));
        mSeriesRating.setText(String.valueOf(rating));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
