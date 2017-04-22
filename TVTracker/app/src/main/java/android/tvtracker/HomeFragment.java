package android.tvtracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.tvtracker.home.SeriesCardItem;
import android.tvtracker.home.SeriesCardAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private MainActivity mActivity;
    private SeriesCardAdapter mAdapter;
    private List<SeriesCardItem> mItems;
    private OnHomeFragmentInteractionListener mListener;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mActivity = (MainActivity) getActivity();
        mItems = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            mItems.add(new SeriesCardItem(i,
                    "The Office, S09E24",
                    "EpisodeItem description. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    "http://az616578.vo.msecnd.net/files/2016/03/26/635946127402869064800809856_yo.jpg"));
        }

        mAdapter = new SeriesCardAdapter(mActivity, mItems);
        mActivity.setTitle(R.string.fragment_home);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            mListener = (OnHomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnHomeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(SeriesCardItem item);
    }
}
