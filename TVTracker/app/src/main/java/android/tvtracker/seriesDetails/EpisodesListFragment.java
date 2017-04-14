package android.tvtracker.seriesDetails;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.tvtracker.R;

import java.util.ArrayList;

public class EpisodesListFragment extends Fragment {
    private int mColumnCount = 1;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<EpisodeItem> items;

    public EpisodesListFragment() {
        items = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            items.add(new EpisodeItem(i, "1x" + i + " EpisodeItem " + i, i % 2 == 0));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_episode_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
            recyclerView.setAdapter(new EpisodeAdapter(items));
        }
        return view;
    }

}
