package bd.com.nabdroid.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    //declaration
    private ImageView addVoteIV, menuIconIV, groupMenuIV;
    private RecyclerView activeVoteRecyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Vote> votes;
    private AdapertForHome adapertForHome;
    long currentDateMS, currentTimeMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();  //initialize declared elements
        deleteExpiredVotes();
        initRecyclerView();  //initialize recyclerview
        getActiveVotes(); //get data from firebase
        addVoteIV.setOnClickListener(this); //takes to new vote creation page
        menuIconIV.setOnClickListener(this);
    }
//-------------------------------------------------------------------------------------------------------------------
    private void deleteExpiredVotes() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        month = month + 1;
        String dateString = day + "/" + month + "/" + year;
        Date dateForDate = new Date();
        SimpleDateFormat simpleDateFormatForDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateForDate = simpleDateFormatForDate.parse(dateString);
            currentDateMS = dateForDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String timeString = hour + ":" + minute;
        Date dateForTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        try {
            dateForTime = simpleDateFormat.parse(timeString);
            currentTimeMS = dateForTime.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Long totalCurrentTime = currentTimeMS + currentDateMS;
        Toast.makeText(this, "MS: " + totalCurrentTime, Toast.LENGTH_LONG).show();


        DatabaseReference timeRef = databaseReference.child("Times");
        timeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        VoteTimes voteTimes = data.getValue(VoteTimes.class);
                        if (voteTimes.getEndTime() <= totalCurrentTime){
                            data.getRef().removeValue();
                            deleteVote(voteTimes.voteId);

                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void deleteVote(final int voteId) {
        DatabaseReference voteRef = databaseReference.child("Votes");
        voteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Vote vote = data.getValue(Vote.class);
                        if (vote.getVoteCode() == voteId){
                            data.getRef().removeValue();
                            deleteVote(vote.getVoteCode());

                        }
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//-------------------------------------------------------------------------------------------------------------------

    //response to button click
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addVoteId) {
            startActivity(new Intent(HomeActivity.this, PostVoteActivity.class));
        } else if (v.getId() == R.id.menuIconHA) {
            firebaseAuth.signOut();
            startActivity(new Intent(HomeActivity.this, LogInActivity.class));
        }
    }

    //initialize declared elements
    private void init() {
        addVoteIV = findViewById(R.id.addVoteId);
        menuIconIV = findViewById(R.id.menuIconHA);
        groupMenuIV = findViewById(R.id.groupIV);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        activeVoteRecyclerView = findViewById(R.id.activeVoteRecyclerViewID);
        votes = new ArrayList<>();
        adapertForHome = new AdapertForHome(votes, this);
    }


    //initialize recyclerview
    private void initRecyclerView() {
        activeVoteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activeVoteRecyclerView.setAdapter(adapertForHome);
    }


    //get data from firebase
    private void getActiveVotes() {

        DatabaseReference activVoteRef = databaseReference.child("Votes");
        activVoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                votes.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Vote vote = data.getValue(Vote.class);
                        votes.add(vote);
                    }

                    adapertForHome.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
