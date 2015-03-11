package us.wylder.stickies.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import us.wylder.stickies.R;

/**
 * Created by mattwylder on 2/24/15.
 */
public class PostCursorAdapter extends CursorAdapter{

    private static final String TAG = "PostCursorAdapter";

    public PostCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.post_list_item, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int ndx = cursor.getColumnIndex("content");

        TextView sampleText = (TextView) view.findViewById(R.id.sample_text);
        if (sampleText == null) {
            Log.e(TAG, "Failed to get sample_text view");
            return;
        }
        String name = cursor.getString(ndx);
        if(name == null){
            Log.e(TAG, "failed to get string");
            return;
        }
        sampleText.setText(name);
    }
}


