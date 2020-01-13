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

    private EditText titleET, voteTopicET, voteLifetimeET;
    private Button startVoteBTN;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_vote);
        init();
        getCurrentUsersUserName();
        startVoteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleET.getText().toString().trim();
                String voteTopic = voteTopicET.getText().toString().trim();
                int voteLifetime = Integer.parseInt(voteLifetimeET.getText().toString().trim());
                postVote(title, voteTopic, voteLifetime);


            }
        });
    }


    private void postVote(String title, String voteTopic, int voteLifetime) {
        Vote vote = new Vote(title, voteTopic, voteLifetime);
        DatabaseReference userRef = databaseReference.child("Votes").child(title);
        userRef.setValue(vote).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(PostVoteActivity.this, "Posted successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCurrentUsersUserName() {

    }

    private void init() {
        titleET = findViewById(R.id.titleETPV);
        voteTopicET = findViewById(R.id.voteTopicPV);
        voteLifetimeET = findViewById(R.id.voteLifeTimePV);
        startVoteBTN = findViewById(R.id.startVoteBTNPV);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }
}
