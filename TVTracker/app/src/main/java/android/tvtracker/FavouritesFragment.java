package android.tvtracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.tvtracker.favourites.FavouriteItem;
import android.tvtracker.favourites.FavouriteAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<FavouriteItem> mItems;
    private LinearLayoutManager mLayoutManager;
    private boolean isSuggested = false;

    public FavouritesFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSuggested = getArguments().getBoolean("isSuggested");
        mItems = new ArrayList<>();
        mItems.add(new FavouriteItem(0, "The Office", "https://pbs.twimg.com/profile_images/734141362031984640/2I-QZZkR.jpg"));

        //TODO
        // if (isSuggested)
        //     data = getSuggested();
        // else
        //     data = getFavourites();
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

            final FavouriteAdapter adapter = new FavouriteAdapter(mItems, mListener, isSuggested);
            recyclerView.setAdapter(adapter);
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
                        adapter.notifyItemChanged(idx);
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

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(FavouriteItem item);
    }

    public boolean IsSuggestedFragment() {
        return isSuggested;
    }
}
