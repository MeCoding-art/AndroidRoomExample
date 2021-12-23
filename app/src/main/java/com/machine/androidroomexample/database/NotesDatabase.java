package com.machine.androidroomexample.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.machine.androidroomexample.entity.Notes;
import com.machine.androidroomexample.dao.NotesDao;

@Database(entities = {Notes.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    public static NotesDatabase INSTANCE;

    public static NotesDatabase getInstance(Context context){
        if(INSTANCE == null){
            return INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class,
                    "Notes_Database").build();

        }
        else {
            return INSTANCE;
        }
    }
}
