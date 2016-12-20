package com.codepath.simpletodo;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment; //IMPORTANT: android studio default gets this wrong
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.Unbinder;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class AddItemDialogFragment extends DialogFragment {
    @BindView(R.id.etNewItem)
        EditText mEditText;

    private Unbinder unbinder;

    public AddItemDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddItemDialogFragment newInstance(String title) {
        AddItemDialogFragment frag = new AddItemDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface AddItemDialogListener {
        void onFinishAddDialog(String inputText);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container);
        unbinder = ButterKnife.bind(this, view);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title");
        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        return view;
    }

    @OnClick(R.id.btnAddItem)
    public void onClick() {
        // Replicate "Done" keyboard press for button press
        mEditText.onEditorAction(IME_ACTION_DONE);
    }

    @OnEditorAction(R.id.etNewItem)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            AddItemDialogListener listener = (AddItemDialogListener) getActivity();
            listener.onFinishAddDialog(mEditText.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 100% of the screen width
        window.setLayout(size.x, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
