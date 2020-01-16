package bd.com.nabdroid.openvote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(@NonNull CustomAdapertForHome.ViewHolder holder, int position) {
        String code, topic, creatorName;
        int endTime;
        Vote vote = votes.get(position);
        creatorName = vote.getCreatorId();
        code = vote.getVoteCode();
        topic = vote.getTopic();
        endTime = vote.getEndTime();
        holder.userNameTV.setText(creatorName);
        holder.topicTV.setText(topic);
        holder.titleTV.setText(code);
        holder.lifetimeTV.setText(Integer.toString(endTime));
    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTV, titleTV, topicTV, voteCountTV, lifetimeTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.userNameTVHAUI);
            titleTV = itemView.findViewById(R.id.titleTVHAUI);
            topicTV = itemView.findViewById(R.id.topicTVHAUI);
            lifetimeTV = itemView.findViewById(R.id.lifetimeTVHAUI);
        }
    }
}
