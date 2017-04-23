package android.tvtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.tvtracker.tools.ImageDownloader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodeDetailsFragment extends Fragment {

    private ImageView mEpisodeImage;
    private TextView mEpisodeTitle;
    private TextView mEpisodeSummary;
    private TextView mSeriesTitle;
    private TextView mSeriesSeason;
    private TextView mSeriesEpisode;
    private TextView mEpisodeAirdate;
    private TextView mEpisodeRuntime;
    private TextView mEpisodeAirtime;

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
        return inflater.inflate(R.layout.fragment_episode_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mEpisodeImage = (ImageView) view.findViewById(R.id.episode_image);
        mEpisodeTitle = (TextView) view.findViewById(R.id.episode_title);
        mEpisodeSummary = (TextView) view.findViewById(R.id.episode_summary);
        mSeriesTitle = (TextView) view.findViewById(R.id.series_title);
        mSeriesSeason = (TextView) view.findViewById(R.id.series_season);
        mSeriesEpisode = (TextView) view.findViewById(R.id.series_episode);
        mEpisodeAirdate = (TextView) view.findViewById(R.id.episode_airdate);
        mEpisodeAirtime = (TextView) view.findViewById(R.id.episode_airtime);
        mEpisodeRuntime = (TextView) view.findViewById(R.id.episode_runtime);

        getActivity().setTitle(R.string.fragment_episode_details);

        new ImageDownloader(mEpisodeImage).execute("http://static.tvmaze.com/uploads/images/original_untouched/49/124795.jpg");

        episodeName = "Pilot";
        seriesTitle = "The Office";
        season = 1;
        episodeNumber = 1;
        runtime = 30;
        airdate = "2005-03-29";
        airtime = "21:30";
        description = "A documentary crew arrives at Dundler Mifflin to observe the workplace. Michael Scott tries to paint a happy picture while facing potential downsizing.";

        mEpisodeTitle.setText(episodeName);
        mEpisodeSummary.setText(description);
        mSeriesTitle.setText(seriesTitle);
        mSeriesSeason.setText(String.valueOf(season));
        mSeriesEpisode.setText(String.valueOf(episodeNumber));
        mEpisodeAirdate.setText(airdate);
        mEpisodeAirtime.setText(airtime);
        mEpisodeRuntime.setText(String.valueOf(runtime));
    }

}
