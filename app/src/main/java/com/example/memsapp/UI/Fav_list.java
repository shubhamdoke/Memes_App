package com.example.memsapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.memsapp.Adapter.FavAdapter;
import com.example.memsapp.HELPER.Fav_SQLiteHelper;
import com.example.memsapp.MODEL.Fav_Model;
import com.example.memsapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Fav_list extends AppCompatActivity {
RecyclerView rc_view;
ArrayList<Fav_Model>list;
FavAdapter favAdapter;
Fav_Model deletedCourse;
    Fav_SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);
        rc_view=findViewById(R.id.rc_list);

        rc_view.setLayoutManager(new LinearLayoutManager(this));
        db=new Fav_SQLiteHelper(Fav_list.this);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // this method is called
                // when the item is moved.
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                 deletedCourse = list.get(viewHolder.getAdapterPosition());

                // below line is to get the position
                // of the item at that position.
                int position = viewHolder.getAdapterPosition();

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                list.remove(viewHolder.getAdapterPosition());

                // below line is to notify our item is removed from adapter.
                favAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                // below line is to display our snackbar with action.
                Snackbar.make(rc_view, deletedCourse.getJoke(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // adding on click listener to our action of snack bar.
                        // below line is to add our item to array list with a position.
                        list.add(position, deletedCourse);

                        // below line is to notify item is
                        // added to our adapter class.
                        favAdapter.notifyItemInserted(position);
                    }
                }).show();
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(rc_view);







        list=db.readMemes();

        favAdapter = new FavAdapter(this, list);
        rc_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rc_view.setAdapter(favAdapter);


    }
}