package com.debugi.app.anyvblog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.debugi.app.anyvblog.utils.Config;

public class DBHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBHelper";
	final String SQL_CREATE_TABLE_ACCOUNTS = "CREATE TABLE IF NOT EXISTS accounts (" +
							"_id integer primary key autoincrement, " +
							"sp integer, " +
							"user_id varchar, " +
							"nick varchar, " +
							"name varchar, " +
							"token varchar, " +
							"token_secret varchar, " +
							"head_url varchar, " +
							"last_update varchar, " +
							"head blob)";
	final String SQL_CREATE_TABLE_HOMELIST = "CREATE TABLE IF NOT EXISTS homelist (" +
							"status_id varchar, " +
							"nick varchar, " +
							"name varchar, " +
							"head_url varchar, " +
							"head blob, " +
							"isvip integer, " +
							"isfav integer, " +
							"type integer, " +
							"time varchar, " +
							"origtime varchar, " +
							"status varchar, " +
							"image blob, " +
							"image_url varchar, " +
							"image_s varchar, " +
							"image_m varchar, " +
							"image_o varchar, " +
							"sfrom varchar, " +
							"forward_count integer, " +
							"comment_count integer, " +
							"long double, " +
							"lat double, " +
							"location varchar, " +
							"source varchar, " +
							"source_status_id varchar, " +
							"source_nick varchar, " +
							"source_name varchar, " +
							"source_isvip varchar, " +
							"source_time varchar, " +
							"source_status varchar, " +
							"source_image blob, " +
							"source_image_url varchar, " +
							"source_image_s varchar, " +
							"source_image_m varchar, " +
							"source_image_o varchar, " +
							"source_from varchar, " +
							"source_location varchar, " +
							"source_forward_count varchar, " +
							"source_comment_count varchar, " +
							"sp integer, " +
							"user_id varchar " +
							")";
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Config.debug(TAG, "start create tables");
		db.execSQL(SQL_CREATE_TABLE_ACCOUNTS);
		db.execSQL(SQL_CREATE_TABLE_HOMELIST);
		Config.debug(TAG, "create success");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Config.debug(TAG, "start update tables");
		String sql1 = "DROP TABLE IF EXISTS accounts";
		String sql2 = "DROP TABLE IF EXISTS homelist";
		db.execSQL(sql1);
		db.execSQL(sql2);
		onCreate(db);
	}
	public void updateColumn(SQLiteDatabase db, String tableName, String oldColumn, String newColumn, String typeColumn) {
		try{
            db.execSQL("ALTER TABLE " +
            		tableName + " CHANGE " +
                    oldColumn + " "+ newColumn +
                    " " + typeColumn
            );
        }catch(Exception e){
			e.printStackTrace();
        }
	}
}
