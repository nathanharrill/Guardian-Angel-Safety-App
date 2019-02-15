package com.example.guardianangelsafetyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import java.lang.Exception;

public class ContactsDatabase extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 2;
  private static final String DATABASE_NAME = "ContactsDB";
  private static final String TABLE_NAME = "contactsinfo";
  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";

  private static ContactsDatabase instance;

  public static void initializeDatabase(Context context) throws Exception {
    if (context == null) {
      throw new Exception("You're an idiot");
    }
    if (instance == null) {
        instance = new ContactsDatabase(context);
    }
  }

  public static ContactsDatabase getInstance()
  {
      return instance;
  }

  private ContactsDatabase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String createTbl = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID +
                       " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT)";
    db.execSQL(createTbl);
  }
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(db);
  }

  private ContactEntry createContactFromCursor(Cursor cursor)
  {
      return new ContactEntry(
          cursor.getInt(cursor.getColumnIndex(KEY_ID)),
          cursor.getString(cursor.getColumnIndex(KEY_NAME))
      );
  }

  public List<ContactEntry> getContacts() {
      SQLiteDatabase db = this.getReadableDatabase();

      String queryStm = "SELECT * FROM " + TABLE_NAME;

      Cursor cursor = db.rawQuery(queryStm, null);

      List<ContactEntry> contactList = new ArrayList<>();

      if(cursor.moveToFirst()) //Cursor is actively pointing at a record
      {
          while(!cursor.isAfterLast())
          {
              contactList.add(createContactFromCursor(cursor));
              cursor.moveToNext();
          }
      }
      cursor.close();
      db.close();
  }

  public ContactEntry getContactById(int id) {
      SQLiteDatabase db = this.getReadableDatabase();
      
      db.close();
  }

}