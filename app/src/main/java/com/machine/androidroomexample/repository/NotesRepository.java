package com.machine.androidroomexample.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.machine.androidroomexample.entity.Notes;
import com.machine.androidroomexample.dao.NotesDao;
import com.machine.androidroomexample.database.NotesDatabase;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> getAllNotes;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getInstance(application);
        notesDao = database.notesDao();

        getAllNotes = notesDao.getAllNotes();

    }

    public void inserNotes(Notes notes){
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(int id){
        notesDao.deleteNotes(id);
    }

    public void updateNotes(Notes notes){
        notesDao.updateNotes(notes);
    }

    public void deleteAllNotes(){
        notesDao.deleteAll();
    }

}
