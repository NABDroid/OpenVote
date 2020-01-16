package bd.com.nabdroid.openvote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapertForHome extends RecyclerView.Adapter<CustomAdapertForHome.ViewHolder> {

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


        String code, topic, creatorName, comment, agreedVote, disagreedVote;
        final int endTime;

        //collecting form DB
        final Vote vote = votes.get(position);
        creatorName = vote.getCreatorId();
        code = vote.getVoteCode();
        topic = vote.getTopic();
        endTime = vote.getEndTime();

        //showing data to homeActivityUI
        holder.userNameTV.setText(creatorName);
        holder.topicTV.setText(topic);
        holder.titleTV.setText(code);
        holder.lifetimeTV.setText(Integer.toString(endTime));

        //sending data to db

        holder.submitIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int voteId = holder.radioGroup.getCheckedRadioButtonId();
                if (voteId == R.id.agreeRBHAUI){
                    int previousAgreedVote = vote.getYesVote();
                    vote.setYesVote(previousAgreedVote+1);
                }
                else if(voteId == R.id.disagreeRBHAUI){
                    int previousDisgreedVote = vote.getNoVote();
                    vote.setNoVote(previousDisgreedVote+1);
                }
            }
        });
        agreedVote = Integer.toString(vote.getYesVote());

        disagreedVote = Integer.toString(vote.getNoVote());
        holder.voteCountTV.setText(agreedVote+" people agreed with you and "+disagreedVote+" people disagreed with you!");



    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTV, titleTV, topicTV, voteCountTV, lifetimeTV;
        private EditText commentET;
        private ImageView submitIV;
        private RadioGroup radioGroup;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.userNameTVHAUI);
            titleTV = itemView.findViewById(R.id.titleTVHAUI);
            topicTV = itemView.findViewById(R.id.topicTVHAUI);
            lifetimeTV = itemView.findViewById(R.id.lifetimeTVHAUI);
            commentET = itemView.findViewById(R.id.commentETHAUI);
            submitIV = itemView.findViewById(R.id.submitVoteIVHAUI);
            radioGroup = itemView.findViewById(R.id.radioGRPHAUI);
            voteCountTV = itemView.findViewById(R.id.voteCountTVHAUI);
        }
    }
}
