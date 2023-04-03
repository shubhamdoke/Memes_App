package com.example.memsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memsapp.MODEL.Fav_Model;
import com.example.memsapp.R;
import com.example.memsapp.UI.MainActivity;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    Context context;
    ArrayList <Fav_Model>list=new ArrayList<>();

    public FavAdapter(Context context, ArrayList<Fav_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_raw,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
        String joke=list.get(position).getJoke();
        String ans=list.get(position).getAns();
        holder.Joke.setText(joke);
        holder.Ans.setText("Ruko Jara Sabar karo ...\n Tap to Karo...");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MainActivity.class);
                intent.putExtra("joke",joke);
                intent.putExtra("ans",ans);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Joke,Ans;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Joke=itemView.findViewById(R.id.joke1);
            Ans=itemView.findViewById(R.id.ans1);
            cardView=itemView.findViewById(R.id.cardview);

        }
    }
}
