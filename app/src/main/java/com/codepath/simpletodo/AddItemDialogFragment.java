package com.codepath.simpletodo;

import android.content.Context;
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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddItemDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {
    @BindView(R.id.etNewItem)
    EditText mEditText;
    @BindView(R.id.btnAddItem)
    Button mButton;
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

        // 2. Setup a callback when the "Done" button is pressed on keyboard
        mEditText.setOnEditorActionListener(this);

        // Replicate "Done" keyboard press for button press
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 100% of the screen width
        //window.setLayout((int) (size.x * 1), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setLayout(size.x, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        // Call super onResume after sizing
        super.onResume();
    }

    // Fires whenever the textfield has an action performed
    // In this case, when the "Done" button is pressed
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
