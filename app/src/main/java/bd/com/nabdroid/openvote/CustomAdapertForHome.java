package bd.com.nabdroid.openvote;

import android.app.PendingIntent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomAdapertForHome extends RecyclerView.Adapter<CustomAdapertForHome.ViewHolder> {

    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ArrayList<Vote> votes;
    public CustomAdapertForHome(ArrayList<Vote> votes) {
        this.votes = votes;
    }



    //
    @NonNull
    @Override
    public CustomAdapertForHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_element_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapertForHome.ViewHolder holder, int position) {


        final String code, topic, creatorId, creatorName;
        final int endTime, yesVote, noVote;

        //collecting form DB
        final Vote vote = votes.get(position);
        code = vote.getVoteCode();
        topic = vote.getTopic();
        creatorId = vote.getCreatorId();
        creatorName = vote.getCreatorName();
        endTime = vote.getEndTime();
        yesVote = vote.getYesVote();
        noVote = vote.getNoVote();





        //showing data to homeActivityUI
        holder.userNameTV.setText(creatorName);
        holder.topicTV.setText(topic);
        holder.titleTV.setText(code);
        holder.lifetimeTV.setText(Integer.toString(endTime));
        String agreedVote = Integer.toString(vote.getYesVote());
        String disagreedVote = Integer.toString(vote.getNoVote());
        holder.voteCountTV.setText(agreedVote+" people agreed with you and "+disagreedVote+" people disagreed with you!");

        //sending data to db
        holder.submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get selected option from radioGroup
                int voteId = holder.radioGroup.getCheckedRadioButtonId();

                //checking selected option
                if (voteId == R.id.agreeRBHAUI){
                    int newVote = yesVote+1;

                    //upgrade yes vote to database
                    DatabaseReference currentNodeRef = databaseReference.child("Votes").child(creatorId).child("yesVote");
                    currentNodeRef.setValue(newVote).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String agreedVote = Integer.toString(vote.getYesVote());
                            String disagreedVote = Integer.toString(vote.getNoVote());
                            holder.voteCountTV.setText(agreedVote+" people agreed with you and "+disagreedVote+" people disagreed with you!");

                            String commentString = holder.commentET.getText().toString().trim();
                            String currentUserID = firebaseAuth.getCurrentUser().getUid();
                            Comment commentObj = new Comment(currentUserID, commentString);
                            DatabaseReference commentRef = databaseReference.child("Votes").child(creatorId).child("comments").child(currentUserID);
                            commentRef.setValue(commentObj);

                            notifyDataSetChanged();
                            holder.radioGroup.setVisibility(View.GONE);
                            holder.commentET.setVisibility(View.GONE);
                            holder.submitBTN.setVisibility(View.GONE);



                        }
                    });



                }
                else if(voteId == R.id.disagreeRBHAUI){
                    int newVote = noVote+1;

                    //upgrade no vote to database
                    DatabaseReference currentNodeRef = databaseReference.child("Votes").child(creatorId).child("noVote");
                    currentNodeRef.setValue(newVote).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String agreedVote = Integer.toString(vote.getYesVote());
                            String disagreedVote = Integer.toString(vote.getNoVote());
                            holder.voteCountTV.setText(agreedVote+" people agreed with you and "+disagreedVote+" people disagreed with you!");

                            String commentString = holder.commentET.getText().toString().trim();
                            String currentUserID = firebaseAuth.getCurrentUser().getUid();
                            Comment commentObj = new Comment(currentUserID, commentString);
                            DatabaseReference commentRef = databaseReference.child("Votes").child(creatorId).child("comments").child(currentUserID);
                            commentRef.setValue(commentObj);

                            notifyDataSetChanged();
                            holder.radioGroup.setVisibility(View.GONE);
                            holder.commentET.setVisibility(View.GONE);
                            holder.submitBTN.setVisibility(View.GONE);

                        }
                    });
                }

               if (votes!=null && votes.size()>0){
                   votes.clear();
               }


            }
        });


    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTV, titleTV, topicTV, voteCountTV, lifetimeTV;
        private EditText commentET;
        private Button submitBTN;
        private RadioGroup radioGroup;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.userNameTVHAUI);
            titleTV = itemView.findViewById(R.id.titleTVHAUI);
            topicTV = itemView.findViewById(R.id.topicTVHAUI);
            lifetimeTV = itemView.findViewById(R.id.lifetimeTVHAUI);
            commentET = itemView.findViewById(R.id.commentETHAUI);
            submitBTN = itemView.findViewById(R.id.submitVoteBTNHAUI);
            radioGroup = itemView.findViewById(R.id.radioGRPHAUI);
            voteCountTV = itemView.findViewById(R.id.voteCountTVHAUI);
        }
    }
}
