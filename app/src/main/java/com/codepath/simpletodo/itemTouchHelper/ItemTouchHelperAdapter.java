package com.codepath.simpletodo.itemTouchHelper;

/**
 * Interface to listen for a move or dismissal event from a {@link ItemTouchHelperCallback}.
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
