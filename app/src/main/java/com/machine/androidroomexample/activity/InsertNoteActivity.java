package com.machine.androidroomexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.machine.androidroomexample.R;
import com.machine.androidroomexample.databinding.ActivityInsertNoteBinding;
import com.machine.androidroomexample.entity.Notes;
import com.machine.androidroomexample.utilities.AppExecutors;
import com.machine.androidroomexample.viewmodel.NotesViewModel;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class InsertNoteActivity extends AppCompatActivity {

    ActivityInsertNoteBinding binding;
    private String title, subtitles, notes, currentDate;
    NotesViewModel viewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM dd, yyyy", date.getTime());
        binding.tvDate.setText(sequence.toString());
        binding.ivGreen.setImageResource(R.drawable.ok);

        viewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        binding.ivGreen.setOnClickListener(v -> {
            priority = "1";
            binding.ivGreen.setImageResource(R.drawable.ok);
            binding.ivYellow.setImageResource(0);
            binding.ivRed.setImageResource(0);

            //Toast.makeText(this, priority, Toast.LENGTH_SHORT).show();
        });

        binding.ivRed.setOnClickListener(v -> {
            priority = "3";
            binding.ivGreen.setImageResource(0);
            binding.ivRed.setImageResource(R.drawable.ok);
            binding.ivYellow.setImageResource(0);

            //Toast.makeText(this, priority, Toast.LENGTH_SHORT).show();

        });

        binding.ivYellow.setOnClickListener(v -> {
            priority = "2";
            binding.ivGreen.setImageResource(0);
            binding.ivRed.setImageResource(0);
            binding.ivYellow.setImageResource(R.drawable.ok);

            //Toast.makeText(this, priority, Toast.LENGTH_SHORT).show();

        });

        binding.fbInserNote.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(InsertNoteActivity.this, "Note Created Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}