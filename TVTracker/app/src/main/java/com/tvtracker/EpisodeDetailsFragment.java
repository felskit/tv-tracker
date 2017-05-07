package com.tvtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tvtracker.controllers.EpisodesController;
import com.tvtracker.interfaces.IEpisodeFragment;
import com.tvtracker.models.Episode;
import com.tvtracker.tools.ImageDownloader;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodeDetailsFragment extends Fragment implements IEpisodeFragment {

    @BindView(R.id.episode_image) ImageView mEpisodeImage;
    @BindView(R.id.episode_title) TextView mEpisodeTitle;
    @BindView(R.id.episode_summary) TextView mEpisodeSummary;
    @BindView(R.id.series_title) TextView mSeriesTitle;
    @BindView(R.id.series_season) TextView mSeriesSeason;
    @BindView(R.id.series_episode) TextView mSeriesEpisode;
    @BindView(R.id.episode_airdate) TextView mEpisodeAirdate;
    @BindView(R.id.episode_runtime) TextView mEpisodeRuntime;
    @BindView(R.id.episode_airtime) TextView mEpisodeAirtime;

    private Unbinder unbinder;

    private String episodeName;
    private String seriesTitle;
    private int season;
    private int episodeNumber;
    private String airdate;
    private String airtime;
    private int runtime;
    private String description;

    public EpisodeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_episode_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.fragment_episode_details);
        EpisodesController controller = new EpisodesController(this);
        controller.start();
        controller.getEpisode(1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(Episode episode) {
        mEpisodeTitle.setText(episode.name);
        mEpisodeSummary.setText(Html.fromHtml(episode.summary));
        mSeriesTitle.setText(episode.showName);
        mSeriesSeason.setText(String.valueOf(episode.season));
        mSeriesEpisode.setText(String.valueOf(episode.episode));
        mEpisodeAirdate.setText(episode.airdate);
        mEpisodeAirtime.setText(episode.airtime);
        mEpisodeRuntime.setText(String.valueOf(episode.runtime));
        new ImageDownloader(mEpisodeImage).execute(episode.image);
    }
}
