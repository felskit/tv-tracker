package android.tvtracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SeriesCardAdapter extends RecyclerView.Adapter<SeriesCardAdapter.ViewHolder> {
    private Context mContext;
    private List<SeriesCard> mItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private CheckBox mCheckBox;
//        private TextView mTextView;
        private SeriesCard mItem;

        public ViewHolder(View itemView) {
            super(itemView);
//            this.mCheckBox = (CheckBox) itemView.findViewById(R.id.item_checked);
//            this.mTextView = (TextView) itemView.findViewById(R.id.item_name);
        }

        public SeriesCard getItem() {
            return mItem;
        }

        public void setItem(SeriesCard item) {
//            this.mCheckBox.setChecked(item.isChecked());
//            this.mTextView.setText(item.getName());
            this.mItem = item;
        }
    }

    public SeriesCardAdapter(Context context, List<SeriesCard> items) {
        this.mContext = context;
        this.mItems = items;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public SeriesCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.series_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SeriesCardAdapter.ViewHolder viewHolder, final int position) {
        final SeriesCard item = mItems.get(position);
        viewHolder.setItem(item);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on click action
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // on long click action
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
