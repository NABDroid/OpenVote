package bd.com.nabdroid.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView resultRecyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<Vote> votes;
    private AdapterForResult adapterForResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        init();
        initRecyclerView();
        getResults();
    }

    private void initRecyclerView() {
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(adapterForResult);
    }

    private void getResults() {
        DatabaseReference resultsRef = databaseReference.child("Results");
        resultsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                votes.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Vote vote = data.getValue(Vote.class);
                        votes.add(vote);
                    }

                    adapterForResult.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        resultRecyclerView = findViewById(R.id.resultRecyclerView);
        votes = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        adapterForResult = new AdapterForResult(votes, this);
    }
}
