package com.codepath.simpletodo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


// Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
public class ItemsViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {//, View.OnLongClickListener {
    // Member variables for any view that will be set as you render a row
    @BindView(R.id.task_name)
        TextView nameTextView;
    @BindView(R.id.calendar_icon)
        ImageView calendarIcon;
    @BindView(R.id.priority_icon)
        ImageView priorityIcon;

    public IMyViewHolderClicks mListener;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ItemsViewHolder(View itemView) { //NOTICE: NO LONGER PASSING IMyViewHolderClicks INTERFACE AS A PARAMETER
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);
        ButterKnife.bind(this, itemView);

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

    public interface IMyViewHolderClicks {
        void onTaskNameClick(View caller);
        //void onTaskNameLongClick(View caller);
        void onCalendarIcon(ImageView callerImage);
        void onPriorityIcon(ImageView callerImage);
    }
}
