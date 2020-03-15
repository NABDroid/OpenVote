package bd.com.nabdroid.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PostVoteActivity extends AppCompatActivity {

    private EditText voteTopicET;
    private Button startVoteBTN;
    private TextView pickDateTV, pickTimeTV;
    private CheckBox checkBoxForNotification;
    private String voteTopic, creatorName, creatorId, endtimeString;
    private long timeFromDatePicker, timeFromTimePicker;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String timeString, dateString;


    //-------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_vote);
        init();
        getCreatorName();
        pickDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });
        pickTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime();
            }
        });


        startVoteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getDataFromField();
                getUniqueVoteId();
                startActivity(new Intent(PostVoteActivity.this, HomeActivity.class));


            }
        });
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------
    private void getCreatorName() {
        final DatabaseReference userInfoRef = databaseReference.child("UserInfo").child(creatorId);
        userInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                creatorName = dataSnapshot.child("userName").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    //-------------------------------------------------------------------------------------------------------------------------------------------------------
    private void pickTime() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeString = i + ":" + i1;
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                try {
                    date = simpleDateFormat.parse(timeString);
                    timeFromTimePicker = date.getTime();
                    pickTimeTV.setText(date.toString() + "///" + Long.toString(timeFromTimePicker));


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        Calendar mTime = Calendar.getInstance();
        int hour = mTime.get(Calendar.HOUR_OF_DAY);
        int minute = mTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, false);
        timePickerDialog.show();


    }


    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    public void pickDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dateString = dayOfMonth + "/" + month + "/" + year;
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    date = simpleDateFormat.parse(dateString);
                    timeFromDatePicker = date.getTime();
                    pickDateTV.setText(date.toString() + "///" + Long.toString(timeFromDatePicker));


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, year, month, day);
        datePickerDialog.show();

    }


    //-------------------------------------------------------------------------------------------------------------------------------------------------------
    private void getDataFromField() {
        voteTopic = voteTopicET.getText().toString().trim();
    }


    //-------------------------------------------------------------------------------------------------------------------------------------------------------

    private void getUniqueVoteId() {
        final int idForNewVote[] = new int[1];
        final DatabaseReference totalVoteRef = databaseReference.child("TotalVotes");
        totalVoteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int lastVoteNumber = Integer.parseInt(dataSnapshot.getValue().toString());
                idForNewVote[0] = lastVoteNumber + 1;
                totalVoteRef.setValue(idForNewVote[0]);
                Toast.makeText(PostVoteActivity.this, "OnDataChange: " + idForNewVote[0], Toast.LENGTH_SHORT).show();
                postVote(idForNewVote[0]);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PostVoteActivity.this, "Failed to count", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postVote(int i) {
        final long endTime = timeFromDatePicker + timeFromTimePicker;
        final int idForNewVote = i;
        endtimeString = "Ending on: "+dateString+" "+timeString+"";
        Vote vote = new Vote(idForNewVote, voteTopic, creatorId, creatorName, endTime, 0, 0, endtimeString);

        DatabaseReference userRef = databaseReference.child("Votes").child(String.valueOf(idForNewVote));
        userRef.setValue(vote).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(PostVoteActivity.this, "Posted successfully", Toast.LENGTH_SHORT).show();
                DatabaseReference timeRef = databaseReference.child("Times").child(Integer.toString(idForNewVote));
                VoteTime voteTime = new VoteTime(endTime, idForNewVote);
                timeRef.setValue(voteTime);
            }
        });

    }


    //-------------------------------------------------------------------------------------------------------------------------------------------------------
    private void init() {

        voteTopicET = findViewById(R.id.voteTopicPVETID);
        startVoteBTN = findViewById(R.id.startVotePVBTN);
        pickDateTV = findViewById(R.id.pickDatePVTVID);
        pickTimeTV = findViewById(R.id.pickTimePVTVID);
        checkBoxForNotification = findViewById(R.id.checkboxPVID);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        creatorId = firebaseAuth.getCurrentUser().getUid();
    }


//-------------------------------------------------------------------------------------------------------------------------------------------------------
}
