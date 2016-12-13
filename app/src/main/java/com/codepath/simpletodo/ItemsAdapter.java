package com.codepath.simpletodo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder> {

    // Store a member variable for the tasks
    private List<Tasks> mItems;
    // Store the context for easy access
    private final Context mContext;

    // Pass in the task array into the constructor
    public ItemsAdapter(Context context, List<Tasks> items) {
        mItems = items;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.individual_item, parent, false);

        // Return a new holder instance
        final ItemsViewHolder viewHolder = new ItemsViewHolder(itemView);
        viewHolder.mListener = new ItemsViewHolder.IMyViewHolderClicks() {
            @Override
            public void onTaskNameLongClick(View caller) {
                final int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MainActivity activity = (MainActivity)context;
                    activity.setupDeleteListener(position);
                }
            }

            @Override
            public void onTaskNameClick(View caller) {
                final int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MainActivity activity = (MainActivity)context;
                    activity.setupEditListener(position);
                }
            }

            @Override
            public void onCalendarIcon(ImageView callerImage) {
                final int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MainActivity activity = (MainActivity) context;
                    activity.showDatePickerDialog(position);
                }
            }

            @Override
            public void onPriorityIcon(ImageView callerImage) {
                final int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MainActivity activity = (MainActivity) context;
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
    public int getItemCount() {
        return mItems.size();
    }
}