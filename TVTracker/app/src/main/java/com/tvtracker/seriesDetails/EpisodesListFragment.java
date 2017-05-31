package com.tvtracker.seriesDetails;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvtracker.controllers.EpisodesPostController;
import com.tvtracker.interfaces.IEpisodesPostFragment;
import com.tvtracker.interfaces.ISeriesFragment;
import com.tvtracker.models.Show;
import com.tvtracker.models.ShowEpisode;

import java.util.ArrayList;

public class EpisodesListFragment extends Fragment implements ISeriesFragment, IEpisodesPostFragment {
    private int mColumnCount = 1;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ShowEpisode> mItems;
    private OnEpisodeInteractionListener mListener;
    private EpisodeAdapter mAdapter;
    private EpisodesPostController mEpisodesController;

    public EpisodesListFragment() {
        mItems = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.tvtracker.R.layout.fragment_episode_list, container, false);

        // Set the mAdapter
        if (view instanceof RecyclerView) {
            mEpisodesController = new EpisodesPostController(this, getContext());
            mEpisodesController.start();

            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
            mAdapter = new EpisodeAdapter(mItems, mEpisodesController);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEpisodeInteractionListener) {
            mListener = (OnEpisodeInteractionListener) context;
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
    public void update(Show show) {
        mItems.clear();
        for(ShowEpisode episode : show.episodes) {
            mItems.add(episode);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void notify(String message, int position) {
        //Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout), message, Snackbar.LENGTH_LONG).show();
        ShowEpisode episode = mItems.get(position);
        episode.watched = !episode.watched;
    }

    public interface OnEpisodeInteractionListener {
        void onFragmentInteraction(int id);
    }

}
