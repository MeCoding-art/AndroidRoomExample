package com.machine.androidroomexample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.machine.androidroomexample.entity.Notes;
import com.machine.androidroomexample.repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    NotesRepository repository;
    public LiveData<List<Notes>> getAllNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        repository = new NotesRepository(application);
        getAllNotes = repository.getAllNotes;
    }

    public void insertNote(Notes notes){
        repository.inserNotes(notes);
    }

    public void deleteNote(int id){
        repository.deleteNotes(id);
    }

    public void updateNote(Notes notes){
        repository.updateNotes(notes);
    }

    public void deleteAllNote(){
        repository.deleteAllNotes();
    }

}
