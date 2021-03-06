package com.tvtracker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.tvtracker.controllers.FavouritesGetController;
import com.tvtracker.controllers.FavouritesPostController;
import com.tvtracker.adapters.FavouriteAdapter;
import com.tvtracker.interfaces.IFavouritesGetFragment;
import com.tvtracker.interfaces.IFavouritesPostFragment;
import com.tvtracker.models.ListShow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavouritesFragment extends Fragment implements IFavouritesGetFragment, IFavouritesPostFragment {
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<ListShow> mItems;
    private LinearLayoutManager mLayoutManager;
    private boolean isSuggested = false;
    private FavouritesGetController mGetController;
    private FavouritesPostController mPostController;
    private FavouriteAdapter mAdapter;
    private List<Pair<ListShow, Integer>> mDeletedItems;

    @BindView(R.id.favourites_splash) TextView mSplashTextView;

    private Unbinder unbinder;

    public FavouritesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSuggested = getArguments().getBoolean("isSuggested");
        mItems = new ArrayList<>();
        mDeletedItems = new ArrayList<>();

        mGetController = new FavouritesGetController(this, getContext());
        mGetController.start();

        mPostController = new FavouritesPostController(this, getContext());
        mPostController.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites_list, container, false);
        getActivity().setTitle(isSuggested ? R.string.fragment_suggested : R.string.fragment_favourites);
        unbinder = ButterKnife.bind(this, view);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // Set the adapter
        Context context = view.getContext();
        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FavouriteAdapter(mItems, mListener, isSuggested, getContext());
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        if (!isSuggested) {
            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    int idx = viewHolder.getAdapterPosition();
                    ListShow item = mItems.get(idx);
                    mDeletedItems.add(0, new Pair<>(item, idx));
                    mPostController.removeFavourite(item.id);
                    mItems.remove(idx);
                    mAdapter.notifyItemRemoved(idx);
                    mAdapter.notifyItemRangeChanged(idx, mItems.size());
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }

        if (isSuggested)
            mGetController.getSuggested();
        else
            mGetController.getFavourites();

        return view;
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
        mItems.clear();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateList(ListShow[] shows) {
        mItems.clear();
        Collections.addAll(mItems, shows);
        mAdapter.notifyDataSetChanged();
        showSplash();
    }

    @Override
    public void notify(String message, boolean undoAction) {
        Snackbar notification = Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        if (undoAction) {
            notification.setAction(R.string.undo_string, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (Pair<ListShow, Integer> pair : mDeletedItems) {
                        mPostController.addFavourite(pair.first.id);
                        mItems.add(pair.second, pair.first);
                    }
                    mAdapter.notifyDataSetChanged();
                    mDeletedItems.clear();
                }
            });
        }
        notification.show();
        showSplash();
    }

    private void showSplash() {
        if (!isSuggested && mSplashTextView != null) {
            if (mItems.size() == 0) {
                mSplashTextView.setVisibility(View.VISIBLE);
            } else {
                mSplashTextView.setVisibility(View.INVISIBLE);
            }
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ListShow item);
    }

    public boolean IsSuggestedFragment() {
        return isSuggested;
    }
}
