package com.codepath.simpletodo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.simpletodo.itemTouchHelper.ItemTouchHelperAdapter;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder> implements ItemTouchHelperAdapter{

    private List<Tasks> mItems;
    private final Context mContext;

    public ItemsAdapter(Context context, List<Tasks> items) {
        mItems = items;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.individual_item, parent, false);

        // Return a new holder instance
        final ItemsViewHolder viewHolder = new ItemsViewHolder(itemView);
        viewHolder.mListener = new ItemsViewHolder.IMyViewHolderClicks() {

            @Override
            public void onTaskNameClick(View caller) {
                final int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MainActivity activity = (MainActivity)mContext;
                    activity.setupEditListener(position);
                }
            }

            @Override
            public void onCalendarIcon(ImageView callerImage) {
                final int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MainActivity activity = (MainActivity) mContext;
                    activity.showDatePickerDialog(position);
                }
            }

            @Override
            public void onPriorityIcon(ImageView callerImage) {
                final int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MainActivity activity = (MainActivity) mContext;
                    activity.setupPriorityListener(position);
                }
            }
        };
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ItemsViewHolder viewHolder, int position) {
        // Get the data model based on position
        Tasks task = mItems.get(position);
        int taskP = task.priority;

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(task.name);

        ImageView imageView = viewHolder.priorityIcon;
        if(taskP == 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_alert_circle_outline_black_24dp));
        }
        else {
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_alert_circle_black_24dp));
        }
    }

    @Override
    public void onItemDismiss(int position) {
        if (position != RecyclerView.NO_POSITION) {
            MainActivity activity = (MainActivity)mContext;
            activity.setupDeleteListener(position);
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition != RecyclerView.NO_POSITION) {
            MainActivity activity = (MainActivity)mContext;
            activity.setupReorderListener(fromPosition, toPosition);
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}