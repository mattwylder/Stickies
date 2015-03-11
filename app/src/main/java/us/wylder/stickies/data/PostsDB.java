package us.wylder.stickies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

/**
 * Created by mattwylder on 2/24/15.
 */
public class PostsDB extends SQLiteOpenHelper{

    private static Context context;
    private static PostsDB singleton = null;

    //Database info
    private static final String DATABASE_NAME = "Posts.db";
    private static final int DATABASE_VERSION = 3;

    private static final String DB_POSTS_TABLE_NAME = "posts";
    private static final String[] DB_POSTS_COLUMN_NAMES = {"_id", "content"};

    //SQL Strings
    private static final String SQL_CREATE_POSTS =
            "CREATE TABLE posts" +
                    "(_id INTEGER PRIMARY KEY," +
                    "content TEXT)";
    private static final String SQL_DROP_POSTS =
            "DROP TABLE posts";

    protected PostsDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static PostsDB getInstance(Context context){
        if(singleton == null){
            singleton = new PostsDB(context);
            Log.v("TAG", "Created singleton");
        }
        return singleton;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("TAG", "OnCreating");
        db.execSQL(SQL_CREATE_POSTS);
        Log.v("PostsDB", "Created table");
        addPost("This is the first post");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("TAG", "Upgrading");
        db.execSQL(SQL_DROP_POSTS);
        db.execSQL(SQL_CREATE_POSTS);
        addPost("This is the new upgrade post");
    }

    public long addPost( String content){

        ContentValues values = new ContentValues();
        values.put("content", content);

        SQLiteDatabase db = this.getReadableDatabase();

        return db.insert(DB_POSTS_TABLE_NAME, null, values);
    }

    public Cursor getCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        Log.v("TAG", "About to query");
        Cursor c = db.query(DB_POSTS_TABLE_NAME, null, null, null, null, null, null, null);

        return c;
    }
}
