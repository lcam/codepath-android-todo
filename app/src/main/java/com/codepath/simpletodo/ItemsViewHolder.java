package com.codepath.simpletodo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.simpletodo.itemTouchHelper.ItemTouchHelperAdapter;

import java.util.Collections;


// Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
public class ItemsViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {//, View.OnLongClickListener {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    public TextView nameTextView;
    public ImageView calendarIcon;
    public ImageView priorityIcon;
    public IMyViewHolderClicks mListener;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ItemsViewHolder(View itemView) { //NOTICE: NO LONGER PASSING IMyViewHolderClicks INTERFACE AS A PARAMETER
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
        //nameTextView.setOnLongClickListener(this); //delete task
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

//    @Override
//    public boolean onLongClick(View view) {
//        mListener.onTaskNameLongClick(view);
//        return true;
//    }

    public static interface IMyViewHolderClicks {
        public void onTaskNameClick(View caller);
        //public void onTaskNameLongClick(View caller);
        public void onCalendarIcon(ImageView callerImage);
        public void onPriorityIcon(ImageView callerImage);
    }
}
