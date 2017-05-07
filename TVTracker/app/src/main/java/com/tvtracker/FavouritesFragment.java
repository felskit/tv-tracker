package com.tvtracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.tvtracker.controllers.FavouritesController;
import com.tvtracker.favourites.FavouriteItem;
import com.tvtracker.favourites.FavouriteAdapter;
import com.tvtracker.interfaces.IFavouritesFragment;
import com.tvtracker.models.ListShow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment implements IFavouritesFragment {
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<ListShow> mItems;
    private LinearLayoutManager mLayoutManager;
    private boolean isSuggested = false;
    private FavouritesController controller;
    private FavouriteAdapter mAdapter;

    public FavouritesFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSuggested = getArguments().getBoolean("isSuggested");
        mItems = new ArrayList<>();

        //TODO
        controller = new FavouritesController(this);
        controller.start();
         if (isSuggested)
             controller.getSuggested();
         else
             controller.getFavourites();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites_list, container, false);
        getActivity().setTitle(isSuggested ? R.string.fragment_suggested : R.string.fragment_favourites);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new FavouriteAdapter(mItems, mListener, isSuggested);
            recyclerView.setAdapter(mAdapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);

            if(!isSuggested) {
                ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int idx = viewHolder.getAdapterPosition();
                        mItems.remove(idx);
                        mAdapter.notifyItemChanged(idx);
                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                itemTouchHelper.attachToRecyclerView(recyclerView);
            }
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FavouritesFragment.OnListFragmentInteractionListener) {
            mListener = (FavouritesFragment.OnListFragmentInteractionListener) context;
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
    public void updateList(ListShow[] shows) {
        for(ListShow show : shows) {
            mItems.add(show);
        }
        mAdapter.notifyDataSetChanged();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ListShow item);
    }

    public boolean IsSuggestedFragment() {
        return isSuggested;
    }
}
