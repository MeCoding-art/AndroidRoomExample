package com.machine.androidroomexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.machine.androidroomexample.R;
import com.machine.androidroomexample.activity.MainActivity;
import com.machine.androidroomexample.activity.UpdateNoteActivity;
import com.machine.androidroomexample.databinding.ItemNotesBinding;
import com.machine.androidroomexample.entity.Notes;
import com.machine.androidroomexample.repository.NotesRepository;
import com.machine.androidroomexample.utilities.AppExecutors;
import com.machine.androidroomexample.viewmodel.NotesViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    Context context;
    List<Notes> notesList;
    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.context = mainActivity;
        this.notesList = notes;
    }


    @NonNull
    @NotNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotesViewHolder holder, int position) {
        Notes notes = notesList.get(position);

        holder.itemView.tvTitle.setText(notes.notesTitle);
        holder.itemView.tvSubTitle.setText(notes.notesSubTitle);
        holder.itemView.tvDate.setText(notes.notesDate);
        holder.itemView.tvNote.setText(notes.notes);
        String priority = notes.notesPriority;

        if(priority.equals("1")){
            holder.itemView.ivPriority.setImageResource(R.drawable.green_oval);
            holder.itemView.cvParent.setCardBackgroundColor(Color.parseColor("#99CC00"));
        }
        if(priority.equals("2")){
            holder.itemView.ivPriority.setImageResource(R.drawable.yellow_oval);
            holder.itemView.cvParent.setCardBackgroundColor(Color.parseColor("#FFEB3B"));

        }
        if(priority.equals("3")){
            holder.itemView.ivPriority.setImageResource(R.drawable.red_oval);
            holder.itemView.cvParent.setCardBackgroundColor(Color.parseColor("#FF4444"));
        }

        holder.itemView.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        NotesViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(NotesViewModel.class);
                        viewModel.deleteNote(notes.id);
                    }
                });
            }
        });

        holder.itemView.cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateNoteActivity.class);
                Gson gson = new Gson();
                String notesObjecttoString = gson.toJson(notes);
                intent.putExtra("NoteObject", notesObjecttoString);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return notesList.size();
    }


       public static class NotesViewHolder extends RecyclerView.ViewHolder{

        private ItemNotesBinding itemView;

        public NotesViewHolder(ItemNotesBinding itemView) {
            super(itemView.getRoot());

            this.itemView = itemView;
        }

    }


}
