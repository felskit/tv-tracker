package com.tvtracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tvtracker.controllers.HomeController;
import com.tvtracker.adapters.HomeAdapter;
import com.tvtracker.interfaces.IHomeFragment;
import com.tvtracker.models.HomeEpisode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements IHomeFragment {
    private MainActivity mActivity;
    private HomeAdapter mAdapter;
    private List<HomeEpisode> mItems;
    private OnHomeFragmentInteractionListener mListener;
    private HomeController mController;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems = new ArrayList<>();

        // TODO
        mController = new HomeController(this);
        mController.start();
        mController.getEpisodes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mActivity = (MainActivity) getActivity();
        mAdapter = new HomeAdapter(mActivity, mItems);
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

    @Override
    public void updateEpisodes(HomeEpisode[] episodes) {
        mItems.clear();
        for (HomeEpisode episode : episodes) {
            mItems.add(episode);
        }
        mAdapter.notifyDataSetChanged();
    }

    public interface OnHomeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(HomeEpisode item);
    }

    public void populate() {
        mController.getEpisodes();
    }
}
