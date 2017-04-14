package android.tvtracker.seriesDetails;

import android.support.v7.widget.RecyclerView;
import android.tvtracker.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class EpisodeRecyclerViewAdapter extends RecyclerView.Adapter<EpisodeRecyclerViewAdapter.ViewHolder> {

    private final List<Episode> mValues;

    public EpisodeRecyclerViewAdapter(List<Episode> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_episode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).title);
        holder.mSeenView.setChecked(mValues.get(position).seen);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox mSeenView;
        public final TextView mTitleView;
        public Episode mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mSeenView = (CheckBox) view.findViewById(R.id.checkbox);
        }

        @Override
        public String toString() {
            return mItem.title;
        }
    }
}
