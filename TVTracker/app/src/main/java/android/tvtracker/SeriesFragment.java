package android.tvtracker;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.tvtracker.seriesDetails.EpisodesListFragment;
import android.tvtracker.seriesDetails.SeriesDetailsFragment;
import android.tvtracker.tools.ImageDownloader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SeriesFragment extends Fragment {
    private TextView mSeriesTitle;
    private TextView mSeriesDescription;
    private ImageView mSeriesImage;
    private CollapsingToolbarLayout mToolbarLayout;

    public SeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //mSeriesTitle = (TextView) view.findViewById(R.id.series_title);
        //mSeriesDescription = (TextView) view.findViewById(R.id.series_details);
        mSeriesImage = (ImageView) view.findViewById(R.id.series_image);
        mToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing);

        //mSeriesTitle.setText("The Office");
        mToolbarLayout.setTitle("The Office");

//        mSeriesDescription.setText("The Office is an American television comedy series that aired on" +
//                " NBC from March 24, 2005 to May 16, 2013.[1] It is an adaptation of the BBC series " +
//                "of the same name. The Office was adapted for American audiences by Greg Daniels, a" +
//                " veteran writer for Saturday Night Live, King of the Hill, and The Simpsons. It is" +
//                " co-produced by Daniels' Deedle-Dee Productions, and Reveille Productions (later Shine America), " +
//                "in association with Universal Television. The original executive producers were Greg Daniels," +
//                " Howard Klein, Ben Silverman, Ricky Gervais, and Stephen Merchant, with numerous others" +
//                " being promoted in later seasons.\n" +
//                "\n" +
//                "The series depicts the everyday lives of office employees in the Scranton, Pennsylvania," +
//                " branch of the fictional Dunder Mifflin Paper Company. To simulate the look of an actual documentary," +
//                " it is filmed in a single-camera setup, without a studio audience or a laugh track. " +
//                "The show debuted on NBC as a mid-season replacement and ran for nine seasons and 201 episodes." +
//                " The Office initially featured Steve Carell, Rainn Wilson, John Krasinski, Jenna Fischer " +
//                "and B. J. Novak as the main cast; the show experienced numerous changes to its ensemble " +
//                "cast during its run.\n" +
//                "\n" +
//                "The first season of The Office was met with mixed reviews, but the following four seasons " +
//                "received widespread acclaim from television critics. These seasons were included on several" +
//                " critics' year-end top TV series lists, winning several awards including four Primetime " +
//                "Emmy Awards, including Outstanding Comedy Series in 2006. While later seasons were criticized" +
//                " for a decline in quality, earlier writers oversaw the final season and ended the " +
//                "show's run with a positive reception.");

        mSeriesImage.setScaleType(ImageView.ScaleType.CENTER);
        new ImageDownloader(mSeriesImage).execute("https://lh4.googleusercontent.com/-g4OI1bwTNIA/AAAAAAAAAAI/AAAAAAAABt8/aSmWUXBm2bk/s0-c-k-no-ns/photo.jpg");

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.materialup_tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.materialup_viewpager);
        viewPager.setAdapter(new TabsAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
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
            //return CalendarFragment.newInstance("a", "a");
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
