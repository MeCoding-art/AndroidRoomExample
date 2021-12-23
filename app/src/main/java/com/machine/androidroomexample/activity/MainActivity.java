package com.machine.androidroomexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.machine.androidroomexample.R;
import com.machine.androidroomexample.adapter.NotesAdapter;
import com.machine.androidroomexample.databinding.ActivityMainBinding;
import com.machine.androidroomexample.entity.Notes;
import com.machine.androidroomexample.utilities.AppExecutors;
import com.machine.androidroomexample.viewmodel.NotesViewModel;

public class MainActivity extends AppCompatActivity{
    ActivityMainBinding binding;
    NotesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        viewModel.getAllNotes.observe(this, notes -> {
            //String data = "";
            if(notes.size() > 0){

                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                binding.rvNotes.setLayoutManager(layoutManager);
                binding.rvNotes.setAdapter(new NotesAdapter(MainActivity.this, notes));
                binding.rvNotes.setHasFixedSize(true);
                /*for(Notes notes1 : notes){
                    data = data + "\n " + notes1.notesTitle + "/" + notes1.notesSubTitle + "/" +
                            notes1.notes + "/" + notes1.notesPriority + "/" + notes1.notesDate;
                }*/
            }
            else{
                Toast.makeText(this, "Empty Database", Toast.LENGTH_SHORT).show();
            }


            //Toast.makeText(this, data, Toast.LENGTH_LONG).show();

            /*AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    viewModel.deleteAllNote();
                }
            });*/

        });



        binding.fbAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertNoteActivity.class));
            }
        });

    }

}