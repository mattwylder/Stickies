package us.wylder.stickies;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import us.wylder.stickies.data.PostsDB;

/**
 * Created by matthew on 4/16/15.
 */
public class PostDialogFragment extends DialogFragment {


    private static final String TAG = "DialogFragment";
    EditText editText;
    PostsDB db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.edit_post);


        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_edit_post, null);


        builder.setView(view)
                .setPositiveButton(R.string.save_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        editText = (EditText) view.findViewById(R.id.textEntry);
                        db = PostsDB.getInstance(getActivity().getApplicationContext());
                        db.addPost(editText.getText().toString());
                        mListener.onDialogPositiveClick(PostDialogFragment.this);
                        //TODO: Save the message
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PostDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
  * implement this interface in order to receive event callbacks.
  * Each method passes the DialogFragment in case the host needs to query it. */
    public interface PostDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    PostDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (PostDialogListener) activity;
            Log.d(TAG, "OnAttached correctly");
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
