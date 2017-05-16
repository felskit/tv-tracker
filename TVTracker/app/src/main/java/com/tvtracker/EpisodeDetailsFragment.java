package com.tvtracker;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tvtracker.controllers.EpisodesGetController;
import com.tvtracker.interfaces.IEpisodesGetFragment;
import com.tvtracker.models.Episode;
import com.tvtracker.tools.DateConverter;
import com.tvtracker.tools.ImageDownloader;

import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodeDetailsFragment extends Fragment implements IEpisodesGetFragment {

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
    private EpisodesGetController controller;

    public EpisodeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new EpisodesGetController(this);
        controller.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        controller.getEpisode(getArguments().getInt("episodeId"));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_episode_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.fragment_episode_details);

        mEpisodeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEpisodeImage != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EpisodeDetailsFragment.this.getContext());
                    final AlertDialog dialog = builder.create();
                    ImageView imageView = new ImageView(EpisodeDetailsFragment.this.getContext());
                    Bitmap bitmap = ((BitmapDrawable) mEpisodeImage.getDrawable()).getBitmap();
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
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(Episode episode) {
        Date utcDate = DateConverter.ConvertToUTC(episode.airstamp);

        mEpisodeTitle.setText(episode.name);
        mEpisodeSummary.setText(episode.summary != null ? Html.fromHtml(episode.summary) : "");
        mSeriesTitle.setText(episode.showName);
        mSeriesSeason.setText(String.valueOf(episode.season));
        mSeriesEpisode.setText(String.valueOf(episode.episode));
        mEpisodeAirdate.setText(DateConverter.getDate(utcDate));
        mEpisodeAirtime.setText(DateConverter.getTime(utcDate));
        mEpisodeRuntime.setText(String.valueOf(episode.runtime));
        new ImageDownloader(mEpisodeImage).execute(episode.image);
    }
}
