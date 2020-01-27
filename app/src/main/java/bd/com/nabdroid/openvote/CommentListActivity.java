package bd.com.nabdroid.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CommentListActivity extends AppCompatActivity {
    private String postId;
    private ArrayList<Comment> commentList;
    private DatabaseReference databaseReference;
    private ImageView backPressIV;
    private RecyclerView recyclerView;
    private AdapterForComment adapertForComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        init();
        initRecyclerView();

        backPressIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //collect the id of the clicked vote
        getClickedPostId();
        getCommentsFromPostId();



    }

    private void getClickedPostId() {
        if (getIntent().hasExtra("postId")){
            postId = getIntent().getStringExtra("postId");
        }
    }

    private void getCommentsFromPostId() {

        DatabaseReference commentRef = databaseReference.child("Comment").child(postId);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        Comment comment = data.getValue(Comment.class);
                        commentList.add(comment);
                        adapertForComment.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapertForComment);
    }

    private void init() {
        backPressIV = findViewById(R.id.backPressIV);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        commentList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewCommentId);
        adapertForComment = new AdapterForComment(commentList);
    }
}
