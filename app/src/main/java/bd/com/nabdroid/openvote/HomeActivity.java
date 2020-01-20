package bd.com.nabdroid.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    //declaration
    private ImageView addVoteIV, menuIconIV;
    private RecyclerView activeVoteRecyclerView;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
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
        menuIconIV.setOnClickListener(this);
    }



    //response to button click
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addVoteId) {
            startActivity(new Intent(HomeActivity.this, PostVoteActivity.class));
        }

        else if(v.getId()==R.id.menuIconHA){
            firebaseAuth.signOut();
            startActivity(new Intent(HomeActivity.this, LogInActivity.class));
        }
    }

    //initialize declared elements
    private void init() {
        addVoteIV = findViewById(R.id.addVoteId);
        menuIconIV = findViewById(R.id.menuIconHA);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
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
