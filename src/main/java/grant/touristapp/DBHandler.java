package grant.touristapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grant on 18/03/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "tourist2";

    private static final String TABLE_JOURNAL_ENTRY = "journalEntry";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
   // private static final String KEY_PHOTO = "photo";

    public DBHandler(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_JOURNAL_ENTRY_TABLE = "CREATE TABLE " + TABLE_JOURNAL_ENTRY + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_NOTES + " TEXT,"
                + KEY_LAT + " TEXT,"
                + KEY_LNG + " TEXT " + ")";

        db.execSQL(CREATE_JOURNAL_ENTRY_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS " +TABLE_JOURNAL_ENTRY);

        onCreate(db);
    }

    void addNewEntry(JournalEntry newEntry){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, newEntry.getTitle());
        values.put(KEY_NOTES, newEntry.getNotes());
        values.put(KEY_LAT, newEntry.getLAT());
        values.put(KEY_LNG, newEntry.getLNG());

        db.insert(TABLE_JOURNAL_ENTRY, null, values);
        db.close();
    }

    public List<JournalEntry> getAllEntries(){

        List<JournalEntry> entryList = new ArrayList<JournalEntry>();

        String selectQuery = "SELECT  * FROM " + TABLE_JOURNAL_ENTRY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{

                JournalEntry journal = new JournalEntry();
                journal.setId(Integer.parseInt(cursor.getString(0)));
                journal.setTitle(cursor.getString(1));
                journal.setNotes(cursor.getString(2));
                journal.setLAT(cursor.getDouble(3));
                journal.setLNG(cursor.getDouble(4));

                entryList.add(journal);
            } while (cursor.moveToNext());


        }
        db.close();
        return entryList;
    }

    public boolean updateJournalInfo(int updID, String updTitle, String updNotes){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, updTitle);
        values.put(KEY_NOTES, updNotes);

        return db.update(TABLE_JOURNAL_ENTRY, values, KEY_ID + "=" + updID, null)>0;
    }

    public boolean deleteJournal(int delID){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_JOURNAL_ENTRY, KEY_ID + "="+ delID, null)>0;

    }
}
