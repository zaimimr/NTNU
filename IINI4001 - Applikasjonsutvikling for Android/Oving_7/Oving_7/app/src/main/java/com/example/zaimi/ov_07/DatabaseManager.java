package com.example.zaimi.ov_07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    static final String TABLE_AUTHOR = "author";
    static final String TABLE_TITLE = "book";
    static final String TABLE_AUTHOR_BOOK = "author_book";
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_TITLE="title";
    static final String KEY_AUTHOR="author_id";
    static final String KEY_BOOK="book_id";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME="BooksDb";
    static final int DATABASE_VERSION=1;
    static final String DATABASE_CREATE1 = "create table " + TABLE_AUTHOR
            + " ("+KEY_ROWID+" integer primary key autoincrement, "
            + KEY_NAME +" text unique not null);";
    static final String DATABASE_CREATE2 = "create table " + TABLE_TITLE
            + " ("+KEY_ROWID+" integer primary key autoincrement, "
            + KEY_TITLE +" text unique not null);";
    static final String DATABASE_CREATE3 = "create table " + TABLE_AUTHOR_BOOK
            + " ("+KEY_ROWID+" integer primary key autoincrement, "
            + KEY_BOOK+" numeric, "
            + KEY_AUTHOR+" numeric,"
            + "FOREIGN KEY("+KEY_AUTHOR+") REFERENCES " + TABLE_AUTHOR + "("+KEY_ROWID+"), "
            + "FOREIGN KEY("+KEY_BOOK+") REFERENCES "+ TABLE_TITLE +"("+KEY_ROWID+")"
            + ");";
    static final String AUTHOR_BOOK_SELECTION =
            "select "+ TABLE_TITLE+"."+KEY_ROWID+", "+TABLE_TITLE+"."+KEY_TITLE + " from "
                    + TABLE_AUTHOR+", "+TABLE_TITLE + ", " + TABLE_AUTHOR_BOOK + " where "
                    + TABLE_AUTHOR + "."+KEY_ROWID+"="+ TABLE_AUTHOR_BOOK+"."+KEY_AUTHOR+" and "
                    + TABLE_TITLE + "."+KEY_ROWID+"=" + TABLE_AUTHOR_BOOK + "."+KEY_BOOK+" and "
                    + TABLE_AUTHOR +"." + KEY_NAME +"=?";
    static final String BOOK_AUTHOR_SELECTION =
            "select "+ TABLE_AUTHOR+"."+KEY_ROWID+", "+TABLE_AUTHOR+"."+KEY_NAME + " from "
                    + TABLE_AUTHOR+", "+TABLE_TITLE + ", " + TABLE_AUTHOR_BOOK + " where "
                    + TABLE_AUTHOR + "."+KEY_ROWID+"="+ TABLE_AUTHOR_BOOK+"."+KEY_AUTHOR+" and "
                    + TABLE_TITLE + "._id=" + TABLE_AUTHOR_BOOK + ".book_id and "
                    + TABLE_TITLE +"." + KEY_TITLE +"=?";
    static final String ALL_AUTHOR_BOOK_SELECTION =
            "select "+TABLE_AUTHOR+"."+KEY_NAME +"," +TABLE_TITLE+"."+KEY_TITLE + " from "
                    + TABLE_AUTHOR+", "+TABLE_TITLE + ", " + TABLE_AUTHOR_BOOK + " where "
                    + TABLE_AUTHOR + "."+KEY_ROWID+"="+ TABLE_AUTHOR_BOOK+"."+KEY_AUTHOR+" and "
                    + TABLE_TITLE + "."+KEY_ROWID+"=" + TABLE_AUTHOR_BOOK + "."+KEY_BOOK+";";

    private Context context;

    public DatabaseManager(Context context) throws Exception {
        super(context,
                /*db name=*/ DATABASE_NAME,
                /*cursorFactory=*/ null,
                /*db version=*/DATABASE_VERSION);
   //     Toast.makeText(context, "Inn i databasekonstruktøren", Toast.LENGTH_LONG).show();
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("db", "onCreate");
        db.execSQL(DATABASE_CREATE1);
        db.execSQL(DATABASE_CREATE2);
        db.execSQL(DATABASE_CREATE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        Log.d("db", "onUpdate");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TITLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
                // re-create the table
        onCreate(db);
     }
    public void clean() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 0, 0);
        db.close();
    }
    private long insertAuthor(String name, SQLiteDatabase db)  {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        long id = db.insert(TABLE_AUTHOR, null, initialValues);
        return id;
    }
    private long insertBook(String title, SQLiteDatabase db) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        long id = db.insert(TABLE_TITLE,  null, initialValues);
        return id;
    }
    private long insertAuthor_Book(long author, long book, SQLiteDatabase db) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_AUTHOR, author);
        initialValues.put(KEY_BOOK, book);
        long id = db.insert(TABLE_AUTHOR_BOOK,  null, initialValues);
        return id;
    }
    public long insert(String author, String title){
        SQLiteDatabase db = null;
        db = this.getWritableDatabase();
        Cursor cursor =
                db.query(true, TABLE_AUTHOR, new String[]{KEY_ROWID, KEY_NAME}, KEY_NAME + "='" + author + "'", null, null, null, null, null);
        long author_id;
        if (cursor == null || cursor.getCount() == 0) {
            author_id = insertAuthor(author,db);
        } else {
            cursor.moveToFirst();
            author_id = cursor.getLong(0);
        }
        long title_id;
        cursor =
                db.query(TABLE_TITLE, new String[]{KEY_ROWID, KEY_TITLE}, KEY_TITLE + "='" + title + "'", null, null, null, null, null);

        if (cursor == null || cursor.getCount() == 0) {
            title_id = insertBook(title,db);
        } else {
            cursor.moveToFirst();
            title_id = cursor.getLong(0);
        }
        long id = insertAuthor_Book(author_id, title_id,db);
        db.close();
        return id;
    }
    public ArrayList<String> getAllAuthors()  {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_AUTHOR, new String[]{KEY_ROWID, KEY_NAME}, null, null, null, null, null, null);
        ArrayList<String> res = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                res.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        db.close();
        return res;
    }
    public ArrayList<String> getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_TITLE, new String[]{KEY_ROWID, KEY_TITLE}, null, null, null, null, null, null);
        ArrayList<String> res = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                res.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        db.close();
        return res;
    }
    public ArrayList<String> getAllBooksAndAuthors() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.rawQuery(ALL_AUTHOR_BOOK_SELECTION, new String[]{});
        ArrayList<String> res = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                res.add(cursor.getString(0)+ ": " +cursor.getString(1));
                cursor.moveToNext();
            }
        }
        db.close();
        return res;
    }
    public ArrayList<String> getBooksByAuthor(String author)  {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> res = new ArrayList<>();
        Cursor cursor =
                db.rawQuery(AUTHOR_BOOK_SELECTION, new String[]{author});
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                res.add(cursor.getString(1));
                cursor.moveToNext();
            }
         }
        db.close();
        return res;
    }
    public ArrayList<String> getAuthorsByBook(String title)  {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> res = new ArrayList<>();
        Cursor cursor =
                db.rawQuery(BOOK_AUTHOR_SELECTION, new String[]{title});
         if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                res.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        db.close();
        return res;
    }

}
