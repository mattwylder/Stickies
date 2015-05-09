package us.wylder.stickies;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import us.wylder.stickies.data.PostCursorAdapter;
import us.wylder.stickies.data.PostsDB;


public class MainActivity extends ActionBarActivity implements PostDialogFragment.PostDialogListener{

    private ListView list;

    private PostCursorAdapter pca;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setup();

    }

    @Override
    protected void onResume(){
        super.onResume();

         setup();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void clearPosts(){
        PostsDB db = PostsDB.getInstance(getApplicationContext());
        db.clearAll();
        setContentView(R.layout.activity_main_no_posts);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_all) {
            Log.d(TAG, "settings");
            clearPosts();
            return true;
        }

        if (id == R.id.new_post){
            showPostDialog();
        }

        return super.onOptionsItemSelected(item);
    }


    private void setup(){
        PostsDB db = PostsDB.getInstance(getApplicationContext());
        Log.d(TAG, "NumPosts = " + db.getNumPosts());

        if(db.getNumPosts() == 0 ){
            setContentView(R.layout.activity_main_no_posts);
        }
        else{
            setContentView(R.layout.activity_main2);

            list = (ListView) findViewById(R.id.list_posts);

            pca = new PostCursorAdapter(getApplicationContext(),
                    PostsDB.getInstance(getApplicationContext()).getCursor());

            list.setAdapter(pca);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showPost(((TextView) view.findViewById(R.id.sample_text)).getText().toString());
                }
            });
        }

    }

    private void showPost(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void showPostDialog(){
        DialogFragment frag = new PostDialogFragment();
        frag.show(getFragmentManager(), "postdialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog){
        Log.d(TAG, "onDialogPositiveClick");
        setup();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog){

    }
}
