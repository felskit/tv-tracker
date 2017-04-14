package android.tvtracker.seriesDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.tvtracker.R;
import android.widget.TextView;

public class SeriesDetailsFragment extends Fragment {

    private TextView mSeriesDescription;

    public SeriesDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_series_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSeriesDescription = (TextView) view.findViewById(R.id.series_details);
        mSeriesDescription.setText("The Office is an American television comedy series that aired on" +
                " NBC from March 24, 2005 to May 16, 2013.[1] It is an adaptation of the BBC series " +
                "of the same name. The Office was adapted for American audiences by Greg Daniels, a" +
                " veteran writer for Saturday Night Live, King of the Hill, and The Simpsons. It is" +
                " co-produced by Daniels' Deedle-Dee Productions, and Reveille Productions (later Shine America), " +
                "in association with Universal Television. The original executive producers were Greg Daniels," +
                " Howard Klein, Ben Silverman, Ricky Gervais, and Stephen Merchant, with numerous others" +
                " being promoted in later seasons.\n" +
                "\n" +
                "The series depicts the everyday lives of office employees in the Scranton, Pennsylvania," +
                " branch of the fictional Dunder Mifflin Paper Company. To simulate the look of an actual documentary," +
                " it is filmed in a single-camera setup, without a studio audience or a laugh track. " +
                "The show debuted on NBC as a mid-season replacement and ran for nine seasons and 201 episodes." +
                " The Office initially featured Steve Carell, Rainn Wilson, John Krasinski, Jenna Fischer " +
                "and B. J. Novak as the main cast; the show experienced numerous changes to its ensemble " +
                "cast during its run.\n" +
                "\n" +
                "The first season of The Office was met with mixed reviews, but the following four seasons " +
                "received widespread acclaim from television critics. These seasons were included on several" +
                " critics' year-end top TV series lists, winning several awards including four Primetime " +
                "Emmy Awards, including Outstanding Comedy Series in 2006. While later seasons were criticized" +
                " for a decline in quality, earlier writers oversaw the final season and ended the " +
                "show's run with a positive reception.");
    }

}
