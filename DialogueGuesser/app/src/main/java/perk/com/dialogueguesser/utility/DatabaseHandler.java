package perk.com.dialogueguesser.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import perk.com.dialogueguesser.model.DialogDataModel;
import perk.com.dialogueguesser.model.LevelDataModel;

/**
 * Created by koroy on 12/19/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_FLIM_DIALOG = "dialog_table";
    private static final String TABLE_LEVEL_INFO = "level_table";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_DIALOG = "dialog";
    private static final String KEY_ACTOR = "actor";
    private static final String KEY_ACTRESS = "actress";
    private static final String KEY_DIRECTOR = "director";
    private static final String KEY_FLIM_NAME = "flim_name";
    private static final String KEY_IS_ATTEMPTED="attempted";

    private static final String KEY_POINTS = "points";
    private static final String KEY_TOTAL_DIALOG = "total_dialog";
    private static final String KEY_IS_LOCKED="locked";
    private static final String KEY_POINTS_TO_BE_UNLOCKED="points_to_be_unlocked";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DIALOG_TABLE = "CREATE TABLE " + TABLE_FLIM_DIALOG + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LEVEL + " TEXT,"
                + KEY_DIALOG + " TEXT,"+ KEY_ACTOR + " TEXT,"
                + KEY_ACTRESS + " TEXT,"+ KEY_DIRECTOR + " TEXT,"
                + KEY_IS_ATTEMPTED + " TEXT,"+ KEY_FLIM_NAME + " TEXT)";

        String CREATE_LEVEL_TABLE = "CREATE TABLE " + TABLE_LEVEL_INFO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LEVEL + " TEXT,"
                + KEY_POINTS + " INTEGER,"+ KEY_IS_LOCKED + " STRING,"
                + KEY_POINTS_TO_BE_UNLOCKED + " INTEGER,"+ KEY_TOTAL_DIALOG + " INTEGER)";
        
        db.execSQL(CREATE_DIALOG_TABLE);
        db.execSQL(CREATE_LEVEL_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIM_DIALOG);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LEVEL_INFO);

        // Create tables again
        onCreate(db);
    }

    public void addLevelData(ArrayList<LevelDataModel> levelDataModels) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (LevelDataModel levelDataModel:levelDataModels) {
            ContentValues values = new ContentValues();
            values.put(KEY_LEVEL, levelDataModel.getLevelName()); // Contact Name
            values.put(KEY_POINTS, levelDataModel.getCompletedDialog()); // Contact Phone Number
            values.put(KEY_TOTAL_DIALOG,levelDataModel.getTotalDialog());
            if(levelDataModel.isLocked())
                values.put(KEY_IS_LOCKED,"Y");
            else if(!levelDataModel.isLocked())
                values.put(KEY_IS_LOCKED,"N");
            values.put(KEY_POINTS_TO_BE_UNLOCKED,levelDataModel.getPointsToBeUnlocked());
            // Inserting Row
            db.insert(TABLE_LEVEL_INFO, null, values);
            Log.d("TAG", "Levels Added");
        }

        db.close(); // Closing database connection
    }

    public void addDialogInfo(ArrayList<DialogDataModel> dialogDataModels){
        SQLiteDatabase db = this.getWritableDatabase();
        for (DialogDataModel levelDataModel:dialogDataModels) {
            ContentValues values = new ContentValues();
            values.put(KEY_LEVEL, levelDataModel.getLevelName()); // Contact Name
            values.put(KEY_DIALOG, levelDataModel.getDialog()); // Contact Phone Number
            values.put(KEY_ACTOR,levelDataModel.getActor());
            values.put(KEY_ACTRESS,levelDataModel.getActress());
            values.put(KEY_DIRECTOR,levelDataModel.getDirector());
            values.put(KEY_FLIM_NAME,levelDataModel.getMovieName());
            if(levelDataModel.isAttempted())
                values.put(KEY_IS_ATTEMPTED,"Y");
            else if(!levelDataModel.isAttempted())
                values.put(KEY_IS_ATTEMPTED,"N");
            // Inserting Row
            db.insert(TABLE_FLIM_DIALOG, null, values);
            Log.d("TAG", "Dialog Added");
        }
        db.close(); // Closing database connection
    }

    public ArrayList<LevelDataModel> getLevelInfo(){
        ArrayList<LevelDataModel> levelDataModels=new ArrayList<LevelDataModel>();
        LevelDataModel levelDataModel;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LEVEL_INFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.getCount();
        if(cursor==null || cursor.getCount()== 0){
            Log.d("TAG","Level Cursor Count is :"+cursor.getCount());
            return null;
        }
        // looping through all rows and adding to list
       if (cursor.moveToFirst()) {
            do {
                levelDataModel = new LevelDataModel();
                levelDataModel.setLevelName(cursor.getString(1));
                levelDataModel.setCompletedDialog(cursor.getInt(2));
                levelDataModel.setPointsToBeUnlocked(cursor.getInt(4));
                if(cursor.getString(3)!=null && cursor.getString(3).equalsIgnoreCase("Y"))
                    levelDataModel.setIsLocked(true);
                else if(cursor.getString(3).equalsIgnoreCase("N"))
                    levelDataModel.setIsLocked(false);
                levelDataModel.setTotalDialog(cursor.getInt(5));

                // Adding contact to list
                levelDataModels.add(levelDataModel);
            } while (cursor.moveToNext());
       }
        return levelDataModels;

    }

    public ArrayList<DialogDataModel> getDialogInfo(){
        ArrayList<DialogDataModel> dialogDataModels=new ArrayList<DialogDataModel>();
        DialogDataModel dialogDataModel;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FLIM_DIALOG;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.getCount();
        if(cursor==null || cursor.getCount()==0){
            Log.d("TAG","Dialog Cursor count is "+cursor.getCount());
            return null;
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                dialogDataModel = new DialogDataModel();
                dialogDataModel.setLevelName(cursor.getString(cursor.getColumnIndex(KEY_LEVEL)));
                dialogDataModel.setDialog(cursor.getString(2));
                dialogDataModel.setActor(cursor.getString(3));
                dialogDataModel.setActress(cursor.getString(4));
                dialogDataModel.setDirector(cursor.getString(5));
                if(cursor.getString(6)!=null && cursor.getString(6).equalsIgnoreCase("Y"))
                    dialogDataModel.setIsAttempted(true);
                else if(cursor.getString(6).equalsIgnoreCase("N"))
                    dialogDataModel.setIsAttempted(false);
                dialogDataModel.setMovieName(cursor.getString(7));

                // Adding contact to list
                dialogDataModels.add(dialogDataModel);
            } while (cursor.moveToNext());
        }
        return dialogDataModels;
    }


    public ArrayList<DialogDataModel> getIncompletedDialogFilteredByLevel(String levelName){
        ArrayList<DialogDataModel> dialogDataModels=new ArrayList<DialogDataModel>();
        DialogDataModel dialogDataModel;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FLIM_DIALOG+" WHERE "+KEY_LEVEL+" = '"+levelName+"' AND "+KEY_IS_ATTEMPTED+" = 'N'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.getCount();
        if(cursor==null || cursor.getCount()==0){
            Log.d("TAG","Dialog Cursor count is "+cursor.getCount());
            return null;
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                dialogDataModel = new DialogDataModel();
                dialogDataModel.setLevelName(cursor.getString(cursor.getColumnIndex(KEY_LEVEL)));
                dialogDataModel.setDialog(cursor.getString(2));
                dialogDataModel.setActor(cursor.getString(3));
                dialogDataModel.setActress(cursor.getString(4));
                dialogDataModel.setDirector(cursor.getString(5));
                if(cursor.getString(6)!=null && cursor.getString(6).equalsIgnoreCase("Y"))
                    dialogDataModel.setIsAttempted(true);
                else if(cursor.getString(6).equalsIgnoreCase("N"))
                    dialogDataModel.setIsAttempted(false);
                dialogDataModel.setMovieName(cursor.getString(7));

                // Adding contact to list
                dialogDataModels.add(dialogDataModel);
            } while (cursor.moveToNext());
        }
        return dialogDataModels;
    }

}
