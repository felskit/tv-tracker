package com.tvtracker;

import android.content.Context;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tvtracker.controllers.CalendarController;
import com.tvtracker.interfaces.ICalendarFragment;
import com.tvtracker.models.CalendarEpisode;
import com.tvtracker.seriesDetails.EpisodesListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.tvtracker.tools.DatePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CalendarFragment extends Fragment implements WeekView.EventClickListener,
        MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, ICalendarFragment {
    private EpisodesListFragment.OnEpisodeInteractionListener mListener;
    @BindView(R.id.weekView) WeekView mWeekView;
    private int id = 0;
    private Unbinder unbinder;
    private CalendarController controller;
    private Map<Integer, Map<Integer, List<WeekViewEvent>>> dictionary = new HashMap<>();

    public CalendarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new CalendarController(this);
        controller.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.fragment_calendar);
        View view =  inflater.inflate(R.layout.fragment_calendar, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EpisodesListFragment.OnEpisodeInteractionListener) {
            mListener = (EpisodesListFragment.OnEpisodeInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEpisodeInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<>();
        if(dictionary.containsKey(newYear) && dictionary.get(newYear).containsKey(newMonth)) {
            events = dictionary.get(newYear).get(newMonth);
        }
        else {
            controller.getEpisodes(newMonth, newYear);
        }
        return events;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        mListener.onFragmentInteraction((int)event.getId());
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void addEpisodes(CalendarEpisode[] episodes) {
        if(episodes.length > 0) {
            int year = episodes[0].beginYear;
            int month = episodes[0].beginMonth;
            if(!dictionary.containsKey(year)) {
                dictionary.put(year, new HashMap<Integer, List<WeekViewEvent>>());
            }
            if(!dictionary.get(year).containsKey(month)) {
                dictionary.get(year).put(month, new ArrayList<WeekViewEvent>());
            }
            List<WeekViewEvent> list = dictionary.get(year).get(month);

            if(list.size() == 0) {
                for (CalendarEpisode episode : episodes) {
                    WeekViewEvent event = new WeekViewEvent(episode.episodeId, episode.showName, episode.beginYear,
                            episode.beginMonth, episode.beginDay, episode.beginHour, episode.beginMinute,
                            episode.endYear, episode.endMonth, episode.endDay, episode.endHour, episode.endMinute);
                    list.add(event);
                }
                mWeekView.notifyDatasetChanged();
            }
        }
    }
}
