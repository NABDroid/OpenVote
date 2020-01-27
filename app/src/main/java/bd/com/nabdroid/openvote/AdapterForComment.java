package bd.com.nabdroid.openvote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForComment extends RecyclerView.Adapter<AdapterForComment.ViewHolder> {
    private ArrayList<Comment> commetList;

    public AdapterForComment(ArrayList<Comment> commetList) {
        this.commetList = commetList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment commentObj = commetList.get(position);
        String commentWriterName, comment, commentFor, createTime;
        commentWriterName = commentObj.getCommentWritterName();
        comment = commentObj.getComment();
        commentFor = commentObj.getCommentFor();
        createTime = commentObj.getCreateTime();


        holder.commentTV.setText(comment);
        holder.commentWriterTV.setText(commentWriterName);
        holder.commentForTV.setText(commentFor);
        holder.createTimeTV.setText(createTime);

    }




    @Override
    public int getItemCount() {
        return commetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView commentWriterTV, commentTV, commentForTV, createTimeTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commentWriterTV = itemView.findViewById(R.id.commentWriterCAUITV);
            commentTV = itemView.findViewById(R.id.commentCAUITV);
            commentForTV = itemView.findViewById(R.id.commentForCAUITV);
            createTimeTV = itemView.findViewById(R.id.createTimeCAUITV);
        }
    }
}
