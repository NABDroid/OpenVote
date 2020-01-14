package bd.com.nabdroid.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    //declaration
    private ImageView addVoteIV;
    private RecyclerView activeVoteRecyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<Vote> votes;
    private CustomAdapertForHome customAdapertForHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();  //initialize declared elements
        initRecyclerView();  //initialize recyclerview
        getActiveVotes(); //get data from firebase
        addVoteIV.setOnClickListener(this); //takes to new vote creation page
    }



    //response to button click
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addVoteId) {
            startActivity(new Intent(HomeActivity.this, PostVoteActivity.class));
        }
    }



    //initialize declared elements
    private void init() {
        addVoteIV = findViewById(R.id.addVoteId);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        activeVoteRecyclerView = findViewById(R.id.activeVoteRecyclerViewID);
        votes = new ArrayList<>();
        customAdapertForHome = new CustomAdapertForHome(votes);
    }



    //initialize recyclerview
    private void initRecyclerView() {
        activeVoteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activeVoteRecyclerView.setAdapter(customAdapertForHome);
    }



    //get data from firebase
    private void getActiveVotes() {
        DatabaseReference activVoteRef = databaseReference.child("Votes");

        activVoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        Vote vote = data.getValue(Vote.class);
                        votes.add(vote);
                        customAdapertForHome.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
