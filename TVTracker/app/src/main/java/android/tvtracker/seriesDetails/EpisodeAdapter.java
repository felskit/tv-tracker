package android.tvtracker.seriesDetails;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.tvtracker.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {
    private final List<EpisodeItem> mValues;

    public EpisodeAdapter(List<EpisodeItem> items) {
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

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mSeenView.setChecked(!holder.mSeenView.isChecked());
            }
        };
        LinearLayout layout = (LinearLayout) holder.itemView.findViewById(R.id.episodeLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mSeenView.setChecked(!holder.mSeenView.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public final View mView;
        public final CheckBox mSeenView;
        public final TextView mTitleView;
        public EpisodeItem mItem;

        private Context mContext;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mSeenView = (CheckBox) view.findViewById(R.id.checkbox);
            mContext = view.getContext();
            view.setOnLongClickListener(this);
        }

        @Override
        public String toString() {
            return mItem.title;
        }

        @Override
        public boolean onLongClick(View v) {
            final TextView episodeDescription = new TextView(mContext);
            episodeDescription.setText("Corporate sends in a consultant after Michael attempts to imitate a Chris Rock routine. Michael ends up staging his own Diversity Day event.");
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle(mTitleView.getText())
                    .setView(episodeDescription)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    })
                    .create();
            episodeDescription.setPadding(50,20,50,0);
            dialog.show();
            return true;
        }
    }
}
