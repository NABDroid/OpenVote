package bd.com.nabdroid.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostVoteActivity extends AppCompatActivity {

    private EditText voteTopicET, voteLifetimeET;
    private Button startVoteBTN;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_vote);
        init();
        startVoteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voteTopic = voteTopicET.getText().toString().trim();
                int voteLifetime = Integer.parseInt(voteLifetimeET.getText().toString().trim());
                postVote(voteTopic, voteLifetime);


            }
        });
    }

    private void postVote(String voteTopic, int voteLifetime) {
        Vote vote = new Vote(voteTopic, voteLifetime);
        DatabaseReference userRef = databaseReference.child("Votes");
        userRef.push().setValue(vote).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(PostVoteActivity.this, "Posted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {
        voteTopicET = findViewById(R.id.voteTopicPV);
        voteLifetimeET = findViewById(R.id.voteLifeTimePV);
        startVoteBTN = findViewById(R.id.startVoteBTNPV);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
