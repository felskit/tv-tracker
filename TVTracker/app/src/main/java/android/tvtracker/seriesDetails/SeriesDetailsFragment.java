package android.tvtracker.seriesDetails;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.tvtracker.R;
import android.widget.TextView;

public class SeriesDetailsFragment extends Fragment {

    private TextView mSeriesDescription;
    private TextView mSeriesTitle;
    private TextView mSeriesPremiered;
    private TextView mSeriesGenres;
    private TextView mSeriesStatus;
    private TextView mSeriesNetwork;
    private TextView mSeriesRuntime;
    private TextView mSeriesRating;

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
        return inflater.inflate(R.layout.fragment_series_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSeriesDescription = (TextView) view.findViewById(R.id.series_details);
        mSeriesTitle = (TextView) view.findViewById(R.id.series_title);
        mSeriesPremiered = (TextView) view.findViewById(R.id.series_premiere);
        mSeriesGenres   = (TextView) view.findViewById(R.id.series_genres);
        mSeriesStatus = (TextView) view.findViewById(R.id.series_status);
        mSeriesNetwork = (TextView) view.findViewById(R.id.series_network);
        mSeriesRuntime = (TextView) view.findViewById(R.id.series_runtime);
        mSeriesRating = (TextView) view.findViewById(R.id.series_rating);

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
}
