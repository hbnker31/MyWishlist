package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.MyWish;

/**
 * Created by Hemant on 26-07-2017.
 */


public class dataBaseHandler extends SQLiteOpenHelper{
    private final ArrayList<MyWish> wishlist=new ArrayList<>();

    public dataBaseHandler(Context context) {
        super(context, constants.DATABASE_NAME, null, constants.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_WISH_TABLE="CREATE TABLE "+constants.TABLE_NAME+" ( "+
                constants.KEY_ID+" INTEGER PRIMARY KEY, "+constants.TITLE_NAME
                +" TEXT, "+constants.CONTENT_NAME+" TEXT, "+constants.DATE_NAME+
                " LONG);";
        sqLiteDatabase.execSQL(CREATE_WISH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+constants.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void AddWishes(MyWish wish)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(constants.TITLE_NAME,wish.getTitle());
        values.put(constants.CONTENT_NAME,wish.getContent());
        values.put(constants.DATE_NAME,java.lang.System.currentTimeMillis());

        db.insert(constants.TABLE_NAME,null,values);
        db.close();
    }

    public void deleteWish(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(constants.TABLE_NAME,constants.KEY_ID+" = ?",new String[]{String.valueOf(id)});
        db.close();
    }
    public ArrayList<MyWish> getWishes()
    {

        String selectQuery="SELECT * FROM "+constants.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(constants.TABLE_NAME,new String[]{constants.KEY_ID,constants.TITLE_NAME,constants.CONTENT_NAME,constants.DATE_NAME},
                null,null,null,null,constants.DATE_NAME+ " DESC");

        if (cursor.moveToFirst()){
            do {

                MyWish wish=new MyWish();
                wish.setTitle(cursor.getString(cursor.getColumnIndex(constants.TITLE_NAME)));
                wish.setContent(cursor.getString(cursor.getColumnIndex(constants.CONTENT_NAME)));

                wish.setID(cursor.getInt(cursor.getColumnIndex(constants.KEY_ID)));
                java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
                String dateData= dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(constants.DATE_NAME))));
                wish.setRecordDate(dateData);

                wishlist.add(wish);

            }while (cursor.moveToNext());
        }

        return wishlist;


    }















}
