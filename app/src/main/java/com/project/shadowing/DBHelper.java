package com.project.shadowing;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.project.shadowing.Fragment2.movieInfoArrayList_fragment2;
import static com.project.shadowing.MainActivity.movieInfoArrayList_fragment1;
import static com.project.shadowing.PlayActivity.Sub_PlayList;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        int key = 0;

        movieInfoArrayList_fragment1.add(new MovieInfo(key++, -1, R.drawable.conimg, 0,"Mom, look at this picture of my classmates."));
        movieInfoArrayList_fragment1.add(new MovieInfo(key++, -1, R.drawable.conimg, 0,"I like her eyes most."));
        movieInfoArrayList_fragment1.add(new MovieInfo(key++, -1, R.drawable.conimg, 0, "He has long hair."));
        movieInfoArrayList_fragment1.add(new MovieInfo(key++, -1, R.drawable.conimg, 0,"We're looking for a child."));
        movieInfoArrayList_fragment1.add(new MovieInfo(key++, -1, R.drawable.conimg, 0, "If you find her, please take her to the information center. Thank you."));

        sqLiteDatabase.execSQL("create table movieinfo_fragment1(_id integer PRIMARY KEY autoincrement, key_ integer, islike integer, previewid integer, level integer, explain_ text)");
        sqLiteDatabase.execSQL("create table movieinfo_fragment2(_id integer PRIMARY KEY autoincrement, key_ integer, islike integer, previewid integer, level integer, explain_ text)");
        sqLiteDatabase.execSQL("create table Sub_PlayList(_id integer PRIMARY KEY autoincrement, key_ integer, track integer, num integer, time integer, sub Text)");

        String sql = "insert into movieinfo_fragment1(key_, islike, previewid, level, explain_) values(?, ?, ?, ?, ?)";
        String sql_Sub = "insert into Sub_PlayList(key_, track, num, time, sub) values(?, ?, ?, ?, ?)";

        for (int i = 0; i<movieInfoArrayList_fragment1.size(); i++) {
            Object[] parmas = {movieInfoArrayList_fragment1.get(i).key, movieInfoArrayList_fragment1.get(i).isLike, movieInfoArrayList_fragment1.get(i).previewId,
                    movieInfoArrayList_fragment1.get(i).level, movieInfoArrayList_fragment1.get(i).explain};
            sqLiteDatabase.execSQL(sql, parmas);
        }

        for (int i = 0; i < Sub_PlayList.size(); i++) {
            Object[] parmas_sub = {Sub_PlayList.get(i).key, Sub_PlayList.get(i).start, Sub_PlayList.get(i).num, Sub_PlayList.get(i).time, Sub_PlayList.get(i).sub};
            sqLiteDatabase.execSQL(sql_Sub, parmas_sub);
        }

        movieInfoArrayList_fragment1.clear();
        Sub_PlayList.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(String sql, Object[] params) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql, params);
        sqLiteDatabase.close();
    }

    public void update(String sql) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void delete(String sql) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void insertToArrayList (String sql, int type) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            int key = cursor.getInt(0);
            int islike = cursor.getInt(1);
            int previewid = cursor.getInt(2);
            int level = cursor.getInt(3);
            String explain = cursor.getString(4);
            if (type == 1) {
                movieInfoArrayList_fragment1.add(new MovieInfo(key, islike, previewid, level, explain));
            } else if (type == 2) {
                movieInfoArrayList_fragment2.add(new MovieInfo(key, islike, previewid, level, explain));
            }
        }

        cursor.close();
        sqLiteDatabase.close();
    }

    public void dropTable () {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL("drop table movieinfo_fragment1");
        sqLiteDatabase.execSQL("drop table movieinfo_fragment2");
        sqLiteDatabase.close();
    }
}
