package bd.com.nabdroid.openvote;

public class Comment {
    String commentWriter, comment;

    public Comment() {
    }

    public Comment(String commentWriter, String comment) {
        this.commentWriter = commentWriter;
        this.comment = comment;
    }

    public String getCommentWriter() {
        return commentWriter;
    }

    public void setCommentWriter(String commentWriter) {
        this.commentWriter = commentWriter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
