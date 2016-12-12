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
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    // Store a member variable for the tasks
    private List<Tasks> mItems;
    // Store the context for easy access
    private final Context mContext;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public ImageView calendarIcon;
        public ImageView priorityIcon;
        public IMyViewHolderClicks mListener;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) { //NOTICE: NO LONGER PASSING IMyViewHolderClicks INTERFACE AS A PARAMETER
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            //mListener = listener; //NOTICE: ASSIGN mListener in onCreateViewHolder, NOT HERE
            nameTextView = (TextView) itemView.findViewById(R.id.task_name);
            calendarIcon = (ImageView) itemView.findViewById(R.id.calendar_icon);
            priorityIcon = (ImageView) itemView.findViewById(R.id.priority_icon);

            calendarIcon.setOnClickListener(this); //calendar icon
            priorityIcon.setOnClickListener(this); //priority icon
            nameTextView.setOnClickListener(this); //edit task
            nameTextView.setOnLongClickListener(this); //delete task
            //ver 5.0: use nameTextView instead of itemView to limit clickable surface
        }

        @Override
        public void onClick(View view) {
            if (view instanceof ImageView){
                switch (view.getId()) {
                    case R.id.calendar_icon:
                        mListener.onCalendarIcon((ImageView)view);
                        break;
                    case R.id.priority_icon:
                        mListener.onPriorityIcon((ImageView)view);
                        break;
                }
            } else {
                mListener.onTaskNameClick(view);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            mListener.onTaskNameLongClick(view);
            return true;
        }

        public static interface IMyViewHolderClicks {
            public void onTaskNameClick(View caller);
            public void onTaskNameLongClick(View caller);
            public void onCalendarIcon(ImageView callerImage);
            public void onPriorityIcon(ImageView callerImage);
        }
    }

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
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.individual_item, parent, false);

        // Return a new holder instance
        final ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.mListener = new ViewHolder.IMyViewHolderClicks() {

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
    public void onBindViewHolder(ItemsAdapter.ViewHolder viewHolder, int position) {
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