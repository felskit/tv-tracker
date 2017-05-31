package com.tvtracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvtracker.adapters.HomeAdapter;
import com.tvtracker.controllers.HomeController;
import com.tvtracker.interfaces.IHomeFragment;
import com.tvtracker.models.HomeEpisode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements IHomeFragment {
    private MainActivity mActivity;
    private HomeAdapter mAdapter;
    private List<HomeEpisode> mItems;
    private OnHomeFragmentInteractionListener mListener;
    private HomeController mController;
    private RecyclerView mRecyclerView;
    private Unbinder unbinder;

    @BindView(R.id.home_splash) TextView mSplashTextView;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems = new ArrayList<>();

        mController = new HomeController(this, getContext());
        mController.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mController.getEpisodes();

        mActivity = (MainActivity) getActivity();
        mAdapter = new HomeAdapter(mActivity, mItems);
        mActivity.setTitle(R.string.fragment_home);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.recycle();
        unbinder.unbind();
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
        mItems.clear();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateEpisodes(HomeEpisode[] episodes) {
        mItems.clear();
        for (HomeEpisode episode : episodes) {
            mItems.add(episode);
        }
        mAdapter.notifyDataSetChanged();

        if (mItems.size() == 0) {
            mSplashTextView.setVisibility(View.VISIBLE);
        }
        else {
            mSplashTextView.setVisibility(View.INVISIBLE);
        }
    }

    public interface OnHomeFragmentInteractionListener {
        void onFragmentInteraction(HomeEpisode item);
    }

    public void populate() {
        mController.getEpisodes();
    }
}
