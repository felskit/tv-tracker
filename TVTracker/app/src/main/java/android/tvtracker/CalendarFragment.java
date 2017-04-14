package android.tvtracker;

import android.content.Context;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CalendarFragment extends Fragment implements WeekView.EventClickListener,
        MonthLoader.MonthChangeListener, WeekView.EventLongPressListener  {
    private OnFragmentInteractionListener mListener;
    private WeekView mWeekView;
    private int id = 0;

    public CalendarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.fragment_calendar);
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) view.findViewById(R.id.weekView);

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        int eventCount = 100;
        Random r = new Random();
        String[] names = new String[] {"The Office", "Friends", "Atlanta", "Narcos", "Halt and Catch Fire",
        "Entourage", "House of Cards", "Jessica Jones", "Brooklyn Nine-Nine", "Ballers", "Weeds",
        "The Flash", "Silicon Valley", "Daredevil", "Arrow", "Breaking Bad", "Better Call Saul", "Shameless",
        "Orange Is the New Black", "Suits", "30 Rock", "Modern Family", "Parks and Recreation", "Futurama",
        "Community", "Scrubs", "South Park", "House"};
        ArrayList<WeekViewEvent> result = new ArrayList<>();
        for(int i = 0; i < eventCount; i++) {
            int day = r.nextInt(29);
            int startHour = r.nextInt(23);
            result.add(new WeekViewEvent(id++, names[r.nextInt(names.length)], newYear,
                    newMonth, day, startHour, 0, newYear, newMonth, day, startHour + 2, 0));
        }
        return result;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
