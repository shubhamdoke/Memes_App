package com.example.memsapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.memsapp.HELPER.Fav_SQLiteHelper;
import com.example.memsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tv,tap;
    ImageView beforefav,fav,share,favlist;
    String joke="",ans="";
   LinearLayout linearLayout;
    Fav_SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.tv_joke);
        tap=findViewById(R.id.tv_ans);
        beforefav=findViewById(R.id.beforefav);
        fav=findViewById(R.id.fav);
        share=findViewById(R.id.share);
        favlist=findViewById(R.id.list);
        db=new Fav_SQLiteHelper(MainActivity.this);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);


        joke="Ye Gadi Start kr";
        ans="Ruko Jara Sabar karo \n...\nRefresh to maro";
        Intent intent=getIntent();
       String fj= intent.getStringExtra("joke");
       String fa= intent.getStringExtra("ans");

       if(fj==null&&fa==null){
           tv.setText(joke);
           tap.setText("Tap to Laugh");
           tap.setOnClickListener(v -> {
               tap.setText(ans);
           });
       }else {
           tv.setText(fj);
           tap.setText("Tap to Laugh");
           beforefav.setVisibility(View.INVISIBLE);
           fav.setVisibility(View.VISIBLE);
           tap.setOnClickListener(v -> {
               tap.setText(fa); });
       }
        beforefav.setOnClickListener(v -> {
            beforefav.setVisibility(View.INVISIBLE);
            fav.setVisibility(View.VISIBLE);
            db.add(joke,ans);
            Toast.makeText(this, "Added to Fav", Toast.LENGTH_SHORT).show();
        });
        share.setOnClickListener(v -> {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body = joke+ "\n\r"+"" +ans;
            String sub = "Your Subject";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
            myIntent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myIntent, "Share Using"));
        });
        favlist.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,Fav_list.class));
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {

            beforefav.setVisibility(View.VISIBLE);
            fav.setVisibility(View.INVISIBLE);
            getmeme();
            swipeRefreshLayout.setRefreshing(false);

        });


    }

    private void getmeme() {

        String url="https://v2.jokeapi.dev/joke/Any";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject obj=response;

                try {
                    joke = obj.getString("setup");
                    ans = obj.getString("delivery");
                    tv.setText(joke);
                    tap.setText("Tap to Laugh");
                    tap.setOnClickListener(v -> { tap.setText(ans);});


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);



    }



}