package us.wylder.stickies;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import us.wylder.stickies.data.PostCursorAdapter;
import us.wylder.stickies.data.PostsDB;


public class MainActivity extends ActionBarActivity {

    private ListView list;

    private PostCursorAdapter pca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        list = (ListView) findViewById(R.id.list_posts);

        pca = new PostCursorAdapter(getApplicationContext(),
                PostsDB.getInstance(getApplicationContext()).getCursor());

        list.setAdapter(pca);
    }

    @Override
    protected void onResume(){
        super.onResume();

        PostsDB db = PostsDB.getInstance(getApplicationContext());

        pca.changeCursor(db.getCursor());
        list.refreshDrawableState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void goToPostActivity(){
        Intent i = new Intent(getApplicationContext(), PostActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.new_post){
            goToPostActivity();
        }

        return super.onOptionsItemSelected(item);
    }


}
