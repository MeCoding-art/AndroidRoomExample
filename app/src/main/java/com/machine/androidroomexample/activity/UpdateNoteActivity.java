package com.machine.androidroomexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.machine.androidroomexample.R;
import com.machine.androidroomexample.databinding.ActivityUpdateNoteBinding;
import com.machine.androidroomexample.entity.Notes;
import com.machine.androidroomexample.utilities.AppExecutors;
import com.machine.androidroomexample.viewmodel.NotesViewModel;

public class UpdateNoteActivity extends AppCompatActivity {
    ActivityUpdateNoteBinding binding;
    NotesViewModel viewModel;
    String title, subtitles, currentDate, priority, notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Update Note");

            Gson gson = new Gson();
            String notesObjecttoString = getIntent().getStringExtra("NoteObject");
            Notes fromJson = gson.fromJson(notesObjecttoString, Notes.class);

            binding.etTitle.setText(fromJson.notesTitle);
            binding.etSubTitle.setText(fromJson.notesSubTitle);
            binding.etNotes.setText(fromJson.notes);
            binding.tvDate.setText(fromJson.notesDate);

            priority = fromJson.notesPriority;
            if(priority.equals("1")){
                binding.ivGreen.setImageResource(R.drawable.ok);
            }
            if(priority.equals("2")){
                binding.ivYellow.setImageResource(R.drawable.ok);
            }
            if(priority.equals("3")){
                binding.ivRed.setImageResource(R.drawable.ok);
            }

            viewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        binding.ivGreen.setOnClickListener(v -> {
            priority = "1";
            binding.ivGreen.setImageResource(R.drawable.ok);
            binding.ivYellow.setImageResource(0);
            binding.ivRed.setImageResource(0);

        });

        binding.ivRed.setOnClickListener(v -> {
            priority = "3";
            binding.ivGreen.setImageResource(0);
            binding.ivRed.setImageResource(R.drawable.ok);
            binding.ivYellow.setImageResource(0);

        });

        binding.ivYellow.setOnClickListener(v -> {
            priority = "2";
            binding.ivGreen.setImageResource(0);
            binding.ivRed.setImageResource(0);
            binding.ivYellow.setImageResource(R.drawable.ok);

        });

            binding.fbUpdateNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    title = binding.etTitle.getText().toString();
                    subtitles = binding.etSubTitle.getText().toString();
                    notes = binding.etNotes.getText().toString();
                    currentDate = binding.tvDate.getText().toString();

                    if(validate()){
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {

                                createNotes();
                            }
                        });
                    }
                    else {

                    }

                }
            });

    }

    private boolean validate() {
        boolean validation = false;
        if(title.isEmpty() || title.equals("")){
            validation = false;
            binding.etTitle.setError("Please enter title");
        }
        else if(subtitles.isEmpty()){
            subtitles = "empty";
            validation = true;
        }
        else if (notes.isEmpty() || notes.equals("")){
            validation = false;
            binding.etNotes.setError("Please enter note");
        }
        else {
            validation = true;
        }
        return validation;
    }

    private void createNotes() {

        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubTitle = subtitles;
        notes1.notes = notes;
        notes1.notesDate = currentDate;
        notes1.notesPriority = priority;

        viewModel.insertNote(notes1);

        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(UpdateNoteActivity.this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}